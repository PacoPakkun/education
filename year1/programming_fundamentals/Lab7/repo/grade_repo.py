from domain.grade import Grade
from domain.student import Student
from domain.problem import Problem

class GradeRepo:
    #tip abstract de date, reprezinta o colectie de note
    
    def __init__(self):
        #creeaza o instanta a repozitoriului de note
        self.__repo=[]
        
    def __str__(self):
        #returneaza o reprezentare a repozitoriului de note
        return str(list(map(str,self.__repo)))
    
    def __len__(self):
        #returneaza nr de note din repo
        return len(self.__repo)
    
    def get_all(self):
        return self.__repo[:]
    
    def search(self,studentID,labID,problemID):
        #returneaza studentul cu idul dat
        #arunca exceptia "[Relatie inexistenta]" daca nu exista o entitate Grade cu parametrii dati in repo
        for grade in self.__repo:
            if (grade.get_student().get_student_id()==studentID)and(grade.get_problem().get_lab_id()==labID)and(grade.get_problem().get_problem_id()==problemID):
                return grade
        raise Exception('[Relatie inexistenta]')
    
    def add(self,grade):
        #adauga o nota in repo
        #arunca exceptia "[Nota existenta]" daca problema exista deja in repo
        if grade in self.__repo:
            raise Exception('[Nota existenta]')
        self.__repo.append(grade)
        
    def modify(self,grade):
        #modifica nota studentului cu idul dat la problema data
        self.search(grade.get_student().get_student_id(),grade.get_problem().get_lab_id(),grade.get_problem().get_problem_id())
        for entity in self.__repo:
            if entity==grade:
                entity.set_grade(grade.get_grade())
                    