#bin/bash
source expr.sh

output="result/us"

for n in 1 2 3
do
	for us in 0.05 0.1 0.15 0.2 0.25
	do
		$MAIN sql test.sql $N $Ns $us $ss $Nc
		echo "n=$n, us=$us, simple"
		$MAIN expr test.sql "$output/simple-$us-$n" $L false
		echo "n=$n, us=$us, range"
		$MAIN expr test.sql "$output/range-$us-$n" $L true
	done
done

./summarize.sh $output us

