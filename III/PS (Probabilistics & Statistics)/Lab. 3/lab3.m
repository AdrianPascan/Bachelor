%1

%1 - Normal

mu= 0;
sig= 1;

%a
a1= cdf('norm', 0, mu, sig)
a2= 1 - a1

%b
b1= cdf('norm', -1, mu, sig)
b2= cdf('norm', 1, mu, sig)
b2-b1
1-(b2-b1)

%c
alpha= 0.3;
xa= icdf('norm', alpha, mu, sig)

%d
beta= 0.7;
xb= icdf('norm', 1-beta, mu, sig)

%1 - Student -> not working properly
mu= 0;
sig= 1;
n= 10;

%a
a1= cdf('t', mu, sig, n)
a2= 1 - a1

%b
b1= cdf('t', mu, sig, -1)
b2= cdf('t', mu, sig, 1)
b2-b1
1-(b2-b1)

%c
alpha= 0.3;
xa= icdf('t', alpha, mu, sig)

%d
beta= 0.7;
xb= icdf('t', 1-beta, mu, sig)