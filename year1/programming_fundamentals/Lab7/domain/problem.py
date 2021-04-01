class Problem:
    #tip abstract de date, reprezinta o problema de laborator
    #<labID> - nr laborator, nr natural nenul
    #<problemID> - nr problema, nr natural nenul
    #<description> - string nevid
    #<deadline> - string nevid
    
    def __init__(self,labID,problemID,description,deadline):
        #creeaza o instanta a clasei problema
        self.__labID=labID
        self.__problemID=problemID
        self.__description=description
        self.__deadline=deadline

    def set_lab_id(self, value):
        self.__labID = value


    def set_problem_id(self, value):
        self.__problemID = value


    def get_lab_id(self):
        return self.__labID


    def get_problem_id(self):
        return self.__problemID


    def get_description(self):
        return self.__description


    def get_deadline(self):
        return self.__deadline
    

    def set_description(self, value):
        self.__description = value


    def set_deadline(self, value):
        self.__deadline = value
        
    def __eq__(self,other):
        #verifica daca instanta curenta problema coincide cu o alta problema
        return (self.__labID==other.get_lab_id())and(self.__problemID==other.get_problem_id())
    
    def __str__(self):
        #returneaza o reprezentare in string a entitatii problema
        return 'lab#'+str(self.__labID)+'/p#'+str(self.__problemID)+' due '+str(self.__deadline)+': ['+str(self.__description)+']'
    
