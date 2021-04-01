from domain.problem import Problem

class ProblemValidator():
    #tip abstract de date, reprezinta entitatea de validare probleme
    
    def __init__(self):
        pass
    
    def validate(self,problem):
        #valideaza entitatea problem
        #arunca exceptia "[LabID invalid]" daca lab_idul nu este nr natural nenul
        #arunca exceptia "[ProblemID invalid]" daca problem_idul nu este nr natural nenul
        #arunca exceptia "[Descriere invalida]" daca descrierea nu e un string nevid
        #arunca exceptia "[Deadline invalid]" daca deadlineul nu este un string nevid
        errortext=''
        try:
            if problem.get_lab_id!=None:
                problem.set_lab_id(int(problem.get_lab_id()))
                if problem.get_lab_id()<=0:
                    errortext+='[LabID invalid]'
        except:
            errortext+='[LabID invalid]'
        try:
            if problem.get_problem_id()!=None:
                problem.set_problem_id(int(problem.get_problem_id()))
                if problem.get_problem_id()<=0:
                    errortext+='[ProblemID invalid]'
        except:
            errortext+='[ProblemID invalid]'
        if problem.get_description()!=None:
            if type(problem.get_description())!=str:
                errortext+='[Descriere invalida]'
            elif problem.get_description()<='':
                errortext+='[Descriere invalida]'
        if problem.get_deadline()!=None:
            if type(problem.get_deadline())!=str:
                errortext+='[Deadline invalid]'
            elif problem.get_deadline()<='':
                errortext+='[Deadline invalid]'
        if errortext!='':
            raise Exception(errortext)
        
    def validate_labID(self,labID):
        try:
            if labID!=None:
                labID=int(labID)
                if (type(labID)!=int)or(labID<=0):
                    raise Exception('[LabID invalid]')
        except:
            raise Exception('[LabID invalid]')
        
    def validate_problemID(self,problemID):
        try:
            if problemID!=None:
                problemID=int(problemID)
                if (type(problemID)!=int)or(problemID<=0):
                    raise Exception('[ProblemID invalid]')
        except:
            raise Exception('[ProblemID invalid]')