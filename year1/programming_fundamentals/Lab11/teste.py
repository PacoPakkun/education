from back_rec import backRec
from back_iter import backIter

def test_back_rec():
    '''
        *fct de testare aferenta variantei recursive de backtracking
    '''
    sol=[]
    backRec('',2,sol)
    assert sol==['()']
    sol=[]
    backRec('',4,sol)
    assert sol==['(())','()()']
    sol=[]
    backRec('',6,sol)
    assert sol==['((()))', '(()())', '(())()', '()(())', '()()()']
    
def test_bac_iter():
    '''
        *fct de testare aferenta variantei iterative de backtracking
    '''
    sol=[]
    backIter(2,sol)
    assert sol==['()']
    sol=[]
    backIter(4,sol)
    assert sol==['(())','()()']
    sol=[]
    backIter(6,sol)
    assert sol==['((()))', '(()())', '(())()', '()(())', '()()()']

def run_all_tests():
    test_back_rec()
    test_bac_iter()

run_all_tests()
    