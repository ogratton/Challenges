# Simple logical checks

## perl

```bash
perl -ne 'if(/(\d+)\-(\d+),(\d+)\-(\d+)/){ if(($1<=$3 && $2>=$4) || ($3<=$1 && $4>=$2)){ $total += 1 } }; END { print "$total\n" }' input.txt

perl -ne 'if(/(\d+)\-(\d+),(\d+)\-(\d+)/){ if(($3 <= $2 && $1 <= $4)){ $total += 1 } }; END { print "$total\n" }' input.txt
```

## awk

Almost a direct translation from the perl solution. I would have done this first, but I didn't know the FS trick:

```bash
awk -F ",|-" '($1<=$3 && $2>=$4) || ($3<=$1 && $4>=$2) { total++ } END { print total }' input.txt

awk -F ",|-" '($3 <= $2 && $1 <= $4) { total++ } END { print total }' input.txt
```

# Perl binary masking

Part 1:

```bash
perl -ne '
if(/(\d+)\-(\d+),(\d+)\-(\d+)/){
    $x0 = $1; $x1 = $2; $y0 = $3; $y1 = $4;
    $min = ($x0, $y0)[$x0 > $y0]; $max = ($x1, $y1)[$x1 < $y1];

    $bsx = "0" x ($x0-$min) . "1" x (1+$x1-$x0) . "0" x ($max-$x1);
    $bsy = "0" x ($y0-$min) . "1" x (1+$y1-$y0) . "0" x ($max-$y1);

    $bs = $bsx | $bsy;
    $total += $bs eq $bsx || $bs eq $bsy;
};
END { print "\nanswer: $total\n" }' input.txt
```

For part 2, just change the condition to:

```perl
    $bs = $bsx & $bsy;
    $total += index($bs, "1") != -1;
```

## Visualisation

Part 1 again but printing the bitstring each time in green or red:

```bash
perl -ne '
use Term::ANSIColor qw(:constants);
if(/(\d+)\-(\d+),(\d+)\-(\d+)/){
    $x0 = $1; $x1 = $2;
    $y0 = $3; $y1 = $4;

    $min = ($x0, $y0)[$x0 > $y0];
    $max = ($x1, $y1)[$x1 < $y1];

    $bsx = "0" x ($x0-$min) . "1" x (1+$x1-$x0) . "0" x ($max-$x1);
    $bsy = "0" x ($y0-$min) . "1" x (1+$y1-$y0) . "0" x ($max-$y1);

    $bs = $bsx | $bsy;
    $total += $bs eq $bsx || $bs eq $bsy;

    # for human readable printing
    $bsx =~ s/0/\./g;
    $bsy =~ s/0/\./g;
    $bs =~  s/0/\./g;

    print "\n$_$bsx\n$bsy\n";
    $bs eq $bsx || $bs eq $bsy ? print GREEN : print RED;
    print "$bs\n", RESET
};
END { print "\nanswer: $total\n" }' input.txt
```

# PSQL range functions

Assuming a table:

```sql
create table aoc_day4 (x0 int, x1 int, y0 int, y1 int);
```

populated with the input data:

```bash
cat input.txt | tr '-' ',' | psql -d postgres -c "\copy aoc_day4 from stdin with csv"
```

Do:

```sql
select count(*) filter (where (xr @> yr) or (xr <@ yr)) as part_1, count(*) filter (where xr && yr) as part_2 from (
    select int4range(x0, x1, '[]') as xr, int4range(y0, y1, '[]') as yr from aoc_day4
) ranges;
```
