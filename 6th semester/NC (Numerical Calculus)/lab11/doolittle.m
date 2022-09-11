function [L, U] = doolittle(A)
    n = size(A, 1);
    
    I = eye(n);
    a = A;
    U = A;
        
    for k = 1 : n-1
        t = zeros(n, 1);
        for i = k+1 : n
            l = a(i, k) / a(k, k);
            t(i) = l;
        end
        
        e = zeros(1, n);
        e(k) = 1;
        
        M = I - t * e;
        
        U = M * U;
        if k == 1
            L = inv(M);
        else
            L = L * inv(M);
        end
        
        a = M * a;
    end
end