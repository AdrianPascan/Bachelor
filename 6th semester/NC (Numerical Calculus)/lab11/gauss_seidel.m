function [x, it] = gauss_seidel(A, b, epsilon)
   n = length(b);
   
   x0 = zeros(n, 1);
   x = zeros(n, 1);
   
   it = 0;
   repeat = 1;
   while repeat
       it = it + 1;
       
       for i = 1 : n
           s = 0;
           for j = 1 : i-1
               s = s + A(i, j) * x(j);
           end
           
           s2 = 0;
           for j = i+1 : n
               s2 = s2 + A(i, j) * x0(j);
           end
           
           x(i) = (b(i) - s - s2) / A(i, i); 
       end
       
       if abs(x - x0) < epsilon
           repeat = 0;
       end
       
       x0 = x;
   end
end