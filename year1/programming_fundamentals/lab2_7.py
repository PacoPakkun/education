import math
n=int(input())
p=1
for i in range(2,round(math.sqrt(n))+1):
    if n%i==0:
        p*=n
if p!=1:
    print(p)
else:
    print('err')
