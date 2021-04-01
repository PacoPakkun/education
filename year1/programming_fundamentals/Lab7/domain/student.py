class Student:
    #tip abstract de date, reprezinta un student
    #<studentID> - identificator unic, nr natural nenul
    #<name> - string nevid
    #<group> - nr natural nenul
    
    def __init__(self,studentID,name,group):
        #creeaza o instanta a clasei student
        self.__studentID=studentID
        self.__name=name
        self.__group=group


    def get_student_id(self):
        return self.__studentID


    def get_name(self):
        return self.__name


    def get_group(self):
        return self.__group


    def set_student_id(self, value):
        self.__studentID = value


    def set_name(self, value):
        self.__name = value


    def set_group(self, value):
        self.__group = value
        
        
    def __eq__(self,other):
        #verifica daca instanta curenta student coincide cu un alt student
        return self.__studentID==other.get_student_id()
    
    def __str__(self):
        #returneaza o reprezentare in string a entitatii student
        return 'student #'+str(self.__studentID)+' ['+str(self.__name)+'] from '+str(self.__group)
    
    
    