function y0 = aitken(x, y, epsilon, x0)
    n = length(x);  % n = m + 1
    
    f = nan(n);
    f(:,1) = y(:);
    for i = 2:n
        for j = 1:i-1
            f(i,j+1) = ( 1 / (x(i) - x(j)) ) * ...
                (f(j,j) * (x(i) - x0) - f(i,j) * (x(j) - x0));
        end
        if abs(f(i,i) - f(i-1,i-1)) <= epsilon
            y0 = f(i,i);
            return;
        end
    end

    y0 = nan;  % error > epsilon
end 