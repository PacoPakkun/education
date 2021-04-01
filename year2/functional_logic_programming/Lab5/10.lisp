; Cret Andreea, grupa 212/2
; L1 P10.

; a) Sa se construiasca o functie care intoarce produsul atomilor numerici dintr-o lista, de la nivelul superficial.

; produs (l: lista)
; produs(l1..ln) = {
;                    1, daca n=0
;                    l1*produs(l2..ln), daca l1 e numar
;                    produs(l2..ln), altfel
;                  }
(defun produs (l)
    (cond
        ((null l) 1)
        ((numberp (car l)) (* (car l) (produs (cdr l))))
        (T (produs (cdr l)))
    )
)

(print (produs '(1 A 2 3 (3 4 5) F)))
;=> 6

; b) Sa se scrie o functie care, primind o lista, intoarce multimea tuturor perechilor din lista. 
; De exemplu: (a b c d) --> ((a b) (a c) (a d) (b c) (b d) (c d))

; pairs(l: lista, a: element)
; pairs(l1..ln,a) = {
;                     [], daca n=0
;                     [[a,l1]] U pairs(l2..ln,a), altfel
;                   }
(defun pairs (l a)
    (cond
        ((null l) nil)
        (T (cons (list a (car l)) (pairs (cdr l) a)))
    )
)

; allPairs(l: lista)
; allPairs(l1..ln) = {
;                       [], daca n=1
;                       pairs(l2..ln, l1) U allPairs(l2..ln), altfel
;                    }
(defun allPairs (l)
    (cond
        ((null (cdr l)) nil)
        (T (append (pairs (cdr l) (car l)) (allPairs (cdr l))))
    )
)

(print (allPairs '(a b c d)))
;=>((A B) (A C) (A D) (B C) (B D) (C D))

; c) Sa se determine rezultatul unei expresii aritmetice memorate in preordine pe o stiva. Exemple:
; (+ 1 3) ==> 4 (1 + 3)
; (+ * 2 4 3) ==> 11 ((2 * 4) + 3)
; (+ * 2 4 - 5 * 2 2) ==> 9 ((2 * 4) + (5 - (2 * 2))

; operatie(op: operator, a: nr, b: nr)
; operatie(op,a,b) = {
;                       a+b, daca op="+"
;                       a-b, daca op="-"
;                       a*b, daca op="*"
;                       a/b, daca op="/"
;                    }
(defun operatie (op a b)
	(cond
		((string= op "+") (+ a b))
		((string= op "-") (- a b))
		((string= op "*") (* a b))
		((string= op "/") (floor a b))
	)
)

; expresie(l: lista)
; expresie(l1..ln) = {
;                       [], daca n=0
;                       operatie(l1,l2,l3) U expresie(l4..ln), daca l2,l3 sunt numere
;                       l1 U expresie(l2..ln), altfel
;                    }
(defun expresie (l)
    (cond
        ((null l) nil)
        ((and (numberp (cadr l)) (numberp (caddr l))) (cons (operatie (car l) (cadr l) (caddr l)) (expresie (cdddr l))))
        (T (cons (car l) (expresie (cdr l))))
    )
)

; solve(l: lista)
; solve(l1..ln) = {
;                   l1, daca n=1
;                   solve(expresie(l1..ln)), altfel
;                 }
(defun solve (l)
    (cond
        ((null (cdr l)) (car l))
        (T (solve (expresie l)))
    )
)

(print (solve '(+ * 2 4 - 5 * 2 2)))
; => 9 ((2 * 4) + (5 - (2 * 2))


; d) Definiti o functie care, dintr-o lista de atomi, produce o lista de perechi (atom n), 
; unde atom apare in lista initiala de n ori. De ex:
; (A B A B A C A) --> ((A 4) (B 2) (C 1)).

; nrAparitii(l: lista, e: element)
; nrAparitii(l1..ln,e) = {
;                           0, daca n=0
;                           nrAparitii(l2..ln,e)+1, daca l1=e
;                           nrAparitii(l2..ln,e), altfel
;                        }
(defun nrAparitii (l e)
    (cond
        ((null l) 0)
        ((equal (car l) e) (+ 1 (nrAparitii (cdr l) e)))
        (T (nrAparitii (cdr l) e))
    )
)

; sterge(l: lista, e: element)
; sterge(l1..ln,e) = {
;                       [], daca n=0
;                       sterge(l2..ln), daca l1=e
;                       l1 U sterge(l2..ln), altfel
;                    }
(defun sterge (l e)
    (cond
        ((null l) nil)
        ((equal (car l) e) (sterge (cdr l) e))
        (T (cons (car l) (sterge (cdr l) e)))
    )
)

; solve(l: lista)
; solve(l1..ln) = {
;                   [], daca n=0
;                   [[l1,nrAparitii(l1..ln,l1)]] U solve(sterge(l2..ln,l1)), altfel
;                 }
(defun solve (l)
    (cond
        ((null l) nil)
        (T (cons (list (car l) (nrAparitii l (car l))) (solve (sterge (cdr l) (car l)))))
    )
)

(print (solve '(A B A B A C A)))
;=> ((A 4) (B 2) (C 1))