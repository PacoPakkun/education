; Cret Andreea
; grupa 222/2
; L3 p3

; 3. Sa se construiasca o functie care verifica daca un atom e membru al unei liste nu neaparat liniara.

; verif(l-list, a-atom)
; verif(l1..ln, a) = {
;       1, daca n=1 si l1=a
;       0, daca n=1 si l1!=a
;       max(verif(l1,a),verif(l2,a),..,verif(ln,a)), daca n>1
;   }

(defun verif (l a)
    (cond
        ((and (atom l) (equal a l)) 1)
        ((atom l) 0)
        ((listp l) (apply 'max (mapcar #'(lambda (arg) (verif arg a)) l)))
    )
)

; (print (verif '(1 (2 3) x) '2))

(defun func (l)
    (cond
        ((null l) 0)
        ((and (numberp (car l)) (>= 5 (car l))) (+ 1 (func (cdr l))))
        (t (func (cdr l)))
    )
)

(print (func '(1 3 (2 3) 6 x)))

; (print (verif '(1 (2 3) 4) '4)) -> 1
; (print (verif '(1 (2 3) 4) '2)) -> 1
; (print (verif '(1 (2 3) 4) '5)) -> 0