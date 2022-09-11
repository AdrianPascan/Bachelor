function Tn = chebyshev(n, x)
    if (n == 0)
        Tn = 1;
    elseif (n == 1)
        Tn = x;
    else
        Tn = 2 .* x .* chebyshev(n-1, x) - chebyshev(n-2, x);
    end
end