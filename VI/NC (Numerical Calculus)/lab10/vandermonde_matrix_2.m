function V = vandermonde_matrix_2(n)
    V = zeros(n, n);
    for k = 1 : n
        tk = -1 + (2 / n) * k;
        V(k, 1) = 1;
        for j = 2 : n
            V(k, j) = V(k, j - 1) * tk;
        end
    end
end