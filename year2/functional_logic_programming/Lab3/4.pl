% a) Sa se interclaseze fara pastrarea dublurilor doua liste sortate.

% elim(L-list, R-list)
% L-lista sortata de intregi
% R-lista L din care au fost eliminate duplicatele
% flux(i,o)
% elim(l1..ln)= {
%                [l1], daca n=1
%                elim(l2..ln), daca l1=r1 unde elim(l2..ln)=r1..rm
%                [l1] U elim(l2..ln), altfel
%               }
elim([H],[H]).
elim([H|T],[H|R]):-elim(T,[H|R]),!.
elim([H|T],[H|R]):-elim(T,R).

% interclasare(L1-list, L2-list, R-list)
% L1-prima lista sortata de intregi
% L2-a 2a lista sortata de intregi
% R-interclasarea fara dubluri a L1 si L2
% flux(i,i,o)
% interclasare(l1..ln,p1..pm)= {
%                               elim(p1..pm), daca n=0
%                               elim(l1..ln), daca m=0
%                               interclasare(l2..ln,p2..pm), daca
%                                 l1=p1=r1 unde
%                                 interclasare(l2..ln,p2..pm)=r1..rk
%                               [l1] U interclasare(l2..ln,p2..pm), daca
%                                 l1=p1
%                               interclasare(l2..ln,p1..pm), daca l1<p1
%                                 si l1=r1 unde
%                                 interclasare(l2..ln,p1..pm)=r1..rk
%                               [l1] U interclasare(l2..ln,p1..pm), daca
%                                 l1<p1
%                               interclasare(l1..ln,p2..pm), daca p1<l1
%                                 si p1=r1 unde
%                                 interclasare(l1..ln,p2..pm)=r1..rk
%                               [p1] U interclasare(l1..ln,p2..pm),
%                                 altfel
%                              }
interclasare([],R,R2):-elim(R,R2),!.
interclasare(R,[],R2):-elim(R,R2),!.
interclasare([H|T],[H|T2],[H|R]):-interclasare(T,T2,[H|R]),!.
interclasare([H|T],[H|T2],[H|R]):-interclasare(T,T2,R),!.
interclasare([H|T],[H2|T2],[H|R]):-H<H2,interclasare(T,[H2|T2],[H|R]),!.
interclasare([H|T],[H2|T2],[H|R]):-H<H2,interclasare(T,[H2|T2],R),!.
interclasare(T,[H|T2],[H|R]):-interclasare(T,T2,[H|R]),!.
interclasare(T,[H|T2],[H|R]):-interclasare(T,T2,R).


% b) Se da o lista eterogena, formata din numere intregi si liste de
% numere sortate. Sa se interclaseze fara pastrarea dublurilor toate
% sublistele.

% interclasare2(L-list, R-list)
% L-lista eterogena
% R-interclasarea fara dubluri a tuturor sublistelor lui L
% flux(i,o)
% interclasare2(l1..ln)= {
%                         [], daca n=0
%                         interclasare(l1,interclasare2(l2..ln)), daca
%                           l1 e lista
%                         interclasare2(l2..ln), altfel
interclasare2([],[]).
interclasare2([H|T],Rez):-is_list(H),interclasare2(T,R),interclasare(R,H,Rez),!.
interclasare2([H|T],Rez):-not(is_list(H)),interclasare2(T,Rez).

% Apel la executie:
%  interclasare2([1,[2,3],4,5,[1,4,6],3,[1,3,7,9,10],5,[1,1,11],8],R).
% Se va afisa solutia:
%  R = [1, 2, 3, 4, 6, 7, 9, 10, 11].


