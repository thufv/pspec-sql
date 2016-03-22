#bin/bash
source script.sh
output="output/rule"

for i in 0 1 2
do
	for rule in 200 400 600 800 1000
	do 
	echo "begin generate policy $i-$rule"
	policy="policy/rule-$rule-$i.xml"
	
	$MAIN "policy" $vocab $policy $rule $maxD $maxRes

	echo "begin redundancy $i-$rule"
	$MAIN "redundancy" $policy > "$output/redundancy-$rule-$i"
	
	echo "begin approxiamte a-redundancy $i-$rule"
	$MAIN "a-redundancy" $policy > "$output/aredundancy-$rule-$i"

	echo "begin consistency $i-$rule"
	$MAIN "consistency" $policy > "$output/consistency-$rule-$i"
	done
done

./summarize.sh $output rule 3	