import Data.List
{-
Attempts to insert spaces back into blocks of characters
-}


sent1 = "thetablewaslowerthanme"
sent2 = "thetaskill"
sent3 = "thetabledownthere"
words' = ["the", "table", "was", "lower", "than", "me", "theta", "blew", "as", "thetas", "kill", "task", "ill", "skill", "bled", "down", "own", "there"]

isWord = flip elem words'

data Tree = Empty | Leaf [[Char]] | Node Tree Tree deriving Show

word_spacer :: [Char] -> [[Char]]
word_spacer = map join . read_words . spacer "" []

spacer :: [Char] -> [[Char]] -> [Char] -> Tree
spacer current agg []   =   if (isWord current) then
                                Leaf (reverse (current:agg))
                            else
                                Empty
spacer current agg (x:xs) = if (isWord current) then
                                Node (spacer [x] (current:agg) xs) (spacer (current ++ [x]) agg xs)
                            else
                                spacer (current ++ [x]) agg xs

-- traverse the tree to pluck out the sentences
read_words :: Tree -> [[[Char]]]
read_words Empty      = []
read_words (Leaf snt) = [snt]
read_words (Node l r) = (read_words l) ++ (read_words r)

join = intercalate " "
