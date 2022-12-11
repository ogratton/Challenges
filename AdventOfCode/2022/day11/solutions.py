from math import prod
from typing import Callable, Dict, List, NewType

MonkeyId = NewType("MonkeyId", str)


class Monkey:

    def __init__(self, lines):
        self.id: MonkeyId = MonkeyId(lines[0][7])
        self.items: List[int] = [int(x) for x in lines[1][16:].split(",")]
        self.divis: int = int(lines[3][19:])
        self.inspect: Callable[[int], int] = lambda old: eval(lines[2][17:])
        self.next: Callable[[int], MonkeyId] = (
            lambda i, d: MonkeyId(lines[4][25:])
            if i % d == 0
            else MonkeyId(lines[5][26:])
        )

        self.num_inspects: int = 0

    def __repr__(self) -> str:
        return f"Monkey {self.id} after {self.num_inspects} inspections: {self.items}"


def parse_infile(filename) -> Dict[MonkeyId, Monkey]:
    with open(filename) as f:
        raw_monkeys = (
            [x.strip() for x in l.split("\n")] for l in f.read().split("\n\n")
        )
    monkey_lookup = {}
    for rm in raw_monkeys:
        m = Monkey(rm)
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


def part_1(ml):
    for i in range(20):
        do_round(i + 1, ml, lambda x: x // 3, verbose=True)


def part_2(ml):
    """
    No longer limiting anxiety. Numbers will grow huuuge quickly
    if we don't do something about it.
    By modding the worry level each time by the product of all
    the divisible test numbers, we keep the numbers in check without
    changing the outcome
    """
    prod_div = prod(m.divis for m in ml.values())
    for i in range(10_000):
        do_round(i + 1, ml, lambda x: x % prod_div)


monkeh = parse_infile("input.txt")
part_2(monkeh)
answer = prod(sorted([m.num_inspects for m in monkeh.values()], reverse=True)[:2])
print(f"Answer is {answer}")

