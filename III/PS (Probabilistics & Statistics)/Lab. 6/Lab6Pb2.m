clear all
clc
close all

x=[22.4, 21.7, 24.5, 23.4, 21.6, 23.3, 22.4, 21.6, 24.8, 20.0];

y=[17.7, 14.8, 19.6, 19.6,12.1,14.8,15.4, 12.6,14.0, 12.2];

n1=length(x);
n2=length(y);
mx=mean(x);
my=mean(y);
sxsqr=var(x);
sysqr=var(y);

alpha=0.05; % significance level

%a)
 typea=0; %two-tailed test
fprintf('a)\n')
fprintf('\n')
fprintf('SIGNIFICANCE LEVEL %f:\n',alpha)
[ha, pa, cia, valstata] = vartest2(x, y, alpha, typea); 
f=sxsqr/sysqr;
fprintf('The value of the test statistc f is %f (given by vartest2 %f)\n',f,valstata.fstat)
RR_Ftest(alpha,n1,n2,typea);
if ha==1
    fprintf('The null hypothesis is rejected (f in RR), i.e. the variances seem to be different\n')
else
    fprintf('The null hypothesis is NOT rejected (f not in RR), i.e. the variances seem to be equal\n')
end    
fprintf('The P-value of the test is %f\n',pa)
if alpha>=pa
    fprintf('The null hypothesis is rejected, i.e. the variances seem to be different\n')
else
    fprintf('The null hypothesis is NOT rejected, i.e. the variances seem to be equal\n')
end

%b)
typeb=1;% right -tailed test
n=n1+n2-2;
if ha==0 
   vartype='equal';
else
   vartype='unequal';
end

fprintf('\n b)\n')
[hb,pb,cib,valstatb]=ttest2(x,y,alpha,typeb,vartype);
t=(mx-my)/sqrt((n1-1)*sxsqr+(n2-1)*sysqr)*sqrt((n1+n2-2)/(1/n1+1/n2));
fprintf('The value of the test statistc t is %f (given by ttest2 %f)\n',t,valstatb.tstat)
RR_Ttest(alpha,n,typeb);
if hb==1
    fprintf('The null hypothesis is rejected (t in RR), i.e. i.e. gas mileage IS higher with premium gasoline \n')
else
    fprintf('The null hypothesis is NOT rejected (t not in RR), i.e. i.e. gas mileage IS NOT higher with premium gasoline\n')
end    
fprintf('The P-value of the test is %.10f\n',pb)
if alpha>=pb
    fprintf('The null hypothesis is rejected, i.e.gas mileage IS higher with premium gasoline\n')
else
    fprintf('The null hypothesis is NOT rejected, i.e. gas mileage IS NOT higher with premium gasoline\n')
end


