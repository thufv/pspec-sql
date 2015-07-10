package edu.thu.ss.spec.meta;

import edu.thu.ss.spec.util.PSpecUtil;

public class Column extends DBObject {

  protected BaseType type;

  protected boolean joinable = false;

  protected Integer multiplicity = null;

  public Integer getMultiplicity() {
    return multiplicity;
  }

  public boolean isJoinable() {
    return joinable;
  }

  public void setJoinable(boolean joinable) {
    this.joinable = joinable;
  }

  public void setMultiplicity(Integer multiplicity) {
    this.multiplicity = multiplicity;
  }

  public void setType(BaseType type) {
    this.type = type;
  }

  public BaseType getType() {
    return type;
  }

  public String toString(int l) {
    StringBuilder sb = new StringBuilder();
    sb.append(PSpecUtil.spaces(l));
    sb.append("Column: ");
    sb.append(name);
    sb.append(type.toString(l + 1));
    if (sb.charAt(sb.length() - 1) != '\n') {
      sb.append('\n');
    }
    return sb.toString();
  }
}
