# awk/bash

## part 1:

`awk -v RS= '{for(i=1;i<=NF;i++) t+=$i; if(t>maxval) maxval=t; t=0} END {print maxval;}' day1.input`

### part 2

`awk -v RS= '{for(i=1;i<=NF;i++) t+=$i; print t; t=0}' day1.input | sort -r | head -3 | paste -sd+ - | bc`

# python

## part 1:

dirty one-ish-liner:

```python
from itertools import groupby
with open("day1.input") as f:
	print(max(sum((int(x.strip()) for x in elf)) for elf in (g for k,g in groupby(f.readlines(), key=lambda x: bool(x.strip())) if k )))
```

the same logic but more legible:

```python
from itertools import groupby
with open("day1.input") as f:
	elves = (g for k,g in groupby(f.readlines(), key=lambda x: bool(x.strip())) if k )
	sum_of_each = (sum((int(x.strip()) for x in elf)) for elf in elves)
	print(max(sum_of_each))
```

## part 2:

calculate all, sort and take the top three. selection sort would be the best algorithmically but in practice the builtin c function will be faster than a custom attempt.

```python
from itertools import groupby
with open("day1.input") as f:
	elves = (g for k,g in groupby(f.readlines(), key=lambda x: bool(x.strip())) if k )
	sum_of_each = (sum((int(x.strip()) for x in elf)) for elf in elves)
	print(sum(sorted(sum_of_each, reverse=True)[:3]))
```
