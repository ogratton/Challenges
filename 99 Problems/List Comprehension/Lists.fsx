(* 1 - Write a function that returns the last element of a list*)

let rec last = function
    | []     -> failwith "last"
    | [x]    -> Some x
    | _ :: t -> last t;;

(* 2 - Find the last two elements of a list*)

let rec last_two = function
    | []     -> failwith "last_two"
    | [_]    -> failwith "last_two"
    | [x;y]  -> Some (x,y)
    | _ :: t -> last_two t;;

(* 3 - Find the kth element of a list*)

let rec kth xs k =
    match xs with
    | []      -> failwith "kth"
    | x :: xs -> if k = 0 then x else kth xs (k-1);;

(* 4 - Length of a list*)

let rec length = function
    | []      -> 0
    | x :: xs -> (1+length xs);;

let length2 list =
    let rec aux n = function
        | [] -> n
        | _::t -> aux (n+1) t
    in aux 0 list;;

(* 5 - Reverse a list*)

let rec rev = function
    | []      -> []
    | x :: xs -> (rev xs) @ [x];;

let rev_alt xs =
    let rec revver sx xs =
        match xs with
        |[]    -> sx
        |x::xs -> revver (x::sx) xs
    in revver [] xs;;

(* 6 - Palindrome checker*)

let palin list =
    list = rev list;; (*Here the equals is being used for equality like == in every sane language*)

(* 7 - Flatten a nested list structure

no fricken clue*)

(* Eliminate consecutive duplicates of list elements *)

let rec compress (xs:string list) =
    match xs with
    |x::y::xys when y=x -> compress (x::xys)
    |x::y::xys          -> x::(compress (y::xys))
    |x                  -> x;;

let rec compressMODEL = function
   | a :: (b :: _ as t) -> if a = b then compressMODEL t else a :: compressMODEL t
   | smaller -> smaller;;

(* Pack consecutive duplicates of list elements into sublists *)

(* doesn't completely work *)
let rec MyPack0 xs =
    match xs with
    |x::y::xys when y=x -> [[x]@[y]]@(MyPack0 xys)
    |x::y::xys          -> [x]::(MyPack0 (y::xys))
    |x                  -> [x];;

let packMODEL list =
    let rec aux current acc = function
      | [] -> []    (* Can only be reached if original list is empty *)
      | [x] -> (x :: current) :: acc
      | a :: (b :: _ as t) ->
         if a = b then aux (a :: current) acc t
         else aux [] ((a :: current) :: acc) t  in
    List.rev (aux [] [] list);;

let MyPack1 xs =
    let rec packer current acc xs =
        match xs with
        |[]                 -> []
        |[x]                -> (x::current)::acc
        |x::(y::_ as xys) when y=x -> packer (x::current) acc xys
        |x::(y::_ as xys)          -> packer [] ((x::current)::acc) xys in
    List.rev (packer [] [] xs)
(* x::(y::_ as xys) is the key part but I don't get it *)
;;

