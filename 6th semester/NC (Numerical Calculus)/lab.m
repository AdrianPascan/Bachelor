% I %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

a = [1 2 3]
% a = [1, 2, 3] ;
b = [4; 5; 6]
% b = [4 5 6]' ;

c = a * b

% d = [4 5 6]
d = b'

e = a.*d 

f = a.^2 

g = a.^d 

v = 1:6 

w = 2 : 3 : 10 

y = 10 : -1 : 0 

exp(a)
exp(1)

sqrt(a) 

m = max(a)
[m,k] = max(a)

h = [-2 -9 8] 

k = abs(h) 

mean(a)
geomean(a) 

sum(a) 
prod(a) 



% II_1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

a = [1 2 3; 4 5 6; 7 8 9]
b = [4 8 12; -1 0 5; 2 3 8]

[m, n] = size(a)

t = b'

c = a * b

d = a.*b

e = a.^2

size(a)
length(a)

m = mean(a)
m1 = mean(a, 2)
g = geomean(a)

s = sum(a)
s1 = sum(a,2)
p = prod(a)
p1 = prod(a,2)

max(a)
min(a)

diag(a)

m > 2
a > b

inv(b)
det(b)

f = abs(b)
b = [16 15 24]'
x = a\b

triu(a)
tril(a)

m = [2 3 5; 7 11 13; 17 19 23]
m(2,1)
m(:,1)
m(2, :)
m(2, 1:2)
m(2, 2:end)
m(2:3, 2:3)


% a

e1 = eye(8)
e2 = eye(5,7)

zeros(5,7)
ones(7,9)


% b

M = magic(4)
sum(M)
sum(diag(M))
sum(diag(fliplr(M)))



% II_2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% 1

p = [2 -5 0 8]
x = 2
polyval(p,x)


% 2

p = [1 -5 -17 21]
roots(p)



% III %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% 1
x = 0:0.01:1 ;
y = exp(10.*x.*(x-1)).*sin(12.*pi.*x) ;
y2 = 3.*exp(5.*x.^2-1).*cos(12.*pi.*x) ;
plot(x, y, 'g')
hold on
plot(x, y2, 'r')


% 2

t = 0:0.01:10*pi ;
a = input('a=') ;
b = input('b=') ;
x = (a+b)*cos(t)-b*cos((a/b+1)*t) ;
y = (a+b)*sin(t)-b*sin((a/b+1)*t) ;
plot(x, y)


% 3

x = 0:0.01:2*pi ;
f1 = cos(x) ;
f2 = sin(x) ;
f3 = cos(2*x) ;

plot(x, f1)
hold on
plot(x, f2, 'g')
plot(x, f3, 'r')


% 4

x = -1:0.01:0 ;
x2 = 0.01:0.01:1 ;
y = x.^3+sqrt(1-x) ;
y2 = x.^3-sqrt(1-x) ;

plot(x, y)
hold on
plot(x, y2, 'r')


% 5

xeven = 0:2:50 ;
xodd = 1:2:50 ;
yeven = xeven/2 ;
yodd = 3*xodd+1;

plot(xeven, yeven)
hold on
plot(xodd, yodd, 'r')


% 6

g = 1/2 ; % 1/(1+1)
for i=1:5
    g = 1/(1+g) ;
end
g


% 7

[X,Y] = meshgrid(-2:0.01:2, -4:0.1:4) ;
Z = exp((-(X-1/2).^2-(Y-1/2).^2)) ;
mesh(X,Y,Z)