#bin/bash
source script.sh
output="output/user"

for n in 0 1 2
do
	for user in 10 20 30 40 50
	do 
	echo "begin generate policy $n-$user"
	policy="policy/user-$user-$n.xml"
	$MAIN "policy" "vocab/expr-$user-60-15.xml" $policy $rule $maxD $maxRes

	echo "begin redundancy $n-$user"
	$MAIN "redundancy" $policy > "$output/redundancy-$user-$n"
	
	echo "begin approxiamte a-redundancy $n-$user"
	$MAIN "a-redundancy" $policy > "$output/aredundancy-$user-$n"

	echo "begin consistency $n-$user"
	$MAIN "consistency" $policy > "$output/consistency-$user-$n"
	done
done

./summarize.sh $output user 3	