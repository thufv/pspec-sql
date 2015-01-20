# privacy
PSpec-SQL is a privacy-integrated big data analytic system that enforcing privacy policies in cloud data.
PSpec-SQL is built upon Spark-SQL(1.1.0), and automatically enforces user-provided PSpec policy during query processing.

language_v2/ contains language parser and policy analyzer for PSpec, an abstract high-level privacy specification language.  
spark-sql/ contains a modified spark project to integrate privacy checking during query processing.  
The modifications mainly focus on spark-catalyst, spark-hive.  
tpc-ds/ contains the tpc-ds benchmark and transformed sql querues suitable for spark-sql to processing. Mainly used for primary evaluation.  

More information will be available soon.
