# awk

## part 1

```
awk -v pos=1 -v cycle=0 '
function check_cycle(){
    cycle += 1;
    if ((cycle+20) % 40 == 0) {
        print ">> " cycle ": " pos
        return cycle * pos;
    }
    return 0;
};
/addx/ {
    for (i=0; i<2; i++) {
        answer += check_cycle();
    }
    pos += $2;
};
/noop/ { 
    answer += check_cycle();
}
END {
    print answer
}' example
```

## part 2


```
awk -v pos=2 '
function abs(v) {v += 0; return v < 0 ? -v : v};
function do_cycle(){
    cycle += 1;
    if (abs((cycle - offset) - pos) < 2) {
        rows[cycle] = "##";
    }
    else {
        rows[cycle] = "  ";
    };
    if ((cycle) % 40 == 0) {
        offset += 40;
    };
};
BEGIN { ORS=""; }
/addx/ {
    for (i=0; i<2; i++) {
        do_cycle();
    }
    pos += $2;
};
/noop/ { 
    do_cycle();
}
END {
    for (c=1; c<=240; c++) {
        printf "%s",rows[c]
        if (c % 40 == 0) {
            print "\n";
        }
    }
}' input.txt
```