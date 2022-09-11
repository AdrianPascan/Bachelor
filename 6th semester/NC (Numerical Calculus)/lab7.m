% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')

time= 1:1:7;
temp= [13 15 20 14 15 13 10];
m= length(time);

a= (m .* sum(time .* temp) - sum(time) .* sum(temp)) ./ ...
    (m .* sum(time .^ 2) - sum(time) .^ 2);
b= (sum(time.^ 2).* sum(temp)- sum(time .* temp).* sum(time)) ./ ...
    (m.* sum(time.^ 2)- sum(time).^ 2);

time0= 8;
temp0= a* time0+ b

E= sum((temp- a.* time+ b).^ 2)

time0= 0:0.25:8;
temp0= a.* time0+ b;

figure(1)
plot(time, temp, 'ro')
hold on
plot(time0, temp0, 'b')
hold off



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')

time= [0 10 20 30 40 60 80 100];
press= [0.0061 0.0123 0.0234 0.0424 0.0738 0.1992 0.4736 1.0133];


% a

fprintf('--- a\n\n')

p1= polyfit(time, press, 1);
p2= polyfit(time, press, 2);

x0= 45;
p1_x0= polyval(p1, x0)
p2_x0= polyval(p2, x0)

p_x0=0.095848;
error1= abs(p_x0 - p1_x0)
error2= abs(p_x0 - p2_x0)


% b

fprintf('--- b\n\n')

x0= 0:2.5:100;
p1_x0= polyval(p1, x0);
p2_x0= polyval(p2, x0);

figure(2)
plot(time, press, 'ro')
hold on
plot(x0, p1_x0, 'b')
plot(x0, p2_x0, 'g')
hold off



% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')

figure(3)
axis([0 3 0 5]);
[x,y] = ginput(10);
[x, indices]= sort(x);
y= y(indices);
xmin= min(0);
xmax= max(length(x));

p2= polyfit(x, y, 2);
x0= 0:0.5:3;
p2_x0= polyval(p2, x0);

plot(x, y, 'ro')
hold on
plot(x0, p2_x0, 'b')
hold off



% FACULATIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fprintf('- FACULTATIVE -------------------------------------------\n\n')


% 4 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 4 ---------------------------------------------------\n\n')


x0 = [0.5 1.7 2.3 4.1 5 5.8 6.3 6.9 7.9 8 8.1 9];
f0 = x0.^3;

A = zeros(length(x0));
b = [];

for k = 1 : length(x0)
    s = 0;
    for j = 1 : length(x0)
        s = s + 1 * f0(j) * x0(j) ^ k;
    end
    b(k) = s;
    
    for i = 1: length(x0)
        s1 = 0;
        for j = 1 : length(x0)
            s1 = s1 + 1 * x0(j) ^ i * x0(j) ^ k;
        end
        A(k, i) = s1;
    end
end

b = reshape(b, [], 1)
x = linsolve(A, b);

fi = [];
for i = 1: length(x0)
    fi(i) = x(i)*(x0(i)^i);
end

figure(4)
plot(x0, fi);









