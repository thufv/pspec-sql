package org.apache.spark.sql.catalyst.trees;
/** Used by {@link TreeNode.getNodeNumbered} when traversing the tree for a given number */
private  class MutableInt {
  public  int i () { throw new RuntimeException(); }
  // not preceding
  public   MutableInt (int i) { throw new RuntimeException(); }
}
