from back_rec import backRec
from back_iter import backIter
import dis

def run():
    while True:
        try:
            cmd=input('[Introduceti metoda de backtracking dorita]\n')
            if cmd=='exit':
                break
            elif cmd=='rec':
                n=input('[Introduceti numarul de paranteze]\n')
                if n=='exit':
                    break
                n=int(n)
                sol=[]
                backRec('', n, sol)
                print(sol)
            elif cmd=='iter':
                n=input('[Introduceti numarul de paranteze]\n')
                if n=='exit':
                    break
                n=int(n)
                sol=[]
                backIter(n, sol)
                print(sol)
            else:
                raise Exception('[Comanda invalida]')
        except Exception as ex:
            print('[Comanda invalida]')
            
run()