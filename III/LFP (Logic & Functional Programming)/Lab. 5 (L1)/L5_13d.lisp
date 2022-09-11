; Write a function that deletes from a linear list all occurrences of the maximum element.


; removeMax(l1..ln) = {
;   removeOcc(l1..ln, max(l1..ln))
; }

(defun removeMax(L)
    (removeOcc L (myMax L))
)


; myMax(l1..ln) = {
;   -1, if n = 0
;   l1, if l1 >= myMax(l2..ln)
;   max(l2..ln), if l1 < myMax(l2..ln))
; }

(defun myMax(L)
    (cond
        ((null L) -1)
        ((>= (car L) (myMax (cdr L))) (car L))
        (t (myMax (cdr L)))
    )
)


; removeOcc(l1..ln, e) = {
;   nil, if n = 0
;   removeOcc(l2..ln), if l1 = e
;   l1 U removeOcc(l2..ln), if l1 != e
; }

(defun removeOcc(L E)
    (cond
        ((null L) nil)
        ((= (car L) E) (removeOcc (cdr L) E))
        (t (cons (car L) (removeOcc (cdr L) E)))
    )
)


(print (removeMax '(1 2 3 4 5 4 3 2 1)))
(print (removeMax '(1)))
(print (removeMax '(1 1 1)))
(print (removeMax '(10 5 0 20 5 20 15 10 20 20)))