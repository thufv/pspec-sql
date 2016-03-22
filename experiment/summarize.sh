#bin/bash
CLASS="edu.thu.ss.spec.lang.expr.Summarizer"
CLASSPATH="pspec.jar:lib/*"
LIBRARY="-Djava.library.path=lib/"
OPTS="-Xmx2g -Xms1g"
MAIN="java -classpath $CLASSPATH $OPTS $LIBRARY $CLASS"

$MAIN $@

