from srv import *

def ui_new_participant(history,listOfParticipants,scorParticipant,*additionalArgs):
    #apeleaza fct new_participant din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    try:
        if additionalArgs!=():
            raise Exception()
        elif scorParticipant=='None':
            scorParticipant=None
        else:
            scorParticipant=float(scorParticipant)
    except:
        raise Exception('[Comanda invalida]')
    new_participant(history,listOfParticipants, scorParticipant)
    return 'exitcode'

def ui_insert_scorParticipant(history,listOfParticipants,idParticipant,scorParticipant,*additionalArgs):
    #apeleaza fct insert_scorParticipant din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    try:
        if additionalArgs!=():
            raise Exception()
        idParticipant=int(idParticipant)
        if scorParticipant=='None':
            scorParticipant=None
        else:
            scorParticipant=float(scorParticipant)
    except:
        raise Exception('[Comanda invalida]')
    insert_scorParticipant(history,listOfParticipants,idParticipant,scorParticipant)
    return 'exitcode'

def ui_delete_scorParticipant(history,listOfParticipants,idParticipant,*additionalArgs):
    #apeleaza fct delete_scorParticipant din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    if additionalArgs!=():
        if idParticipant=='range':
            ui_delete_scorParticipant_in_range(history,listOfParticipants,*additionalArgs)
        else:
            raise Exception()
    else:
        try:
            idParticipant=int(idParticipant)
            delete_scorParticipant(history,listOfParticipants,idParticipant)
        except:
            raise Exception('[Comanda invalida]')
    return 'exitcode'

def ui_delete_scorParticipant_in_range(history,listOfParticipants,idParticipantFirst,idParticipantLast,*additionalArgs):
    #apeleaza fct delete_scorParticipant_in_range din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    try:
        if additionalArgs!=():
            raise Exception()
        idParticipantFirst=int(idParticipantFirst)
        idParticipantLast=int(idParticipantLast)
    except:
        raise Exception('[Comanda invalida]')
    delete_scorParticipant_in_range(history,listOfParticipants,idParticipantFirst,idParticipantLast)

def ui_show(history,listOfParticipants,function,*additionalArgs):
    #apeleaza fct de tiparire
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    funcs={"lower":ui_show_lowerGradesThan,"top":get_leaderboard,"higher":ui_show_higherGradesThan}
    if function in funcs:
        new_list=funcs[function](history,listOfParticipants,*additionalArgs)
        return new_list
    else:
        raise Exception('[Comanda invalida]')
    
def ui_show_lowerGradesThan(history,listOfParticipants,scorParticipant,*additionalArgs):
    #apeleaza fct lowerGradesThan din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    if additionalArgs!=():
        raise Exception('[Comanda invalida]')
    else:
        try:
            scorParticipant=float(scorParticipant)
        except:
            raise Exception('[Comanda invalida]')
        new_list=lowerGradesThan(history,listOfParticipants, scorParticipant)
        return new_list

def ui_show_leaderboard(history,listOfParticipants,*additionalArgs):
    #apeleaza fct leaderboard din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    if additionalArgs!=():
        raise Exception('[Comanda invalida]')
    else:
        new_list=get_leaderboard(listOfParticipants)
        return new_list

def ui_show_higherGradesThan(history,listOfParticipants,scorParticipant,*additionalArgs):
    #apeleaza fct higherGradesThan din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    if additionalArgs!=():
        raise Exception('[Comanda invalida]')
    else:
        try:
            scorParticipant=float(scorParticipant)
        except:
            raise Exception('[Comanda invalida]')
        new_list=get_leaderboard(higherGradesThan(history,listOfParticipants, scorParticipant))
        return new_list

def ui_filter(history,listOfParticipants,function,*additionalArgs):
    #apeleaza fct de filtrare
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    funcs={"multiple":ui_filter_byMultiple,"lower":ui_filter_lowerGradesThan}
    if function in funcs:
        new_list=funcs[function](history,listOfParticipants,*additionalArgs)
        return 'exitcode'
    else:
        raise Exception('[Comanda invalida]')

def ui_filter_lowerGradesThan(history,listOfParticipants,scorParticipant,*additionalArgs):
    #apeleaza fct filter_lowerGradesThan din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    if additionalArgs!=():
        raise Exception('[Comanda invalida]')
    else:
        try:
            scorParticipant=float(scorParticipant)
        except:
            raise Exception('[Comanda invalida]')
        filter_lowerGradesThan(history,listOfParticipants, scorParticipant)

def ui_filter_byMultiple(history,listOfParticipants,multiple,*additionalArgs):
    #apeleaza fct filter_byMultiple din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    if additionalArgs!=():
        raise Exception('[Comanda invalida]')
    else:
        try:
            multiple=int(multiple)
        except:
            raise Exception('[Comanda invalida]')
        filter_byMultiple(history,listOfParticipants, multiple)

def ui_undo(history,listOfParticipants,*additionalArgs):
    #apeleaza fct undo din modulul srv
    #arunca exceptia "[Comanda invalida]" daca parametrii sunt invalizi
    if additionalArgs!=():
        raise Exception ('[Comanda invalida]')
    else:
        undo(history,listOfParticipants)
        return 'exitcode'
        
def ui2():
    #interfata utilizator
    #prompt comenzi posibile
    #comenzile sunt apelate dintr-un dictionar de comenzi
    #comanda exit termina executia programului
    print("Bine ati venit in aplicatia de gestiune a concurentilor!\n")
    commands={"add":ui_new_participant,"insert":ui_insert_scorParticipant,"delete":ui_delete_scorParticipant,"show":ui_show,"filter":ui_filter,"undo":ui_undo}
    listOfParticipants=create_listOfParticipants()
    history=[]
    while True:
        print("[Selectati comanda dorita]")
        cmd=input("> ").strip().split(" ")
        try:
            if cmd[0]=='exit':
                break
            elif cmd[0]=='list':
                print(listOfParticipants)
            elif cmd[0] in commands:
                result=commands[cmd[0]](history,listOfParticipants,*cmd[1:])
                if result!='exitcode':
                    print(result)
            else:
                print('[Comanda invalida]')
        except Exception as ex:
            if type(ex)==TypeError:
                print('[Comanda invalida]')
            else:
                print(str(ex))
