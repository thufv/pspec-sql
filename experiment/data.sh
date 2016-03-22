#bin/bash
source script.sh
output="output/data"

for n in 0 1 2
do
	for data in 20 40 60 80 100
	do 
	echo "begin generate policy $n-$data"
	policy="policy/data-$data-$n.xml"
	$MAIN "policy" "vocab/expr-30-$data-15.xml" $policy $rule $maxD $maxRes

	echo "begin redundancy $n-$data"
	$MAIN "redundancy" $policy > "$output/redundancy-$data-$n"
	
	echo "begin approxiamte a-redundancy $n-$data"
	$MAIN "a-redundancy" $policy > "$output/aredundancy-$data-$n"

	echo "begin consistency $n-$data"
	$MAIN "consistency" $policy > "$output/consistency-$data-$n"
	done
done

./summarize.sh $output data 3	