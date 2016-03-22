#bin/bash
source script.sh
output="output/res"

for n in 0 1 2
do
	for res in 1 3 5 7 9
	do 
	echo "begin generate policy $n-$res"
	policy="policy/res-$res-$n.xml"
	$MAIN "policy" $vocab $policy $rule $maxD $res

	echo "begin redundancy $n-$res"
	$MAIN "redundancy" $policy > "$output/redundancy-$res-$n"
	
	echo "begin approxiamte a-redundancy $n-$res"
	$MAIN "a-redundancy" $policy > "$output/aredundancy-$res-$n"

	echo "begin consistency $n-$res"
	$MAIN "consistency" $policy > "$output/consistency-$res-$n"
	done
done

./summarize.sh $output rest 3	
