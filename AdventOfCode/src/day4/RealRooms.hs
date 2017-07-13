import Data.Map
import System.IO
import Control.Monad

-- TO RUN: 	simply type 'main' in an interactive session


-- example input
-- 		ixccb-hjj-uhvhdufk-725[hcjub]

-- make a freq table of a sentence
freq input = toList $ fromListWith (+) [(c, 1) | c <- input]

-- check the checksum is in decreasing order of frequency
check id table [] = id
check id table (x:[]) = id
check id table (x:y:xys) = if (Just (Prelude.lookup x table) >= Just(Prelude.lookup y table)) then check id table (y:xys) else 0

-- take everything inbetween square brackets
bracket [] = []
bracket ('[':xs) = before ']' xs
bracket (x:xs) = bracket xs

-- take everything in a list before first occurrence of var b
before b [] = []
before b (x:xs) = if x==b then [] else x: before ']' xs

getID = take 3 . reverse . take 10 . reverse

strToInt s = read s :: Integer

eval string = check (strToInt $ getID string) (freq (before ']' string)) (bracket string)


-- all IO stuff below here

evalAll [] agg = agg
evalAll (x:xs) agg = evalAll xs (agg+eval x)

loadData file = do  
    handle <- openFile file ReadMode  
    contents <- hGetContents handle  
    let ls = lines contents
    putStr $ show $ evalAll ls 0;
	putStr "\n"
    hClose handle
	
main2 = loadData "input.txt"

