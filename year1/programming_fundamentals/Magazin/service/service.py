from domain.produs import Produs
from repo.repo import ProdusRepo

class Service:
    '''
        *tip abstract de date, reprezinta entitatea care coordoneaza functionalitatile programului
    '''
    
    def __init__(self,repo):
        '''
            *instantiaza o entitate service
            *repo: obiect de tip repozitoriu
        '''
        self.__repo=repo
        
    def add(self,id_produs,denumire,pret):
        '''
            *construieste un obiect de tip produs pe baza parametrilor
            *adauga noul produs in repo
            *id_produs: nr nat nenul
            *denumire: string
            *pret: float
        '''
        produs=Produs(id_produs,denumire,pret)
        self.__repo.add(produs)
    
    def remove(self,id_produs):
        '''
            *elimina din repo obiectele dupa cheia data
            *returneaza numarul de produse sterse
            *id_produs: nr nat
        '''
        count=self.__repo.remove(id_produs)
        return count
    
    def filter(self,denumire,pret):
        '''
            *returneaza o copie filtrata dupa denumire si pret a repozitoriului
            *denumire: string
            *pret: float
        '''
        filtered_repo=self.__repo.filter(denumire,pret)
        return filtered_repo
    
    def undo(self):
        '''
            *reface ultima operatie de stergere
        '''
        self.__repo.undo()