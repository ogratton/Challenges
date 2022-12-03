# by reddit user barnybug

from itertools import takewhile, islice
import re

def decompress(data, recurse):
    answer = 0
    chars = iter(data)
    for c in chars:
        if c == '(':
            n, m = map(int, [''.join(takewhile(lambda c: c not in 'x)', chars)) for _ in (0, 1)])
            s = ''.join(islice(chars, n))
            answer += (decompress(s, recurse) if recurse else len(s))*m
        else:
            answer += 1
    return answer


# by LieutenantSwr2d2

def day9a(d):
    bracket = re.search(r'\((\d+)x(\d+)\)', d)
    if not bracket:
        return len(d)
    pos = bracket.start(0)
    sz = int(bracket.group(1))
    rpt = int(bracket.group(2))
    i = pos + len(bracket.group())
    return len(d[:pos]) + len(d[i:i+sz]) * rpt + day9a(d[i+sz:])

def day9b(d):
    bracket = re.search(r'\((\d+)x(\d+)\)', d)
    if not bracket:
        return len(d)
    pos = bracket.start(0)
    sz = int(bracket.group(1))
    rpt = int(bracket.group(2))
    i = pos + len(bracket.group())
    return len(d[:pos]) + day9b(d[i:i+sz]) * rpt + day9b(d[i+sz:])

data = open('input.txt').read()
print('Answer #1:', decompress(data, False))
print('Answer #2:', decompress(data, True))
print('Answer #1:', day9a(data))
print('Answer #2:', day9b(data))
