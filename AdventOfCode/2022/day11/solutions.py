from dataclasses import dataclass
from math import prod
from typing import Callable, Dict, List, NewType

MonkeyId = NewType("MonkeyId", str)

@dataclass
class Monkey:
    id: MonkeyId
    items: List[int]
    inspect: Callable[[int], int]
    divis: int
    next: Callable[[int], MonkeyId]
    num_inspects: int = 0
    

    @classmethod
    def parse(cls, lines) -> 'Monkey':
        return cls(
            id=MonkeyId(lines[0][7]),
            items=[int(x) for x in lines[1][16:].split(",")],
            divis=int(lines[3][19:]),
            inspect=lambda old: eval(lines[2][17:]),
            next=lambda i, d: MonkeyId(lines[4][25:]) if i % d == 0 else MonkeyId(lines[5][26:])
        )
    
    def __repr__(self) -> str:
        return f"Monkey {self.id} after {self.num_inspects} inspections: {self.items}"

def parse_infile(filename) -> Dict[MonkeyId, Monkey]:
    with open(filename) as f:
        raw_monkeys = [[x.strip() for x in l.split("\n")] for l in f.read().split("\n\n")]
    monkey_lookup = {}
    for rm in raw_monkeys:
        m = Monkey.parse(rm)
        monkey_lookup[m.id] = m
    return monkey_lookup

def do_round(n, monkey_lookup, valium, verbose=False):
    for mid, monkey in monkey_lookup.items():
        for item in monkey.items:
            worry_level = valium(monkey.inspect(item))
            monkey.num_inspects += 1
            throw_to = monkey.next(worry_level, monkey.divis)
            if verbose:
                print(f"Monkey {mid} throws item {worry_level} to monkey {throw_to}")
            monkey_lookup[throw_to].items.append(worry_level)
        monkey.items = []
    
    if verbose:
        print(f"After round {n}:")
        print(*monkey_lookup.values(), sep="\n")
        print("")
    elif not n % 100:
        print(f"Round {n}")

def calculate_answer(ml):
    top_2 = sorted([m.num_inspects for m in ml.values()], reverse=True)[:2]
    return prod(top_2)

def part_1():
    ml = parse_infile("input.txt")
    for i in range(20):
        do_round(i+1, ml, lambda x: x//3, verbose=True)
    
    print(f"Answer is {calculate_answer(ml)}")

def part_2():
    """
    No longer limiting anxiety. Numbers will grow huuuge quickly
    if we don't do something about it.
    By modding the worry level each time by the product of all
    the divisible test numbers, we keep the numbers in check without
    changing the outcome
    """
    ml = parse_infile("input.txt")
    prod_div = prod(m.divis for m in ml.values())
    for i in range(10_000):
        do_round(i+1, ml, lambda x: x % prod_div)
    
    print(f"Answer is {calculate_answer(ml)}")

part_2()