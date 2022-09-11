% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')

syms x real
f= 2/ (1+ x^ 2);
secder_f = diff(diff(f));
fourthder_f = diff(diff(secder_f));
f= matlabFunction(f);
secder_f= matlabFunction(secder_f);
fourthder_f= matlabFunction(fourthder_f);
clear x

actual_value = 1.5707


% a

fprintf('--- a\n\n')

trapezium_value = trapezium(0, 1, f, secder_f, 108)


% b

fprintf('--- b\n\n')

x= 0:.001:1;
fx= f(x);
xt= [0 0 1 1];
fxt= [0 f(0) f(1) 0];

figure(1)
plot(x, fx, 'b')
hold on
plot(xt, fxt, 'r')
hold off


% c

fprintf('--- c\n\n')

simpson_value= simpson(0, 1, f, fourthder_f, 100)



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')

syms x y
f= log(x+ 2* y);
f= matlabFunction(f);
clear x y

actual_value= 0.4295545
double_trapezium_value= double_trapezium(1.4, 2, 1, 1.5, f)



% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')

syms x
r= 110;
p= 75;
f= ((60* r)/ (r^ 2- p^ 2))* ((1- (p/ r)^ 2* sin(x))^ (1/2));
secder_f = diff(diff(f));
f= matlabFunction(f);
secder_f = matlabFunction(secder_f);
clear x

actual_value= 6.3131
trapezium_value_30= trapezium(0, 2*pi, f, secder_f, 30)
trapezium_value_100= trapezium(0, 2*pi, f, secder_f, 100)



% 4 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 4 ---------------------------------------------------\n\n')

syms x
f= x* log(x);
secder_f= diff(diff(f));
f= matlabFunction(f);
secder_f = matlabFunction(secder_f);
clear x

n= n_trapezium(1, 2, f, 0.001)

actual_value =0.636294368858383
trapezium_value= trapezium(1, 2, f, secder_f, n)



% 5 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 5 ---------------------------------------------------\n\n')

syms x
f= 1/ (4+ sin(20* x));
fourthder_f= diff(diff(diff(diff(f))));
f= matlabFunction(f);
fourthder_f = matlabFunction(fourthder_f);
clear x

actual_value= 0.8111579
simpson_value_10= simpson(0, pi, f, fourthder_f, 10)
simpson_value_30= simpson(0, pi, f, fourthder_f, 30)


% 6 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 6 ---------------------------------------------------\n\n')

syms t
f= (2/ sqrt(pi))* exp(-t^ 2);
fourthder_f= diff(diff(diff(diff(f))));
f= matlabFunction(f);
fourthder_f = matlabFunction(fourthder_f);
clear t

actual_value= 0.520499876
simpson_value_4= simpson(0, pi, f, fourthder_f, 4)
simpson_value_10= simpson(0, pi, f, fourthder_f, 10)
error_4= abs(actual_value- simpson_value_4)
error_10= abs(actual_value- simpson_value_10)



% FACULATIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fprintf('- FACULTATIVE -------------------------------------------\n\n')


% 7 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 7 ---------------------------------------------------\n\n')

f = @(x, y) exp(y/x);

double_simpson_value = double_simpson(0.1, 0.5, 0.01, 0.25, f, 10, 10)




