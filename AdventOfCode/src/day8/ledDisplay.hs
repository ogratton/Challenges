-- NOTE: use this in bash, not cmd/powershell
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