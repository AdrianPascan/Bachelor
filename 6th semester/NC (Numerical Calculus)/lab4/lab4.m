% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')

x = [1 1.5 2 3 4];
lgx = [0 0.17609 0.30103 0.47712 0.60206];
Dlgx = divided_differences(x, lgx);

fprintf('For x= 2.5:\n\tNlgx= %f\n\n', newton(x, lgx, Dlgx, 2.5))
fprintf('For x= 3.25:\n\tNlgx= %f\n\n', newton(x, lgx, Dlgx, 3.25))

y = 10:35;
y = y ./ 10;
lgy = log10(y);
Nlgy = zeros(1, length(y));
for i = 1:length(y)
    Nlgy(i) = newton(x, lgx, Dlgx, y(i));
end
fprintf('Error= %f\n\n', max(abs(lgy - Nlgy)))



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')

x = [1 2 3 4 5];
y = [22 23 25 30 28];
Dy = divided_differences(x, y);


% a

fprintf('--- a)\n\n')
fprintf('For x=2.5:\n\t%f\n\n', newton(x, y, Dy, 2.5))


% b

fprintf('--- b)\n\n')

figure(2)
plot(x, y, 'r')
hold on

x0 = [1 1.5 2 2.5 3 3.5 4 4.5 5];
Ny0 = zeros(1, length(x0));
for i = 1:length(x0)
    Ny0(i) = newton(x, y, Dy, x0(i));
end

plot(x0, Ny0, 'b')
hold off



% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')

x = linspace(0, 6, 13);
y = exp(sin(x));

figure(3)
plot(x, y, 'r')
hold on

Dy = divided_differences(x, y);
x0 = linspace(0, 6, 26);
Ny0 = zeros(1, length(x0));
for i = 1:length(x0)
    Ny0(i) = newton(x, y, Dy, x0(i));
end

plot(x0, Ny0, 'b')
hold off



% 4 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 4 ---------------------------------------------------\n\n')

x0 = 115;
epsilon = 0.001;
x = [121 100 144 81 169];
y = sqrt(x);

fprintf('For epsilon=%f:\n\tsqrt(%d)~= %f\n\n', ...
    epsilon, x0, aitken(x, y, epsilon, x0));



% FACULATIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fprintf('- FACULTATIVE -------------------------------------------\n\n')


% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')


x = -5 : 0.1 : 5;
y = sin(x);
epsilon = 0.001;
x0 = linspace(-5, 5, 20);
y0 = sin(x0);

Ay0 = zeros(1, length(x0));
for i = 1 : length(x0)
    Ay0(i) = aitken(x, y, epsilon, x0(i));
end

figure(4)
plot(x, y, 'r');
hold on
plot(x0, y0, '*r');
hold on
plot(x0, Ay0, 'b');
hold off



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')


n = 10;
x = linspace(-1, 1, n + 1);
% x = linspace(-1, 1, n);


% a

fprintf('--- a\n\n')


x0 = 0.1159
lebesque_x0 = sum(abs(fundamental_lagrange(x, x0)))


% b

fprintf('--- b\n\n')


xi = linspace(-1, 1, n);
gi = zeros(1, n);
for i = 1 : n
    gi(i) = sum(abs(fundamental_lagrange(x, xi(i))));
end

figure(5)
plot(xi, gi, 'g')
hold on


% c

fprintf('--- c\n\n')


xi = zeros(1, n);
gi = zeros(1, n);
for i = 1 : n
    xi(i) = cos(((2 .* i - 1) * pi) / (2 * n));
    gi(i) = sum(abs(fundamental_lagrange(x, xi(i))));
end

plot(xi, gi, 'r')


% d

fprintf('--- d\n\n')


xi = zeros(1, n);
gi = zeros(1, n);
for i = 1 : n
    xi(i) = cos(((i - 1) * pi) / n);
    gi(i) = sum(abs(fundamental_lagrange(x, xi(i))));
end

plot(xi, gi, 'b')
hold off




