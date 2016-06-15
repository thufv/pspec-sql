#bin/bash
source expr.sh

output="result/Nc"

for n in 1 2 3
do
	for Nc in 1 2 3 4 5
	do
		$MAIN sql test.sql $N $Ns $us $ss $Nc
		echo "n=$n, Nc=$Nc, simple"
		$MAIN expr test.sql "$output/simple-$Nc-$n" $L false
		echo "n=$n, Nc=$Nc, range"
		$MAIN expr test.sql "$output/range-$Nc-$n" $L true
	done
done

./summarize.sh $output Nc

