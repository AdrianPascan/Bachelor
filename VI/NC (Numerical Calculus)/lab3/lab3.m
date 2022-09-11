% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 -----------------------------------------------------\n')

x = [1930 1940 1950 1960 1970 1980];
f = [123203 131669 150697 179323 203212 226505];

lagrange(x, f, 1955)
lagrange(x, f, 1995)



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 -----------------------------------------------------\n')

x = [100 121 144];
f = [10 11 12];

lagrange(x, f, 115)



% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 -----------------------------------------------------\n')

x = 0:0.01:10;
f = (1 + cos(pi .* x)) ./ (1 + x); 
plot(x, f, 'g')

points_no = 21;
x0 = linspace(0, 10, points_no);
Lf = zeros(1, points_no);
for i = 1 : points_no
    Lf(i) = lagrange(x, f, x0(i));
end

hold on
plot(x0, Lf, 'r')



% FACULATIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fprintf('- FACULTATIVE -------------------------------------------\n\n')


% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')


x = [0 pi/4 pi/3];
f = cos(x);


% a

fprintf('--- a\n\n')


u = [1 1 1];
A = [];
for i = 1 : 2
    for j = i+1 : 3
        u(i) = u(i) * (x(i) - x(j));
        u(j) = u(j) * (x(j) -  x(i));
    end
    A(end + 1) = 1 / u(i);
end
A(end + 1) = 1 / u(3);

x2 = linspace(-pi/4, pi/2);
l0 = ((x2 - x(2)) .* (x2 - x(3))) ./ (u(1));
l1 = ((x2 - x(1)) .* (x2 - x(3))) ./ (u(2));
l2 = ((x2 - x(1)) .* (x2 - x(2))) ./ (u(3));
lagrange = l0 .* f(1) + l1 .* f(2) + l2 .* f(3);


% b

fprintf('--- b\n\n')


x3 = pi/6;
L0 = ((x3 - x(2)) * (x3 - x(3))) / u(1);
L1 = ((x3 - x(1)) * (x3 - x(3))) / u(2);
L2 = ((x3 - x(1)) * (x3 - x(2))) / u(3);
classical_lagrange = L0 * f(1) + L1 * f(2) + L2 * f(3)

numerator = 0;
denominator = 0;
for i =1 : 3
    numerator = numerator +  A(i) * f(i) / (x3 - x(i));
    denominator = denominator + A(i) / (x3 - x(i));
end

barycentric_lagrange = numerator/denominator


% c

fprintf('--- c\n\n')


figure(4)
subplot(3, 3, [1 2 3]);
hold on
plot(x2, l0, 'r');
plot(x2, l1, 'b');
plot(x2, l2, 'g');

subplot(3, 3, [4 5 6]);
hold on
f2 = cos(x2);
plot(x2, f2, 'r');
plot(x2, lagrange, 'b');


% d

fprintf('--- d\n\n')


subplot(3, 3, [7 8 9]);
hold on

x = [-pi/6 0 pi/5];
f = cos(x);
u = [1 1 1];

for i = 1 : 2    
    for j =  i+1 : 3
        u(i) = u(i) * (x(i) - x(j));
        u(j) = u(j) * (x(j) -  x(i));
    end
end

l0 = ((x2 - x(2)) .* (x2 - x(3))) ./ (u(1));
l1 = ((x2 - x(1)) .* (x2 - x(3))) ./ (u(2));
l2 = ((x2 - x(1)) .* (x2 - x(2))) ./ (u(3));
lagrange = l0 .* f(1) + l1 .* f(2) + l2 .* f(3);

plot(x2,lagrange,'r');


x = [-pi/8 pi/12 pi/9 pi/3];
f = cos(x);

u = [1 1 1 1];
for i=1:3    
    for j = i+1:4
        u(i) = u(i)* (x(i) - x(j));
        u(j) = u(j)* (x(j) -  x(i));
    end
end

l0 = ((x2 - x(2)) .* (x2 - x(3)) .* (x2 - x(4))) ./ (u(1));
l1 = ((x2 - x(1)) .* (x2 - x(3)) .* (x2 - x(4))) ./ (u(2));
l2 = ((x2 - x(1)) .* (x2 - x(2)) .* (x2 - x(4))) ./ (u(3));
l3 = ((x2 - x(1)) .* (x2 - x(2)) .* (x2 - x(3))) ./ (u(4));
lagrange = l0 .* f(1) + l1 .* f(2) + l2 .* f(3) + l3 .* f(4);

plot(x2, lagrange, 'b');



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')


clear


% a

fprintf('--- a\n\n')


x = -5 : 0.1 : 5;
f = 1 ./ (1 + x .^ 2);

x4 = linspace(-5, 5, 4);
f4 = 1 ./ (1 + x4 .^ 2);

Lf4 = zeros(1, length(x));
for i = 1 : length(x)
    Lf4(i) = lagrange(x4, f4, x(i));
end

x8 = linspace(-5, 5, 8);
f8 = 1 ./ (1 + x8 .^ 2);

Lf8 = zeros(1, length(x));
for i = 1 : length(x)
    Lf8(i) = lagrange(x8, f8, x(i));
end

x12 = linspace(-5, 5, 12);
f12 = 1 ./ (1 + x12 .^ 2);

Lf12 = zeros(1, length(x));
for i = 1 : length(x)
    Lf12(i) = lagrange(x12, f12, x(i));
end

figure(5)
plot(x, f, 'r');
hold on
plot(x, Lf4, 'b')
plot(x, Lf8, 'm')
plot(x, Lf12, 'k')


% b

fprintf('--- b\n\n')


a = -5;
b = 5;
n = 15;
x = zeros(1, n);
for i = 1 : n
    xi = cos(((2 * i - 1) * pi) / (2 * n));
    x(i) = (1 / 2) * ((b - a) * xi + a + b); 
end
f = 1 ./ (1 + x .^ 2);

x4 = linspace(-5, 5, 4);
f4 = 1 ./ (1 + x4 .^ 2);

Lf4 = zeros(1, length(x));
for i = 1 : length(x)
    Lf4(i) = lagrange(x4, f4, x(i));
end

x8 = linspace(-5, 5, 8);
f8 = 1 ./ (1 + x8 .^ 2);

Lf8 = zeros(1, length(x));
for i = 1 : length(x)
    Lf8(i) = lagrange(x8, f8, x(i));
end

x12 = linspace(-5, 5, 12);
f12 = 1 ./ (1 + x12 .^ 2);

Lf12 = zeros(1, length(x));
for i = 1 : length(x)
    Lf12(i) = lagrange(x12, f12, x(i));
end

figure(6)
plot(x, f, 'r');
hold on
plot(x, Lf4, 'b')
plot(x, Lf8, 'm')
plot(x, Lf12, 'k')


% c

fprintf('--- c\n\n')


a = -5;
b = 5;
n = 15;
x = zeros(1, n);
for i = 1 : n
    xi = cos(((i - 1) * pi) / n);
    x(i) = (1 / 2) * ((b - a) * xi + a + b); 
end
f = 1 ./ (1 + x .^ 2);

x4 = linspace(-5, 5, 4);
f4 = 1 ./ (1 + x4 .^ 2);

Lf4 = zeros(1, length(x));
for i = 1 : length(x)
    Lf4(i) = lagrange(x4, f4, x(i));
end

x8 = linspace(-5, 5, 8);
f8 = 1 ./ (1 + x8 .^ 2);

Lf8 = zeros(1, length(x));
for i = 1 : length(x)
    Lf8(i) = lagrange(x8, f8, x(i));
end

x12 = linspace(-5, 5, 12);
f12 = 1 ./ (1 + x12 .^ 2);

Lf12 = zeros(1, length(x));
for i = 1 : length(x)
    Lf12(i) = lagrange(x12, f12, x(i));
end

figure(7)
plot(x, f, 'r');
hold on
plot(x, Lf4, 'b')
plot(x, Lf8, 'm')
plot(x, Lf12, 'k')


