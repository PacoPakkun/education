% Laborator P3
% Cret Andreea
% grupa 222/2

% 4. Se dau doua numere naturale n si m. Se cere sa se afiseze in toate
% modurile posibile toate numerele de la 1 la n, astfel incat intre orice
% doua numere afisate pe pozitii consecutive, diferenta in modul sa fie
% >=m.

% add_sfarsit(L: list, E: elem, R: list)
% (i, i, o) - determinist
% add_sfarsit(l1l2..ln, elem) = {
%                  elem, daca n = 0
%                  L1 (+) add_sfarsit(l2l3..ln, elem), altfel
%                  }
add_sfarsit([], E, [E]).
add_sfarsit([H|T], E, [H|R]) :- add_sfarsit(T, E, R).


% inversare(L: list, R: list)
% (i, o) - determinist
% inversare(l1l2..ln) = {
%                 [], daca n = 0
%                 add_sfarsit(inversare(l2l3..ln), l1), altfel
%                 }
inversare([], []).
inversare([H|T], Rez) :- inversare(T, R),
                         add_sfarsit(R, H, Rez).

% apare(I: integer, L: list)
% (i, i) - determinist
% apare(elem, l1l2..ln) = {
%                 false, daca n = 0
%                 true, daca l1 = elem
%                 apare(elem, l2l3..ln), altfel
%                 }
apare(I, [I|_]) :- !.
apare(I, [_|T]) :- apare(I, T).


% candidat(N: integer, I: integer)
% (i, o) - nedeterminist
% candidat(n) = {
%                n,
%                candidat(n – 1), daca n > 1
%               }
candidat(N, N).
candidat(N, I) :- N > 1,
                  N1 is N - 1,
                  candidat(N1, I).

% permutari_aux (N: integer, M:integer, Lg: integer, Col: list, L:list)
% (i, i, i, i, o) - nedeterminist
% permutari_aux(n,m,c1..ck) = {
%                inversare(c1..ck), daca n=k
%                permutari_aux(n,m,elem(+)c1..ck), unde
%                  elem=candidat(n), daca not(apare(elem,c1..ck)) si
%                  |elem-c1|>=m
permutari_aux(N, _, N, Col, Inv) :- inversare(Col, Inv),!.
permutari_aux(N, M, Lg, [H|T], L) :- candidat(N, I),
                                \+ apare(I, [H|T]),
                                abs(H-I)>=M,
                                Lg1 is Lg + 1,
                                permutari_aux(N, M, Lg1, [I|[H|T]], L).

% permutari (N: integer, M: integer, L: list)
% (i, i, o) - nedeterminist
% permutari(n,m) = permutari_aux(n,m,[1])
permutari(N, M, L) :- candidat(N, I),
                   permutari_aux(N, M, 1, [I], L).


% Colecteaza toate solutiile gasite pentru predicatul permutari intr-o lista
% problema(N: integer, M:integer, L: list)
% (i, i, o) - determinist
% problema(n,m) = U(permutari(n,m))
problema(N, M, L) :- findall(F, permutari(N, M, F), L).

% ex:
% ?- problema(5,2,R),write(R).
%  [[5,3,1,4,2],[5,2,4,1,3],[4,2,5,3,1],[4,2,5,1,3],[4,1,3,5,2],[3,5,2,4,1],[3,5,1,4,2],[3,1,5,2,4],[3,1,4,2,5],[2,5,3,1,4],[2,4,1,5,3],[2,4,1,3,5],[1,4,2,5,3],[1,3,5,2,4]]
%
%  ?- problema(3,1,R).
% R = [[3, 2, 1], [3, 1, 2], [2, 3, 1], [2, 1, 3], [1, 3, 2], [1, 2,
% 3]].
%
%   ?- problema(3,2,R).
% R = [].
