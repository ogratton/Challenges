# simple awk

## split record in half:

echo "1234" | awk '{print substr($1, 1, length($1)/2)}'
echo "1234" | awk '{print substr($1, 1+length($1)/2)}'

awk '{ h1=substr($1, 1, length($1)/2); h2=substr($1, 1+length($1)/2); print h1 " " h2 }'

## score

awk -v score="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" '{ s = index(score, "A"); print s }'

cat input.txt | awk -v score="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" '{ h1=substr($1, 1, length($1)/2); h2=substr($1, 1+length($1)/2); split(h1, chars, ""); for (c in chars) { if (index(h2, chars[c]) > 0) { total += index(score, chars[c]); break } } } END { print total }'


# perl!

## turns out awk can't really do bitwise ands properly (removes the trailing zeros) so we're using perl

## bitwise and:
perl -ne '$a = "0101"; $b = "0100"; print ($a & $b)'

## split in half:

cat example | perl -ne 'chomp $_; $h1 = substr($_, 1, length($_)/2 -1); $h2 = substr($_, length($_)/2); print "$h1\t$h2\n"'

## hmm but how to make the masks in the first place?

echo -e "cb" | perl -ne 'BEGIN {$alph = "abc"; $n = length $alph}; chomp $_; @chars = split(//, $_); $bs=""; foreach(@chars) { $first_index = index($alph, $_); $bs1 = "0" x ($first_index) . "1" . "0" x ($n-$first_index-1); $bs = $bs | $bs1 } END { print "$bs\n" }'

## making the mask for each line:

cat example | perl -ne 'BEGIN {$alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; $n = length $alph}; chomp $_; $h1 = substr($_, 1, length($_)/2 -1); $h2 = substr($_, length($_)/2); $full_bs="1" x $n; foreach(($h1, $h2)) { @chars = split(//, $_); $bs=""; foreach(@chars) { $ind = index($alph, $_); $temp_bs = "0" x ($ind) . "1" . "0" x ($n-$ind-1); $bs = $bs | $temp_bs }; $full_bs = $full_bs & $bs }; print "$full_bs\n"'

## given a file with both masks split by a space, calculate the AND

echo -e "101 111" | perl -ne 'chomp $_; @x = split(/ /, $_); $bs = @x[0] & @x[1]; print "$bs\n"'

## scoring:

echo -e "100101 000110" | perl -ne 'chomp $_; @x = split(/ /, $_); $bs = @x[0] & @x[1]; $i=1+index($bs, "1"); print "$i\n"'

## yay!

perl -ne 'BEGIN {$alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; $n = length $alph}; chomp $_; $h1 = substr($_, 0, length($_)/2); $h2 = substr($_, length($_)/2); $full_bs="1" x $n; foreach(($h1, $h2)) { @chars = split(//, $_); $bs=""; foreach(@chars) { $ind = index($alph, $_); $temp_bs = "0" x ($ind) . "1" . "0" x ($n-$ind-1); $bs = $bs | $temp_bs }; $full_bs = $full_bs & $bs }; $total += 1+index($full_bs, "1"); END { print "$total\n" }' example




# python


## part 1

score = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
total = 0 
for l in open("input.txt").readlines(): 
    h1, h2 = l[:len(l)//2], l[len(l)//2:] 
    s1, s2 = set(h1), set(h2) 
    [a] = list(s1 & s2) 
    total += score.index(a) + 1 
print(total)
    
## part 2

total = 0 
for group in zip(*[iter([x.strip() for x in open("input.txt").readlines()])] * 3): 
    sets = [set(g) for g in group] 
    [a] = list(sets[0].intersection(*sets[1:])) 
    total += score.index(a) + 1 
print(total) 
