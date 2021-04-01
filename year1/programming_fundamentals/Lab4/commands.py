from srv import *

'''cmd 1'''

def command_1_1(history,listOfParticipants):
    #comanda 1 din meniul aferent functionalitatii de adaugare
    #primeste prin parametru lista de scoruri
    #corespunde inserarii unui participant, respectiv scorul sau
    #apeleaza functiile din modulul srv1 in urma validarii datelor
    while True:
        print("Introduceti scorul participantului:")
        while True:
            try:
                scor_participant=float(input())
                if 1<=scor_participant<=10:
                    break
                else:
                    print("Valoare invalida! Scorul apartine intervalului [1,10].\n")
            except ValueError:
                print("Valoare invalida! Scorul este o valoare reala.\n")
        #try:
        new_participant(history,listOfParticipants,scor_participant)
        break
        #except Exception as ex:
            #print(str(ex))

def command_1_2(history,listOfParticipants):
    #comanda 2 din meniul aferent functionalitatii de adaugare
    #primeste prin parametru lista de scoruri
    #corespunde inserarii unui scor la un participant la alegere
    #apeleaza functiile din modulul srv1 in urma validarii datelor
    while True:
        print("Introduceti numarul de concurs al participantului:")
        while True:
            try:
                id_participant=int(input())
                if id_participant<1:
                    raise ValueError
                else:
                    break
            except ValueError:
                print("Valoare invalida! Numarul de concurs este un nr natural nenul.")
        print("Introduceti scorul participantului:")
        while True:
            try:
                scor_participant=float(input())
                if 1<=scor_participant<=10:
                    break
                else:
                    print("Valoare invalida! Scorul apartine intervalului [1,10].\n")
            except ValueError:
                print("Valoare invalida! Scorul este o valoare reala.\n")
        try:
            insert_scorParticipant(history,listOfParticipants,id_participant,scor_participant)
            break
        except Exception as ex:
            print(str(ex))

'''cmd 2'''

def command_2_1(history,listOfParticipants):
    #comanda 1 din meniul aferent functionalitatii de modificare
    #primeste prin parametru lista de scoruri
    #corespunde stergerii scorului unui participant dat
    #apeleaza functiile din modulul srv2 in urma validarii datelor
    while True:
        print("Introduceti numarul de concurs al participantului:")
        while True:
            try:
                id_participant=int(input())
                if id_participant<1:
                    raise ValueError
                else:
                    break
            except ValueError:
                print("Valoare invalida! Numarul de concurs este un nr natural nenul.")
        try:
            delete_scorParticipant(history,listOfParticipants,id_participant)
            break
        except Exception as ex:
            print(str(ex))
    print("Scorul participantului",id_participant,"a fost sters!\n")


def command_2_2(history,listOfParticipants):
    #comanda 2 din meniul aferent functionalitatii de modificare
    #primeste prin parametru lista de scoruri
    #corespunde stergerii scorului unui interval de participanti
    #apeleaza functiile din modulul srv2 in urma validarii datelor
    pass

def command_2_3(history,listOfParticipants):
    #comanda 3 din meniul aferent functionalitatii de modificare
    #primeste prin parametru lista de scoruri
    #corespunde inlocuirii scorului unui participant
    #apeleaza functiile din modulul srv2 in urma validarii datelor
    pass

'''cmd 3'''

def command_3_1(history,listOfParticipants):
    #comanda 1 din meniul aferent functionalitatii de tiparire
    #primeste prin parametru lista de scoruri
    #corespunde afisarii idurilor participantilor cu scor mai mic decat un scor dat
    #apeleaza functiile din modulul srv3 in urma validarii datelor
    print("Dati un scor:")
    while True:
        try:
            scor=float(input())
            if 1<scor<=10:
                break
            else:
                print("Valoare invalida! Scorul apartine intervalului [1,10].\n")
        except ValueError:
            print("Valoare invalida! Scorul trebuie sa fie o valoare numerica.\n")
    print("Participantii cu scorul mai mic decat",scor,"sunt:",lowerGradesThan(history,listOfParticipants,scor),"\n")

def command_3_2(history,listOfParticipants):
    #comanda 2 din meniul aferent functionalitatii de tiparire
    #primeste prin parametru lista de scoruri
    #corespunde afisarii idurilor participantilor in ordine descresc in fct de scor
    #apeleaza functiile din modulul srv3 in urma validarii datelor
    print("In ordinea scorului, participantii sunt:",get_leaderboard(history,listOfParticipants),"\n")

def command_3_3(history,listOfParticipants):
    #comanda 3 din meniul aferent functionalitatii de tiparire
    #primeste prin parametru lista de scoruri
    #corespunde afisarii idurilor participantilor cu scor mai mare decat un scor dat, in ordine descresc in fct de scor
    #apeleaza functiile din modulul srv3 in urma validarii datelor
    print("Dati un scor:")
    while True:
        try:
            scor=float(input())
            if 1<scor<=10:
                break
            else:
                print("Valoare invalida! Scorul apartine intervalului [1,10].\n")
        except ValueError:
            print("Valoare invalida! Scorul trebuie sa fie o valoare numerica.\n")
    print("Participantii cu scorul mai mare decat",scor,"sunt:",higherGradesThan(history,listOfParticipants,scor),"\n")

'''cmd 5'''

def command_5_1(history,listOfParticipants):
    #comanda 1 din meniul aferent functionalitatii de filtrare
    #primeste prin parametru lista de scoruri
    #corespunde filtrarii participantilor cu scorul multiplu de un scor dat
    #apeleaza functiile din modulul srv5 in urma validarii datelor
    print("Dati un scor:")
    while True:
        try:
            scor=float(input())
            if 1<=scor<=10:
                break
            else:
                print("Valoare invalida! Scorul apartine intervalului [1,10].\n")
        except ValueError:
            print("Valoare invalida! Scorul trebuie sa fie o valoare numerica.\n")
    filter_byMultiple(history,listOfParticipants,scor)
    print("Participantii au fost filtrati!\n")

def command_5_2(history,listOfParticipants):
    #comanda 2 din meniul aferent functionalitatii de filtrare
    #primeste prin parametru lista de scoruri
    #corespunde filtrarii participantilor cu scorul mai mic decat un scor dat
    #apeleaza functiile din modulul srv5 in urma validarii datelor
    print("Dati un scor:")
    while True:
        try:
            scor=float(input())
            if 1<scor<=10:
                break
            else:
                print("Valoare invalida! Scorul apartine intervalului (1,10].\n")
        except ValueError:
            print("Valoare invalida! Scorul trebuie sa fie o valoare numerica.\n")
    filter_lowerGradesThan(history,listOfParticipants,scor)
    print("Participantii au fost filtrati!\n")

'''cmd'''

def command_1(history,listOfParticipants):
    #comanda 1 din meniul principal
    #corespunde functionalitatii de adaugare
    #primeste prin parametru lista de scoruri
    #prompt meniu secundar cu comenzile posibile (1/2/3)
    #utilizatorul alege comanda dorita prin tastarea cifrei corespunzatoare
    #comanda 3 -> returnare la meniu principal
    commands={"1":command_1_1,"2":command_1_2}
    while True:
        try:
            print("[Selectati comanda dorita]")
            cmd=input('''1: Adaugare participant/scor nou
2: Inserare scor participant
3: Back\n''')
            print()
            while True:
                if cmd=="3":
                    raise ValueError
                try:
                    commands[cmd](history,listOfParticipants)
                    break
                except KeyError:
                    cmd=input("Selectie invalida! Va rugam introduceti cifra corespunzatoare operatiei selectate!\n")
        except ValueError:
            break

def command_2(history,listOfParticipants):
    #comanda 2 din meniul principal
    #corespunde functionalitatii de modificare
    #primeste prin parametru lista de scoruri
    #deschide un meniu secundar cu comenzile posibile (1/2/3/4)
    #utilizatorul alege comanda dorita prin tastarea cifrei corespunzatoare
    #comanda 4 -> returnare la meniu principal
    commands={"1":command_2_1,"2":command_2_2,"3":command_2_3}
    while True:
        try:
            print("[Selectati comanda dorita]")
            cmd=input('''1: Șterge scorul pentru un participant dat
2: Șterge scorul pentru un interval de participanți
3: Înlocuiește scorul de la un participant
4: Back\n''')
            print()
            while True:
                if cmd=="4":
                    raise ValueError
                try:
                    commands[cmd](history,listOfParticipants)
                    break
                except KeyError:
                    cmd=input("Selectie invalida! Va rugam introduceti cifra corespunzatoare operatiei selectate!\n")
        except ValueError:
            break

def command_3(history,listOfParticipants):
    #comanda 3 din meniul principal
    #corespunde functionalitatii de tiparire
    #primeste prin parametru lista de scoruri
    #deschide un meniu secundar cu comenzile posibile (1/2/3/4)
    #utilizatorul alege comanda dorita prin tastarea cifrei corespunzatoare
    #comanda 4 -> returnare la meniu principal
    commands={"1":command_3_1,"2":command_3_2,"3":command_3_3}
    while True:
        try:
            print("[Selectati comanda dorita]")
            cmd=input('''1: Tipărește participanții care au scor mai mic decât un scor dat
2: Tipărește participanții ordonat după scor
3: Tipărește participanții cu scor mai mare decât un scor dat, ordonat după scor
4: Back\n''')
            print()
            while True:
                if cmd=="4":
                    raise ValueError
                try:
                    commands[cmd](history,listOfParticipants)
                    break
                except KeyError:
                    cmd=input("Selectie invalida! Va rugam introduceti cifra corespunzatoare operatiei selectate!\n")
        except ValueError:
            break

def command_4(history,listOfParticipants):
    pass

def command_5(history,listOfParticipants):
    #comanda 5 din meniul principal
    #corespunde functionalitatii de filtrare
    #primeste prin parametru lista de scoruri
    #deschide un meniu secundar cu comenzile posibile (1/2/3)
    #utilizatorul alege comanda dorita prin tastarea cifrei corespunzatoare
    #comanda 3 -> returnare la meniul principal
    commands={"1":command_5_1,"2":command_5_2}
    while True:
        try:
            print("[Selectati comanda dorita]")
            cmd=input('''1: Filtrare participanți care au scorul multiplu unui număr dat
2: Filtrare participanți care au scorul mai mic decât un scor dat
3: Back\n''')
            print()
            while True:
                if cmd=="3":
                    raise ValueError
                try:
                    commands[cmd](history,listOfParticipants)
                    break
                except KeyError:
                    cmd=input("Selectie invalida! Va rugam introduceti cifra corespunzatoare operatiei selectate!\n")
        except ValueError:
            break

def command_6(history,listOfParticipants):
    pass
