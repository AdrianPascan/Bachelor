function [x, it] = gauss_seidel_matrix(A, b, epsilon)
   n = length(b);
   
   D = diag(diag(A));
   U = triu(A, 1);
   L = tril(A, -1);
   
   x0 = zeros(n, 1);
   x = zeros(n, 1);
   
   it = 1;
   repeat = 1;
   while repeat
       it = it + 1;
       
       x = inv(D + L) * (-U * x0 + b);
       
       if abs(x - x0) < epsilon
           repeat = 0;
       end
       
       x0 = x;
   end
end