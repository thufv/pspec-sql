#bin/bash
source expr.sh

output="result/ss"

for n in 1 2 3
do
	for ss in 0.02 0.04 0.06 0.08 0.1
	do
		$MAIN sql test.sql $N $Ns $us $ss $Nc
		echo "n=$n, ss=$ss, simple"
		$MAIN expr test.sql "$output/simple-$ss-$n" $L false
		echo "n=$n, ss=$ss, range"
		$MAIN expr test.sql "$output/range-$ss-$n" $L true
	done
done

./summarize.sh $output ss

