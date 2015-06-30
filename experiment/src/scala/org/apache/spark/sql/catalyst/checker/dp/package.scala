package org.apache.spark.sql.catalyst.checker;

import scala.collection.mutable

package object dp {
  type Map[A, B] = mutable.Map[A, B]
  type Set[A] = mutable.Set[A]

  val Map = mutable.Map
  val Set = mutable.Set
}