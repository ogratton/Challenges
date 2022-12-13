"""
Much more sensible/concise solution using the same library + numpy stolen from reddit:

```
import numpy as np, networkx as nx

H = np.array([[*x.strip()] for x in open(0)])

S = tuple(*np.argwhere(H=='S')); H[S] = 'a'
E = tuple(*np.argwhere(H=='E')); H[E] = 'z'

G = nx.grid_2d_graph(*H.shape, create_using=nx.DiGraph)
G.remove_edges_from([(a,b) for a,b in G.edges if ord(H[b]) > ord(H[a])+1])

p = nx.shortest_path_length(G, target=E)
print(p[S], min(p[a] for a in p if H[a]=='a'))
```

In the space it took me to build the graph I could have done an actual BFS/A*
"""

import networkx

grid = [l.strip() for l in open("input.txt")]
H = len(grid)
W = len(grid[0])

def get_neighbours(x, y):
    return [(x+i, y+j) for i, j in [(0, 1), (0, -1), (1, 0), (-1, 0)]]

def value(char):
    return {"E": ord("z"), "S": ord("a")}.get(char, ord(char))

def is_accessible(current_char, target_char):
    val_cur = value(current_char)
    val_tar = value(target_char)
    return any([val_cur >= val_tar, val_tar - val_cur == 1])
    

graph = networkx.DiGraph()
start = None
end = None

a_coords = set()

for height, line in enumerate(grid):
    for width, char in enumerate(line):
        if char == "S":
            start = (height, width)
        if char == "E":
            end = (height, width)
        if char == "a":
            a_coords.add((height, width))
        graph.add_node((height, width))
        neighbour_coords = get_neighbours(height, width)
        accessible_neighbours = [((x, y), grid[x][y]) for x, y in neighbour_coords if 0 <= x < H and 0 <= y  < W and is_accessible(char, grid[x][y])]
        # print(f"{height, width}: {char} is neighboured by {accessible_neighbours}")
        for (x, y), n in accessible_neighbours:
            graph.add_edge((height, width), (x, y))

def part_1():
    print("Shortest path:", networkx.shortest_path_length(graph, start, end))

def part_2():
    # Absolutely not efficient in runtime but efficient in human time because I'm hungry
    answer = float('inf')
    for x, y in a_coords:
        try:
            answer = min(networkx.shortest_path_length(graph, (x, y), end), answer)
        except:
            ...
    print("Shortest path:",  answer)

part_1()
part_2()