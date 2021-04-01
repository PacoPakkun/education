from domain.produs import Produs

class ProdusRepo:
    '''
        *tip abstract de date, reprezinta un repozitoriu de produse
        *incarca si salveaza datele in fisier
        *repo: lista de produse
        *history: lista cu istoricul elementelor sterse
        *fileName: string cu numele fisierului asociat
    '''
    
    def __init__(self,fileName):
        '''
            *instantiaza un repozitoriu de produse
            *incarca continutul din fisier
            *fileName: string
        '''
        self.__repo=[]
        self.__history=[]
        self.__fileName=fileName
        self.__loadFromFile()
        
    def get_all(self):
        '''
            *returneaza o copie a repozitoriului
        '''
        return self.__repo[:]
    
    def get_history(self):
        '''
            *returneaza o copie a istoricului operatiilor
        '''
        return self.__history[:]
        
    def __loadFromFile(self):
        '''
            *incarca in repozitoriu datele din fisier
            *arunca exceptia FileNotFoundException daca nu exista fisierul
            *arunca exceptia ValueError daca apare o eroare la citirea din fisier
        '''
        self.__repo=[]
        with open(self.__fileName,"r") as file:
            for line in file:
                if line.strip()=="":
                    continue
                line=line.strip()
                attributes=line.split(";")
                produs=Produs(int(attributes[0]),attributes[1],float(attributes[2]))
                self.__repo.append(produs)
    
    def __saveToFile(self):
        '''
            *salveaza datele din repozitoriu in fisier
            *arunca exceptia FileNotFoundException daca nu exista fisierul
            *arunca exceptia ValueError daca apare o eroare la scrierea in fisier
        '''
        with open(self.__fileName,"w") as file:
            produse=self.get_all()
            for produs in produse:
                self.__addToFile(produs, file)
    
    def __addToFile(self,produs,file):
        '''
            *adauga un nou produs in fisier
        '''
        file.write("\n"+str(produs))
    
    def __str__(self):
        repoString=''
        for produs in self.__repo:
            repoString+=str(produs)+' '
        return repoString[:-1]
    
    def add(self,produs):
        '''
            *adauga un produs in repozitoriu
            *salveaza schimbarile in fisier
            *produs: obiect de tip produs
        '''
        self.__loadFromFile()
        self.__repo.append(produs)
        self.__saveToFile()

    def remove(self,id_produs):
        '''
            *sterge produsele cu id_produs in id
            *salveaza modificarile in fisier
            *returneaza nr de produse sterse
            *id_produs: nr nat
        '''
        self.__loadFromFile()
        history=[]
        count=0
        i=0
        while i<len(self.__repo):
            if str(id_produs) in str(self.__repo[i].get_id()):
                history.append(self.__repo[i])
                del(self.__repo[i])
                count+=1
            else:
                i+=1
        self.__saveToFile()
        self.__history.append(history)
        return count
    
    def filter(self,denumire,pret):
        '''
            *returneaza o copie repo filtrata dupa denumire si pret
            *denumire: string
            *pret: float
        '''
        repo=self.get_all()
        i=0
        while i<len(repo):
            if (denumire!='')and(repo[i].get_denumire()!=denumire):
                del(repo[i])
            elif (pret!=-1)and(repo[i].get_pret()!=pret):
                del(repo[i])
            else:
                i+=1
        return repo
        
    def undo(self):
        '''
            *reface starea repozitoriului inainte de ultima operatie de stergere
            *salveaza modificarile in fisier
        '''
        history=self.__history[-1]
        for produs in history:
            self.add(produs)
        self.__history=self.__history[:-1]
        