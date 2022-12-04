# Boring set logic (Python)

## Part 1

```python
score = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
total = 0 
for l in open("input.txt").readlines(): 
    h1, h2 = l[:len(l)//2], l[len(l)//2:] 
    s1, s2 = set(h1), set(h2) 
    [a] = list(s1 & s2) 
    total += score.index(a) + 1 
print(total)
```
    
## Part 2

```python
total = 0 
for group in zip(*[iter([x.strip() for x in open("input.txt").readlines()])] * 3): 
    sets = [set(g) for g in group] 
    [a] = list(sets[0].intersection(*sets[1:])) 
    total += score.index(a) + 1 
print(total)
```


# Boring simple loop (awk)

## Part 1

`awk -v score="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" '{ h1=substr($1, 1, length($1)/2); h2=substr($1, 1+length($1)/2); split(h1, chars, ""); for (c in chars) { if (index(h2, chars[c]) > 0) { total += index(score, chars[c]); break } } } END { print total }' input.txt`

# Perl binary masking!

If we make a binary string of length [a-zA-Z] for each half of the "rucksack" with a 1 in the index for each letter like so:

```
abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
0000000000000000000000000010000000000000000000000000
```

and bitwise AND them, the resulting string will have a single 1 in the index of the shared item ("A" above).

Turns out awk can't really do bitwise ands properly (removes the trailing zeros) so we're using perl.

## Part 1

```perl
perl -ne '
    BEGIN {
        $alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        $n = length $alph
    };
    chomp $_;
    $h1 = substr($_, 0, length($_) / 2);
    $h2 = substr($_, length($_) / 2);
    $full_bs = "1" x $n;
    foreach(($h1, $h2)) {
        $bs="";
        foreach(split( //, $_)) {
            $ind = index($alph, $_);
            $temp_bs = "0" x ($ind) . "1" . "0" x ($n-$ind-1);
            $bs = $bs | $temp_bs 
        };
        $full_bs = $full_bs & $bs
    };
    $total += 1+index($full_bs, "1");
    END {
        print "$total\n"
    }
' input.txt
```

### working:

Bitwise and of two masks:

`perl -e '$a = "0101"; $b = "0100"; print ($a & $b)'`

Split lines in half:

`cat example | perl -ne 'chomp $_; $h1 = substr($_, 0, length($_)/2); $h2 = substr($_, length($_)/2); print "$h1\t$h2\n"'`

But how to make the masks in the first place? it's not going to be efficient

`echo -e "cb" | perl -ne 'BEGIN {$alph = "abc"; $n = length $alph}; chomp $_; @chars = split(//, $_); $bs=""; foreach(@chars) { $first_index = index($alph, $_); $bs1 = "0" x ($first_index) . "1" . "0" x ($n-$first_index-1); $bs = $bs | $bs1 } END { print "$bs\n" }'`

Making the mask for each line:

`cat example | perl -ne 'BEGIN {$alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; $n = length $alph}; chomp $_; $h1 = substr($_, 1, length($_)/2 -1); $h2 = substr($_, length($_)/2); $full_bs="1" x $n; foreach(($h1, $h2)) { @chars = split(//, $_); $bs=""; foreach(@chars) { $ind = index($alph, $_); $temp_bs = "0" x ($ind) . "1" . "0" x ($n-$ind-1); $bs = $bs | $temp_bs }; $full_bs = $full_bs & $bs }; print "$full_bs\n"'`

Given a file with both masks split by a space, calculate the AND

`echo -e "101 111" | perl -ne 'chomp $_; @x = split(/ /, $_); $bs = @x[0] & @x[1]; print "$bs\n"'`

Scoring is just the index of the 1:

`echo -e "100101 000110" | perl -ne 'chomp $_; @x = split(/ /, $_); $bs = @x[0] & @x[1]; $i=1+index($bs, "1"); print "$i\n"'`

All together!

`perl -ne 'BEGIN {$alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; $n = length $alph}; chomp $_; $h1 = substr($_, 0, length($_)/2); $h2 = substr($_, length($_)/2); $full_bs="1" x $n; foreach(($h1, $h2)) {$bs=""; foreach(split(//, $_)) { $ind = index($alph, $_); $temp_bs = "0" x ($ind) . "1" . "0" x ($n-$ind-1); $bs = $bs | $temp_bs }; $full_bs = $full_bs & $bs }; $total += 1+index($full_bs, "1"); END { print "$total\n" }' input.txt`