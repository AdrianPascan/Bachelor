%oddPositionToFactorial(l1..ln) = oddPosToFact(l1..ln, 1)
%
%oddPositionToFactorial(L - list, R - list)
%oddPositionToFactorial(i, o)

oddPositionToFactorial([], []).
oddPositionToFactorial([H|T], R) :- oddPosToFact([H|T], 1, R).


%oddPosToFact(l1..ln, i) = {
% [], n = 0
% factorial(l1) U oddPosToFact(l2..ln, i+1), i is odd
% l1 U oddPosToFact(l2..ln, i+1), i is even
%}
%
%oddPosToFact(L - list, I - integer, R - list)
%oddPosToFact(i, i, o)

oddPosToFact([], _, []) :- !.
oddPosToFact([H|T], I, R) :- 1 is mod(I, 2), !,
	factorial(I, F),
	I1 is I + 1,
	oddPosToFact(T, I1, R1),
	R = [F|R1].
oddPosToFact([H|T], I, R) :- 0 is mod(I, 2), !,
	I1 is I + 1,
	oddPosToFact(T, I1, R1),
	R = [H|R1].


%factorial(n) = {
% 1, n = 0 OR n = 1
% n * factorial(n - 1)
%}
%
%factorial(N - integer, F - integer)
%factorial(i, o)

factorial(0, 1) :- !.
factorial(1, 1) :- !.
factorial(N, F) :- !,
	N1 is N - 1,
	factorial(N1, F1),
	F is N * F1.
