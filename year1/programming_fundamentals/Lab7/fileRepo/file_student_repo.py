from repo.student_repo import StudentRepo
from domain.student import Student

class FileStudentRepo(StudentRepo):
    #tip abstract de date, subclasa a repozitoriului de studenti, reprezinta un StudentRepo integrat in memorie
    
    def __init__(self,fileName):
        #creeaza o instanta a repozitoriului de studenti din fisier
        StudentRepo.__init__(self)
        self.__fileName=fileName
        self.__loadFromFile()
       
    def __loadFromFile(self):
        #incarca in repozitoriu datele din fisier
        #arunca exceptia FileNotFoundException daca nu exista fisierul
        #arunca exceptia ValueError daca apare o eroare la citirea din fisier
            #StudentRepo.set_all(self, [])
        self._repo=[]
        with open(self.__fileName,"r") as file:
            for line in file:
                if line.strip()=="":
                    continue
                line=line.strip()
                attributes=line.split(";")
                student=Student(int(attributes[0]),attributes[1],int(attributes[2]))
                StudentRepo.add(self, student)
    
    def __saveToFile(self):
        with open(self.__fileName,"w") as file:
            students=StudentRepo.get_all(self)
            for student in students:
                self.__addToFile(student, file)
        
    def __addToFile(self,student,file):
        #adauga un nou student in fisier
        studentString=str(student.get_student_id())+';'+student.get_name()+';'+str(student.get_group())
        file.write("\n"+studentString)

    def add(self,student):
        self.__loadFromFile()
        StudentRepo.add(self, student)
        with open(self.__fileName,"a") as file:
            self.__addToFile(student, file)

    def remove(self,studentID):
        self.__loadFromFile()
        StudentRepo.remove(self, studentID)
        self.__saveToFile()
        
    def modify(self,student):
        self.__loadFromFile()
        StudentRepo.modify(self, student)
        self.__saveToFile()
