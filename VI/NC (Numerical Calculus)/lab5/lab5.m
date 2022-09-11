% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')

time = [0 3 5 8 13];
distance = [0 225 383 623 993];
speed = [75 77 80 74 72];

[Hf, derivative_Hf] = hermite_interpolation_polynomial(time, distance, speed);

t = 10;
fprintf('For time t= %d:\n', t)
fprintf('\t distance H(t)= %f\n', Hf(t))
fprintf('\t speed H''(t)= %f\n', derivative_Hf(t))
fprintf('\n')


% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')

x = [1 2];
f = log(x);
derivative_f = [1 0.5];

[Hf, ~] = hermite_interpolation_polynomial(x, f, derivative_f);

x0 = 1.5;
f0 = log(1.5);
Hf0 = Hf(1.5);
error = abs(f0 - Hf0);

fprintf('For x= %d:\n', x0)
fprintf('\t f(x)= %f\n', f0)
fprintf('\t Hf(x)= %f\n', Hf0)
fprintf('\t absoluteError= %f\n', error)
fprintf('\n')


% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')

x = linspace(-5, 5, 15);
f = sin(2.* x);
derivative_f = 2.* cos(2.* x);
[Hf, ~] = hermite_interpolation_polynomial(x, f, derivative_f);

x0 = linspace(-7.5, 7.5, 45);
f0 = sin(2.* x0);
Hf0 = Hf(x0);

figure(1)
plot(x0, f0, 'g')
hold on
plot(x0, Hf0, 'ro')
hold off



% FACULATIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fprintf('- FACULTATIVE -------------------------------------------\n\n')


% 4 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 4 ---------------------------------------------------\n\n')

x = [8.3 8.6];
f = [17.56492 3.116256];
derivative_f = [3.116256 3.151762];

[Hf, ~] = hermite_interpolation_polynomial(x, f, derivative_f);

x0 = 8.4;
f0 = x0 * log(x0);
Hf0 = Hf(x0);
error = abs(f0 - Hf0);

fprintf('For x= %d:\n', x0)
fprintf('\t f(x)= %f\n', f0)
fprintf('\t Hf(x)= %f\n', Hf0)
fprintf('\t absoluteError= %f\n', error)
fprintf('\n')


% 5 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 5 ---------------------------------------------------\n\n')


% a

fprintf('--- a\n\n')

x = [1 1.05];
f = -1.* exp(x) .* (exp(x)- 3.* x); % 3.* x.* exp(x)- exp(2.* x)
derivative_f = -1.* exp(x) .* (2.* exp(x)- 3.* x- 3);

[Hfa, ~] = hermite_interpolation_polynomial(x, f, derivative_f);

x0 = 1.03;
f0 = -1.* exp(x0) .* (exp(x0)- 3.* x0);
Hf0 = Hfa(x0);
error = abs(f0 - Hf0);

fprintf('For x= %f:\n', x0)
fprintf('\t f(x)= %f\n', f0)
fprintf('\t Hf(x)= %f\n', Hf0)
fprintf('\t absoluteError= %f\n', error)
fprintf('\n')


% b

fprintf('--- b\n\n')

x = [1 1.05 1.07];
f = -1.* exp(x) .* (exp(x)- 3.* x); % 3.* x.* exp(x)- exp(2.* x)
derivative_f = -1.* exp(x) .* (2.* exp(x)- 3.* x- 3);

[Hfb, ~] = hermite_interpolation_polynomial(x, f, derivative_f);

x0 = 1.03;
f0 = -1.* exp(x0) .* (exp(x0)- 3.* x0);
Hf0 = Hfb(x0);
error = abs(f0 - Hf0);

fprintf('For x= %f:\n', x0)
fprintf('\t f(x)= %f\n', f0)
fprintf('\t Hf(x)= %f\n', Hf0)
fprintf('\t absoluteError= %f\n', error)
fprintf('\n')


% c

fprintf('--- b\n\n')

x0 = linspace(0.5, 1.57, 30);
f0 = -1.* exp(x0) .* (exp(x0)- 3.* x0);
Hf0a = Hfa(x0);
Hf0b = Hfb(x0);

figure(2)
plot(x0, f0, 'g')
hold on
plot(x0, Hf0a, 'rx')
plot(x0, Hf0b, 'b*')
hold off







