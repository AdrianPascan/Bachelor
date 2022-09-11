; A linear list is given. Eliminate from the list all elements from N to N steps, N-given.


; removeNth (n, l1..ln) = {
;   nil, if n <= 0
;   removeNthRec(n, l1..ln, 1)
; }
(defun removeNth (N L)
    (cond
        ((<= N 0) nil)
        (t (removeNthRec N L 1))
    )
)

; removeNthRec (n, l1..ln, cI) = {
;   removeNthRec (n, l2.. ln, cI + 1), if cI % n = 0
;   l1 U removeNthRec (n, l2.. ln, cI + 1), otherwise
; }
(defun removeNthRec (N L CI)
    (cond
        ((null L) nil)
        ((= (mod CI N) 0)
            (removeNthRec N (cdr L) (+ CI 1))
        )
        (t 
            (cons (car L) (removeNthRec N (cdr L) (+ CI 1)))
        )
    )
)

(print (removeNth 5 '(1 2 3 4 5 6 7 8 9 10 11 12 13)))
(print (removeNth 2 '(1 2 3 4 5 6 7 8 9 10 11 12 13)))
(print (removeNth 1 '(1 2 3 4 5 6 7 8 9 10 11 12 13)))