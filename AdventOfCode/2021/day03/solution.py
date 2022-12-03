import sys

def part1(lines):
    array = [list(line.strip()) for line in lines]
    gamma = ""
    epsilon = ""
    for j in range(len(array[0])):
        word = "".join((array[i][j] for i in range(len(array))))
        zeros_greater = word.count("0") > word.count("1")
        gamma += str(int(not zeros_greater))
        epsilon += str(int(zeros_greater))
    return int(gamma, 2) * int(epsilon, 2)

def part2(lines):
    array = [list(line.strip()) for line in lines]
    
    return "todo"

with open(sys.argv[1]) as f:
    flines = f.readlines()

print("Part 1:", part1(flines))
print("Part 2:", part2(flines))
