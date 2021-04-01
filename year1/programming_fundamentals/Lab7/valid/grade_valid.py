from domain.grade import Grade
from domain.student import Student
from domain.problem import Problem
from valid.student_valid import StudentValidator
from valid.problem_valid import ProblemValidator

class GradeValidator:
    #tip abstract de date, reprezinta entitatea de validare note
    
    def __init__(self):
        self.__studentValidator=StudentValidator()
        self.__problemValidator=ProblemValidator()
    
    def validate(self,grade):
        #valideaza entitatea grade
        #arunca exceptia "[Student invalid]" daca studentul nu e un obiect student valid
        #arunca exceptia "[Problema invalida]" daca problema nu e un obiect problema valid
        #arunca exceptia "[Nota invalida]" daca nota nu e un nr real din (0,10]
        errortext=''
        try:
            self.__studentValidator.validate(grade.get_student())
        except Exception as ex:
            errortext+=str(ex)
        try:
            self.__problemValidator.validate(grade.get_problem())
        except Exception as ex:
            errortext+=str(ex)
        try:
            if grade.get_grade()!=None:
                grade.set_grade(float(grade.get_grade()))
                if not(0<grade.get_grade()<=10):
                    errortext+='[Nota invalida]'
        except:
            errortext+='[Nota invalida]'
        if errortext!='':
            raise Exception(errortext)