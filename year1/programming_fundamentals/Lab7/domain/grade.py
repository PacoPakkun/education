class Grade:
    #tip abstract de date, reprezinta nota unui student pt o problema lab
    #<student> - obiect student
    #<problem> - obiect problema
    #<grade> - nota studentului la problema respectiva, nr real din (0,10]
    
    def __init__(self,student,problem,grade):
        #creeaza o instanta a clasei nota
        self.__student=student
        self.__problem=problem
        self.__grade=grade

    def get_student(self):
        return self.__student


    def get_problem(self):
        return self.__problem


    def get_grade(self):
        return self.__grade


    def set_student(self, value):
        self.__student = value


    def set_problem(self, value):
        self.__problem = value


    def set_grade(self, value):
        self.__grade = value
        
    def __eq__(self,other):
        #verifica daca instanta notei curente coincide cu o alta nota
        return (self.__student==other.get_student())and(self.__problem==other.get_problem())
        
    def __str__(self):
        #returneaza o reprezentare in string a entitatii nota
        return 'student #'+str(self.__student.get_student_id())+' scored '+str(self.__grade)+' on lab#'+str(self.__problem.get_lab_id())+'/p#'+str(self.__problem.get_problem_id())