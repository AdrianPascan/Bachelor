% Write a program to generate the list of all subsets of sum S with the
% elements of a list (S - given).
%
% Eg: [1,2,3,4,5,6,10] and S=10 =>
%     [[1,2,3,4], [1,3,6], [1,4,5], [2,3,5], [4,6]]
%     (not necessary in this order)


%subsetOfSumS(l1..ln, s) = {
% subsets(l1..ln, s, 0, [])
%}
%
%subsetsOfSumS(L - List, S - Integer, R - List)
%(i,i,o)
subsetsOfSumS(L, S, R) :-
	subsets(L, S, 0, [], R1),
	reverse(R1, R).


%subsets(l1..ln, s, cSum, cSet) = {
% [], n = 0 OR cSum > s
% reverse(cSet), cSum = s
% concat(R1, R2), if cSum < s, where
%    R1 = subsets(L, s, cSum, cSet)
%    R2 = subsets(L, s, cSum + e, cSet U e),
%    (L, e) = candidate(l1..ln)
%}
% subsets(L - List, S - Integer,
%                 CSum - Integer, CSet - List, R - List)
% (i,i,i,i,o)
subsets([], _, _, _, []) :- !.
subsets(_, S, CSum, CSet, R) :- CSum =:= S, !,
	reverse(CSet, RCSet),
	R = [RCSet].
subsets(_, S, CSum, _, R) :- CSum > S, !,
	R = [].
subsets(L, S, CSum, CSet, R) :- CSum < S, !,
	candidate(L, E, L2),
	subsets(L2, S, CSum, CSet, R1),
	CSum2 is CSum + E,
	CSet2 = [E|CSet],
	subsets(L2, S, CSum2, CSet2, R2),
	concat(R1, R2, R).


%candidate(l1..ln) = {
% (l1, l2..ln)
% (e, l1 U L), (e, L) = candidate(l2..ln)
%}
%
%candidate(L - List, E - Integer, R - List)
%(i,o,o)
candidate([H|T], H, T).
candidate([H|T], E, R) :- candidate(T, E, R2), R = [H|R2].


%concat(l11..l1n, l21..l2m) = {
% l21..l2m, n = 0
% l11 U concat(l12..l1n, l21..l2m), otherwise
%}
%
%concat(L1 - List, L2, List, R - List)
%(i,i,o)
concat([], L2, L2) :- !.
concat([H|T], L2, R) :- concat(T, L2, R1), R = [H|R1].


%reverse(l1..ln) = {
% [], n = 0
% l1 U reverse(l2..ln), otherwise
%}
%
%reverse(L - List, R - List)
%(i,o)
reverse([], []) :- !.
reverse([H|T], R) :- reverse(T, R1), concat(R1, [H], R).
