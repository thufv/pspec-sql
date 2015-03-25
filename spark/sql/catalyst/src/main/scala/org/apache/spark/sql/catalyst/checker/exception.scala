package org.apache.spark.sql.catalyst.checker

case class UnsupportedPlanException(val message: String) extends Exception(message) {
}

case class PrivacyException(val message: String) extends Exception(message) {

}