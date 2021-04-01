from domain.student import Student
from domain.problem import Problem
from domain.grade import Grade
from repo.student_repo import StudentRepo
from repo.problem_repo import ProblemRepo
from repo.grade_repo import GradeRepo
from fileRepo.file_student_repo import FileStudentRepo
from fileRepo.file_problem_repo import FileProblemRepo
from fileRepo.file_grade_repo import FileGradeRepo
from valid.student_valid import StudentValidator
from valid.problem_valid import ProblemValidator
from valid.grade_valid import GradeValidator
from sorts.sorts import selectionSort, shakeSort
from domain import problem
import random
import string

class Service:
    #tip de date abstract, reprezinta entitatea care valideaza si proceseaza datele programului
    
    def __init__(self,studentRepo,problemRepo,gradeRepo):
        self.__studentRepo=studentRepo
        self.__problemRepo=problemRepo
        self.__gradeRepo=gradeRepo
        studentValidator=StudentValidator()
        problemValidator=ProblemValidator()
        gradeValidator=GradeValidator()
        self.__studentValidator=studentValidator
        self.__problemValidator=problemValidator
        self.__gradeValidator=gradeValidator

    def get_student_repo(self):
        return self.__studentRepo


    def get_problem_repo(self):
        return self.__problemRepo


    def get_grade_repo(self):
        return self.__gradeRepo


    def set_student_repo(self, value):
        self.__studentRepo = value


    def set_problem_repo(self, value):
        self.__problemRepo = value


    def set_grade_repo(self, value):
        self.__gradeRepo = value
        
        
    def show_student_repo(self):
        return str(self.__studentRepo)


    def show_problem_repo(self):
        return str(self.__problemRepo)
    

    def show_grade_repo(self):
        return str(self.__gradeRepo)
    
    def add_student(self,studentID,name,group):
        #apeleaza metodele de validare
        #creeaza un nou student pe care il adauga in repo
        student=Student(studentID,name,group)
        self.__studentValidator.validate(student)
        self.__studentRepo.add(student)
        
    def add_random_students(self,count):
        #creeaza count studenti cu campuri random pe care ii adauga in repo
        #arunca exceptia "[Nr invalid]" daca count nu e un nr natural <200
        try:
            count=int(count)
            if 0>count>200:
                raise Exception()
        except:
            raise Exception('[Nr invalid]')
        while count>0:
            try:
                #names=['paco','anni','georgi','ralu','elena','cata','ada','bia','ioana','stefan']
                studentID=random.randrange(200)
                letters=string.ascii_lowercase
                name=str("".join(random.choice(letters) for _ in range(random.randrange(5,10))))
                group=random.randrange(201,218)
                self.add_student(studentID, name, group)
                count-=1
            except:
                continue
        
    def add_problem(self,labID,problemID,description,deadline):
        #apeleaza metodele de validare
        #creeaza o noua problema pe care o adauga in repo
        problem=Problem(labID,problemID,description,deadline)
        self.__problemValidator.validate(problem)
        self.__problemRepo.add(problem)
        
    def search_student_byID(self,studentID):
        #apeleaza metodele de validare
        #cauta un student in repo in fct de id
        self.__studentValidator.validate_studentID(studentID)
        studentID=int(studentID)
        student=self.__studentRepo.search(studentID,0)
        return student
    
    def search_student_byName(self,name):
        #apeleaza metodele de validare
        #cauta studenti in repo in fct de nume
        self.__studentValidator.validate_name(name)
        list=self.__studentRepo.search_name(name)
        return list
    
    def search_problem(self,labID,problemID):
        #apeleaza metodele de validare 
        #cauta o problema in repo in fct de id
        errortext=''
        try:
            self.__problemValidator.validate_labID(labID)
            labID=int(labID)
        except Exception as ex:
            errortext+=str(ex)
        try:
            self.__problemValidator.validate_problemID(problemID)
            problemID=int(problemID)
        except Exception as ex:
            errortext+=str(ex)
        if errortext=='':
            problem=self.__problemRepo.search(labID,problemID,0)
            return problem
        else:
            raise Exception(errortext)
    
    def delete_student(self,studentID):
        #apeleaza metode de validare
        #sterge un student din repo in fct de id
        #arunca exceptia "[Student inexistent]" daca nu exista un stud cu idul dat
        self.__studentValidator.validate_studentID(studentID)
        studentID=int(studentID)
        self.__studentRepo.remove(studentID)
        
    def delete_problem(self,labID,problemID):
        #apeleaza metode de validare
        #sterge un student din repo in fct de id
        errortext=''
        try:
            self.__problemValidator.validate_labID(labID)
            labID=int(labID)
        except Exception as ex:
            errortext+=str(ex)
        try:
            self.__problemValidator.validate_problemID(problemID)
            problemID=int(problemID)
        except Exception as ex:
            errortext+=str(ex)
        if errortext=='':
            self.__problemRepo.remove(labID,problemID)
        else:
            raise Exception(errortext)
        
    def modify_student(self,studentID,name,group):
        #apeleaza metode de validare
        #modifica datele unui student in fct de ID
        student=Student(studentID,name,group)
        self.__studentValidator.validate(student)
        studentID=int(studentID)
        if group!=None:
            group=int(group)
        self.search_student_byID(studentID)
        self.__studentRepo.modify(student)
    
    def assign_lab(self,studentID,labID,problemID):
        #asigneaza o tema de laborator unui anumit student in fct de ID
        #creeaza o noua entitate Grade pe care o adauga in gradeRepo
        #arunca exceptia "[Student inexistent]" daca studentul nu exista
        #arunca exceptia "[Problema inexistenta]" daca problema nu exista
        try:
            student=self.search_student_byID(studentID)
        except:
            raise Exception('[Student inexistent]')
        student=Student(studentID,None,None)
        try:
            self.search_problem(labID, problemID)
        except:
            raise Exception('[Problema inexistenta]')
        problem=Problem(labID,problemID,None,None)
        grade=Grade(student,problem,None)
        self.__gradeValidator.validate(grade)
        self.__gradeRepo.add(grade)
    
    def grade_lab(self,studentID,labID,problemID,grade):
        #asigneaza o nota unui student la o tema de laborator
        #arunca exceptia "[Relatie inexistenta]"
        student=Student(studentID,None,None)
        problem=Problem(labID,problemID,None,None)
        entity=Grade(student,problem,grade)
        self.__gradeValidator.validate(entity)
        self.__gradeRepo.modify(entity)
    
    
    def leaderboard_grade(self):
        #returneaza lista de note ordonata dupa nota
        leaderboard=[]
        for grade in self.__gradeRepo.get_all():
            if grade.get_grade()!=None:
                leaderboard.append(grade)
        leaderboard = shakeSort(leaderboard, key=lambda x: (x.get_grade()), reversed = True)
        return leaderboard
    
    def leaderboard_name(self):
#         returneaza lista de note ordonata dupa nume
        leaderboard=[]
        for grade in self.__gradeRepo.get_all():
            if grade.get_grade()!=None:
                name=self.__studentRepo.search(grade.get_student().get_student_id(),0).get_name()
                grade.get_student().set_name(name)
                leaderboard.append(grade)
        leaderboard = selectionSort(leaderboard, cmp = self.compare_student_names)
        return leaderboard
    
    def assignments(self,studentID):
        #returneaza lista de probleme asignate studentului cu idul dat
        self.__studentValidator.validate_studentID(studentID)
        studentID=int(studentID)
        result=[]
        for grade in self.__gradeRepo.get_all():
            if grade.get_student().get_student_id()==studentID:
                labID=grade.get_problem().get_lab_id()
                problemID=grade.get_problem().get_problem_id()
                problem=self.__problemRepo.search(labID,problemID,0)
                result.append(problem)
        return result

    def compare_student_names(self, el1, el2):
        return (el1.get_student().get_name()) < (el2.get_student().get_name())