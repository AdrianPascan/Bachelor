(defun root (tree)
    (car tree)
)

(defun getLeftSubtree (tree nodes edges)
    (cond
        ((null tree) nil)
        ((= nodes (+ edges (cadr tree))) (list (car tree) (cadr tree)))
        (t (append (list (car tree) (cadr tree)) (getLeftSubtree (cddr tree) (+ 1 nodes) (+ (cadr tree) edges))))
    )
)

(defun left (tree)
    (getLeftSubtree (cddr tree) 0 0)
)

(defun getRightSubtree (tree nodes edges)
    (cond
        ((null tree) nil)
        ((= nodes (+ edges (cadr tree))) (cddr tree))
    )
)

(defun right (tree)
    (getRightSubtree (cddr tree) 0 0)
)

(defun inorder (tree)
    (cond
        ((null tree) nil)
        (t (append (inorder (left tree)) (list (root tree)) (inorder (right tree))))
    )
)
