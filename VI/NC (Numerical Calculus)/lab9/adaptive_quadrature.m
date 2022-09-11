function I = adaptive_quadrature(a, b, f, epsilon) 
    I1 = simpson(a, b, f, 2);
    I2 = simpson(a, (a + b) / 2, f, 2) + simpson((a + b) / 2, b, f, 2);
    
    if abs(I1 - I2) < 15 * epsilon
        I = I2;
        return;
    end
    I = adaptive_quadrature(a, (a + b) / 2, f, epsilon / 2) + ...
        adaptive_quadrature((a + b) / 2, b, f, epsilon / 2);
end