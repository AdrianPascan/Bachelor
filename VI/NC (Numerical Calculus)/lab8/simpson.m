function I = simpson(a, b, f, fourthder_f, n) 
    xi= (b-a)/ n;    
    Rnf= ((-(b- a)^ 5)/ (2880* n^ 4))* fourthder_f(xi);
    
%     xk= linspace(a, b, n+1);
    h= (b- a)/ n;
    xk = zeros(1, n+1);
    for k= 1:n+1
        xk(k)= a+ (k-1)* h;
    end
    
    s= 0;
    for k= 2: n+1
        s= s + f((xk(k-1)+ xk(k))/ 2);
    end
    s2= sum(f(xk));
    
    I= ((b- a)/(6* n))* (f(a)+ f(b)+ 4* s + 2* s2);%+ Rnf;
end 