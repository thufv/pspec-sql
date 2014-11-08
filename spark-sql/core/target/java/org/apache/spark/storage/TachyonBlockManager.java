package org.apache.spark.storage;
/**
 * Creates and maintains the logical mapping between logical blocks and tachyon fs locations. By
 * default, one block is mapped to one file with a name given by its BlockId.
 * <p>
 * @param rootDirs The directories to use for storing block files. Data will be hashed among these.
 */
private  class TachyonBlockManager implements org.apache.spark.Logging {
  public  java.lang.String master () { throw new RuntimeException(); }
  // not preceding
  public   TachyonBlockManager (org.apache.spark.storage.ShuffleBlockManager shuffleManager, java.lang.String rootDirs, java.lang.String master) { throw new RuntimeException(); }
  public  tachyon.client.TachyonFS client () { throw new RuntimeException(); }
  private  int MAX_DIR_CREATION_ATTEMPTS () { throw new RuntimeException(); }
  private  int subDirsPerTachyonDir () { throw new RuntimeException(); }
  private  tachyon.client.TachyonFile[] tachyonDirs () { throw new RuntimeException(); }
  private  tachyon.client.TachyonFile[][] subDirs () { throw new RuntimeException(); }
  public  boolean removeFile (tachyon.client.TachyonFile file) { throw new RuntimeException(); }
  public  boolean fileExists (tachyon.client.TachyonFile file) { throw new RuntimeException(); }
  public  tachyon.client.TachyonFile getFile (java.lang.String filename) { throw new RuntimeException(); }
  public  tachyon.client.TachyonFile getFile (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  private  tachyon.client.TachyonFile[] createTachyonDirs () { throw new RuntimeException(); }
  private  void addShutdownHook () { throw new RuntimeException(); }
}
