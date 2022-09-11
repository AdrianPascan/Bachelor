function I = repeated_rectangle(a, b, f, n) 
    x = zeros(1, n);
    x(1) = a + (b - a) / (2 * n);
    for i = 2:n
        x(i) = x(1) + (i - 1) * ((b - a) / n);
    end
    
    I = ((b - a) / n) * sum(f(x));
end