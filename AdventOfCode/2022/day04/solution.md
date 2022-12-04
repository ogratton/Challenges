# perl

# part 1

Could just check if `(x0<=y0 && x1>=y1) || (y0<=x0 && y1>=x1)`.

```bash
perl -ne 'if(/(\d+)\-(\d+),(\d+)\-(\d+)/){ if(($1<=$3 && $2>=$4) || ($3<=$1 && $4>=$2)){ $total += 1 } }; END { print "$total\n" }' input.txt
```

Or could have more fun with binary masks.

```bash
perl -ne '
use Term::ANSIColor qw(:constants);
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

## part 2

just change the condition to:

```perl
    $bs = $bsx & $bsy;
    $total += index($bs, "1") != -1;
```

## visualisation

just prints the mask at each stage

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
END { print "\nanswer: $total\n" }' example
```