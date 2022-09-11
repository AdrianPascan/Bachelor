function [Hf, derivative_Hf] = hermite_interpolation_polynomial(x, f_x, derivative_f_x)
    m = length(x);
    n = 2*m;
    
    % initialize z, f(z)
    z = zeros(1, n);
    for i = 1:m
        z(2*i-1) = x(i);
        z(2*i) = x(i);
    end
    
    f_z = zeros(1, n);
    for i = 1:m
        f_z(2*i-1) = f_x(i);
        f_z(2*i) = f_x(i);
    end
    
    % divided differences
    Df_z = hermite_divided_differences(z, f_z, derivative_f_x);
    
    %polynomial
    syms x real
    
    Hf = f_z(1);    
    for i = 2:n
        prod = 1;
        for j = 1:i-1
            prod = prod .* (x - z(j));
        end
        prod = prod .* Df_z(1, i-1);
        
        Hf = Hf + prod;
    end
    derivative_Hf = diff(Hf);
    
    Hf = matlabFunction(Hf);
    derivative_Hf = matlabFunction(derivative_Hf);
end