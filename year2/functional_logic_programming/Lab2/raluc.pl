%sa se intercaleze un element pe pozitia a n-a a unei liste
%E - elementul care trebuie inserat
%N - pozitia pe care elementul trebuie inserat
%L - lista initiala
%R - lista rezultata in urma inserarii elementului E pe pozitia N
%insereaza(E: element, N: intreg, L: Lista, R: Lista)
%model de flux: (i,i,i,o), (i,i,i,i)
insereaza(_, _, [], []).
insereaza(E, _, [], [E]):-!.
insereaza(E, N, [H|T], [E|[H|T]]):-N=:=1.
insereaza(_, _, [H|T], [H|T]):-!.
