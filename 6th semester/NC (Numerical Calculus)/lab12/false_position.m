function [c, it] = false_position(a, b, f, epsilon, it_max)
    for it = 0 : it_max
        c = (f(b) * a - f(a) * b) / (f(b) - f(a));
        
        if abs(f(c)) <= epsilon 
            break;
        end
        
        if f(a) * f(c) < 0
            b = c;
        else
            a = c;
        end
    end
end