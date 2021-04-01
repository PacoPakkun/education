import math
def prim(nr):
    if (nr==0)or(nr==1):
        return False
    for i in range(2,round(math.sqrt(nr))+1):
        if nr%i==0:
            return False
    return True

n=int(input())
ok=False
while ok==False:
    n+=1
    if prim(n):
        ok=True
print(n)
