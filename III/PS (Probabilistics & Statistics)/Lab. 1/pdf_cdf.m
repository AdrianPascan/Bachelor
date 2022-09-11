% a) pdf
n=3;
x=0:n;
p=0.5;
figure(1)
hold on;
plot(x, pdf('bino',x,n,p), 'r*');


% b) cdf
figure(2)
plot(x, cdf('bino',x,n,p), 'bo');

% c) 
pdf('bino',0,3,0.5)
1-pdf('bino',1,3,0.5)

% d)
cdf('bino',2,3,0.5)
cdf('bino',1,3,0.5)

% e)
1-cdf('bino',0,3,0.5)
1-cdf('bino',1,3,0.5)

% f)
