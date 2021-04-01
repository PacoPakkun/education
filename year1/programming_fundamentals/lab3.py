import math

'''prop 4'''
def prim(n):
    #verifica daca un numar este prim
    #primeste prin parametru un numar natural n
    #returneaza True daca e prim, respectiv False altfel
    if n<2:
        return False
    for i in range(2,round(math.sqrt(n))+1):
        if n%i==0:
            return False
    return True

def test_prim():
    #testeaza functia de prim prin verificarea unor valori relevante
    assert(prim(-5)==False)
    assert(prim(0)==False)
    assert(prim(1)==False)
    assert(prim(2)==True)
    assert(prim(5)==True)
    assert(prim(25)==False)

def secventa_prime(l):
    #gaseste secventa de lungime maxima care contine doar nr prime, prin comparare
    #primeste prin parametru o lista l
    #returneaza secventa gasita intr-o lista, respectiv un mesaj in cazul in care aceasta nu exista
    max=-1
    pmax=0
    k=0
    seq=[]
    for i in range(0,len(l)):
        if prim(l[i]):
            k+=1
            if k>max:
                max=k
                pmax=i-k+1
        else:
            k=0
    for i in range(pmax,pmax+max):
        seq.append(l[i])
    return seq

def test_secventa_prime():
    #testeaza functia de prim prin verificarea unor valori relevante
    assert(secventa_prime([])==[])
    assert(secventa_prime([3,3,3])==[3,3,3])
    assert(secventa_prime([1,4,0,5,5,6])==[5,5])
    assert(secventa_prime([4,4,4,4])==[])

'''prop 5'''
def secventa_egale(l):
    #gaseste secventa de lungime maxima care contine doar elemente egale, prin comparare
    #primeste prin parametru o lista l
    #returneaza secventa gasita intr-o lista
    max=1
    pmax=0
    k=1
    seq=[]
    if l==[]:
        return seq
    else:
        for i in range(0,len(l)-1):
            if l[i]==l[i+1]:
                k+=1
                if k>max:
                    max=k
                    pmax=i-k+2
            else:
                k=1
        for i in range(pmax,pmax+max):
            seq.append(l[i])
        return seq

def test_secventa_egale():
    #testeaza functia de prim prin verificarea unor valori relevante
    assert(secventa_egale([])==[])
    assert(secventa_egale([1,2,3,4,5])==[1])
    assert(secventa_egale([1,1,1,1])==[1,1,1,1])
    assert(secventa_egale([1,2,0,6,6,6,9])==[6,6,6])

'''prop 3'''
def cmmdc(a,b):
    #primeste prin parametru 2 valori intregi a,b
    #returneaza cel mai mare divizor comun al celor 2 numere
    a=abs(a)
    b=abs(b)
    if a==0:
        return b
    elif b==0:
        return a
    elif (a==1)or(b==1):
        return 1
    else:
        while a!=b:
            if a>b:
                a-=b
            else:
                b-=a
        return a

def test_cmmdc():
    #testeaza functia cmmdc prin verificarea unor valori relevante
    assert(cmmdc(1,7)==1)
    assert(cmmdc(8,-1)==1)
    assert(cmmdc(4,0)==4)
    assert(cmmdc(-2,0)==2)
    assert(cmmdc(2,5)==1)
    assert(cmmdc(3,-7)==1)
    assert(cmmdc(3,9)==3)
    assert(cmmdc(6,8)==2)
    assert(cmmdc(-12,-9)==3)

def secventa_relativ_prime(l):
    #gaseste secventa de lungime maxima care contine doar valori relativ prime consecutiv
    #primeste prin parametru o lista l
    #returneaza secventa gasita intr-o lista
    max=-1
    pmax=0
    k=1
    seq=[]
    for i in range(0,len(l)-1):
        if cmmdc(l[i],l[i+1])==1:
            k+=1
            if k>max:
                max=k
                pmax=i-k+2
        else:
            k=1
    for i in range(pmax,pmax+max):
        seq.append(l[i])
    return seq

def test_secventa_relativ_prime():
    assert(secventa_relativ_prime([])==[])
    assert(secventa_relativ_prime([2])==[])
    assert(secventa_relativ_prime([2,3])==[2,3])
    assert(secventa_relativ_prime([-5,11,7])==[-5,11,7])
    assert(secventa_relativ_prime([1,5])==[1,5])
    assert(secventa_relativ_prime([0,6])==[])
    assert(secventa_relativ_prime([4,6,5,7,3,9,-18])==[6,5,7,3])

'''test'''
def test():
    #incorporeaza toate functiile de testare
    test_prim()
    test_secventa_prime()
    test_secventa_egale()
    test_cmmdc()
    test_secventa_relativ_prime()

'''meniu'''
def meniu():
    #interfata cu meniu in consola
    #bucla infinita pana la selectarea comandei de break
    #utilizatorul selecteaza operatia dorita prin introducerea cifrei corespunzatoare la tastatura
    #tasta 1 corespunde citirii unui sir
    #tasta 2 corespunde gasirii secventei prime maxime
    #tasta 3 corespunde gasirii secventei cu elemente egale maxime
    #tasta 4 corespunde iesirii din aplicatie
    #exceptii pt selectii invalide
    while True:
        print("Selectati operatia dorita: (1/2/3/4)")
        n=input("1.Citire lista\n2.Gasire secventa maxima prime\n3.Gasire secventa maxima elemente egale\n4.Gasire secventa maxima relativ prime\n5.Iesire din aplicatie\n\n")
        if n=='1':
            while True:
                try:
                    l=list(map(int,input("Introduceti elementele unei liste, separate prin spatiu:\n").split()))
                    print()
                    break
                except ValueError:
                    print("Valorile introduse trebuie sa fie numerice!\n")
        elif n=='2':
            try:
                seq=secventa_prime(l)
                if seq==[]:
                    print("Nu exista secventa prima in lista data!\n")
                else:
                    print("Secventa prima de lungime maxima este",seq,"\n")
            except NameError:
                print("Nu ati introdus nicio lista!\n")
        elif n=='3':
            try:
                seq=secventa_egale(l)
                if seq==[]:
                    print("Nu exista secventa cu elemente egale in lista data!\n")
                else:
                    print("Secventa cu elemente egale de lungime maxima este",seq,"\n")
            except NameError:
                print("Nu ati introdus nicio lista!\n")
        elif n=='4':
            try:
                seq=secventa_relativ_prime(l)
                if seq==[]:
                    print("Nu exista secventa cu elemente egale in lista data!\n")
                else:
                    print("Secventa cu elemente relativ prime de lungime maxima este",seq,"\n")
            except NameError:
                print("Nu ati introdus nicio lista!\n")
        elif n=='5':
            break
        else:
            print("Selectie invalida! Introduceti cifra corespunzatoare operatiei dorite.\n")
'''run'''
def run():
    #incorporeaza arborele tuturor functiilor programului
    #include functia de testare si programul efectiv
    test()
    meniu()

'''main'''
run()
