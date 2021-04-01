from domain.produs import Produs
from repo.repo import ProdusRepo
from service.service import Service

class Teste:
    '''
        *tip abstract de date, reprezinta entitatea de testare
    '''
    
    def __init__(self):
        pass
    
    def __test_domain(self):
        '''
            *testeaza functionalitatile aferente clasei Produs
        '''
        produs=Produs(13,'tractor',70)
        assert produs.get_id()==13
        assert produs.get_denumire()=='tractor'
        assert produs.get_pret()==70
        produs.set_id(23)
        assert produs.get_id()==23
        produs.set_denumire('chiuveta')
        assert produs.get_denumire()=='chiuveta'
        produs.set_pret(69.99)
        assert produs.get_pret()==69.99
        
    def __test_repo(self):
        '''
            *testeaza functionalitatile aferente clasei ProdusRepo
        '''
        repo=ProdusRepo('test_repo.txt')
        assert str(repo)=='13;tractor;70.0 21;ambulanta;450.0 33;chiuveta;21.5'
        produs=Produs(7,'Madonna',15.5)
        repo.add(produs)
        assert str(repo)=='13;tractor;70.0 21;ambulanta;450.0 33;chiuveta;21.5 7;Madonna;15.5'
        produs=Produs(17,'jambon',44)
        repo.add(produs)
        assert str(repo)=='13;tractor;70.0 21;ambulanta;450.0 33;chiuveta;21.5 7;Madonna;15.5 17;jambon;44.0'
        count=repo.remove(7)
        assert str(repo)=='13;tractor;70.0 21;ambulanta;450.0 33;chiuveta;21.5'
        assert count==2
        assert list(map(str,repo.get_history()[0]))==['7;Madonna;15.5','17;jambon;44.0']
        repo.undo()
        assert str(repo)=='13;tractor;70.0 21;ambulanta;450.0 33;chiuveta;21.5 7;Madonna;15.5 17;jambon;44.0'
        repo.remove(7)
        assert list(map(str,repo.filter('',-1)))==['13;tractor;70.0', '21;ambulanta;450.0', '33;chiuveta;21.5']
        assert list(map(str,repo.filter('ambulanta',-1)))==['21;ambulanta;450.0']
        assert list(map(str,repo.filter('',21.5)))==['33;chiuveta;21.5']
        assert list(map(str,repo.filter('tractor',70)))==['13;tractor;70.0']
        
    def __test_service_add(self):
        '''
            *testeaza functia de adaugare din Service
        '''
        repo=ProdusRepo('test_repo.txt')
        service=Service(repo)
        service.add(5, 'cactus', 567)
        assert str(repo.get_all()[-1])=='5;cactus;567.0'
    
    def __test_service_remove(self):
        '''
            *testeaza functia de stergere din Service
        '''
        repo=ProdusRepo('test_repo.txt')
        service=Service(repo)
        service.remove(5)
        assert str(repo)=='13;tractor;70.0 21;ambulanta;450.0 33;chiuveta;21.5'
        
    def __test_service_filter(self):
        '''
            *testeaza functia de stergere din Service
        '''
        repo=ProdusRepo('test_repo.txt')
        service=Service(repo)
        assert list(map(str,service.filter('',-1)))==['13;tractor;70.0', '21;ambulanta;450.0', '33;chiuveta;21.5']
        assert list(map(str,service.filter('ambulanta',-1)))==['21;ambulanta;450.0']
        assert list(map(str,service.filter('',21.5)))==['33;chiuveta;21.5']
        assert list(map(str,service.filter('tractor',70)))==['13;tractor;70.0']
    
    def __test_service_undo(self):
        '''
            *testeaza functia uno din Service
        '''
        repo=ProdusRepo('test_repo.txt')
        service=Service(repo)
        service.remove(33)
        service.undo()
        assert str(repo)=='13;tractor;70.0 21;ambulanta;450.0 33;chiuveta;21.5'
    
    def run_all_tests(self):
        '''
            *ruleaza toate testele
        '''
        self.__test_domain()
        self.__test_repo()
        self.__test_service_add()
        self.__test_service_remove()
        self.__test_service_filter()
        self.__test_service_undo()
        
test=Teste()
test.run_all_tests()