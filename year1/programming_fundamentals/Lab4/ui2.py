from domain import *
from repo import *
from commands import *

def ui1():
    #interfata utilizator
    #prompt meniu principal cu comenzile posibile (1/2/3/4/5/6/7)
    #utilizatorul alege comanda dorita prin tastarea cifrei corespunzatoare
    #comenzile sunt apelate din modulul commands
    #comanda 7 termina executia programului
    commands={"1":command_1,"2":command_2,"3":command_3,"4":command_4,"5":command_5,"6":command_6}
    print("Bine ati venit in aplicatia de gestiune a concurentilor!\n")
    listOfParticipants=create_listOfParticipants()
    history=[]
    while True:
        try:
            print("[Selectati comanda dorita]")
            cmd=input('''1: Adaugare participant/scor
2: Modificare scor participant
3: Tiparire lista participanti
4: Operatii subset participanti
5: Filtrare
6: Undo
7: Exit\n''')
            print()
            while True:
                if cmd=="7":
                    raise ValueError
                try:
                    commands[cmd](history,listOfParticipants)
                    break
                except KeyError:
                    cmd=input("Selectie invalida! Va rugam introduceti cifra corespunzatoare operatiei selectate!\n")
        except ValueError:
            break
