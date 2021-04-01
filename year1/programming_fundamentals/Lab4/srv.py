from repo import *
from domain import *
from copy import deepcopy

def new_participant(history,listOfParticipants,scorParticipant):
    #primeste prin parametru o lista de participanti, respectiv un scor (None/val reala din [1,10])
    #creeaza un participant cu scorul dat, pe care il adauga in lista 
    #arunca exceptia "[Scor invalid]" daca scorul primit nu respecta conditiile
    history.append(deepcopy(listOfParticipants[:]))
    add_participant(listOfParticipants)
    set_scorParticipant_in_list(listOfParticipants, get_nrParticipants(listOfParticipants), scorParticipant)
    
def insert_scorParticipant(history,listOfParticipants,idParticipant,scorParticipant):
    #primeste prin parametru o lista de participanti, un id (nr natural nenul,<=nrParticipants) si un scor (None/val reala din [1,10])
    #modifica scorul participantului #id in valoarea data
    #arunca exceptia "[ID invalid]" daca idul primit nu respecta conditiile
    #arunca exceptia "[Scor invalid]" daca scorul primit nu respecta conditiile
    history.append(deepcopy(listOfParticipants[:]))
    set_scorParticipant_in_list(listOfParticipants, idParticipant, scorParticipant)
    
def delete_scorParticipant(history,listOfParticipants,idParticipant):
    #primeste prin parametru lista de participanti, respectiv un id (nr natural nenul)
    #modifica scoul participantului #id in None
    #arunca exceptia "[ID invalid]" daca idul primit nu respecta conditiile
    history.append(deepcopy(listOfParticipants[:]))
    set_scorParticipant_in_list(listOfParticipants, idParticipant, None)
    
def delete_scorParticipant_in_range(history,listOfParticipants,idParticipantFirst,idParticipantLast):
    #primeste prin parametru lista de participanti, idul primului participant din interval, respectiv idul ultimului (nr naturale nenule,idFirst<=idLast)
    #modifica scorurile part din interval in None
    #arunca exceptia "[ID invalid]" daca idurile primite nu respecta conditiile
    history.append(deepcopy(listOfParticipants[:]))
    try:
        if idParticipantFirst<=idParticipantLast:
            for i in range(idParticipantFirst,idParticipantLast+1):
                delete_scorParticipant(listOfParticipants, i)
        else:
            raise Exception('[ID invalid]')
    except:
        raise Exception('[ID invalid]')

def lowerGradesThan(history,listOfParticipants,scorParticipant):
    #primeste prin parametru lista de participanti, respectiv un scor maximal (None/val reala din [1,10])
    #returneaza participantii cu scorul mai mic decat scorul dat
    #arunca exceptia "[Scor invalid]" daca scorul primit nu respecta conditiile
    if ((type(scorParticipant)==int)or(type(scorParticipant)==float))and(1<=scorParticipant<=10):
        new_list=[]
        for participant in listOfParticipants[1:]:
            if (get_scorParticipant(participant)!=None)and(get_scorParticipant(participant)<scorParticipant):
                new_list.append(participant)
        return new_list
    else:
        raise Exception('[Scor invalid]')
    
def get_leaderboard(history,listOfParticipants):
    #primeste prin parametru lista de participanti
    #returneaza participantii in orine descresc in fct de scor
    new_list=[]
    for participant in listOfParticipants:
        if (participant!=None)and(get_scorParticipant(participant)!=None):
            new_list.append(participant)
    for i in range(0,len(new_list)-1):
        for j in range(i+1,len(new_list)):
            if get_scorParticipant(new_list[j])>get_scorParticipant(new_list[i]):
                new_list[i],new_list[j]=new_list[j],new_list[i]
    return new_list

def higherGradesThan(history,listOfParticipants,scorParticipant):
    #primeste prin parametru lista de participanti, respectiv un scor minimal (None/val reala din [1,10])
    #returneaza participantii cu scorul mai mare decat scorul dat
    #arunca exceptia "[Scor invalid]" daca scorul primit nu respecta conditiile
    if ((type(scorParticipant)==int)or(type(scorParticipant)==float))and(1<=scorParticipant<=10):
        new_list=[]
        for participant in listOfParticipants[1:]:
            if (get_scorParticipant(participant)!=None)and(get_scorParticipant(participant)>scorParticipant):
                new_list.append(participant)
        return new_list
    else:
        raise Exception('[Scor invalid]')
    
def filter_lowerGradesThan(history,listOfParticipants,scorParticipant):
    #primeste prin parametru lista de participanti, respectiv un scor maximal (None/val reala din [1,10])
    #elimina din lista participantii cu scorul mia mic decat valoarea data
    #arunca exceptia "[Scor invalid]" daca scorul primit nu respecta conditiile
    history.append(deepcopy(listOfParticipants[:]))
    if ((type(scorParticipant)==int)or(type(scorParticipant)==float))and(1<=scorParticipant<=10):
        for participant in listOfParticipants[1:]:
            if (get_scorParticipant(participant)!=None)and(get_scorParticipant(participant)<scorParticipant):
                set_scorParticipant_in_list(listOfParticipants, get_idParticipant(participant), None)
    else:
        raise Exception('[Scor invalid]')

def filter_byMultiple(history,listOfParticipants,multiple):
    #primeste prin parametru lista de participanti, respectiv un multiplu fata de care se va face filtrarea
    #elimina din lista participantii cu scorul multiplu de nr dat
    #arunca exceptia "[Multiplu invalid]" daca multiplul dat nu respecta conditiile (nr natural, 1<=multiplu<=10)
    history.append(deepcopy(listOfParticipants[:]))
    if (type(multiple)==int)and(1<=multiple<=10):
        for participant in listOfParticipants[1:]:
            if (get_scorParticipant(participant)!=None)and(get_scorParticipant(participant)%multiple==0):
                set_scorParticipant_in_list(listOfParticipants, get_idParticipant(participant), None)
    else:
        raise Exception('[Multiplu invalid]')

def undo(history,listOfParticipants):
    #anuleaza ultima operatie facuta care a schimbat lista de participanti
    #primeste ca parametru lista de part, respectiv istoricul listei
    #aduce lista la starea anterioara
    #arunca exceptia "[Undo invalid]" daca nu se mai poate face undo
    if len(history)>0:
        listOfParticipants.clear()
        listOfParticipants.extend(history.pop())
    else:
        raise Exception('[Undo invalid]')
