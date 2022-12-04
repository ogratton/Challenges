# Python

## part 1

```python
with open("day2.input") as f:
    mapping = {"A": 1, "X": 1, "B": 2, "Y": 2, "C": 3, "Z": 3}
    score = 0
    for line in f:
        i, j = [mapping[x] for x in line.split()]
        score += j
        if i == j:
            score += 3
        else:
            score += 6 * (j - (i%3) == 1)
    print(score)
```

## part 2

```python
with open("day2.input") as f:
    mapping = {"A": 1, "B": 2, "C": 3}
    score = 0
    for line in f:
        their_move = line[0]
        your_move = moves[their_move][line[2]]
        i = mapping[their_move]
        j = mapping[your_move]
        score += j
        if their_move == your_move:
            score += 3
        else:
            score += 6 * (j - (i%3) == 1)
    print(score)
```

Or:

```python
with open("day2.input") as f:
    scores = {"B X": 1, "C X": 2, "A X": 3, "A Y": 4, "B Y": 5, "C Y": 6, "C Z": 7, "A Z": 8, "B Z": 9}
    total = 0
    for line in f:
        total += scores[line.strip()]
    print(total)
```

# Awk

## part 1

stolen from reddit, though this was my inital reaction of how to approach it before the post-work-xmas-party-brain-fog set in

```bash
awk '/X/ {s += 1}; /Y/ {s += 2}; /Z/ { s += 3 }; /A X|B Y|C Z/ { s += 3}; /A Y|B Z|C X/ { s += 6}; END {print s}' day2.input
```

## part 2

```bash
awk '/Y/ { s += 3}; /Z/ { s += 6 }; /A Y|B X|C Z/ { s += 1 }; /B Y|C X|A Z/ { s += 2 }; /C Y|A X|B Z/ { s += 3}; END {print s}' day2.input
```
