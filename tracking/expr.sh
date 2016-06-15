#bin/bash
CLASS="edu.thu.ss.experiment.Experimenter"
CLASSPATH="expr.jar"
SCALA="$SCALA_HOME/lib/scala-library.jar:$SCALA_HOME/lib/scala-reflect.jar"
LIBPATH="lib/*"
RESPATH="res/*"
OPTS="-Xmx4g -Xms1g"
MAIN="java -classpath $CLASSPATH:$SCALA:$LIBPATH:$RESPATH $OPTS $CLASS"

N=1000
Ns=3
us=0.1
ss=0.04
Nc=2
L=10
