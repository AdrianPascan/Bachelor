function [x, it] = secant(x0, x1, f, epsilon, it_max)
    for it = 1 : it_max
        x = x1 - f(x1) * ((x1 - x0) / (f(x1) * f(x0)));
        
        if abs(f(x1)) <= epsilon || ...
                abs(x - x1) <= epsilon || ...
                abs(x - x1) / abs(x) <= epsilon
            break;
        end
        
        x0 = x1;
        x1 = x;
    end
end