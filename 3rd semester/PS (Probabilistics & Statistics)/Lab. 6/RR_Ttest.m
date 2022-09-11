function RR_Ttest(alpha,n,type)

 switch type
     case -1
         l=-Inf;
         r=icdf('t',alpha,n-1);
         fprintf('The rejection region, RR, is (%f, %f)\n',l,r)
     case 0
         r=icdf('t',1-alpha/2,n-1);
         l=-r;
         fprintf('The rejection region, RR, is (%f,%f)U(%f, %f)\n',-Inf,l,r,Inf)
     case 1
         l=icdf('t',1-alpha,n-1);
         r=Inf;
         fprintf('The rejection region, RR, is (%f, %f)\n',l,r)
 end