function n = n_trapezium(a, b, f, epsilon) 
    M2f= max(f(a:.001:b));
    const= ((b- a)^ 3* M2f)/ (12* epsilon);
    
    n = 1;
    while not(const < n^2)
        n = n+ 1;
    end
end 