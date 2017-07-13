import Data.Map
import System.IO
import Control.Monad

-- make a freq table of a sentence
freq input = toList $ fromListWith (+) [(c, 1) | c <- input]

check id table [] = id
check id table (x:[]) = id
check id table (x:y:xys) = if (Just (Prelude.lookup x table) >= Just(Prelude.lookup y table)) then check id table (y:xys) else 0

bracket [] = []
bracket ('[':xs) = before ']' xs
bracket (x:xs) = bracket xs

before b [] = []
before b (x:xs) = if x==b then [] else x: before ']' xs

getID = take 3 . reverse . take 10 . reverse

strToInt s = read s :: Integer

eval string = check (strToInt $ getID string) (freq (before ']' string)) (bracket string)

evalAll [] agg = agg
evalAll (x:xs) agg = evalAll xs (agg+eval x)

loadData file = do  
    handle <- openFile file ReadMode  
    contents <- hGetContents handle  
    let ls = lines contents
    putStr $ show $ evalAll ls 0
    hClose handle

--main = forever $ do
--   l <- getLine
--    putStrLn $ show $ eval l

