; Build a function that returns the minimum numeric atom from a list, at any level.


; minNr(l1..ln) = {
;   999999, if n = 0
;   l1, if l1 is number and l1 <= minNr(l2..ln)
;   minNr(l2..ln), if l1 is number and l1 > minNr(l2..ln)
;   minNr(l1), if l1 is list and minNr(l1) <= minNr(l2..ln)
;   minNr(l2..ln), if l1 is list and minNr(l1) > minNr(l2..ln)
; }

(defun minNr(L)
    (cond
        ((null L) 999999)
        ((and (numberp (car L)) (<= (car L) (minNr (cdr L)))) (car L))
        ((and (numberp (car L)) (> (car L) (minNr (cdr L)))) (minNr (cdr L)))
        ((and (listp (car L)) (<= (minNr (car L)) (minNr (cdr L)))) (minNr(car L)))
        ((and (listp (car L)) (> (minNr (car L)) (minNr (cdr L)))) (minNr(cdr L)))
    )
)

(print (minNr '(1 2 3 4 5)))
(print (minNr '(10 9 8 7 6)))
(print (minNr '(10 150 999 2 641)))
(print (minNr '(10 9 8 (10 (1 2 3 4 5) 150 999 2 641) 7 6)))
(print (minNr nil))


;; (defun minNr (L) 
;;     (if (null L) 
;;         100000)
;;         ((setq nextMin (minNr (cdr L)))
;;          (cond
;;             ((and (numberp (car L)) (<= (car L) nextMin)) (car L))
;;             ((and (numberp (car L)) (> (car L) nextMin)) nextMin)
;;             (if (listp (car L))
;;                 (setq currentMin (minNr (car L)))
;;                 (cond
;;                     ((<= currentMin nextMin) currentMin)
;;                     ((> currentMin nextMin) nextMin)
;;                 )
;;             )
;;          )
;;         )
;; )