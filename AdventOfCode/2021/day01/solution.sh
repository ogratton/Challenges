inc_count(){
	perl -ne 'if (/(\d+)/){$current=$1; if(defined($prev) && $current > $prev){$total += 1}; $prev = $current} END{print "Part '$1': $total\n"}'
}

<input.txt inc_count 1
paste -d+ input.txt <(tail input.txt -n+2) <(tail input.txt -n+3) | head -n-2 | bc | inc_count 2
