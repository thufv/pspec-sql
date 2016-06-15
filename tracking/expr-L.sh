#bin/bash
source expr.sh

output="result/L"

for n in 1 2 3
do
	for L in 5 10 15 20 25
	do
		$MAIN sql test.sql $N $Ns $us $ss $Nc
		echo "n=$n, L=$L, simple"
		$MAIN expr test.sql "$output/simple-$L-$n" $L false
		echo "n=$n, L=$L, range"
		$MAIN expr test.sql "$output/range-$L-$n" $L true
	done
done

./summarize.sh $output L

