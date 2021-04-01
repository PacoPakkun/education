import math
n=input()
n=int(n)
ok=True
for i in range(2,round(math.sqrt(n))):
    if n%i==0:
        ok=False
if ok==False:
    print('nu')
else:
    print('da')
