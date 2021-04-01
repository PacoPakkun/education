def consistent(x,n):
    '''
        *verifica validitatea solutiei construite
        *o solutie este valida doar daca numarul de paranteze de acelasi tip nu depaseste n/2, respectiv daca nr de paranteze ')' nu depaseste nr de paranteze '('
    '''
    pleft=0
    pright=0
    for el in x:
        if el=='(':
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

def backRec(x,n,sol):
    '''
        *functie recursiva care cauta secvente de paranteze inchise corect
        *foloseste metoda backtracking pt generarea solutiilor
        *primeste ca parametru stringul x, dimensiunea solutiei dorite, respectiv lista de solutii
        *adauga cate un element nou in string (parcurgand un sir de posibile variante)
        *verifica validitatea solutiei construite
        *verifica conditia de stop
        *adauga solutiile gasite in lista de solutii
    '''
    for el in ['(',')']:
        x+=el
        if consistent(x,n):
            if solution(x,n):
                sol.append(x)
            else:
                backRec(x,n,sol)
        x=x[:-1]
        
        