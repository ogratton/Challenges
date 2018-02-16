import timer

@timer.timeit
def kraken(n,m):
    l = max(n,m)
    k = min(n,m)

    a = [1]*l
    above = 1

    for i in range(1,k):
        above = a[i-1]
        a[i-1] = a[i] # set left of TL/BR diag
        for j in range(i,l):
            left = a[j-1]
            diag = above
            above = a[j]
            a[j] = left + above + diag

    return a[-1]


print(kraken(10000,10000))
