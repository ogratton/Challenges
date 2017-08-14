strToInt s = read s :: Int

decompress :: String -> String
decompress [] = []
decompress ('(':xs) =   let (com,rest) = span (/=')') xs
                            [freq,amount] = parse com
                            (h,t) = splitAt freq (tail rest) in          -- tail is to get rid of ')'
                        concat (replicate amount h) ++ (decompress t)
decompress (x:xs) = x : decompress xs

splitBy            :: Char -> String -> [String]
splitBy c ""         =  []
splitBy c s          =  let (l, s') = break (== c) s in
                        l : case s' of
                            []      -> []
                            (_:s'') -> splitBy c s''

parse com = map strToInt (splitBy 'x' com)


-- PART 2
decompLength :: String -> Int
decompLength [] = 0
decompLength ('(':xs) =  let (com,rest) = span (/=')') xs in
                         let [freq,amount] = parse com in
                         let (h,t) = splitAt freq (tail rest) in
                        (amount * (decompLength h)) + (decompLength t)
decompLength (_:xs) = 1 + (decompLength xs)


{--- No idea what's going on here but this is from reddit user bblum:
import Control.Arrow
import Data.List.Extra
import Data.Maybe

decompress pt2 ('(':s) =
    let (x,(y,r)) = read *** first read $ second (fromJust . stripInfix ")") $ fromJust $ stripInfix "x" s
    in (decompress pt2 $ drop x r) + if pt2 then y * (decompress pt2 $ take x r) else x * y
decompress pt2 (_:s) = 1 + decompress pt2 s
decompress pt2 [] = 0

main = interact $ show . (decompress False &&& decompress True) . trim

-}