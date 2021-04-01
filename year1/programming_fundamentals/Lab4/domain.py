def create_participant(idParticipant,scorParticipant):
    #creeaza o entitate participant
    #primeste prin parametru idul (nr natural nenul), respectiv scorul participantului (None/val reala din [1,10])
    #returneaza un participant
    #arunca exceptia "[ID invalid]" daca idul primit nu respecta conditiile
    #arunca exceptia "[Scor invalid]" daca scorul primit nu respecta conditiile
    exception_text=''
    if (type(idParticipant)==int)and(idParticipant>0)and((scorParticipant==None)or(((type(scorParticipant)==float)or(type(scorParticipant)==int))and(1<=scorParticipant<=10))):
        return {"id":idParticipant,"scor":scorParticipant}
    else:
        if not((type(idParticipant)==int)and(idParticipant>0)):
            exception_text+='[ID invalid]'
        if not((scorParticipant==None)or(((type(scorParticipant)==float)or(type(scorParticipant)==int))and(1<=scorParticipant<=10))):
            exception_text+='[Scor invalid]'
        raise Exception(exception_text)

def get_idParticipant(participant):
    #primeste prin parametru un participant
    #returneaza idul participantului
    return participant["id"]

def set_idParticipant(participant,idParticipant):
    #primeste prin parametru un participant, respectiv un id (nr natural nenul)
    #modifica idul participantului in idParticipant
    #arunca exceptia "[ID invalid]" daca idul primit nu respecta conditiile
    if (type(idParticipant)==int)and(idParticipant>0):
        participant["id"]=idParticipant
    else:
        raise Exception('[ID invalid]')

def get_scorParticipant(participant):
    #primeste prin parametru un participant
    #returneaza scorul participantului
    return participant["scor"]

def set_scorParticipant(participant,scorParticipant):
    #primeste prin parametru un participant, respectiv un scor (None/val reala din [1,10])
    #modifica scorul participantului in scorParticipant
    #arunca exceptia "[Scor invalid]" daca scorul primit nu respecta conditiile
    if (scorParticipant==None)or(((type(scorParticipant)==int)or(type(scorParticipant)==float))and(1<=scorParticipant<=10)):
        participant["scor"]=scorParticipant
    else:
        raise Exception('[Scor invalid]')

# def clone_participant(participant):
#     clone=create_participant(get_idParticipant(participant), get_scorParticipant(participant))
#     return clone