with open("input.txt") as f:
    lines = list(map(int, f.readlines()))

def inc_count(xs):
    return sum(map(lambda x: x[1] > x[0], zip(xs, xs[1:])))

def triple_sum(xs):
    return list(map(sum, zip(xs, xs[1:], xs[2:])))

print(f"Part 1: {inc_count(lines)}")
print(f"Part 2: {inc_count(triple_sum(lines))}")
