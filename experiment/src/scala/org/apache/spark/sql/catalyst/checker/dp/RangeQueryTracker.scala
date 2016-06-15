package org.apache.spark.sql.catalyst.checker.dp

import com.microsoft.z3.Context
import org.apache.spark.sql.catalyst.structure.RedBlackBST
import scala.collection.mutable.HashSet
import org.apache.spark.sql.catalyst.structure.RedBlackBST.Node
import scala.collection.mutable.HashMap
import org.apache.spark.sql.catalyst.plans.logical.Aggregate

private case class ValueWrapper(val value: Double) extends Comparable[ValueWrapper] {
  def compareTo(other: ValueWrapper): Int = {
    return java.lang.Double.compare(this.value, other.value);
  }
}

private abstract class PartitionIndex[T <: DPPartition](val column: String) {

  type RangeType <: Range

  def addPartition(partition: T);

  def removePartition(partition: T);

  def lookupPartition(range: RangeType): T;

}

private class NumericalIndex[T <: DPPartition](column: String)
    extends PartitionIndex[T](column) {

  type RangeType = NumericalRange

  private val checked: Set[Int] = new HashSet

  private val startTree = new RedBlackBST[ValueWrapper, Set[T]];
  private val endTree = new RedBlackBST[ValueWrapper, Set[T]];

  def addPartition(partition: T) {
    val range = partition.getRanges.getOrElse(column, null).asInstanceOf[NumericalRange];
    if (range != null) {
      range.intervals.foreach(addPartition(_, partition));
    }
  }

  def checkPartitions(range: Range, partitions: Set[T]): T = {
    partitions.foreach(p => {
      var success = !checked.contains(p.getId) && p.disjoint(column, range);
      checked.add(p.getId);
      if (success) {
        return p;
      }
    });
    return null.asInstanceOf[T];
  }

  private def addPartition(interval: Interval, partition: T) {
    def add(point: Double, partition: T, tree: RedBlackBST[ValueWrapper, Set[T]]) {
      val set = tree.get(ValueWrapper(point));
      if (set != null) {
        set.add(partition);
      } else {
        val set = new HashSet[T];
        set.add(partition);
        tree.put(ValueWrapper(point), set);
      }
    }

    add(interval.start, partition, startTree);
    add(interval.end, partition, endTree);
  }

  def removePartition(partition: T) {
    val range = partition.getRanges.getOrElse(column, null).asInstanceOf[NumericalRange];
    range.intervals.foreach(removePartition(_, partition));
  }

  private def removePartition(interval: Interval, partition: T) {
    def remove(point: Double, partition: T, tree: RedBlackBST[ValueWrapper, Set[T]]) {
      val set = tree.get(ValueWrapper(point));
      if (set == null) {
        println("error");
      }
      assert(set != null);
      set.remove(partition);
      if (set.size == 0) {
        tree.delete(ValueWrapper(point));
      }
    }
    remove(interval.start, partition, startTree);
    remove(interval.end, partition, endTree);
  }

  def lookupPartition(range: NumericalRange): T = {
    checked.clear();

    range.intervals.foreach(interval => {
      val start = interval.start;
      val end = interval.end;
      val left = lookupByStart(end, range, startTree.root);
      if (left != null) {
        return left;
      }
      val right = lookupByEnd(start, range, endTree.root);
      if (right != null) {
        return right;
      }
    });
    return null.asInstanceOf[T];
  }

  /**
   * lookup by the start tree, only consider nodes > point
   */
  private def lookupByStart(point: Double, range: NumericalRange, node: Node[ValueWrapper, Set[T]]): T = {
    if (node == null) {
      return null.asInstanceOf[T];
    }
    val start = node.key;
    if (start.value > point) {
      val current = checkPartitions(range, node.value);
      if (current != null) {
        return current;
      }
      val t = lookupByStart(point, range, node.left);
      if (t != null) {
        return t;
      } else {
        return lookupByStart(point, range, node.right);
      }
    } else {
      return lookupByStart(point, range, node.right);
    }
  }

  private def lookupByEnd(point: Double, range: NumericalRange, node: Node[ValueWrapper, Set[T]]): T = {
    if (node == null) {
      return null.asInstanceOf[T];
    }
    val end = node.key;
    if (end.value < point) {
      val current = checkPartitions(range, node.value);
      if (current != null) {
        return current;
      }
      val t = lookupByEnd(point, range, node.right);
      if (t != null) {
        return t;
      } else {
        return lookupByEnd(point, range, node.left);
      }
    } else {
      return lookupByEnd(point, range, node.left);
    }
  }

}

private class CategoricalIndex[T <: DPPartition](column: String) extends PartitionIndex[T](column) {
  type RangeType = CategoricalRange

  private val partitions = new HashSet[T];

  def addPartition(partition: T) {
    partitions.add(partition);
  }

  def removePartition(partition: T) {
    partitions.remove(partition);
  }

  def lookupPartition(range: CategoricalRange): T = {
    partitions.foreach {
      p =>
        if (p.disjoint(column, range)) {
          return p;
        }
    }
    return null.asInstanceOf[T];
  }

}

class RangeQueryTracker[T <: DPPartition](budget: DPBudgetManager, limit: Int, partitionBuilder: Context => T)
    extends DPQueryTracker[T](budget, limit, partitionBuilder) {

  private val partitionIndex = new HashMap[String, PartitionIndex[T]];

  override def locatePartition(query: DPQuery) {
    stat.beginTiming(TrackerStat.Range_Time);

    val success = locatePartitionByRange(query);

    stat.endTiming(TrackerStat.Range_Time);

    if (success) {
      stat.rangeHit += 1;
      return ;
    }

    locatePartitionBySMT(query);
  }

  protected def resolveRange(plan: Aggregate): Map[String, Range] = {
    val resolver = new RangeResolver;
    return resolver.resolve(plan);
  }

  protected override def updatePartition(partition: T, query: DPQuery) {
    partition.getRanges.keys.foreach(column => {
      val index = partitionIndex.getOrElse(column, null);
      if (index != null) {
        index.removePartition(partition);
      }
    });

    super.updatePartition(partition, query);

    partition.getRanges.keys.foreach(column => {
      val index = partitionIndex.getOrElse(column, null);
      if (index != null) {
        index.addPartition(partition);
      }
    });
  }

  protected override def createPartition(query: DPQuery): T = {
    val partition = super.createPartition(query);
    //create index
    partition.getRanges.foreach(t => {
      val column = t._1;
      val range = t._2;
      val index = partitionIndex.getOrElseUpdate(column, range match {
        case num: NumericalRange => new NumericalIndex[T](column);
        case cat: CategoricalRange => new CategoricalIndex[T](column);
      });
      index.addPartition(partition);
    });

    return partition;
  }

  protected override def removePartition(partition: T) {
    super.removePartition(partition);
    partition.getRanges.keys.foreach(column => {
      val index = partitionIndex.getOrElse(column, null);
      if (index != null) {
        index.removePartition(partition);
      }
    });
  }

  /**
   * lookup the index to commit the query
   */
  private def locatePartitionByRange(query: DPQuery): Boolean = {
    query.ranges = resolveRange(query.plan);
    val ranges = query.ranges;
    ranges.foreach(t => {
      val column = t._1;
      val range = t._2;
      val index = partitionIndex.getOrElse(column, null);
      if (index != null) {
        val partition = index.lookupPartition(range.asInstanceOf[index.RangeType]);
        if (partition != null) {
          updatePartition(partition, query);
          return true;
        }
      }
    });
    return false;
  }

}