n=int(input())
k=1
i=1
while k<n:
    i+=1
    ok=True
    for j in range(2,i):
        if i%j==0:
            ok=False
            k+=1
            if k==n:
                break
    if ok==True:
        k+=1
if ok==True:
    print(i)
else:
    print(j)
            
