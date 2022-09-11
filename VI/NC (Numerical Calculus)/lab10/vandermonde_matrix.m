function V = vandermonde_matrix(n)
    V = zeros(n, n);
    for k = 1 : n
        tk = 1 / k;
        V(k, 1) = 1;
        for j = 2 : n
            V(k, j) = V(k, j - 1) * tk;
        end
    end
end