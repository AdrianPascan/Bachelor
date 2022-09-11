% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')


syms x
f = x - cos(x);
der_f = diff(f);
f = matlabFunction(f);
der_f = matlabFunction(der_f);
clear x

[x, it] = newton(pi / 4, f, der_f, .0001, 100)
cos_x = cos(x)



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')


syms E
f = E - .8 * sin(E) - (2 * pi) / 10;
der_f = diff(f);
f = matlabFunction(f);
der_f = matlabFunction(der_f);
clear x

E = 1;
for n = 1 : 6
    [E, it] = newton(E, f, der_f, .000001, 100)
end


% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')


syms x
f = x ^ 3 - x ^ 2 - 1;
f = matlabFunction(f);
clear x

[x, it] = secant(1, 2, f, .0001, 100)



% 4 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 4 ---------------------------------------------------\n\n')


syms x
f = (x - 2) ^ 2 - log(x);
f = matlabFunction(f);
clear x

[x, it] = bisection(1, 2, f, .0001, 100)
[x, it] = false_position(1, 2, f, .0001, 100)








