function QT = romberg(a, b, f, epsilon)
    h = b - a;
    
    QT0 = (h / 2) * (f(a) + f(b));
    QT = (1/2) * QT0 + h * f(a + h / 2);
    k = 1;
    while abs(QT - QT0) >= epsilon
        QT0 = QT;
        k = k + 1;
        
        s = 0;
        for j = 1:2^(k-1)
            s = s + f(a + ((2 * j - 1) / 2 ^ k) * h);
        end
        
        QT = (1 / 2) * QT0 + (h / 2 ^ k) * s;
    end
end