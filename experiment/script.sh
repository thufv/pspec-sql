#bin/bash
CLASS="edu.thu.ss.spec.lang.expr.Main"
CLASSPATH="pspec.jar:lib/*"
LIBRARY="-Djava.library.path=lib/"
OPTS="-Xmx2g -Xms1g"
MAIN="java -classpath $CLASSPATH $OPTS $LIBRARY $CLASS"

rule=600
maxD=5
maxRes=5
vocab="vocab/expr-30-60-15.xml"

$MAIN "policy" $vocab "output.xml" $rule $maxD $maxRes

