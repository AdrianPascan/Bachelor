function I = simpson(a, b, f, n) 
%     h= (b- a)/ n;
%     xk = zeros(1, n+1);
%     for k= 1:n+1
%         xk(k)= a+ (k-1)* h;
%     end
    
    xk= linspace(a, b, n + 1);
    s= 0;
    for k= 2: n + 1
        s= s + f((xk(k-1)+ xk(k))/ 2);
    end
    s2= sum(f(xk));
    
    I= ((b - a) / (6 * n)) * (f(a) + f(b) + 4 * s + 2 * s2);
end 