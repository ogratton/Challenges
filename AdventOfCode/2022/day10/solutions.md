# awk

## part 1

We can take advantage of the fact that a `noop` command, which takes one cycle, is one word long, and `addx X`, which takes two cycles, is two words long:

```
awk -v pos=1 '
{
    for (i=0; i<NF; i++) {
        cycle += 1;
        if ((cycle+20) % 40 == 0)
            answer += cycle * pos;
    }
    pos += $2;
};
END { print answer; }' example
```

Exactly the same thing but without newlines because I like one-liners:

`awk -v pos=1 '{for (i=0; i<NF; i++) {cycle += 1; if ((cycle+20) % 40 == 0) answer += cycle * pos;} pos += $2; }; END { print answer; }' example`

## part 2

```
awk -v pos=2 '
function abs(v) {v += 0; return v < 0 ? -v : v};
BEGIN { ORS=""; }
{
    for (i=0; i<NF; i++) {
        cycle++;
        if (abs((cycle - offset) - pos) < 2)
            printf "%s", "##";
        else
            printf "%s", "  ";
        if ((cycle) % 40 == 0) {
            offset += 40;
            print "\n";
        };
    }
    pos += $2;
}' input.txt
```

Prints:

```
######    ##        ########  ########  ########  ##          ####    #######
##    ##  ##        ##              ##  ##        ##        ##    ##  ##
##    ##  ##        ######        ##    ######    ##        ##        ######
######    ##        ##          ##      ##        ##        ##  ####  ##
##  ##    ##        ##        ##        ##        ##        ##    ##  ##
##    ##  ########  ########  ########  ##        ########    ######  ########
```
