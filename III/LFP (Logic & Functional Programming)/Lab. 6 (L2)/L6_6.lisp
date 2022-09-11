; Return the list of nodes of a binary tree of type (node no-subtrees list-subtree-1 list-subtree-2 ...) accessed inorder.

; root(tree) = {
;   tree[0]
; }
(defun root(tree)
    (car tree)
)

; leftSubtree(tree) = {
;   tree[1]
; }
(defun leftSubtree(tree)
    (cadr tree)
)

; rightSubtree(tree) = {
;   tree[2]
; }
(defun rightSubtree(tree)
    (caddr tree)
)


; inorder(tree) = {
;   null, if tree is null
;   root U {0}, if leftSubtree and rightSubtree are null
;   inorder(leftSubtree) U root U {2} U inorder(rightSubtree), if leftSubtree and rightSubtree are not null
;   root U {1} U inorder(rightSubtree), if leftSubtree is null and rightSubtree is not null
;   inorder(leftSubtree) U root U {1}, if leftSubtree is not null and rightSubtree is null
; }
(defun inorder(tree)
    (cond
        ((null tree) nil)
        ((and (null (leftSubtree tree)) (null (rightSubtree tree))) (list (root tree) 0))
        ((and (not (null (leftSubtree tree))) (not (null (rightSubtree tree)))) (append (inorder (leftSubtree tree)) (list (root tree) 2) (inorder (rightSubtree tree))))
        ((null (leftSubtree tree)) (append (list (root tree) 1) (inorder (rightSubtree tree))))
        ((null (rightSubtree tree)) (append (inorder (leftSubtree tree)) (list (root tree) 1)))
    )
)


(print (inorder '(A (B) (C (D) (E))) ))
(print (inorder nil ))
(print (inorder '(A) ))
(print (inorder '(A (B)) ))
(print (inorder '(A nil (B)) ))
(print (inorder '(A (B (C))) ))
(print (inorder '(A (B nil (C))) ))
(print (inorder '(A (B (X) (Y)) (C (D) (E)) )))
(print (inorder '(A (B (X)) (C (D) (E)) )))
(print (inorder '(A (B nil (Y)) (C (D) (E)) )))