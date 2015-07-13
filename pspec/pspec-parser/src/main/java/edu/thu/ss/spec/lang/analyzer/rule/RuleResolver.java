package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.parser.PSpec.PSpecEventType;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.ParseListener;
import edu.thu.ss.spec.lang.parser.event.PolicyEvent;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ObjectRef;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.PSpecUtil;

public class RuleResolver extends BaseRuleAnalyzer {

	private static final Logger logger = LoggerFactory.getLogger(RuleResolver.class);

	public RuleResolver(EventTable<PolicyEvent> table) {
		super(table);
	}

	@Override
	public boolean stopOnError() {
		return true;
	}

	@Override
	public String errorMsg() {
		return "Error occurred when resolving rules, see error message above.";
	}

	@Override
	public boolean analyzeRule(final Rule rule, UserContainer userContainer,
			DataContainer dataContainer) {

		ParseListener<PolicyEvent> listener = new ParseListener<PolicyEvent>() {
			@Override
			public void handleEvent(PolicyEvent e) {
				assert (e.data.length == 2);
				ObjectRef ref = (ObjectRef) e.data[0];
				if (ref instanceof UserRef) {
					logger.error("Fail to locate user category: {} referenced in rule: {}.", e.data[1],
							rule.getId());
				} else {
					logger.error("Fail to locate data category: {} referenced in rule: {}.", e.data[1],
							rule.getId());
				}
			}
		};
		table.hook(PSpecEventType.Policy_Category_Ref_Not_Exist, listener);

		boolean error = false;
		for (UserRef ref : rule.getUserRefs()) {
			error = error || PSpecUtil.resolveCategoryRef(ref, userContainer, false, table);
		}
		for (DataRef ref : rule.getDataRefs()) {
			error = error || PSpecUtil.resolveCategoryRef(ref, dataContainer, false, table);
		}
		if (!rule.isSingle()) {
			error = error || checkAssociation(rule);
		}
		error = resolveRestrictions(rule);

		table.unhook(PSpecEventType.Policy_Category_Ref_Not_Exist, listener);
		return error;
	}

	private boolean checkAssociation(Rule rule) {
		boolean error = false;
		List<DataRef> refs = rule.getDataRefs();

		for (int i = 0; i < refs.size(); i++) {
			DataRef ref1 = refs.get(i);
			for (int j = i + 1; j < refs.size(); j++) {
				DataRef ref2 = refs.get(j);
				if (PSpecUtil.bottom(ref1.getAction(), ref2.getAction()) != null
						&& PSpecUtil.intersects(ref1.getMaterialized(), ref2.getMaterialized())) {
					logger.error(
							"Overlap of data category: {} and {} detected in data association in rule: {}.",
							ref1.getRefid(), ref2.getRefid(), rule.getId());
					error = true;
				}
			}
		}
		if (error) {
			PolicyEvent event = new PolicyEvent(PSpecEventType.Policy_Data_Association_Overlap, null,
					policy, rule);
			table.sendEvent(event);
		}
		return error;
	}

	private boolean resolveRestrictions(Rule rule) {
		boolean error = false;
		if (rule.isSingle() && rule.getRestrictions().size() > 1) {
			error = true;
			logger.error("Only one restriction element is allowed in rule:{} when rule is single",
					rule.getId());

			PolicyEvent event = new PolicyEvent(PSpecEventType.Policy_Single_One_Restriction, null,
					policy, rule);
			table.sendEvent(event);
			//fix
			Restriction res = rule.getRestriction();
			rule.getRestrictions().clear();
			rule.getRestrictions().add(res);
		}

		//check forbid
		for (Restriction restriction : rule.getRestrictions()) {
			if (restriction.isForbid() && rule.getRestrictions().size() > 1) {
				error = true;
				logger.error("Only one restriction element is allowed in rule:{} when forbidden",
						rule.getId());
				PolicyEvent event = new PolicyEvent(PSpecEventType.Policy_Restriction_One_Forbid, null,
						policy, rule);
				table.sendEvent(event);
				//fix
				rule.getRestrictions().clear();
				rule.getRestrictions().add(restriction);
				return error;
			}
		}

		for (Restriction restriction : rule.getRestrictions()) {
			if (restriction.isForbid()) {
				continue;
			}
			if (rule.isSingle()) {
				error = error || resolveSingleRestriction(restriction, rule);
			} else {
				error = error || resolveAssociateRestriction(restriction, rule);
			}
		}
		return error;
	}

	private boolean resolveSingleRestriction(Restriction res, Rule rule) {
		boolean error = false;
		if (res.getDesensitizations().size() > 1) {
			logger.error("Only 1 desensitization is allowed for non-associate rule: {}", rule.getId());
			PolicyEvent event = new PolicyEvent(PSpecEventType.Policy_Single_Restriction_One_Desensitize,
					null, null, rule);
			table.sendEvent(event);
			//fix
			Desensitization de = res.getDesensitization(0);
			res.getDesensitizations().clear();
			res.getDesensitizations().add(de);
		}
		Desensitization de = res.getDesensitization(0);
		if (!de.getDataRefId().isEmpty()) {
			logger
					.error("No data-category-ref element should appear in desensitize element when only data category is referenced in rule: "
							+ rule.getId());
			PolicyEvent event = new PolicyEvent(PSpecEventType.Policy_Single_Restriction_No_DataRef,
					null, null, rule, res);
			table.sendEvent(event);
			//fix
			de.setDataRefId("");
			error = true;
		}
		boolean inclusionError = false;
		for (DataRef ref : rule.getDataRefs()) {
			if (checkInclusion(ref.getData(), de.getOperations(), rule)) {
				inclusionError = true;
				error = true;
			}
		}
		if (inclusionError) {
			PolicyEvent event = new PolicyEvent(PSpecEventType.Policy_Restriction_Unsupported_Operation,
					null, null, rule, res);
			table.sendEvent(event);
		}
		return error;
	}

	private boolean resolveAssociateRestriction(Restriction res, Rule rule) {
		boolean error = false;
		boolean inclusionError = false;
		DataAssociation association = rule.getAssociation();
		for (Desensitization de : res.getDesensitizations()) {
			if (de.getDataRefId().isEmpty()) {
				logger
						.error("Restricted data category must be specified explicitly when data association is referenced by rule: "
								+ rule.getId());
				PolicyEvent event = new PolicyEvent(
						PSpecEventType.Policy_Associate_Restriction_Explicit_DataRef, null, policy, rule, res);
				table.sendEvent(event);
				error = true;
				continue;
			}
			String refid = de.getDataRefId();
			DataRef ref = association.get(refid);
			if (ref == null) {
				logger
						.error(
								"Restricted data category: {} must be contained in referenced data association in rule: {}",
								refid, rule.getId());
				PolicyEvent event = new PolicyEvent(
						PSpecEventType.Policy_Associate_Restriction_DataRef_Not_Exist, null, policy, rule, res);
				table.sendEvent(event);
				error = true;
				continue;
			}
			de.setDataRef(ref);
			if (checkInclusion(ref.getCategory(), de.getOperations(), rule)) {
				inclusionError = true;
				error = true;
			}
		}
		if (inclusionError) {
			PolicyEvent event = new PolicyEvent(PSpecEventType.Policy_Restriction_Unsupported_Operation,
					null, null, rule, res);
			table.sendEvent(event);
		}

		//adjust desensitization
		List<Desensitization> list = new ArrayList<>(rule.getAssociation().getDimension());
		for (DataRef ref : association.getDataRefs()) {
			Desensitization de = res.getDesensitization(ref.getRefid());
			if (de == null) {
				de = new Desensitization();
			}
			de.setDataRef(ref);
			de.materialize();
			list.add(de);
		}

		res.setDesensitizationList(list);

		return error;
	}

	private boolean checkInclusion(DataCategory data, Set<DesensitizeOperation> operations, Rule rule) {
		if (operations == null || operations.isEmpty()) {
			return false;
		}
		boolean error = false;

		Iterator<DesensitizeOperation> it = operations.iterator();
		while (it.hasNext()) {
			DesensitizeOperation op = it.next();
			if (!data.support(op)) {
				logger.error("Desensitize operation: {} is not supported by data category: {} in rule: {}",
						op.getName(), data.getId(), rule.getId());
				error = true;
				//fix
				it.remove();
			}
		}
		return error;

	}

}
