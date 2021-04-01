from domain import *
from repo import *
from srv import *

'''domain'''

def test_get_idParticipant():
    #testeaza functia get_idParticipant pt niste valori relevante
    participant={"id":1,"scor":9}
    assert(get_idParticipant(participant)==1)
    participant={"id":13,"scor":9}
    assert(get_idParticipant(participant)==13)

def test_set_idParticipant():
    #testeaza functia set_idParticipant pt niste valori relevante
    participant={"id":1,"scor":9}
    set_idParticipant(participant,2)
    assert(participant["id"]==2)
    try:
        participant={"id":1,"scor":9}
        set_idParticipant(participant,0)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        participant={"id":1,"scor":9}
        set_idParticipant(participant,-5)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        participant={"id":1,"scor":9}
        set_idParticipant(participant,'abc')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')

def test_get_scorParticipant():
    #testeaza functia get_scorParticipant pt niste valori relevante
    participant={"id":1,"scor":None}
    assert(get_scorParticipant(participant)==None)
    participant={"id":1,"scor":9}
    assert(get_scorParticipant(participant)==9)
    participant={"id":1,"scor":3.5}
    assert(get_scorParticipant(participant)==3.5)

def test_set_scorParticipant():
    #testeaza functia set_scorParticipant pt niste valori relevante
    participant={"id":1,"scor":9}
    set_scorParticipant(participant,None)
    assert(participant["scor"]==None)
    participant={"id":1,"scor":9}
    set_scorParticipant(participant,3)
    assert(participant["scor"]==3)
    participant={"id":1,"scor":9}
    set_scorParticipant(participant,5.6)
    assert(participant["scor"]==5.6)
    try:
        participant={"id":1,"scor":9}
        set_scorParticipant(participant,11)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        participant={"id":1,"scor":9}
        set_scorParticipant(participant,-3)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        participant={"id":1,"scor":9}
        set_scorParticipant(participant,'ab')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')

def test_create_participant():
    #testeaza functia create_participant pt niste valori relevante
    participant=create_participant(1,10)
    assert(get_idParticipant(participant)==1)
    assert(get_scorParticipant(participant)==10)
    participant=create_participant(13,8.5)
    assert(get_idParticipant(participant)==13)
    assert(get_scorParticipant(participant)==8.5)
    try:
        participant=create_participant('abc',9)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        participant=create_participant(-5,8)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        participant=create_participant(0,9)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        participant=create_participant(1,14)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        participant=create_participant(3,0.5)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        participant=create_participant(2,'ac')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        participant=create_participant(-18,200)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid][Scor invalid]')

'''repo'''

def test_create_listOfParticipants():
    #testeaza functia create_listOfParticipants pt niste valori relevante
    listOfParticipants=create_listOfParticipants()
    assert(listOfParticipants==[None])
    
def test_get_nrParticipants():
    #testeaza functia create_listOfParticipants pt niste valori relevante
    listOfParticipants=[None]
    assert(get_nrParticipants(listOfParticipants)==0)
    listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":8.7},{"id":3,"scor":4},{"id":4,"scor":7.81}]
    assert(get_nrParticipants(listOfParticipants)==4)

def test_add_participant():
    #testeaza functia add_participant pt niste valori relevante
    listOfParticipants=[None]
    add_participant(listOfParticipants)
    assert(listOfParticipants==[None,{"id":1,"scor":None}])
    listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":8.7},{"id":3,"scor":4}]
    add_participant(listOfParticipants)
    assert(listOfParticipants==[None,{"id":1,"scor":10},{"id":2,"scor":8.7},{"id":3,"scor":4},{"id":4,"scor":None}])

def test_get_scorParticipant_in_list():
    #testeaza functia get_scorParticipant_in_list pt niste valori relevante
    listOfParticipants=[None,{"id":1,"scor":None}]
    assert(get_scorParticipant_in_list(history,listOfParticipants,1)==None)
    listOfParticipants=[None,{"id":1,"scor":3}]
    assert(get_scorParticipant_in_list(history,listOfParticipants,1)==3)
    listOfParticipants=[None,{"id":1,"scor":9},{"id":2,"scor":8.5},{"id":1,"scor":6.3}]
    assert(get_scorParticipant_in_list(history,listOfParticipants,2)==8.5)
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        scorParticipant=get_scorParticipant_in_list(history,listOfParticipants,2)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        scorParticipant=get_scorParticipant_in_list(history,listOfParticipants,'ab')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
        
def test_set_scorParticipant_in_list():
    #testeaza functia set_scorParticipant_in_list pt niste valori relevante
    listOfParticipants=[None,{"id":1,"scor":None}]
    set_scorParticipant_in_list(history,listOfParticipants,1,None)
    assert(get_scorParticipant_in_list(history,listOfParticipants,1)==None)
    listOfParticipants=[None,{"id":1,"scor":None}]
    set_scorParticipant_in_list(history,listOfParticipants,1,10)
    assert(get_scorParticipant_in_list(history,listOfParticipants,1)==10)
    listOfParticipants=[None,{"id":1,"scor":3},{"id":2,"scor":4.5},{"id":3,"scor":7}]
    set_scorParticipant_in_list(history,listOfParticipants,3,5.6)
    assert(get_scorParticipant_in_list(history,listOfParticipants,3)==5.6)
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        set_scorParticipant_in_list(history,listOfParticipants,3,10)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        set_scorParticipant_in_list(history,listOfParticipants,'c',10)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        set_scorParticipant_in_list(history,listOfParticipants,1,14)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        set_scorParticipant_in_list(history,listOfParticipants,1,'abc')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        set_scorParticipant_in_list(history,listOfParticipants,3,'abc')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid][Scor invalid]')

'''srv'''
        
def test_new_participant():
    #testeaza functia new_participant pt niste valori relevante
    listOfParticipants=[None]
    new_participant(history,listOfParticipants,10)
    assert(listOfParticipants==[None,{"id":1,"scor":10}])
    listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":3}]
    new_participant(history,listOfParticipants,8.6)
    assert(listOfParticipants==[None,{"id":1,"scor":10},{"id":2,"scor":3},{"id":3,"scor":8.6}])
    try:
        listOfParticipants=[None]
        new_participant(history,listOfParticipants, 20)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        listOfParticipants=[None]
        new_participant(history,listOfParticipants, 'a')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')

def test_insert_scorParticipant():
    #testeaza insert_scorParticipant pt niste valori relevante
    listOfParticipants=[None,{"id":1,"scor":None}]
    insert_scorParticipant(history,listOfParticipants, 1, 9)
    assert(listOfParticipants==[None,{"id":1,"scor":9}])
    listOfParticipants=[None,{"id":1,"scor":3}]
    insert_scorParticipant(history,listOfParticipants, 1, 5.5)
    assert(listOfParticipants==[None,{"id":1,"scor":5.5}])
    listOfParticipants=[None,{"id":1,"scor":None},{"id":2,"scor":5},{"id":3,"scor":7.8}]
    insert_scorParticipant(history,listOfParticipants, 2, 7.6)
    assert(listOfParticipants==[None,{"id":1,"scor":None},{"id":2,"scor":7.6},{"id":3,"scor":7.8}])
    try:
        listOfParticipants=[None]
        insert_scorParticipant(history,listOfParticipants, 1, 5)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        insert_scorParticipant(history,listOfParticipants, '1', 5)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        insert_scorParticipant(history,listOfParticipants, 0, 5)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        insert_scorParticipant(history,listOfParticipants, 1, 12)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        insert_scorParticipant(history,listOfParticipants, 1, 'r')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":None}]
        insert_scorParticipant(history,listOfParticipants, 4, 12)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid][Scor invalid]')

def test_delete_scorParticipant():
    #testeaza insert_scorParticipant pt niste valori relevante
    listOfParticipants=[None,{"id":1,"scor":10}]
    delete_scorParticipant(history,listOfParticipants, 1)
    assert(listOfParticipants==[None,{"id":1,"scor":None}])
    listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":9.5},{"id":3,"scor":7}]
    delete_scorParticipant(history,listOfParticipants, 2)
    assert(listOfParticipants==[None,{"id":1,"scor":10},{"id":2,"scor":None},{"id":3,"scor":7}])
    try:
        listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":9.5},{"id":3,"scor":7}]
        delete_scorParticipant(history,listOfParticipants, 4)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":9.5},{"id":3,"scor":7}]
        delete_scorParticipant(history,listOfParticipants, 'r')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')

def test_delete_scorParticipant_in_range():
    #testeaza insert_scorParticipant pt niste valori relevante
    listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":9.5},{"id":3,"scor":7}]
    delete_scorParticipant_in_range(history,listOfParticipants, 2, 3)
    assert(listOfParticipants==[None,{"id":1,"scor":10},{"id":2,"scor":None},{"id":3,"scor":None}])
    listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":9.5},{"id":3,"scor":7}]
    delete_scorParticipant_in_range(history,listOfParticipants, 2, 2)
    assert(listOfParticipants==[None,{"id":1,"scor":10},{"id":2,"scor":None},{"id":3,"scor":7}])
    try:
        listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":9.5},{"id":3,"scor":7}]
        delete_scorParticipant_in_range(history,listOfParticipants, 4, 5)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":9.5},{"id":3,"scor":7}]
        delete_scorParticipant_in_range(history,listOfParticipants, 3, 1)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":10},{"id":2,"scor":9.5},{"id":3,"scor":7}]
        delete_scorParticipant_in_range(history,listOfParticipants, 'abc', None)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[ID invalid]')

def test_lowerGradesThan():
    #testeaza lowerGradesThan pt niste valori relevante
    listOfParticipants=[None]
    assert(lowerGradesThan(history,listOfParticipants, 10)==[])
    listOfParticipants=[None,{"id":1,"scor":7}]
    assert(lowerGradesThan(history,listOfParticipants, 8)==[{"id":1,"scor":7}])
    listOfParticipants=[None,{"id":1,"scor":7},{"id":2,"scor":3},{"id":3,"scor":8},{"id":4,"scor":2}]
    assert(lowerGradesThan(history,listOfParticipants, 6)==[{"id":2,"scor":3},{"id":4,"scor":2}])
    try:
        listOfParticipants=[None,{"id":1,"scor":7}]
        list=lowerGradesThan(history,listOfParticipants, 13)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":7}]
        list=lowerGradesThan(history,listOfParticipants, 'ab')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')

def test_get_leaderboard():
    #testeaza get_leaderboard pt niste valori relevante
    listOfParticipants=[None,{"id":1,"scor":7},{"id":2,"scor":3},{"id":3,"scor":8},{"id":4,"scor":2}]
    assert(get_leaderboard(listOfParticipants)==[{"id":3,"scor":8},{"id":1,"scor":7},{"id":2,"scor":3},{"id":4,"scor":2}])
    listOfParticipants=[None]
    assert(get_leaderboard(listOfParticipants)==[])

def test_higherGradesThan():
    #testeaza higherGradesThan pt niste valori relevante
    listOfParticipants=[None]
    assert(higherGradesThan(history,listOfParticipants, 10)==[])
    listOfParticipants=[None,{"id":1,"scor":7}]
    assert(higherGradesThan(history,listOfParticipants, 5)==[{"id":1,"scor":7}])
    listOfParticipants=[None,{"id":1,"scor":7},{"id":2,"scor":3},{"id":3,"scor":8},{"id":4,"scor":2}]
    assert(higherGradesThan(history,listOfParticipants, 6)==[{"id":1,"scor":7},{"id":3,"scor":8}])
    try:
        listOfParticipants=[None,{"id":1,"scor":7}]
        list=higherGradesThan(history,listOfParticipants, 13)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":7}]
        list=higherGradesThan(history,listOfParticipants, 'ab')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')

def test_filter_lowerGradesThan():
    #testeaza filter_lowerGradesThan pt niste valori relevante
    listOfParticipants=[None]
    filter_lowerGradesThan(history,listOfParticipants, 5)
    assert(listOfParticipants==[None])
    listOfParticipants=[None,{"id":1,"scor":7},{"id":2,"scor":3},{"id":3,"scor":4}]
    filter_lowerGradesThan(history,listOfParticipants, 5)
    assert(listOfParticipants==[None,{"id":1,"scor":7},{"id":2,"scor":None},{"id":3,"scor":None}])
    try:
        listOfParticipants=[None,{"id":1,"scor":7},{"id":2,"scor":3},{"id":3,"scor":4}]
        filter_lowerGradesThan(history,listOfParticipants, 13)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":7},{"id":2,"scor":3},{"id":3,"scor":4}]
        filter_lowerGradesThan(history,listOfParticipants, 'x')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Scor invalid]')

def test_filter_byMultiple():
    #testeaza filter_byMultiple pt niste valori relevante
    listOfParticipants=[None]
    filter_byMultiple(history,listOfParticipants, 5)
    assert(listOfParticipants==[None])
    listOfParticipants=[None,{"id":1,"scor":7}]
    filter_byMultiple(history,listOfParticipants, 5)
    assert(listOfParticipants==[None,{"id":1,"scor":7}])
    listOfParticipants=[None,{"id":1,"scor":2},{"id":2,"scor":4},{"id":3,"scor":7}]
    filter_byMultiple(history,listOfParticipants, 2)
    assert(listOfParticipants==[None,{"id":1,"scor":None},{"id":2,"scor":None},{"id":3,"scor":7}])
    try:
        listOfParticipants=[None,{"id":1,"scor":7}]
        filter_byMultiple(history,listOfParticipants, 0)
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Multiplu invalid]')
    try:
        listOfParticipants=[None,{"id":1,"scor":7}]
        filter_byMultiple(history,listOfParticipants, 'f')
        assert(False)
    except Exception as ex:
        assert(str(ex)=='[Multiplu invalid]')

def test_undo():
    listOfParticipants=[None]
    history=[]
    new_participant(history,listOfParticipants,10)
    undo(history,listOfParticipants)
    assert(listOfParticipants==[None])
    
'''final test'''

def test():
    '''
    #contine toate fct de testare aferente programului
    #domain:
    test_get_idParticipant()
    test_set_idParticipant()
    test_get_scorParticipant()
    test_set_scorParticipant()
    test_create_participant()
    #repo:
    test_create_listOfParticipants()
    test_get_nrParticipants()
    test_add_participant()
    test_get_scorParticipant_in_list()
    test_set_scorParticipant_in_list()
    #srv:
    test_new_participant()
    test_insert_scorParticipant()
    test_delete_scorParticipant()
    test_delete_scorParticipant_in_range()
    test_lowerGradesThan()
    test_get_leaderboard()
    test_higherGradesThan()
    test_filter_lowerGradesThan()
    test_filter_byMultiple()
    '''
    test_undo()
    
test()
