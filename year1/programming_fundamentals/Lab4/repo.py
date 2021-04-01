from domain import *

def create_listOfParticipants():
    #returneaza o lista de participanti
    return [None]

def get_nrParticipants(listOfParticipants):
    #primeste ca parametru o lista de participanti
    #returneaza nr participantilor
    return len(listOfParticipants)-1

def add_participant(listOfParticipants):
    #adauga un nou participant in lista de participanti, cu idul consecutiv ultimului participant din lista, respectiv scorul None
    #arunca exceptia "[Lista invalida]" daca parametrul nu respecta conditiile (nu e o lista)
    participant=create_participant(get_nrParticipants(listOfParticipants)+1,None)
    listOfParticipants.append(participant)

def get_scorParticipant_in_list(listOfParticipants,idParticipant):
    #primeste prin parametru o lista de participanti, respectiv un id (nr natural nenul,<=nrParticipants)
    #returneaza scorul participantului din lista cu idul dat
    #arunca exceptia "[ID invalid]" daca idul primit nu respecta conditiile
    if (type(idParticipant)==int)and(0<idParticipant<=get_nrParticipants(listOfParticipants)):
        return listOfParticipants[idParticipant]["scor"]
    else:
        raise Exception('[ID invalid]')

def set_scorParticipant_in_list(listOfParticipants,idParticipant,scorParticipant):
    #primeste prin parametru o lista de participanti, un id (nr natural nenul,<=nrParticipants) si un scor (None/val reala din [1,10])
    #modifica scorul participantului din lista cu idul dat in scorul dat
    #arunca exceptia "[ID invalid]" daca idul primit nu respecta conditiile
    #arunca exceptia "[Scor invalid]" daca scorul primit nu respecta conditiile
    exception_text=''
    if ((type(idParticipant)==int)and(0<idParticipant<=get_nrParticipants(listOfParticipants)))and((scorParticipant==None)or(((type(scorParticipant)==int)or(type(scorParticipant)==float))and(1<=scorParticipant<=10))):
        listOfParticipants[idParticipant]["scor"]=scorParticipant
    else:
        if not((type(idParticipant)==int)and(0<idParticipant<=get_nrParticipants(listOfParticipants))):
            exception_text+='[ID invalid]'
        if not((scorParticipant==None)or(((type(scorParticipant)==int)or(type(scorParticipant)==float))and(1<=scorParticipant<=10))):
            exception_text+='[Scor invalid]'
        raise Exception(exception_text)
