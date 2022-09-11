function [x, it] = relaxation_matrix(A, b, omega, epsilon)
   n = length(b);
   
   D = diag(diag(A));
   U = triu(A, 1);
   L = tril(A, -1);
   
   x0 = zeros(n, 1);
   x = zeros(n, 1);
   
   it = 0;
   repeat = 1;
   while repeat
       it = it + 1;
       
       x = inv(D + omega * L) * ...
           (((1 - omega) * D - omega * U) * x0 + omega * b);
       
       if abs(x - x0) < epsilon
           repeat = 0;
       end
       
       x0 = x;
   end
end