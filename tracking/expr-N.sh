#bin/bash
source expr.sh

output="result/N"

for n in 1 2 3
do
	for N in 500 1000 1500 2000 2500
	do
		$MAIN sql test.sql $N $Ns $us $ss $Nc
		echo "n=$n, N=$N, simple"
		$MAIN expr test.sql "$output/simple-$N-$n" $L false
		echo "n=$n, N=$N, range"
		$MAIN expr test.sql "$output/range-$N-$n" $L true
	done
done

./summarize.sh $output N

