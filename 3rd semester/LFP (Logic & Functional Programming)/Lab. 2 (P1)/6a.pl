% Write a predicate to test if a list is a set.


%isSet(l1..ln) = {
% true, n = 0
% false, exists(e, l2...ln) = true
% isSet(l2...ln), otherwise
% }

%(i,o)
%isSet(L-list,R-integer)
isSet([], 1).
isSet([H|L], R):- exists(H,L,1), !, R is 0.
isSet([H|L], R):- exists(H,L,0), !, isSet(L,R1), R is R1.


%exists(e, l1...ln) {
% false, n = 0
% true, e = l1
% exists(e, l2...ln)

%(i,i,o)
%exists(E-integer, L-list, R-integer)
exists(_,[],0):- !.
exists(E,[H|_],R):- E=:=H, !, R is 1.
exists(E,[H|L],R):- E=\=H, !, exists(E,L,R1), R is R1.
