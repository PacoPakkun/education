; Cret Andreea
; Lab L2, p6
; 6. Sa se construiasca lista nodurilor unui arbore de tipul (1) parcurs in inordine.

; se returneaza lista formata din lista subarborelui stang si lista subarborelui drept al listei subarborilor unui arbore
; (arb: list, nv: integer, nm: integer, st: list)
; nv - numarul de varfuri parcurse
; nm - numarul de muchii parcurse
; st - subarborele stang (variabila colectoare)
;
; parcurg(l1..ln, nv, nm, s1..sm) = {
;	(s1..sm (+) l1..ln), daca nv = 1 + nm
;	(), daca l e vida
;	parcurg(l3..ln, nv+1, nm+l2, s1..sm (+) l1 (+) l2), altfel
; }
(defun parcurg(arb nv nm st)
 (cond
 ((= nv (+ 1 nm)) (list st arb))
 ((null arb) nil)
 (t (parcurg (cddr arb) (+ 1 nv) (+ (cadr arb) nm) (append st (list (car arb) (cadr arb)))))
 )
)

; se returneaza lista formata din subarborele stang al unui arbore
; (arb: list)
;
; stang(l1..ln) = p1, unde p1p2 = parcurg(l3..ln, 0, 0, ())
;
(defun stang(arb)
 (car (parcurg (cddr arb) 0 0 nil))
)

; se returneaza lista formata din subarborele drept al unui arbore
; (arb: list)
;
; drept(l1..ln) = p2, unde p1..pn = parcurg(l3..ln, 0, 0, ())
;
(defun drept(arb)
 (cadr (parcurg (cddr arb) 0 0 nil))
)

; se returneaza lista nodurilor arborelui l parcurs in inordine
; (l: list)
;
; inordine(l1..ln) = {
;	(), daca l e vida
;	inordine(stang(l1..ln)) (+) l1 (+) inordine(drept(l1..ln)), altfel
;  }
(defun inordine(l)
 (cond
 ((null l) nil)
 (t (append (inordine (stang l)) (list (car l)) (inordine (drept l))))
 )
)

; (print (inordine '(A 2 B 0 C 2 D 0 E 0)) -> (B A D C E)
; (print (inordine '(A 2 B 2 C 0 D 1 E 2 F 0 G 1 H 1 I 2 J 0 K 0 L 1 M 2 N 0 P 1 Q 1 R 2 S 0 T 0))) -> (C B F E J I K H G D A N M S R T Q P L) 