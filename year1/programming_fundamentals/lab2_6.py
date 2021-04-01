def fibonacci(n):
        #parcurge sirul lui Fibonacci utilizand formula data
        #returneaza prima valoare mai mare decat n
        p,m=1,1
        while m<=n:
                p,m=m,p+m
        print(m)

#citeste o valoare de la tastatura si verifica validitatea ei
ok=False
while ok==False:
        try:
                n=int(input("Introduceti un numar natural:\n"))
                ok=True
        except ValueError:
                print("Valoarea introdusa trebuie sa fie numerica!")
                
fibonacci(n)
