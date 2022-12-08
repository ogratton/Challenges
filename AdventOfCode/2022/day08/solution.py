import os
os.chdir("/Users/oliver.gratton/Documents/challenges/AdventOfCode/2022/day08")

with open("example") as f:
    grid_ex = list(map(lambda xs: [int(x) for x in xs.strip()], f.readlines()))

with open("input.txt") as f:
    grid_in = list(map(lambda xs: [int(x) for x in xs.strip()], f.readlines()))

def get_crosshair(i, j, grid):
    left = grid[i][:j]
    right = grid[i][j+1:]
    up = [grid[n][j] for n in range(i)]
    down = [grid[n][j] for n in range(i+1, len(grid))]
    return up, down, left, right

def is_visible(dirs, target):
    vis = False
    for d in dirs:
        if not d:
            return True
        vis |= max(d) < target
    return vis
        
def calc_is_visible(i, j, grid):
    dirs = get_crosshair(i, j, grid)
    yield 1 * is_visible(dirs, grid[i][j])

def scan_grid(grid, func):
    for i in range(len(grid)):
        for j in range(len(grid[0])):
            yield from func(i, j, grid)
    
def part_1(grid):
    return sum(scan_grid(grid, calc_is_visible))




def calc_viewing_distance(i, j, grid):
    curr = grid[i][j]

    up, down, left, right = get_crosshair(i, j, grid)
    up = up[::-1]
    left = left[::-1]
    
    score = 1
    for d in (up, down, left, right):
        subtotal = 0
        for tree in d:
            # if tree <= curr:
            subtotal += 1
            if tree >= curr:
                break
        score *= subtotal
    yield score

def part_2(grid):
    return max(scan_grid(grid, calc_viewing_distance))





print(part_1(grid_in))
print(part_2(grid_in))