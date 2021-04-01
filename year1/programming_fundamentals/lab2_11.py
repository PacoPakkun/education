n1=input()
n2=input()
ok=True
for i in n1:
    if i not in n2:
        ok=False
for i in n2:
    if i not in n1:
        ok=False
if ok==False:
    print('nu')
else:
    print('da')
