function l = fundamental_lagrange(x, x0)
    n = length(x);
    
    l = ones(1, n);
    for i = 1 : n
        for j = 1 : n
            if i ~= j
                l(i) = l(i) * ( (x0 - x(j)) / (x(i) - x(j)) );
            end
        end
    end
end