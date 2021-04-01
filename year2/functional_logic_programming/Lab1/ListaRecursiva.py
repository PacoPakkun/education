from test._test_multiprocessing import check_enough_semaphores
class Nod:
    def __init__(self, e):
        self.e = e
        self.urm = None
    
class Lista:
    def __init__(self):
        self.prim = None
        

'''
crearea unei liste din valori citite pana la 0
'''
def creareLista():
    lista = Lista()
    lista.prim = creareLista_rec()
    return lista

def creareLista_rec():
    x = int(input("x="))
    if x == 0:
        return None
    else:
        nod = Nod(x)
        nod.urm = creareLista_rec()
        return nod
    
'''
tiparirea elementelor unei liste
'''
def tipar(lista):
    tipar_rec(lista.prim)
    
def tipar_rec(nod):
    if nod != None:
        print (nod.e)
        tipar_rec(nod.urm)
        

'''
    operatii lista
'''
        
def Evida(lista):
    return lista.prim==None

def PrimElem(lista):
    return lista.prim.e

def CreeazaListaVida():
    lista=Lista();
    return lista

def Sublista(lista):
    sub=Lista()
    sub.prim=lista.prim.urm
    return sub

def AdaugaInceput(elem, lista):
    nou=Lista()
    nod=Nod(elem)
    nod.urm=lista.prim
    nou.prim=nod
    return nou

def AdaugaFinal(elem, lista):
    if(Evida(lista)):
        nou=Lista()
        nou=AdaugaInceput(elem, nou)
        return nou
    else:
        return AdaugaInceput(PrimElem(lista), AdaugaFinal(elem, Sublista(lista)))

'''
    4a
'''

def invLista(lista):
    if(Evida(lista)):
        return CreeazaListaVida()
    else:
        return AdaugaFinal(PrimElem(lista), invLista(Sublista(lista)))
    
'''
    4b
'''
def maxLista(lista):
    if(Evida(Sublista(lista))):
        return PrimElem(lista)
    max=maxLista(Sublista(lista))
    if(PrimElem(lista)>max):
        return PrimElem(lista)
    else:
        return max
        
        
def main():
    lista = creareLista()
    tipar(lista)
    print ("max este ")
    print (maxLista(lista))
    print ("lista inversata este:")
    tipar(invLista(lista))
    
main() 
    
    
    
    
    