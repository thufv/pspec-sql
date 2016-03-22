#bin/bash
source script.sh
output="output/op"

for n in 0 1 2
do
	for op in 5 10 15 20 25
	do 
	echo "begin generate policy $n-$op"
	policy="policy/op-$op-$n.xml"
	$MAIN "policy" "vocab/expr-30-60-$op.xml" $policy $rule $maxD $maxRes

	echo "begin redundancy $n-$op"
	$MAIN "redundancy" $policy > "$output/redundancy-$op-$n"
	
	echo "begin approxiamte a-redundancy $n-$op"
	$MAIN "a-redundancy" $policy > "$output/aredundancy-$op-$n"

	echo "begin consistency $n-$op"
	$MAIN "consistency" $policy > "$output/consistency-$op-$n"
	done
done

./summarize.sh $output op 3	