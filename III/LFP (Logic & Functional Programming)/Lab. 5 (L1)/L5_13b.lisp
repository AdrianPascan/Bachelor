; Write a function to test if a linear list of integer numbers has a "valley" aspect (a list has a valley
; aspect if the items decrease to a certain point and then increase. Eg. 10 8 6 17 19 20). A list must
; have at least 3 elements to fullfill this condition.


; isValley (l1..ln) = {
;   isValleyRec(l1..ln, 0)
; }
(defun isValley (L)
    (isValleyRec L 0)
)


; isValleyRec (l1..ln, d) = {
;   0, if n = 0 OR
;      if n = 1 and (d = 0 or d = -1) OR
;      if d = 0 and l1 < l2 OR
;      if d = 1 and l1 > l2
;   1, if n = 1 and d = 1
;   isValleyRec(l2..ln, -1), if d = 0 and l1 >= l2 
;   isValleyRec(l2..ln, 1), if d = -1 and l1 < l2 
;   isValleyRec(l2..ln, d), otherwise
; }

(defun isValleyRec (L D)
    (cond
        ((null L) 0)
        ((and (null (cdr L)) (/= D 1) 0))
        ((and (null (cdr L)) (= D 1)) 1)
        ((and (= D 0) (< (car L) (car (cdr L)))) 0)
        ((and (= D 0) (>= (car L) (car (cdr L)))) (isValleyRec (cdr L) -1))
        ((and (= D 1) (> (car L) (car (cdr L)))) 0)
        ((and (= D -1) (< (car L) (car (cdr L)))) (isValleyRec (cdr L) 1))
        (t (isValleyRec (cdr L) D))
    )
)

(print (isValley '(10 8 6 17 19 20)))
(print (isValley nil))
(print (isValley '(10)))
(print (isValley '(10 8 6)))
(print (isValley '(10 8 6 6)))
(print (isValley '(6 17 19 20)))
(print (isValley '(6 17 19 20 20)))
(print (isValley '(10 8 6 17 19 20 19)))