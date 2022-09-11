function Nf = newton(x, f, Df, x0)
    m = length(x) - 1;
    
    Nf = f(1, 1);
    
    for i = 1:m
        prod = 1;
        for j = 0:i-1
            prod = prod .* (x0 - x(1, j+1));
        end
        prod = prod .* Df(1,i);
        
        Nf = Nf + prod;
    end
end