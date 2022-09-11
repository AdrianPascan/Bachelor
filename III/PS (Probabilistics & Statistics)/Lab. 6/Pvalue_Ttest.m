function [c]=Pvalue_Ttest(t,n,type)

 switch type
     case -1
         c=cdf('t',t,n-1);
     case 0
         c=2*(1-cdf('t',t,n-1));
     case 1
          c=1-cdf('t',t,n-1);
 end