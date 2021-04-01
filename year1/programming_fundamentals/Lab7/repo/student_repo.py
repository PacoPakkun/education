from domain.student import Student

class StudentRepo:
    #tip abstract de date, reprezinta o colectie de studenti
    
    def __init__(self):
        #creeaza o instanta a repozitoriului de studenti
        self._repo=[]

    def __str__(self):
        #returneaza o reprezentare a repozitoriului de studenti
        return str(list(map(str,self._repo)))
        
    def __len__(self):
        #returneaza nr de studenti din repo
        return len(self._repo)
        
    def get_all(self):
        return self._repo[:]
    
    def set_all(self,repo):
        self._repo=repo
        
    def add(self,student):
        #adauga un student in repo
        #arunca exceptia "[ID existent]" daca studentul exista deja in repo
        if student in self._repo:
            raise Exception('[ID existent]')
        self._repo.append(student)
        
    def search(self,studentID,index):
        #returneaza studentul cu idul dat
        #arunca exceptia "[Student inexistent]" daca nu exista un student cu idul dat in repo
        #complexitate:
        #best case: T(n)=1 € teta(1) - primul student din repo este cel cautat
        #worst case: T(n)=n € teta(n) - ultimul student din repo este cel cautat
        #average: T(n)=(1/n)*1+(1/n)*2+...+(1/n)*n=(n+1)/2 € teta(n)
        #complexitate=O(n)
        if index==len(self._repo):
            raise Exception('[Student inexistent]')
        else:
            student=self._repo[index]
            if student.get_student_id()==studentID:
                return student
            else:
                return self.search(studentID,index+1)
#         for student in self._repo:
#             if student.get_student_id()==studentID:
#                 return student
#         raise Exception('[Student inexistent]')
    
    def search_name(self,name):
        #returneaza studentii cu numele dat
        #arunca exceptia "[Student inexistent]" daca nu exista un student cu idul dat in repo
        list=[]
        for student in self._repo:
            if name in student.get_name():
                list.append(student)
        if list!=[]:
            return list
        else:
            raise Exception('[Student inexistent]')
    
    def modify(self,student):
        #modifica campurile studentului cu idul dat
        #arunca exceptia "[Student inexistent]" daca nu exista un student cu idul dat in repo
        if student not in self._repo:
            raise Exception('[Student inexistent]')
        else:
            for i in range(0,len(self._repo)):
                if self._repo[i]==student:
                    if student.get_name()!=None:
                        self._repo[i].set_name(student.get_name())
                    if student.get_group()!=None:
                        self._repo[i].set_group(student.get_group())
    
    def remove(self,studentID):
        #elimina studentul cu idul dat
        #arunca exceptia "[ID inexistent]" daca nu exista un student cu idul dat in repo
        for i in range(0,len(self._repo)):
            if self._repo[i].get_student_id()==studentID:
                del(self._repo[i])
                return
        raise Exception('[Student inexistent]')
    