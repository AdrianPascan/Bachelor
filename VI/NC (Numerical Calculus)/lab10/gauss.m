function x = gauss(n, A, b)
   for p = 1 : n-1
       [~, max_index] = max(abs(A(p : n, p)));
       
       q = max_index + p - 1;
       
       if A(q, p) == 0
           x = -1;
           return
       end
       
       if q ~= p
           aux = A(q, :);
           A(q, :) = A(p, :);
           A(p, :) = aux;
           
           aux = b(q);
           b(q) = b(p);
           b(p) = aux;
       end
       
       for i = p+1 : n
           coeff = A(i, p) / A(p, p);
           for j = p : n
               A(i, j) = A(i, j) - coeff * A(p, j);
           end
           b(i) = b(i) - coeff * b(p);
       end
   end
   
   if A(n, n) == 0
       x = -1;
       return
   end

   x = zeros(1, n);
   for i = n : -1 : 1
       x(i) = b(i);
       for j = i+1 : n
           x(i) = x(i) - A(i, j) * x(j);
       end
       x(i) = x(i) / A(i,i);
   end
   x = x';
end