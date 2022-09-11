function I = trapezium(a, b, f, n) 
%     h = (b - a) / n;
%     xk = zeros(1, n + 1);
%     for k=1:n+1
%         xk(k) = a + (k - 1) * h;
%     end
    
    xk= linspace(a, b, n + 1);
    s= sum(f(xk));
    
    I= ((b- a)/(2* n))* (f(a)+ f(b)+ 2* s);
end 