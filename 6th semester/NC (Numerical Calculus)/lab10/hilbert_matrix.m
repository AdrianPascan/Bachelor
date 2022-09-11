function Hn = hilbert_matrix(n)
    Hn = zeros(n, n);
    for i = 1 : n
        for j = 1 : n
            Hn(i, j) = 1 / (i + j - 1);
        end
    end
end