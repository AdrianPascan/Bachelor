function Df = divided_differences(x, f)
    n = length(x);
    table = zeros(n, n+1);
    
    table(:, 1) = x';
    table(:, 2) = f';
    
    for column = 3 : n+1
        order = column - 2;
        for row = 1 : n
            if row <= n - order
                table(row, column) = ...
                    (table(row + 1, column - 1) - table(row, column - 1)) ...
                    / ( x(1, row + order) - x(1, row) );
            else
                table(row, column) = nan;
            end
        end
    end
        
    Df = table(1:end-1, 3:end);
end