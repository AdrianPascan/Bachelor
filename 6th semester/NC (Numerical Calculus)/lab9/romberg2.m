function T = romberg2(a, b, f, epsilon)
%     trapezium: start with n=1???
    T0 = [trapezium(a, b, f, 1)];
    
    t = trapezium(a, b, f, 2);
    T = [t, (4 ^ (-1) * T0(1) - t) / (4 ^ (-1) - 1)];
    
    i = 1;
    while abs(T(i+1) - T0(i)) >= epsilon
        T0 = T;
        i = i + 1;
        
        T = zeros(1, i+1);
        T(1) = trapezium(a, b, f, i + 1);
        for j = 2:i+1
            T(j) = (4 ^ (-(j - 1)) * T0(j - 1) - T(j-1)) / ...
                (4 ^ (-(j - 1)) - 1);  
        end
    end
        
    T = T(i+1);
end