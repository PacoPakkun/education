from domain.student import Student

class StudentValidator():
    #tip abstract de date, reprezinta entitatea de validare studenti
    
    def __init__(self):
        pass
    
    def validate(self,student):
        #valideaza entitatea student
        #arunca exceptia "[ID invalid]" daca idul nu este nr natural nenul
        #arunca exceptia "[Nume invalid]" daca idul nu este string nevid
        #arunca exceptia "[Grupa invalida]" daca idul nu este nr natural nenul
        errortext=''
        try:
            if student.get_student_id!=None:
                student.set_student_id(int(student.get_student_id()))
                if student.get_student_id()<=0:
                    errortext+='[ID invalid]'
        except:
            errortext+='[ID invalid]'
        if student.get_name()!=None:
            if type(student.get_name())!=str:
                errortext+='[Nume invalid]'
            elif student.get_name()=='':
                errortext+='[Nume invalid]'
        try:
            if student.get_group()!=None:
                student.set_group(int(student.get_group()))
                if student.get_group()<=0:
                    errortext+='[Grupa invalida]'
        except:
            errortext+='[Grupa invalida]'
        if errortext!='':
            raise Exception(errortext)
        
    def validate_studentID(self,studentID):
        try:
            if studentID!=None:
                studentID=int(studentID)
                if (type(studentID)!=int)or(studentID<=0):
                    raise Exception('[ID invalid]')
        except:
            raise Exception('[ID invalid]')
        
    def validate_name(self,name):
        if name!=None:
            if (type(name)!=str)or(name==''):
                raise Exception('[Nume invalid]')