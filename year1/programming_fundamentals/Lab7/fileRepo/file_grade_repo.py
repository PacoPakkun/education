from repo.grade_repo import GradeRepo
from domain.grade import Grade
from domain.student import Student
from domain.problem import Problem

class FileGradeRepo(GradeRepo):
    #tip abstract de date, subclasa a repozitoriului de gradei, reprezinta un gradeRepo integrat in memorie
    
    def __init__(self,fileName):
        #creeaza o instanta a repozitoriului de gradei din fisier
        GradeRepo.__init__(self)
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
                grade=Grade(Student(int(attributes[0]),None,None),Problem(int(attributes[1]),int(attributes[2]),None,None),float(attributes[3]))
                GradeRepo.add(self, grade)
    
    def __saveToFile(self):
        with open(self.__fileName,"w") as file:
            grades=GradeRepo.get_all(self)
            for grade in grades:
                self.__addToFile(grade, file)
        
    def __addToFile(self,grade,file):
        #adauga un nou grade in fisier
        gradeString=str(grade.get_student().get_student_id())+';'+str(grade.get_problem().get_lab_id())+';'+str(grade.get_problem().get_problem_id())+';'+str(grade.get_grade())
        file.write("\n"+gradeString)

    def add(self,grade):
        GradeRepo.add(self, grade)
        with open(self.__fileName,"a") as file:
            self.__addToFile(grade, file)
        
    def modify(self,grade):
        GradeRepo.modify(self, grade)
        self.__saveToFile()
