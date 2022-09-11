% 1 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 1 ---------------------------------------------------\n\n')

A = [3 -1 0 0 0 0;
     -1 3 -1 0 0 0;
     0 -1 3 -1 0 0;
     0 0 -1 3 -1 0;
     0 0 0 -1 3 -1;
     0 0 0 0 -1 3
    ];
b = [2 1 1 1 1 2]';
epsilon = .001;

[x, it] = jacobi(A, b, epsilon)
[x, it] = gauss_seidel(A, b, epsilon)
[x, it] = relaxation(A, b, .95, epsilon)



% 2 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 2 ---------------------------------------------------\n\n')


A = [3 1 1;
     -2 4 0;
     -1 2 -6
    ];
b = [12 2 -5]';
epsilon = .00001;

[x, it] = jacobi_matrix(A, b, epsilon)
[x, it] = gauss_seidel_matrix(A, b, epsilon)
[x, it] = relaxation_matrix(A, b, .95, epsilon)



% FACULATIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
fprintf('- FACULTATIVE -------------------------------------------\n\n')


% 3 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('--- 3 ---------------------------------------------------\n\n')


A = [6 2 1 -1;
     2 4 1 0;
     1 1 4 -1;
     -1 0 -1 3;
    ];

[L, U] = doolittle(A)
LU = L * U

b = [8 7 5 1]';
epsilon = .01;

[z, it_z] = jacobi(L, b, epsilon)
[x, it_x] = jacobi(U, z, epsilon)
it = it_z + it_x

[x, it] = jacobi(A, b, epsilon)



% % lecture example
% 
% A = [2 1;
%      6 8
%     ];
% 
% [L, U] = doolittle(A)
% LU = L * U
% 
% b = [3 9]';
% epsilon = .01;
% 
% [z, it_z] = jacobi(L, b, epsilon)
% [x, it_x] = jacobi(U, z, epsilon)
% 
% [x, it] = jacobi(A, b, epsilon)










