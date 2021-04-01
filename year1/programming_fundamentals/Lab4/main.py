from tests import test
from ui import ui2
from ui2 import ui1

def main():
    #prog principal
    #apeleaza atat programul in sine, cat si functiile de testare
    test()
    while True:
        meniu=input("[Alegeti format meniu]\n")
        if meniu=='1':
                ui1()
        elif meniu=='2':
                ui2()
        else:
            print('[Comanda invalida]')

main()
