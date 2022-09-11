% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')

syms x real
f = exp((-1) .* x .^ 2);
f = matlabFunction(f);
clear x

a = 1;
b = 1.5;


% a

fprintf('--- a\n\n')

actual_value = 0.1094
rectangle_value = rectangle(a, b, f)


% b

fprintf('--- b\n\n')

x = 0:.001:1;
fx = f(x);
r = [0, 0, 1, 1, 0];
yr = (f(0) + f(1)) / 2;
fr = [0, yr, yr, 0, 0];

figure(1)
plot(x, fx, 'g')
hold on
plot(r, fr, 'r')
hold off


% c

fprintf('--- c\n\n')

actual_value = 0.1094
repeated_rectangle_value_150 = repeated_rectangle(a, b, f, 150)
repeated_rectangle_value_500 = repeated_rectangle(a, b, f, 500)



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')

syms x real
f= 2 / (1 + x ^ 2);
f= matlabFunction(f);
clear x

a = 0;
b = 1;
epsilon = .00001;

actual_value = 1.570796326794897
romberg_value = romberg(a, b, f, epsilon)
romberg_value2 = romberg2(a, b, f, epsilon)



% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')

syms x real
f= (100 / x ^ 2) * sin(10 / x);
f= matlabFunction(f);
clear x

a = 1;
b = 3;
epsilon = .0001;

actual_value = -1.4260247818
adaptive_quadrature_value = adaptive_quadrature(a, b, f, epsilon)
simpson_value_50 = simpson(a, b, f, 50)
simpson_value_100 = simpson(a, b, f, 100)



