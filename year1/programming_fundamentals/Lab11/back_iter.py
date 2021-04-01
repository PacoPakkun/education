def consistent(x,n):
    '''
        *verifica validitatea solutiei construite
        *o solutie este valida doar daca numarul de paranteze de acelasi tip nu depaseste n/2, respectiv daca nr de paranteze ')' nu depaseste nr de paranteze '('
    '''
    pleft=0
    pright=0
    for el in x:
        if el==0:
            pleft+=1
        else:
            pright+=1
        if (pleft>n/2)or(pright>n/2)or(pright>pleft):
            return False
    return True

def solution(x,n):
    '''
        *verifica conditia de stop
        *solutia este completa daca lungimea ei este n
    '''
    return len(x)==n

def solutionFound(x,sol):
    '''
        *adauga solutia gasita in lista de solutii
    '''
    solution=''
    for el in x:
        if el==0:
            solution+='('
        else:
            solution+=')'
    sol.append(solution)

def backIter(n,sol):
    '''
        *functie iterativa care cauta secvente de paranteze inchise corect
        *foloseste metoda backtracking pt generarea solutiilor
        *primeste ca parametru dimensiunea solutiei dorite, respectiv lista de solutii
        *construieste un string pt construirea si testarea solutiilor
        *adauga cate un element nou in string (parcurgand un sir de posibile variante)
        *verifica validitatea solutiei construite
        *verifica conditia de stop
        *adauga solutiile gasite in lista de solutii
    '''
    x=[-1]
    while len(x)>0:
        found=False
        while not found and x[-1]<1:
            x[-1]=x[-1]+1
            found=consistent(x, n)
        if found:
            if solution(x, n):
                solutionFound(x, sol)
            x.append(-1)
        else:
            x=x[:-1]
            
                
        

