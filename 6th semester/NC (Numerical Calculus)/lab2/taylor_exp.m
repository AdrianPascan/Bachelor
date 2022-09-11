function Pn = taylor_exp(n, x, x0)
    Pn = 0;
    for k = 0:n
        Pn = Pn + ( (x - x0).^k ./ factorial(k) ) .* exp(x0);  
    end
end

