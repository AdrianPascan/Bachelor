function Lf = lagrange(x, f, x0)
    numerator = 0;
    denominator = 0;
    n = length(x);
    for i = 1:n
        ui = 1;
        for j = 1:n
            if i ~= j
                ui = ui .* (x(i) - x(j));
            end
        end
        Ai = 1 ./ ui;
        if x0 - x(i) == 0
            numerator = numerator + f(i);
            denominator = denominator + 1;
        else
            numerator = numerator + Ai * f(i) ./ (x0 - x(i));
            denominator = denominator + Ai ./ (x0 - x(i));
        end
    end
    Lf = numerator / denominator; 
end