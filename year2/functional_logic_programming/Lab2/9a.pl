% Sa se scrie un predicat care intoarce intersectia a doua multimi.

% find(L-list, E-int)
% L-multime de intregi
% E-element de cautat in multime
% flux(i,i)
% find(l1..ln,E)= {
%                  true, E=l1
%                  false, n=0
%                  find(l2..ln,E), altfel
%                 }
find([H|_],H):-!.
find([H|T],E):-not(H=:=E),find(T,E).

% intersect(L1-list, L2-list ,Rez-list)
% L1-prima multime
% L2-a 2a multime
% Rez-intersectia dintre L1 si L2
% flux(i,i,o)
% intersect(l1..ln,a1..am)= {
%                            [], daca n=0
%                            [l1] U intersect(l2..ln,a1..am), daca
%                              find(a1..am,l1)=true
%                            intersect(l2..ln,a1..am), altfel
%                           }
intersect([],_,[]).
intersect([H|T],L2,[H|Rez]):-find(L2,H),intersect(T,L2,Rez).
intersect([H|T],L2,Rez):-not(find(L2,H)),intersect(T,L2,Rez).

% Apel la executie:   intersect([1,2,3,4,5],[10,2,9,4],R).
% se va afisa solutia  R=[2,4].
