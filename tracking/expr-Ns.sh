#bin/bash
source expr.sh

output="result/Ns"

for n in 1 2 3
do
	for Ns in 1 2 3 4 5
	do
		$MAIN sql test.sql $N $Ns $us $ss $Nc
		echo "n=$n, Ns=$Ns, simple"
		$MAIN expr test.sql "$output/simple-$Ns-$n" $L false
		echo "n=$n, Ns=$Ns, range"
		$MAIN expr test.sql "$output/range-$Ns-$n" $L true
	done
done

./summarize.sh $output Ns

