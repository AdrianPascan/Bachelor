%2

%a)

n= 30;
p= 0.3;
k= 0:n;
y= pdf('bino', k, n, p);
figure(1)
hold on
bar(k, y)

med= n*p;
sig= sqrt(n*p*(1-p));

x= (med - 3 * sig):0.01:(med+3*sig);
z= pdf('norm', x, med, sig);
plot(x, z, 'Color', 'r', 'Linewidth', 2)

%b)
n= 30;
p= 0.05;
k= 0:n;
y= pdf('bino', k, n, p);
figure(1)
hold on
bar(k, y)

L= n*p;
Vi= floor(L - 3 * sqrt(L))
Vf= ceil(L + 3 * sqrt(L))
x= Vi : Vf;
z= pdf('poiss', x, L);
figure(2)
bar(x, z, 'r')
