# privacy
PSpec-SQL is a privacy-integrated big data analytic system that enforcing privacy policies in cloud data.
PSpec-SQL is built upon Spark-SQL(1.1.0), and automatically enforces user-provided PSpec policy during query processing.

language_v2/ contains language parser and policy analyzer for PSpec, an abstract high-level privacy specification language.  
spark-sql/ contains a modified spark project to integrate privacy checking during query processing.  
The modifications mainly focus on spark-catalyst, spark-hive.  
tpc-ds/ contains the tpc-ds benchmark and transformed sql querues suitable for spark-sql to processing. Mainly used for primary evaluation.  

More information will be available soon.


Development with Eclipse  
Prerequisites:  
Eclipse Kepler 4.3  
Eclipse Scala Plugin for Scala 2.10  
Scala 2.10  
sbt  
maven  
ivy  

Steps:  
1. clone project  
2. cd project dir, run "sbt/sbt eclipse -Phive-thriftserver" (this may download a lot of jars...)  
3. discard changes of .classpath files for eclipse projects (with git discard)  
4. import projects into Eclipse, including language v2, spark-core, spark-sql, spark-hive, spark-catalys, spark-network-common, spark-network-shuffle  
5. fix any potential build problems in eclipse...    
