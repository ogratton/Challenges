from itertools import takewhile

def load_stacks(f):
    stacks_raw = list(takewhile(lambda x: bool(x.strip()), f))
    # print(*stacks_raw)
    legend = stacks_raw[-1]
    keys = {int(k): legend.index(k) for k in legend.split()}
    # empty at 0 so we can be 1-indexed
    stacks = [list() for _ in range(len(keys)+1)]
    for line in stacks_raw[-2::-1]:
        for key, index in keys.items():
            if box := line[index].strip():
                stacks[key].append(box)
    return stacks, list(f)    

def parse_move(raw_move):
    _move, n, _from, i, _to, j = raw_move.split()
    return int(n), int(i), int(j)

def part_1(stacks, raw_moves):
    for raw_move in raw_moves:
        n, i, j = parse_move(raw_move)
        stacks[j].extend(stacks[i][::-1][:n])
        stacks[i] = stacks[i][:-n]
    return "".join(s[-1] for s in stacks if s)

def part_2(stacks, raw_moves):
    for raw_move in raw_moves:
        n, i, j = parse_move(raw_move)
        stacks[j].extend(stacks[i][-n:])
        stacks[i] = stacks[i][:len(stacks[i])-n]
    return "".join(s[-1] for s in stacks if s)

def main():
    with open("input.txt") as f:
      stacks, moves = load_stacks(f)
    print("Part 1: ", part_1(stacks, moves)

