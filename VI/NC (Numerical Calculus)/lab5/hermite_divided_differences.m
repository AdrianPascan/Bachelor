function Df = hermite_divided_differences(z, f_z, derivative_f_x)
    m = length(derivative_f_x);
    n = length(z);
    table = zeros(n, n+1);
    
    table(:, 1) = z;
    table(:, 2) = f_z;
    
    % divided differences of 1st order
    for i = 1: m
        table(2*i-1, 3) = derivative_f_x(i);
    end
    for i = 1: m-1
        table(2*i, 3) = (f_z(2*i+1) - f_z(2*i)) / (z(2*i+1) - z(2*i));
    end
        
    % divided differences of order > 1
    for column = 4: n+1
        order = column - 2;
        for row = 1: n
            if row <= n - order
                table(row, column) = ...
                    (table(row + 1, column - 1) - table(row, column - 1)) ...
                    / ( z(1, row + order) - z(1, row) );
            else
                table(row, column) = nan;
            end
        end
    end
        
    Df = table(1:end-1, 3:end);
end