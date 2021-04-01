class UI:
    '''
        *tip abstract de date, reprezinta interfata cu utilizatorul
    '''
    
    def __init__(self,service):
        '''
            *creeaza o instanta a interfatei cu utilizatorul
            *service: obiect de tip service
            *commands: dictionar comenzi
            *filtered: copie repo cu filtrul curent
        '''
        self.__service=service
        self.__commands={'add':self.__ui_add,'del':self.__ui_remove,'filter':self.__ui_filter,'undo':self.__ui_undo}
        self.__filter=['',-1]
        
    def __ui_add(self,id_produs,denumire,pret,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.add(int(id_produs),denumire,float(pret))
            print(list(map(str,self.__service.filter(self.__filter[0],self.__filter[1]))))
    
    def __ui_remove(self,id_produs,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            count=self.__service.remove(int(id_produs))
            print(count)
            print(list(map(str,self.__service.filter(self.__filter[0],self.__filter[1]))))
    
    def __ui_filter(self,denumire,pret,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__filter=[denumire,float(pret)]
            print(list(map(str,self.__service.filter(self.__filter[0],self.__filter[1]))))
    
    def __ui_undo(self,*args):
        if args!=():
            raise Exception('[Comanda invalida]')
        else:
            self.__service.undo()
            print(list(map(str,self.__service.filter(self.__filter[0],self.__filter[1]))))
        
    def run(self):
        '''
            *ruleaza programul principal
            *preia comenzi si apeleaza functii service
            *comanda exit termina executia
            *afiseaza eroarea [Comanda invalida] daca comanda introdusa e invalida
        '''
        print('Bine ati venit in aplicatia de gestiune magazin!\nSelectati comanda dorita:')
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