% Write a predicate to remove the first three occurrences of an element
% in a list. If the element occurs less than three times, all
% occurrences will be removed.


%removeFirstN(l1...ln, e, nr) = {
% [], n = 0
% l1...ln, nr = 0
% l1 U removeFirstN(l2...ln, e, nr), l1 != e
% removeFirstN(l2...ln, e, nr-1), l1 = e

%(i,i,i,o)
%removeFirstN(L-list, E-integer, Nr-integer, R-list)
removeFirstN([],_,_,[]).
removeFirstN([H|T],_,0,[H|T]):- !.
removeFirstN([H|T],E,Nr,R):- E=H, !,
	Nr1 is Nr-1, removeFirstN(T,E,Nr1,R).
removeFirstN([H|T],E,Nr,R):- E\=H, !,
	removeFirstN(T,E,Nr,R1), R=[H|R1].



%removeFirst3(l1...ln, e) = removeFirstN(l1...ln, e, 3)

%(i,i,o)
%removeFirst3(L-list, E-integer, R-list)
removeFirst3([],_,[]).
removeFirst3([H|T],E,R):- removeFirstN([H|T],E,3,R).

