# by reddit user Commod0re
import collections
import re
import sys
import time

PXOFF = '\u2592'
PXON = '\u2588'


def newscreen(rows=6, cols=50):
    return [ collections.deque([PXOFF for c in range(cols)]) for r in range(rows) ]


def printscreen(screen):
    # clear the terminal
    sys.stdout.write('\033c')

    ssize = len(screen[0])

    print('┍{}┑'.format('━' * ssize))
    for row in screen:
        sys.stdout.write('│')
        for px in row:
            sys.stdout.write('{}'.format(px))
        sys.stdout.write('│\n')
    print('┕{}┙'.format('━' * ssize))


def rect(screen, x, y):
    # turn on pixels in an x * y square in the top left to PXON
    for scrow, _ in zip(screen, range(y)):
        for sx in range(x):
            scrow[sx] = PXON


def rrow(screen, row, dist):
    # rotating rows (to the right) is easy, since it's a deque
    screen[row].rotate(dist)


def rcol(screen, col, dist):
    # rotating columns (downward) is a wee bit more work
    column = collections.deque([ scrow[col] for scrow in screen ])
    column.rotate(dist)
    for scrow, cpx in zip(screen, column):
        scrow[col] = cpx


if __name__ == '__main__':
    # screen = [ [PXOFF for _ in range(50) ] for _ in range(6) ]
    screen = newscreen()
    printscreen(screen)

    rectargs = re.compile(r' (?P<x>\d+)x(?P<y>\d)+')
    rotargs = re.compile(r' [xy]=(?P<rc>\d+) by (?P<dist>\d+)')

    with open('input.txt', 'r') as inf:
        directions = inf.readlines()

    for line in directions:
        # make it look sorta animated
        time.sleep(0.10)

        if line.startswith('rect'):
            args = rectargs.search(line).groupdict()
            rect(screen, int(args['x']), int(args['y']))

        elif line.startswith('rotate row'):
            args = rotargs.search(line).groupdict()
            rrow(screen, int(args['rc']), int(args['dist']))

        elif line.startswith('rotate column'):
            args = rotargs.search(line).groupdict()
            rcol(screen, int(args['rc']), int(args['dist']))

        printscreen(screen)

    print('\n\nlit pixels: {}'.format(len([p for r in screen for p in r if p == PXON])))
