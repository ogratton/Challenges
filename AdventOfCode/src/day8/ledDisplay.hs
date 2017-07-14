import System.IO

-- NOTE: only seems to work on linux, not windows
-- Based on Graham Hutton's Game of Life code

-- Screen Utilities

cls :: IO()
cls = putStr "\ESC[2J"

type Pos = (Int,Int)

writeat :: Pos -> String -> IO()
writeat p xs = do   goto p;
                    putStr xs
                    
goto :: Pos -> IO()
goto (x,y) = putStr("\ESC[" ++ show y ++ ";" ++ show x ++ "H")

-- LED Display

width :: Int
width = 50

height :: Int
height = 6

type Board = [Pos]

showcells :: Board -> IO()
showcells b = sequence_ [writeat p "0" | p <- b]

wrap :: Pos -> Pos
wrap (x,y) = (((x-1) `mod` width) + 1,
              ((y-1) `mod` height) + 1)
              
-- COMMANDS

rect w h board = [(i,j) | i <- [1..w], j <- [1..h]] ++ board

rotate isRow index amount [] = []
rotate isRow index amount ((c,r):b) =   if isRow 
                                        then
                                            if r==index+1
                                            then ((mod' (c+amount) width),r) : rotate isRow index amount b
                                            else (c,r) : rotate isRow index amount b
                                        else
                                            if c==index+1
                                            then (c,(mod' (r+amount) height)) : rotate isRow index amount b
                                            else (c,r) : rotate isRow index amount b
                                        -- need a modified mod cos it doesn't start at 0    
                                        where mod' n m = if (mod n m == 0) then m else (mod n m)
                                        
-- TODO dumb mod error with:
-- rotate False 1 1 (rotate True 0 4 (rotate False 1 1 (rect 3 2 [])))
-- for some reason it still returns (2,4) as a point instead of (2,1) even though I defo use mod'

-- PARSING

splitBy            :: Char -> String -> [String]
splitBy c ""         =  []
splitBy c s          =  let (l, s') = break (== c) s
                      in  l : case s' of
                                []      -> []
                                (_:s'') -> splitBy c s''
                                
strToInt s = read s :: Int

parse command = let wds = splitBy ' ' command in if length wds == 2 then rectparse wds else rotparse (tail wds)

rectparse :: [String] -> [Int]
rectparse (_:command:[]) = map strToInt (splitBy 'x' command)

rotparse :: [String] -> [Int]
rotparse (row:index:_:amount:[]) = [(if row=="row" then 1 else 0),strToInt (drop 2 index), strToInt amount]

-- needs to be used with a board
executeOne coms =  if length coms == 2 
                then rect (head coms) (head $ tail coms)
                else rotate (if (coms !! 0)==1 then True else False) (coms !! 1) (coms !! 2)
				
executeAll []     last_board = last_board
executeAll (x:xs) last_board = executeAll xs (executeOne (parse x) last_board)

-- IO

perform file = do  
    handle <- openFile file ReadMode  
    contents <- hGetContents handle  
    let ls = lines contents
    showcells $ executeAll ls [];
	putStr "\n\n\n\n\n\n"
    hClose handle
