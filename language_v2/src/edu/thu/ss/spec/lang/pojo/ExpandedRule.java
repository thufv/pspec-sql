package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.SetUtil;

/**
 * an expanded version of {@link Rule}
 * @author luochen
 *
 */
public class ExpandedRule extends DescribedObject implements Comparable<ExpandedRule> {

	/**
	 * the number of expanded rule from {@link #ruleId}
	 */
	protected int num = 1;

	protected Restriction[] restrictions;

	protected List<UserRef> userRefs;

	/**
	 * materialized version of {@link #userRefs}
	 */
	protected Set<UserCategory> users;

	/**
	 * only for single rule
	 */
	protected DataRef dataRef;

	protected List<DataRef> rawDataRefs;

	/**
	 * only for data association
	 */
	protected DataAssociation association;

	public ExpandedRule() {
	}

	private ExpandedRule(Rule rule, int num) {
		this.id = rule.getId();
		this.shortDescription = rule.getShortDescription();
		this.longDescription = rule.getLongDescription();
		this.num = num;
		this.userRefs = rule.getUserRefs();
		this.users = new HashSet<>();
		for (UserRef ref : userRefs) {
			this.users.addAll(ref.getMaterialized());
		}
	}

	public ExpandedRule(Rule rule, DataAssociation association, int num) {
		this(rule, num);
		this.association = association;
		this.restrictions = rule.restrictions.toArray(new Restriction[0]);
	}

	public ExpandedRule(Rule rule, DataRef ref, int num) {
		this(rule, num);
		this.dataRef = ref;
		this.restrictions = new Restriction[1];
		this.restrictions[0] = rule.getRestriction().clone();
		if (!this.restrictions[0].isForbid()) {
			Desensitization de = this.restrictions[0].getDesensitization();
			de.setDataRef(ref);
			de.materialize(ref.getMaterialized());
		}
	}

	@Override
	public Element outputElement(Document document) {
		Element element = super.outputType(document, ParserConstant.Ele_Policy_Rule);
		element.setAttribute(ParserConstant.Attr_Id, getRuleId());

		for (UserRef ref : userRefs) {
			Element refEle = ref.outputElement(document);
			element.appendChild(refEle);
		}

		if (this.dataRef != null) {
			//TODO
			if (this.rawDataRefs != null) {
				for (DataRef ref : rawDataRefs) {
					Element refEle = ref.outputElement(document);
					element.appendChild(refEle);
				}
			} else {
				Element refEle = dataRef.outputElement(document);
				element.appendChild(refEle);
			}

		} else {
			Element assocEle = association.outputElement(document);
			element.appendChild(assocEle);
		}

		for (Restriction res : restrictions) {
			Element resEle = res.outputElement(document);
			element.appendChild(resEle);
		}
		return element;

	}

	@Override
	public int compareTo(ExpandedRule o) {
		return Integer.compare(this.getDimension(), o.getDimension());
	}

	public void setRawDataRefs(List<DataRef> rawDataRefs) {
		this.rawDataRefs = rawDataRefs;
	}

	public List<DataRef> getRawDataRefs() {
		return rawDataRefs;
	}

	public Set<UserCategory> getUsers() {
		return users;
	}

	public String getRuleId() {
		return id + "#" + num;
	}

	public String getRawRuleId() {
		return id;
	}

	public Restriction[] getRestrictions() {
		return restrictions;
	}

	public Restriction getRestriction() {
		return restrictions[0];
	}

	public List<UserRef> getUserRefs() {
		return userRefs;
	}

	public DataAssociation getAssociation() {
		return association;
	}

	public void setAssociation(DataAssociation association) {
		this.association = association;
	}

	public void setRestrictions(Restriction[] restrictions) {
		this.restrictions = restrictions;
	}

	public void setUserRefs(List<UserRef> userRefs) {
		this.userRefs = userRefs;
	}

	public void setDataRef(DataRef dataRef) {
		this.dataRef = dataRef;
	}

	public boolean contains(UserCategory user) {
		for (UserRef ref : userRefs) {
			if (ref.contains(user)) {
				return true;
			}
		}
		return false;
	}

	public DataRef getDataRef() {
		return dataRef;
	}

	public boolean isAssociation() {
		return association != null;
	}

	public boolean isSingle() {
		return association == null;
	}

	public int getDimension() {
		if (dataRef != null) {
			return 1;
		} else {
			return association.getDimension();
		}
	}

	public boolean isGlobal() {
		if (dataRef == null) {
			throw new UnsupportedOperationException("isGlobal() is only supported by single rule.");
		}
		return dataRef.isGlobal();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(getRuleId());

		sb.append("\n\t");
		sb.append("Users: ");
		sb.append(SetUtil.format(users, ","));
		sb.append("\n\t");

		if (dataRef != null) {
			sb.append("Datas: ");
			if (dataRef.isGlobal()) {
				sb.append("global\t");
				sb.append(dataRef.getCategory().getId());
			} else {
				sb.append("local\t");
				sb.append(SetUtil.format(dataRef.getMaterialized(), ","));
			}

		} else {
			sb.append(association);
		}
		sb.append("\n\t");
		for (Restriction restriction : restrictions) {
			sb.append(restriction);
			sb.append("\n\t");
		}
		return sb.toString();
	}
}
