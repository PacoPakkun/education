from datetime import date
a=input()
a=list(map(int,a.split("/")))
t=date.today()
y=int(t.strftime("%Y"))
m=int(t.strftime("%m"))
d=int(t.strftime("%d"))
v=(y-a[2]-1)*365+a[1]*30+a[0]+m*30+d
print(v)
