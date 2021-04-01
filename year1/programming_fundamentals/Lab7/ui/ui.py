from service.srv import Service

class UI:
    #tip abstract de date, reprezinta interfata cu utilizatorul
    
    def __init__(self,service):
        #creeaza o instanta a clasei UI
        self.__service=service
        self.__commands={"add":self.__ui_add,"search":self.__ui_search,"delete":self.__ui_delete,"modify":self.__ui_modify,"assign":self.__ui_assign_lab,"grade":self.__ui_grade_lab,"top":self.__ui_top,"assignments":self.__ui_assignments}
        
    def __ui_add(self,entity,*args):
        subcommands={"stud":self.__ui_add_student,"prob":self.__ui_add_problem,"random":self.__ui_add_random}
        if entity in subcommands:
            subcommands[entity](*args)
        else:
            raise Exception('[Comanda invalida]')
            
    def __ui_add_student(self,studentID,name,group,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.add_student(studentID,name,group)
        print(self.__service.show_student_repo())
          
    def __ui_add_random(self,entity,*args):
        subcommands={"stud":self.__ui_add_random_students,"prob":self.__ui_add_random_students}
        if entity in subcommands:
            subcommands[entity](*args)
        else:
            raise Exception('[Comanda invalida]')
  
    def __ui_add_random_students(self,count,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.add_random_students(count)
        print(self.__service.show_student_repo())
            
    def __ui_add_problem(self,labID,problemID,description,deadline,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.add_problem(labID,problemID,description,deadline)
        print(self.__service.show_problem_repo())
        
    def __ui_search(self,entity,*args):
        subcommands={"stud":self.__ui_search_student,"prob":self.__ui_search_problem}
        if entity in subcommands:
            result=subcommands[entity](*args)
            print(result)
        else:
            raise Exception('[Comanda invalida]')
        
    def __ui_search_student(self,entity,*args):
        subcommands={"id":self.__ui_search_student_byID,"name":self.__ui_search_student_byName}
        if entity in subcommands:
            result=subcommands[entity](*args)
            return result
        else:
            raise Exception('[Comanda invalida]')
        
    def __ui_search_student_byID(self,studentID,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            return self.__service.search_student_byID(studentID)
        
    def __ui_search_student_byName(self,name,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            return list(map(str,self.__service.search_student_byName(name)))
        
    def __ui_search_problem(self,labID,problemID,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            return self.__service.search_problem(labID,problemID)
    
    def __ui_delete(self,entity,*args):
        subcommands={"stud":self.__ui_delete_student,"prob":self.__ui_delete_problem}
        if entity in subcommands:
            subcommands[entity](*args)
        else:
            raise Exception('[Comanda invalida]')
    
    def __ui_delete_student(self,studentID,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.delete_student(studentID)
            print(self.__service.show_student_repo())
        
    def __ui_delete_problem(self,labID,problemID,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.delete_problem(labID,problemID)
            print(self.__service.show_problem_repo())
        
    def __ui_modify(self,entity,*args):
        subcommands={"stud":self.__ui_modify_student,"prob":self.__ui_modify_student}
        if entity in subcommands:
            subcommands[entity](*args)
        else:
            raise Exception('[Comanda invalida]')
        
    def __ui_modify_student(self,entity,studentID,value,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            name,group=None,None
            if entity=='name':
                name=value
            elif entity=='group':
                group=value
            else:
                raise Exception('[Comanda invalida]')
            self.__service.modify_student(studentID,name,group)
            print(self.__service.show_student_repo())
        
    def __ui_assign_lab(self,studentID,labID,problemID,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.assign_lab(studentID,labID,problemID)
        print(self.__service.show_grade_repo())
        
    def __ui_grade_lab(self,studentID,labID,problemID,grade,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.grade_lab(studentID,labID,problemID,grade)
        print(self.__service.show_grade_repo())
    
    def __ui_top(self,entity,*args):
        subcommands={"grade":self.__ui_top_grade,"name":self.__ui_top_name}
        if entity in subcommands:
            result=subcommands[entity](*args)
            print(result)
        else:
            raise Exception('[Comanda invalida]')
    
    def __ui_top_grade(self,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            result=str(list(map(str,self.__service.leaderboard_grade())))
            return result
    
    def __ui_top_name(self,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            result=str(list(map(str,self.__service.leaderboard_name())))
            return result
    
    def __ui_assignments(self,studentID,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            result=str(list(map(str,self.__service.assignments(studentID))))
            print(result)
        
    def run(self):
        print('Bine ati venit in aplicatia de gestiune laboratoare!\nSelectati comanda dorita:\n  <add stud #studentID #name #group>\n  <add prob #labID #problemID #description #deadline>\n  <add random stud #number>\n  <search stud id #studentID>\n  <search stud name #name>\n  <search prob #labID #problemID>\n  <delete stud #studentID>\n  <delete prob #labID #problemID>\n  <modify stud name/group #id #value>\n  <assign #studentID #labID #problemID>\n  <grade #studentID #labID #problemID #grade>\n  <top grade>\n  <top name>\n  <assignments #studentID>')
        while True:
            try:
                cmd=input('>')
                cmd=cmd.strip().split(' ')
                if cmd[0]=='exit':
                    break
                elif cmd[0] in self.__commands:
                    self.__commands[cmd[0]](*cmd[1:])
                else:
                    raise Exception('[Comanda invalida]')
            except Exception as ex:
                if type(ex)==TypeError:
                    print('[Comanda invalida]')
                else:
                    print(str(ex))