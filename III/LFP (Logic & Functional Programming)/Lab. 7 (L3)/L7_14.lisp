; Write a function that returns the number of atoms in a list, at any level.

; countAtoms(l) = {
;   1, if l atom
;   0, if l is not atom/list
;   SUM(i=1..n) countAtoms(li), if l is list, l = l1..ln
; }
(defun countAtoms(L)
    (cond
        ((atom L) 1)
        ((not (listp L)) 0)
        (t (apply #'+ (mapcar #'countAtoms L)))
    )
)


(print (countAtoms '(1 2 3 A B C D 4 5 E 6) ))
(print (countAtoms '(1 2 3 A () B C D 4 5 E 6) ))
(print (countAtoms '(1 2 3 A (10 X Y) B C D 4 5 E 6) ))
(print (countAtoms '(1 () 2 3 (nil) A (10 X Y) B C D (20 (M N 30) 21 22 (40 K)) 4 5 E 6) ))
