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
decompLength ('(':xs) =  let (com,rest) = span (/=')') xs
                            [freq,amount] = parse com
                            (h,t) = splitAt freq (tail rest) in
                        (amount * (decompLength h)) + (decompLength t)
decompLength (x:xs) = 1 + (decompLength xs)