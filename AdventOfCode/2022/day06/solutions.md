# python

## part 1

```python
with open("input.txt") as f:
    string = f.read().strip()

n = 4

for i in range(len(string) - n):
    substr = string[i:i+n]
    if len(set(substr)) == n:
        print(i+n)
    break
```

## part 2

just change 4 to 14

# awk

## part 1

```bash
awk -v window=4 'BEGIN {FS=""} { for(i=1; i<NF; i+=1) { for(n=i; n-i<window; n++) {if (a[$n] == 1) {skip=1; break}; a[$n]++;}; if (skip == 0) {print "answer: " i-1+window; exit}; delete a; skip=0 }}' input.txt
```

## part 2

set window=14
