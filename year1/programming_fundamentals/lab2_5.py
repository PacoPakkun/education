import math

def prim(nr):
    #verifica daca un numar natural nenul este prim
    #returneaza True daca numarul e prim
    #altfel returneaza False 
    if nr<2:
        return False
    for i in range(2,round(math.sqrt(nr))+1):
        if nr%i==0:
            return False
    return True

def test():
    #testeaza functia prim in anumite cazuri exceptionale
    #da eroare daca functia de prim returneaza valori eronate
    assert(prim(0)==False)
    assert(prim(1)==False)
    assert(prim(2)==True)
    assert(prim(-5)==False)
    assert(prim(5)==True)
    assert(prim(35)==False)

test()

#citeste o valoare de la tastatura si verifica validitatea ei
ok=False
while ok == False:
    try:
        n=int(input("Introduceti un numar natural nenul:\n"))
        ok=True
    except ValueError:
        print('Valoarea trebuie sa fie numerica!')

#algoritmul cauta incremental 2 numere prime p,q (p<q,q-p=2) mai mari decat valoarea citita
ok=False
while ok == False:
    n+=1
    if prim(n) and prim(n+2):
        ok=True
print(n,n+2) #cele 2 valori sunt afisate
