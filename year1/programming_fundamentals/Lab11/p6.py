def consistent(x):
    for i in range(0,len(x)):
        for j in range(0,len(x)):
            if (i!=j)and(x[i]==x[j]):
                return False
    return True

def solution(x,n):
    sum=0
    for i in x:
        sum+=i
    if sum%n==0:
        return True
    else:
        return False

def back(x,n,sol):
    for el in [1,2,3,4,5,6,7,8,9,10]:
        x.append(el)
        if consistent(x):
            if solution(x,n):
                sol.append(x)
            else:
                back(x,n,sol)
        x=x[:-1]

sol=[]        
back([],10,sol)
print(sol)