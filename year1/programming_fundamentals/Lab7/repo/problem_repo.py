from domain.problem import Problem

class ProblemRepo:
    #tip abstract de date, reprezinta o colectie de probleme
    
    def __init__(self):
        #creeaza o instanta a repozitoriului de probleme
        self._repo=[]
        
    def __str__(self):
        #returneaza o reprezentare a repozitoriului de probleme
        return str(list(map(str,self._repo)))
    
    def __len__(self):
        #returneaza nr de probleme din repo
        return len(self._repo)
        
    def get_all(self):
        return self._repo[:]
        
    def add(self,problem):
        #adauga o problema in repo
        #arunca exceptia "[Problema existenta]" daca problema exista deja in repo
        if problem in self._repo:
            raise Exception('[Problema existenta]')
        self._repo.append(problem)
        
    def search(self,labID,problemID,index):
        #returneaza studentul cu idul dat
        #arunca exceptia "[Problema inexistenta]" daca nu exista un student cu idul dat in repo
        if index==len(self._repo):
            raise Exception('[Problema inexistenta]')
        else:
            problem=self._repo[index]
            if (problem.get_lab_id()==labID)and(problem.get_problem_id()==problemID):
                return problem
            else:
                return self.search(labID,problemID,index+1)
#         for problem in self.__repo:
#             if (problem.get_lab_id()==labID)and(problem.get_problem_id()==problemID):
#                 return problem
#         raise Exception('[Problema inexistenta]')
    
    def remove(self,labID,problemID):
        #elimina problema cu idul dat
        #arunca exceptia "[ID inexistent]" daca nu exista o problema cu idul dat in repo
        for i in range(0,len(self._repo)):
            if (self._repo[i].get_lab_id()==labID)and(self._repo[i].get_problem_id()==problemID):
                del(self._repo[i])
                return
        raise Exception('[Problema inexistenta]')