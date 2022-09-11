% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')

x= [0 pi/2 pi (3*pi)/2 2*pi];
f= sin(x);


% a

fprintf('--- a\n\n')

x0 = pi/ 4;
f0 = sin(x0);
natural_spline0 = spline(x, f, x0);
clamped_spline0 = spline(x, [1 f 0], x0);

fprintf('For x= %f:\n', x0)
fprintf('\t f(x)= %f\n', f0)
fprintf('\t naturalSpline= %f\n', natural_spline0)
fprintf('\t clampedSpline= %f\n', clamped_spline0)
fprintf('\n')


% b

fprintf('--- b\n\n')

x0= 0: 0.1: 2*pi;
f0 = sin(x0);
natural_spline0= spline(x, f, x0);
clamped_spline0= spline(x, [1 f 0], x0);

figure(1)
plot(x0, f0, 'r')
hold on
plot(x0, natural_spline0, 'bx')
plot(x0, clamped_spline0, 'go')
hold off



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')

figure(2)
[x,y] = ginput(5);

[x, indices]= sort(x);
y= y(indices);
xmin= min(0);
xmax= max(length(x));

x0 = xmin: 0.1: xmax;
natural_spline0= spline(x, y, x0);

plot(x, y, 'r')
hold on
plot(x0, natural_spline0, 'bo')



% FACULATIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fprintf('- FACULTATIVE -------------------------------------------\n\n')


% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')


x= 0: pi/4: 2*pi;
f= cos(x);

x0= 0: pi/20: 2*pi;
f0= cos(x0);

p0= zeros(1, length(x0));
i= 2;
for i0=1:length(x0)
    p0(i0) = f(i-1)+ ((f(i)- f(i-1))/ (x(i)- x(i-1))) * (x0(i0)- x(i-1));
    if x0(i0) > x(i)
        i= i+ 1;
    end
end

figure(3)
plot(x0, f0, 'r');
hold on
plot(x0, p0, 'bo');
hold off








