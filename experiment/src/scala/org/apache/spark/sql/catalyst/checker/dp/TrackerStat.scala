package org.apache.spark.sql.catalyst.checker.dp

import java.io.PrintStream
import java.io.FileOutputStream
import scala.collection.mutable.HashMap
import edu.thu.ss.experiment.ExperimentConf
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil
import scala.collection.mutable.LinkedHashMap

object TrackerStat {
  private var _stat = new TrackerStat;

  def reset() = _stat = new TrackerStat;

  def get = _stat;

  val Track_Time = "track_time";

  val SMT_Time = "smt_time";

  val Z3_Time = "z3_time";

  val Range_Time = "range_time";

  val Checked_Partitions = "checked_partitions";

  val Processed_Partitions = "processed_partitions";
}

case class Record(var count: Int, var value: Double, var min: Double = Double.MaxValue, var max: Double = Double.MinValue) {

}

class TrackerStat private () {
  var rangeHit = 0;

  var queryNum = 0;

  var partitionNum = 0;

  var droppedPartition = 0;

  val maxTrackTime = System.getProperty("max.track.time", ExperimentConf.Max_Track_Time).toInt;

  val records = new LinkedHashMap[String, Record];

  val lastTime = new HashMap[String, Long];

  def beginTiming(key: String) {
    val time = System.currentTimeMillis();
    lastTime.put(key, time);
  }

  def endTiming(key: String) {
    val time = System.currentTimeMillis() - lastTime.getOrElse(key, 0L);

    if (key == TrackerStat.SMT_Time || key == TrackerStat.Track_Time) {
      if (time > maxTrackTime) {
        return ;
      }
    }

    profile(key, time);
  }

  def profile(key: String, value: Double) {
    val previous = records.getOrElseUpdate(key, new Record(0, 0));
    previous.count += 1;
    previous.value += value;
    if (value < previous.min) {
      previous.min = value;
    }
    if (value > previous.max) {
      previous.max = value;
    }
  }

  def show(writer: PrintStream) {
    writer.println(s"###queries:$queryNum");
    writer.println(s"###partitions:$partitionNum");
    writer.println(s"###droppedPartitions:$droppedPartition");
    writer.println(s"###range-hits:$rangeHit");
    writer.println(s"###smt-time:${CheckerUtil.format(getRecordAverage(TrackerStat.SMT_Time), 2)}")
    writer.println(s"###range-time:${CheckerUtil.format(getRecordAverage(TrackerStat.Range_Time), 2)}")
    writer.println(s"###track-time:${CheckerUtil.format(getRecordAverage(TrackerStat.Track_Time), 2)}")

    writer.println();

    records.foreach(entry => {
      val key = entry._1;
      val record = entry._2;
      writer.print(key);
      writer.print("\tcount:" + record.count);
      writer.print(", value:" + CheckerUtil.format(record.value / record.count, 2));
      writer.print(", min:" + record.min);
      writer.print(", max:" + record.max);
      writer.println();
    })

    writer.println();
  }

  def getRecordAverage(key: String): Double = {
    val record = records.getOrElse(key, null);
    if (record != null) {
      return record.value / record.count;
    } else {
      return 0.0;
    }
  }

  def show() {
    show(System.out);
  }

  def show(path: String) {
    val writer = new PrintStream(new FileOutputStream(path));

    show(writer);

    writer.flush();
    writer.close();
  }

  private def average(time: Long, num: Int): Long = {
    if (num == 0) {
      return 0;
    } else {
      return time / num;
    }
  }

}