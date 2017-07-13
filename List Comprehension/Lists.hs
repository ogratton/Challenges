import Data.List
import System.Random

-- Problem 1
---- Find the last element of a list.
last' = head . reverse

-- Problem 2
---- Find the last but one element of a list.
penultimate = head . tail . reverse

-- Problem 3
---- Find the K'th element of a list. The first element in the list is number 1.
kth (x:xs) n = if n==1 then x else kth xs (n-1) 

-- Problem 4
---- Find the number of elements in a list.
------ In increasing order of efficiency (lengthD is the best)
lengthA' [] n = n
lengthA' (x:xs) n = lengthA' xs (n+1)
lengthA xs = lengthA' xs 0

lengthB [] = 0
lengthB (_:xs) = 1 + lengthB xs

lengthC = sum . map (\_ -> 1)

lengthD = fst . last . zip [1..] -- zip with infinity until we run out of our list and take it from the pair

-- Problem 5
---- Reverse a list.
reverseA [] = []
reverseA (x:xs) = (reverseA xs) ++ [x]

reverseB = foldl (flip (:)) []
reverseB' xs = foldl (flip (:)) [] xs -- TODO don't really get this whole folding thing

reverseC xs = foldr (\x fId empty -> fId (x : empty)) id xs [] -- TODO no idea

-- Problem 6
---- Check whether a list is a palindrome
palindrome xs = xs == reverse xs

-- Problem 7
---- Something about nested lists. CBA

-- Problem 8
---- Eliminate consecutive duplicates in a list
compressA' [] prev = []
compressA' (x:xs) prev =  if x==prev 
				then compressA' xs x 
				else x : (compressA' xs x)
compressA (x:xs) = x : (compressA' xs x) 

compressB (x:ys@(y:_))
    | x == y    = compressB ys
    | otherwise = x : compressB ys
compressB ys = ys -- TODO don't get this one

-- Problem 9
---- Rewrite Data.List.group
packA (x:xs) = let (first,rest) = span (==x) xs
               in (x:first) : packA rest
packA [] = []

packB :: (Eq a) => [a] -> [[a]]
packB [] = []
packB [x] = [[x]]
packB (x:xs) = if x `elem` (head (packB xs))
              then (x:(head (packB xs))):(tail (packB xs))
              else [x]:(packB xs)

-- Problem 10
---- Histogram
histoA xs = zip (map head chars) (map length chars) 
	where
	-- sort the string and then group the characters 
	chars = group (sort (xs))

histoB xs = map (\x -> (length x,head x)) (group list)
	where list = sort xs

histoC xs = [(length x, head x) | x <- group list]
	where list = sort xs

histoD xs = [(length x, head x) | x <- group xs]

decodeHistoD = concatMap (uncurry replicate) -- nice

-- Problem 11
---- Modified Histogram
data Quantity a = Single a | Multiple Int a
	deriving (Show)

modHistoA xs = [ q | x <- group xs, let q = if (length x) > 1 then Multiple (length x) (head x) else Single (head x) ]

-- Problem 12
---- Decoding Modified Histograms
decodeModHisto [] = []
decodeModHisto ((Single x):hs) = x : (decodeModHisto hs)
decodeModHisto ((Multiple n x):hs) = (replicate n x) ++ (decodeModHisto hs)

-- Problem 13
---- Decode directly
encodeDirect [] = []
encodeDirect (x:xs) = encodeDirect' 1 x xs

encodeDirect' n y [] = [encodeElement n y]
encodeDirect' n y (x:xs) 
			| y == x    = encodeDirect' (n+1) y xs
			| otherwise = (encodeElement n y) : (encodeDirect' 1 x xs)

encodeElement 1 y = Single y
encodeElement n y = Multiple n y

-- Problem 14
---- Duplicate each item in a list
combine x y = x : [y]
dupeA xs = concatMap (uncurry combine) (zip xs xs)

dupeB [] = []
dupeB (x:xs) = x:x:(dupeB xs)

dupeC = concatMap (replicate 2) -- fastest

dupeD = concatMap (\x -> [x,x])

dupeE = foldl (\acc x -> acc ++ [x,x]) [] -- slow as shit

dupeF = foldr (\x xs -> x : x : xs) []

-- Problem 15
---- Replicate each item n times in situ
repli xs n = concatMap (replicate 3) xs

-- Problem 16
---- Drop every nth element of a list
dropA' [] n m = []
dropA' (x:xs) n m = if m==1 then dropA' xs n n else x : (dropA' xs n (m-1))

dropA xs n = dropA' xs n n

dropB = flip $ \n -> map snd . filter ((n/=) . fst) . zip (cycle [1..n]) -- TODO fastest but wtf

dropC [] _ = [] -- slow
dropC (x:xs) n = dropC' (x:xs) n 1 
	where
	dropC' (x:xs) n i = (if (n `divides` i) then [] else [x]) ++ (dropC' xs n (i+1))
	dropC' [] _ _ = []
	divides x y = y `mod` x == 0

-- Problem 17
---- Split a list at index n
splitA xs n = (take n xs, drop n xs)

splitB = flip splitAt

-- Problem 18
---- Extract a slice from a list (inclusive)
sliceA xs i k = drop (i-1) (take k xs)

sliceB xs i k | i>0 = take (k-i+1) $ drop (i-1) xs -- mildly faster

sliceC xs i k = [x | (x,j) <- zip xs [1..k], i <= j] -- slow as balls

-- Problem 19
---- Rotate a list n times to the left
rotateA xs n = snd blob ++ fst blob 
	where
	m = if n > 0 then n else n+(length xs)
	blob = (splitA xs m)

-- Problem 20
---- Pop element k from a list
pop k xs = ((xs !! (k-1)), (take (k-1) xs) ++ (drop k xs))

-- Problem 21
---- Insert an element into a list at index k
insertA y (x:xs) k = if k==1 then y:x:xs else x:(insertA y xs (k-1))

insertB y (x:xs) k
	| k ==1 = y:x:xs
	| otherwise = x:(insertB y xs (k-1))

insertC y xs k = foldr concat' [] $ zip [1..] xs -- fastest
    where
        concat' (i, x) xs
            | i == k  = y:x:xs
            | otherwise = x:xs

-- Problem 22
---- Python's range function
rangeA i j = sliceB [1..] i j

rangeB i j
	| i== j = [j]
	| i < j = i:(rangeB (i+1) j)
	| otherwise = reverse $ rangeB j i

-- Problem 23
---- Extract a given number of randomly selected elements from a list.

randSelectA xs n = do
    gen <- getStdGen
    return $ take n [ xs !! x | x <- randomRs (0, (length xs) - 1) gen]

-- Problem 24
---- Lottery draw (unique numbers)
decrementLarger j ks = [i | k <- ks, let i = if k > j then (k-1) else k]

draw ks xs 
		| ks==[] = []
		| otherwise = (fst pair) : (draw (decrementLarger (head ks) (tail ks)) (snd pair))  
		where
		k = if (head ks) > (length xs) then (head ks)-(length xs) else (head ks) -- overflow
		pair = pop k xs

{- -- doesn't work cos of the "do" stuff but you can type it in the shell
lottoA n m = do
	gen <- getStdGen
	let ks = take n [x | x <- randomRs (0,m-1) gen] :: [Int]
	draw ks [1..m]
-}

lottoB :: Int -> Int -> IO [Int]
lottoB n m = do
  gen <- getStdGen
  return . take n $ randomRs (1, m) gen
