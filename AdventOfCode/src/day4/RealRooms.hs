import Data.Map
import System.IO
import Control.Monad

-- TO RUN: 	simply type 'main' in an interactive session

--- PART 1 ---

-- split the main body from the checksum and ID
partsSplit s = splitAt ((length s)-10) s

-- remove all instances of c in a string
removeAll [] c = []
removeAll (x:xs) c = if (c==x) then removeAll xs c else x : removeAll xs c

-- sort the freq table
sortFreq :: Ord n => [(x,n)] -> [(x,n)]
sortFreq [] = []
sortFreq ((x,n):xns) = sortFreq smallerSorted ++ [(x,n)] ++ sortFreq largerSorted
	where
	smallerSorted = [(a,m) | (a,m) <- xns, m > n]
	largerSorted = [(a,m) | (a,m) <- xns, m <= n]
	
-- make a freq table of a sentence
freq input = sortFreq $ toList $ fromListWith (+) [(c, 1) | c <- input]

-- generate true checksum (5 long)
checksum n ((c,_):table) = if n > 0 then c : (checksum (n-1) table) else []

-- read string as Integer
strToInt s = read s :: Integer

-- parse input into a triple with helper functions
eval' string = let (l,c) = partsSplit string in (checksum 5 $ freq $ removeAll l '-',strToInt $ take 3 c, take 5 $ drop 4 c)

-- check if the checksum matches the one we generate
eval string = let (myChecksum,id,checksum) = eval' string in if myChecksum==checksum then id else 0

--- PART 2 ---

-- rotate characters around the alphabet Caesar-Cipher-style
rotate :: Integer -> Char -> Char
rotate 0 c = c
rotate n c
    | c == '-' = ' '
    | c == 'z' = rotate (n-1) 'a'
    | otherwise= rotate (n-1) (succ c)

-- rotate a string n times
decrypt' n = Prelude.map (rotate (mod n 26))

decrypt :: String -> String
decrypt string = if (eval string > 0) then let (l, c) = partsSplit string in decrypt' (strToInt $ take 3 c) l else ""

-- IO stuff below

evalAll [] agg = agg
evalAll (x:xs) agg = evalAll xs (agg+eval x)

part1 file = do  
    handle <- openFile file ReadMode  
    contents <- hGetContents handle  
    let ls = lines contents
    putStr $ show $ evalAll ls 0;
	putStr "\n"
    hClose handle
	
--main = part1 "input.txt"

main = forever $ do   
    l <- getLine  
    putStrLn $ decrypt l 

