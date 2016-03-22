#bin/bash
source script.sh
output="output/dim"

for n in 0 1 2
do
	for dim in 1 3 5 7 9
	do 
	echo "begin generate policy $n-$dim"
	policy="policy/dim-$dim-$n.xml"
	$MAIN "policy" $vocab $policy $rule $dim $maxRes

	echo "begin redundancy $n-$dim"
	$MAIN "redundancy" $policy > "$output/redundancy-$dim-$n"
	
	echo "begin approxiamte a-redundancy $n-$dim"
	$MAIN "a-redundancy" $policy > "$output/aredundancy-$dim-$n"

	echo "begin consistency $n-$dim"
	$MAIN "consistency" $policy > "$output/consistency-$dim-$n"
	done
done

./summarize.sh $output dim 3	