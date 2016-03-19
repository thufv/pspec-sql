package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.RefErrorType;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.RestrictionErrorType;
import edu.thu.ss.spec.lang.pojo.CategoryRef;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.PSpecUtil;

public class RuleResolver extends BaseRuleAnalyzer {

	private static final Logger logger = LoggerFactory.getLogger(RuleResolver.class);

	public RuleResolver(EventTable table) {
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

		PSpecListener listener = new PSpecListener() {
			@Override
			public void onRuleRefError(RefErrorType type, Rule rule, CategoryRef<?> ref, String refid) {
				if (type.equals(RefErrorType.Category_Ref_Not_Exist)) {
					if (ref instanceof UserRef) {
						logger.error("Fail to locate user category: {} referenced in rule: {}.", refid,
								rule.getId());
					} else {
						logger.error("Fail to locate data category: {} referenced in rule: {}.", refid,
								rule.getId());
					}
				}
			}
		};
		table.add(listener);

		boolean error = false;
		error = PSpecUtil.resolveCategoryRef(rule.getUserRef(), userContainer, rule, false, table)
				|| error;
		for (DataRef ref : rule.getDataAssociation().getDataRefs()) {
			error = PSpecUtil.resolveCategoryRef(ref, dataContainer, rule, false, table) || error;
		}
		error = checkAssociation(rule) || error;
		error = resolveRestrictions(rule) || error;

		table.remove(listener);
		return error;
	}

	private boolean checkAssociation(Rule rule) {
		boolean error = false;
		List<DataRef> refs = rule.getDataAssociation().getDataRefs();

		for (int i = 0; i < refs.size(); i++) {
			DataRef ref1 = refs.get(i);
			if (ref1.isError()) {
				continue;
			}
			for (int j = i + 1; j < refs.size(); j++) {
				DataRef ref2 = refs.get(j);
				if (ref2.isError()) {
					continue;
				}
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
			table.onRuleRefError(RefErrorType.Data_Association_Overlap, rule, null, null);
		}
		return error;
	}

	private boolean resolveRestrictions(Rule rule) {
		boolean error = false;

		for (Restriction restriction : rule.getRestrictions()) {
			error = resolveRestriction(restriction, rule) || error;
		}
		return error;
	}

	private boolean resolveRestriction(Restriction res, Rule rule) {
		boolean error = false;
		boolean inclusionError = false;
		DataAssociation association = rule.getDataAssociation();
		for (Desensitization de : res.getDesensitizations()) {
			String refid = de.getDataRefId();
			DataRef ref = association.get(refid);
			if (ref == null) {
				logger.error(
						"Restricted data category: {} must be contained in referenced data association in rule: {}",
						refid, rule.getId());
				table.onRestrictionError(RestrictionErrorType.Associate_Restriction_DataRef_Not_Exist, rule,
						res, refid);
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
			table.onRestrictionError(RestrictionErrorType.Unsupported_Operation, rule, res, null);
		}

		//adjust desensitization
		List<Desensitization> list = new ArrayList<>(rule.getDataAssociation().getDimension());
		for (DataRef ref : association.getDataRefs()) {
			Desensitization de = res.getDesensitization(ref.getRefid());
			if (de == null) {
				de = new Desensitization();
			}
			de.setDataRef(ref);
			list.add(de);
		}

		res.setDesensitizationList(list);

		return error;
	}

	private boolean checkInclusion(DataCategory data, Set<DesensitizeOperation> operations,
			Rule rule) {
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
