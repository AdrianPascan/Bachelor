function I = trapezium(a, b, f, secder_f, n) 
    xi= (b-a)/ n;    
    Rnf= ((-(b- a)^ 3)/ 12)* secder_f(xi);
    
    xk= linspace(a, b, n);
    s= sum(f(xk));
    
    I= ((b- a)/(2* n))* (f(a)+ f(b)+ 2* s); %+ Rnf;
end 