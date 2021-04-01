% Sa se calculeze suma alternanta a elementelor unei liste
% (l1 - l2 + l3 ...).

f([],_,0).
f([H1|T],I,SUM2):-(I mod 2=:=0),f(T,I+1,SUM),SUM2 is SUM-H1.
f([H1|T],I,SUM2):-not(I mod 2=:=0),f(T,I+1,SUM),SUM2 is SUM+H1.
