clear all
clc

A=[7 7 4 5 9 9
4 12 8 1 8 7
3 13 2 1 17 7
12 5 6 2 1 13
14 10 2 4 9 11
3 5 12 6 10 7];

x=A(:)';
n=length(x);
fprintf(' Volume %d \n',n)
med=mean(x);
fprintf(' Sample mean %f\n',med)
sigma=5;
m0=9;

type=-1; %left tailed test

alpha1=0.05; %significance level
alpha2=0.01;

fprintf('\n')
fprintf('SIGNIFICANCE LEVEL %f:\n',alpha1)
[h1, p1, ci1, zstat1] = ztest(x, m0, sigma, alpha1, type); 
z1=(med-m0)/sigma*sqrt(n);
fprintf('The value of the test statistc z is %f (given by ztest %f)\n',z1,zstat1)
RR_Ztest(alpha1,type);
if h1==1
    fprintf('The null hypothesis is rejected (z in RR)\n')
else
    fprintf('The null hypothesis is NOT rejected (z not in RR)\n ')
end    
fprintf('The P-value of the test is %f\n',p1)
if alpha1>=p1
    fprintf('The null hypothesis is rejected\n')
else
    fprintf('The null hypothesis is NOT rejected\n')
end


fprintf('\n')

fprintf('SIGNIFICANCE LEVEL %f\n',alpha2)
[h2, p2, ci2, zstat2] = ztest(x, m0, sigma, alpha2, type);
z2=(med-m0)/sigma*sqrt(n);
fprintf('The value of the test statistc z is %f (gien by ztest %f)\n',z2,zstat2)
RR_Ztest(alpha2,type);
if h2==1
    fprintf('The null hypothesis is rejected (z in RR)\n')
else
    fprintf('The null hypothesis is NOT rejected (z not in RR)\n')
end   
fprintf('The P-value of the test is %f\n',p2)
if alpha2>=p2
    fprintf('The null hypothesis is rejected\n')
else
    fprintf('The null hypothesis is NOT rejected\n')
end





