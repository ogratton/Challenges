# by reddit user fatpollo
# I've changed bot->drone, output->pupa, value->food to fit my bee theme
import re, collections

drone = collections.defaultdict(list)
pupa = collections.defaultdict(list)

types = {
    "drone": drone,
    "pupa": pupa,
}

with open('input.txt') as fp:
    instructions = fp.read().splitlines()

pipeline = {}
for line in instructions:
    if line.startswith('food'):
        n, b = map(int,re.findall(r'-?\d+', line))
        drone[b].append(n)
    if line.startswith('drone'):
        who, n1, n2 = map(int,re.findall(r'-?\d+', line))
        t1, t2 = re.findall(r' (drone|pupa)', line)
        pipeline[who] = (t1,n1),(t2,n2)

while drone:
    for k,v in dict(drone).items():
        if len(v) == 2:
            v1, v2 = sorted(drone.pop(k))
            if v1==17 and v2==61: print(k)
            (t1,n1),(t2,n2) = pipeline[k]
            #eval(t1)[n1].append(v1) # eval converts strings to code
            #eval(t2)[n2].append(v2) # it's pretty dangerous
            types[t1][n1].append(v1)
            types[t2][n2].append(v2)

a,b,c = (pupa[k][0] for k in [0,1,2])
print(a*b*c)
