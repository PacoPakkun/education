class Produs:
    '''
        *tip abstract de date, reprezinta o entitate de tip produs
    '''
    
    def __init__(self,id_produs,denumire,pret):
        '''
            *instantiaza un obiect de tip produs
            *id_produs: nr nat >0
            *denumire: string
            *pret: float
        '''
        self.__id=id_produs
        self.__denumire=denumire
        self.__pret=float(pret)
    
    def __str__(self):
        return str(self.__id)+';'+self.__denumire+';'+str(self.__pret)
        
    def get_id(self):
        return self.__id


    def get_denumire(self):
        return self.__denumire


    def get_pret(self):
        return self.__pret


    def set_id(self, value):
        self.__id = value


    def set_denumire(self, value):
        self.__denumire = value


    def set_pret(self, value):
        self.__pret = value
