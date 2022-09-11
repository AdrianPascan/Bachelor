; Definiti o functie care determina succesorul unui numar reprezentat cifra cu cifra intr-o lista, fara a converti numarul in baza 10.



; myReverse(l1 .. ln) = {
;   null, if n = 0
;   myReverse(l2 .. ln) U l1, otherwise
; }
(defun myReverse(L)
    (cond
        ((null L) nil)
        (t (append (myReverse (cdr L)) (list (car L))))
    )
)


; succesorRec (l1 .. ln, b) = {
;   1, if n = 0 and b = 1
;   null, if n = 0 and b = 0
;   {0} U succesorRec(l2 .. ln, 1), if l1 = 9 and b = 1
;   {b + 1} U succesorRec(l2 .. ln, 0), otherwise
; }
(defun succesorRec (L B)
    (cond
        ((and (null L) (= B 1)) (list 1))
        ((null L) nil)
        ((and (= (car L) 9) (= B 1)) (cons 0 (succesorRec (cdr L) 1)))
        (t (cons (+ (car L) B) (succesorRec (cdr L) 0)))
    )
)


; succesor (l1 .. ln) = {
;   myReverse( succesorRec (myReverse (l1 .. ln, 1)) )
; }
(defun succesor(L)
    (myReverse (succesorRec (myReverse L) 1))
)


(print (succesor '(1 9 3 5 9 9)))
(print (succesor '(9 9)))

