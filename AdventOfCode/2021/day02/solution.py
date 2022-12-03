import sys

def part1(lines):
    horiz, depth = 0, 0
    for line in lines:
        direction, amount_s = line.split()
        amount = int(amount_s)
        horiz, depth = {
                "forward": lambda h, d, x: (horiz+x, d),
                "down": lambda h, d, x: (h, d+x),
                "up": lambda h, d, x: (h, d-x),
        }[direction](horiz, depth, amount)
    print("Part 1:", horiz, depth, horiz*depth)

def part2(lines):
    horiz, depth, aim = 0, 0, 0
    for line in lines:
        direction, amount_s = line.split()
        amount = int(amount_s)
        horiz, depth, aim = {
                "forward": lambda h, d, a, x: (horiz+x, d+a*x, a),
                "down": lambda h, d, a, x: (h, d, a+x),
                "up": lambda h, d, a, x: (h, d, a-x),
        }[direction](horiz, depth, aim, amount)
    print("Part 2:", horiz, depth, aim, horiz*depth)


with open(sys.argv[1]) as f:
    flines = f.readlines()

part1(flines)
part2(flines)
