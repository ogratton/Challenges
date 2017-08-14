-- To run:
-- cat input.txt | ./b1 
-- (or b2 for part 2)
-- (in cmd or powershell use 'more' instead of 'cat')

main = interact part1

f '1' 'R' = '2'
f '1' 'D' = '4'
f '2' 'R' = '3'
f '2' 'D' = '5'
f '2' 'L' = '1'
f '3' 'D' = '6'
f '3' 'L' = '2'
f '4' 'U' = '1'
f '4' 'R' = '5'
f '4' 'D' = '7'
f '5' 'U' = '2'
f '5' 'R' = '6'
f '5' 'D' = '8'
f '5' 'L' = '4'
f '6' 'U' = '3'
f '6' 'D' = '9'
f '6' 'L' = '5'
f '7' 'U' = '4'
f '7' 'R' = '8'
f '8' 'U' = '5'
f '8' 'R' = '9'
f '8' 'L' = '7'
f '9' 'U' = '6'
f '9' 'L' = '8'
f  s   _  =  s


findNumber :: Char -> (Char -> Char -> Char) -> String -> String
findNumber s f = go s . lines
    where go _ [] = []
          go s (x:xs) = c : go c xs
              where c = foldl f s x


part1 :: String -> String
part1 = findNumber '5' f


f' '1' 'D' = '3'
f' '2' 'D' = '6'
f' '2' 'R' = '3'
f' '3' 'U' = '1'
f' '3' 'R' = '4'
f' '3' 'D' = '7'
f' '3' 'L' = '2'
f' '4' 'D' = '8'
f' '4' 'L' = '3'
f' '5' 'R' = '6'
f' '6' 'U' = '2'
f' '6' 'R' = '7'
f' '6' 'D' = 'A'
f' '6' 'L' = '5'
f' '7' 'U' = '3'
f' '7' 'R' = '8'
f' '7' 'D' = 'B'
f' '7' 'L' = '6'
f' '8' 'U' = '4'
f' '8' 'R' = '9'
f' '8' 'D' = 'C'
f' '8' 'L' = '7'
f' '9' 'L' = '8'
f' 'A' 'U' = '6'
f' 'A' 'R' = 'B'
f' 'B' 'U' = '7'
f' 'B' 'R' = 'C'
f' 'B' 'D' = 'D'
f' 'B' 'L' = 'A'
f' 'C' 'U' = '8'
f' 'C' 'L' = 'B'
f' 'D' 'U' = 'B'
f'  s   _  =  s


part2 :: String -> String
part2 = findNumber '5' f'