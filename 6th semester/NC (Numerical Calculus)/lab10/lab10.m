% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')

A = [10 7 8 7; ...
     7 5 6 5; ...
     8 6 10 9; ...
     7 5 9 10];
b = [32 23 33 31]';
n = 4;


% a

fprintf('--- a\n\n')


x = gauss(n, A, b)
cond_A = cond(A)


% b

fprintf('--- b\n\n')


b_tilde = [32.1 22.9 33.1 30.9]';

x_tilde = gauss(n, A, b_tilde)
input_relative_error = norm(b - b_tilde) / norm(b)
output_relative_error = norm(x - x_tilde) / norm(x)
relative_error = output_relative_error / input_relative_error


% c

fprintf('--- c\n\n')


A_tilde = [10 7 8.1 7.2; ...
    7.08 5.04 6 5; ...
    8 5.98 9.89 9; ...
    6.99 4.99 9 9.98];

x_tilde = gauss(n, A_tilde, b)
input_relative_error = norm(A - A_tilde) / norm(A)
output_relative_error = norm(x - x_tilde) / norm(x)
relative_error = output_relative_error / input_relative_error



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')


for n = 10 : 15
    n
    cond_Hn = cond(hilbert_matrix(n))
end



% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')


A = [1 1 1 1; ...
     2 3 1 5; ...
     -1 1 -5 3; ...
     3 1 7 -2];
b = [10 31 -2 18]';
n = 4;

x = gauss(n, A, b)



% FACULATIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fprintf('- FACULTATIVE -------------------------------------------\n\n')


% 4 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 4 ---------------------------------------------------\n\n')


% a

fprintf('--- a\n\n')

for n = 10 : 15
    n
    cond_V = cond(vandermonde_matrix(n))
end


% b

fprintf('--- b\n\n')

for n = 10 : 15
    n
    cond_V = cond(vandermonde_matrix_2(n))
end













