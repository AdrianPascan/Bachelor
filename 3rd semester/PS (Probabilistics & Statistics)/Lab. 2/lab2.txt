k=0:1:3

% a
P=pdf('F',k,3,0.5)

% b
C=cdf('F',k,3,0.5)

%c
p0=pdf('F',0,3,0.5)
pn1=pdf('F',[0,2,3],3,0.5)

%d
ple2=pdf('F',[0,1,2],3,0.5)
pl2=pdf('F',[0,1],3,0.5)

%e
pge1=pdf('F',[1,2,3],3,0.5)
pg1=pdf('F',[2,3],3,0.5)

%f
n=1000;
a=randn(3,n);
a<0;
x=sum(a<0); %heads/game
v(1)=sum(x==0)/n
v(2)=sum(x==1)/n
v(3)=sum(x==2)/n
v(4)=sum(x==3)/n

plot(v,'m*:')