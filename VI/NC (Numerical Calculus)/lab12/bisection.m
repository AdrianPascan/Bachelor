function [c, it] = bisection(a, b, f, epsilon, it_max)
    for it = 0 : it_max
        c = (a + b) / 2;
        
        if abs(f(c)) <= epsilon 
            break;
        end
        
        if f(a) * f(c) <= 0
            b = c;
        else
            a = c;
        end
    end
end