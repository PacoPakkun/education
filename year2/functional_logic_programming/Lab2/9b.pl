% Sa se construiasca lista (m, ..., n), adica multimea numerelor intregi
% din intervalul [m, n].

% func(M-int, N-int, R-list)
% M-capatul stang al intervalului
% N-capatul drept al intervalului
% R-multimea nr intregi din interval
% flux(i,i,o)
% func(M,N)={
%             [N], daca M=N
%             [M] U func(M+1,N), altfel
%           }

func(N,N,[N]):-!.
func(M,N,[M|Rez]):-X is M+1,func(X,N,Rez).

% Apel la executie:   func(1,5,R).
% se va afisa solutia  R=[1,2,3,4,5].
