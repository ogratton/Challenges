import System.IO

-- TODO part2:
-- this case defeats me: zazbz[bzb]cdb
-- this is because hasAba never finds zbz, only ever zaz

-- part 1 ABBA and TLS
hasAbba (a:b:c:d:zs) = if a==d && b==c && a/=b then True else hasAbba (b:c:d:zs)
hasAbba _ = False

evalTLS string = let (in_,out_) = bracketSplit string in     
                    if not $ hasAbba in_ 
                    then    
					    if hasAbba out_ 
                        then 1 
                        else 0
                    else 0

-- part 2 ABA, BAB, and SSL
hasAba (a:b:c:zs) = if a==c && a/=b then (True,a,b) else hasAba (b:c:zs)
hasAba _ = (False,'z','z')

hasBab (a:b:c:zs) m n = if a==c && a/=b && a==m && b==n then True else hasBab (b:c:zs) m n
hasBab _ _ _ = False

evalSSL string = let (in_,out_) = bracketSplit string in
					let (aba,a,b) = hasAba out_ in
						if aba 
						then
                            if hasBab in_ b a
							then 1
							else 0
                        else 0

-- returns a tuple of (notBracketed, bracketed)
-- doesn't matter if the results are reversed in this case but oh well
-- also doesn't matter that they will end in a comma
bracketSplit' [] _ inB outB         = (reverse inB, reverse outB)
bracketSplit' (x:xs) False inB outB = if x/='[' then bracketSplit' xs False inB (x:outB)   else bracketSplit' xs True inB (',':outB)
bracketSplit' (x:xs) True inB outB  = if x/=']' then bracketSplit' xs True (x:inB) outB    else bracketSplit' xs False (',':inB) outB

-- wrapper for above
bracketSplit string = bracketSplit' string False [] []
                    
-- IO stuff

evalAll [] agg = agg
evalAll (x:xs) agg = evalAll xs (agg+evalTLS x)

part1 file = do  
    handle <- openFile file ReadMode  
    contents <- hGetContents handle  
    let ls = lines contents
    putStr $ show $ evalAll ls 0;
    putStr "\n"
    hClose handle