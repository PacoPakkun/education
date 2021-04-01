from repo.problem_repo import ProblemRepo
from domain.problem import Problem

class FileProblemRepo(ProblemRepo):
    #tip abstract de date, subclasa a repozitoriului de Problemi, reprezinta un ProblemRepo integrat in memorie
    
    def __init__(self,fileName):
        #creeaza o instanta a repozitoriului de Problemi din fisier
        ProblemRepo.__init__(self)
        self.__fileName=fileName
        self.__loadFromFile()
       
    def __loadFromFile(self):
        #incarca in repozitoriu datele din fisier
        #arunca exceptia FileNotFoundException daca nu exista fisierul
        #arunca exceptia ValueError daca apare o eroare la citirea din fisier
        with open(self.__fileName,"r") as file:
            for line in file:
                if line.strip()=="":
                    continue
                line=line.strip()
                attributes=line.split(";")
                problem=Problem(int(attributes[0]),int(attributes[1]),attributes[2],attributes[3])
                ProblemRepo.add(self, problem)
    
    def __saveToFile(self):
        with open(self.__fileName,"w") as file:
            Problems=ProblemRepo.get_all(self)
            for Problem in Problems:
                self.__addToFile(Problem, file)
        
    def __addToFile(self,Problem,file):
        #adauga un nou Problem in fisier
        ProblemString=str(Problem.get_lab_id())+';'+str(Problem.get_problem_id())+';'+Problem.get_description()+';'+Problem.get_deadline()
        file.write("\n"+ProblemString)

    def add(self,Problem):
        ProblemRepo.add(self, Problem)
        with open(self.__fileName,"a") as file:
            self.__addToFile(Problem, file)

    def remove(self,ProblemID):
        ProblemRepo.remove(self, ProblemID)
        self.__saveToFile()
