import math
def prim(nr):
    for i in range(2,round(math.sqrt(nr))+1):
        if nr%i==0:
            return False
    return True
n=int(input())
ok=False
i=1
while (ok==False)and(i<=n//2):
    i+=1
    if prim(i) and prim(n-i):
        ok=True
if ok==True:
    print(i,n-i)
else:
    print('nu')
