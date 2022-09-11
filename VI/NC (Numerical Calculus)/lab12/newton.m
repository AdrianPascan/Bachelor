function [x, it] = newton(x0, f, der_f, epsilon, it_max)
    for it = 0 : it_max
        x = x0 - f(x0) / der_f(x0);
        
        if abs(f(x0)) <= epsilon || ...
                abs(x - x0) <= epsilon || ...
                abs(x - x0) / abs(x) <= epsilon
            break;
        end
        
        x0 = x;
    end
end