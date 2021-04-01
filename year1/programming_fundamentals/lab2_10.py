n=int(input())
p=1
l=[0]*10
while n!=0:
    l[n%10]+=1
    p=p*10
    n=n//10
p=p//10
for i in range(0,10):
    while l[i]!=0:
        n+=i*p
        p=p//10
        l[i]-=1
print(n)
