from domain.student import Student
from domain.problem import Problem
from domain.grade import Grade
from repo.student_repo import StudentRepo
from repo.problem_repo import ProblemRepo
from repo.grade_repo import GradeRepo
from valid.student_valid import StudentValidator
from valid.problem_valid import ProblemValidator
from valid.grade_valid import GradeValidator
from service.srv import Service
import unittest

class Test(unittest.TestCase):
    #tip abstract de date, reprezinta entitatea de testare
    
    def test_student(self):
        #testeaza clasa student, respectiv metodele sale
        student=Student(23,'paco',212)
        self.assertEqual(student.get_student_id(), 23)
        self.assertEqual(student.get_name(),'paco')
        self.assertEqual(student.get_group(), 212)
        self.assertEqual(str(student), 'student #23 [paco] from 212')
        student.set_group(223)
        self.assertEqual(student.get_group(), 223)
        student2=Student(23,'peco',212)
        self.assertEqual(student, student2)
        
    def test_problem(self):
        #testeaza clasa problema, respectiv metodele sale
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        self.assertEqual(problem.get_problem_id(), 2)
        self.assertEqual(problem.get_description(), 'Gestiune laboratoare studenti')
        self.assertEqual(problem.get_deadline(), '23/11/2019')
        self.assertEqual(str(problem), 'lab#7/p#2 due 23/11/2019: [Gestiune laboratoare studenti]')
        problem.set_description('Catalog studenti')
        self.assertEqual(problem.get_description(), 'Catalog studenti')
        problem.set_deadline('21/11/2019')
        self.assertEqual(problem.get_deadline(), '21/11/2019')
        problem2=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        self.assertEqual(problem, problem2)
        
    def test_grade(self):
        #testeaza clasa nota, respectiv metodele sale
        student=Student(23,'paco',212)
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        grade=Grade(student,problem,9.5)
        self.assertEqual(grade.get_student(),student)
        self.assertEqual(grade.get_problem(),problem)
        self.assertEqual(grade.get_grade(),9.5)
        self.assertEqual(str(grade),'student #23 scored 9.5 on lab#7/p#2')
        grade.set_grade(10)
        self.assertEqual(grade.get_grade(),10)
        student2=Student(15,'anni',223)
        grade.set_student(student2)
        self.assertEqual(grade.get_student(),student2)
        problem2=Problem(7,3,'Catalog studenti','23/11/2019')
        grade.set_problem(problem2)
        self.assertEqual(grade.get_problem(),problem2)
        grade2=Grade(student,problem,8)
        #self.assertEqual(grade,grade2)
        
    def test_student_valid(self):
        #testeaza clasa student_valid, respectiv metodele sale
        valid=StudentValidator()
        student=Student(23,'paco',212)
        valid.validate(student)
        with self.assertRaises(Exception):
            student=Student('x','anni',214)
            valid.validate(student)
        with self.assertRaises(Exception):
            student=Student(-3,'paco',212)
            valid.validate(student)
        with self.assertRaises(Exception):
            student=Student(23,4,212)
            valid.validate(student)
        with self.assertRaises(Exception):
            student=Student(23,'',212)
            valid.validate(student)
        with self.assertRaises(Exception):
            student=Student(23,'peco',-5)
            valid.validate(student)
        with self.assertRaises(Exception):
            student=Student(23,'peco','212b')
            valid.validate(student)
        with self.assertRaises(Exception):
            student=Student('23g','','')
            valid.validate(student)
        
    def test_problem_valid(self):
        #testeaza clasa problem_valid, respectiv metodele sale
        valid=ProblemValidator()
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        valid.validate(problem)
        with self.assertRaises(Exception):
            problem=Problem('x',2,'Gestiune laboratoare studenti','23/11/2019')
            valid.validate(problem)
        with self.assertRaises(Exception):
            problem=Problem(-3,2,'Gestiune laboratoare studenti','23/11/2019')
            valid.validate(problem)
        with self.assertRaises(Exception):
            problem=Problem(7,0,'Gestiune laboratoare studenti','23/11/2019')
            valid.validate(problem)
        with self.assertRaises(Exception):
            problem=Problem(7,'t','Gestiune laboratoare studenti','23/11/2019')
            valid.validate(problem)
        with self.assertRaises(Exception):
            problem=Problem(7,2,'','23/11/2019')
            valid.validate(problem)
        with self.assertRaises(Exception):
            problem=Problem(7,3,'Gestiune laboratoare studenti','')
            valid.validate(problem)
        with self.assertRaises(Exception):
            problem=Problem('5x','4x',5,23)
            valid.validate(problem)
            
    def test_grade_valid(self):
        #testeaza clasa grade_valid, respectiv metodele sale
        valid=GradeValidator()
        student=Student(23,'paco',212)
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        grade=Grade(student,problem,10)
        valid.validate(grade)
        with self.assertRaises(Exception):
            student=Student(23,'paco',212)
            problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
            grade=Grade(student,problem,11)
            valid.validate(grade)
        with self.assertRaises(Exception):
            student=Student(23,'paco',212)
            problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
            grade=Grade(student,problem,'')
            valid.validate(grade)
        
    def test_student_repo(self):
        #testeaza clasa student_repo, respectiv metodele sale
        repo=StudentRepo()
        self.assertEqual(str(repo),'[]')
        self.assertEqual(len(repo),0)
        with self.assertRaises(Exception):
            st=repo.search(23)
        student=Student(23,'paco',212)
        repo.add(student)
        self.assertEqual(str(repo),"['student #23 [paco] from 212']")
        self.assertEqual(len(repo),1)
        self.assertEqual(repo.search(23),student)
        self.assertEqual(repo.search_name('paco'),[student])
        student=Student(23,'anni',None)
        repo.modify(student)
        #self.assertEqual(str(repo)=="['student #23 [anni] from 212']")
        student=Student(23,'anni',214)
        repo.modify(student)
        self.assertEqual(str(repo),"['student #23 [anni] from 214']")
        repo.remove(23)
        with self.assertRaises(Exception):
            st=repo.search(23)
        with self.assertRaises(Exception):
            st=repo.search_name('anni')
        with self.assertRaises(Exception):
            repo.remove(23)

    def test_problem_repo(self):
        #testeaza clasa problem_repo, respectiv metodele sale
        repo=ProblemRepo()
        self.assertEqual(str(repo),'[]')
        self.assertEqual(len(repo),0)
        with self.assertRaises(Exception):
            pr=repo.search(7,2)
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        repo.add(problem)
        self.assertEqual(str(repo),"['lab#7/p#2 due 23/11/2019: [Gestiune laboratoare studenti]']")
        self.assertEqual(len(repo),1)
        self.assertEqual(repo.search(7,2),problem)
        with self.assertRaises(Exception):
            problem=Problem(7,2,'Catalog','30/11/2019')
            repo.add(problem)
        repo.remove(7,2)
        with self.assertRaises(Exception):
            pr=repo.search(7,2)
        with self.assertRaises(Exception):
            repo.remove(7,2)
            
    def test_grade_repo(self):
        #testeaza clasa grade_repo, respectiv metodele sale
        repo=GradeRepo()
        self.assertEqual(str(repo),'[]')
        self.assertEqual(len(repo),0)
        student=Student(23,'paco',212)
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        grade=Grade(student,problem,9.5)
        repo.add(grade)
        self.assertEqual(str(repo),"['student #23 scored 9.5 on lab#7/p#2']")
        self.assertEqual(len(repo),1)
        with self.assertRaises(Exception):
            student2=Student(23,'pac',214)
            problem2=Problem(7,2,'Catalog','25/11/2019')
            grade2=Grade(student2,problem2,8)
            repo.add(grade2)

    def test_service_add_student(self):
        #testeaza metoda add_student din clasa service
        pass
    
    def test_service_add_problem(self):
        #testeaza metoda add_problem din clasa service
        pass

    def test_service_search_student_byID(self):
        #testeaza metoda search_student_byID din service
        student=Student(23,'paco',212)
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        with self.assertRaises(Exception):
            service.search_student_byID('x')
        with self.assertRaises(Exception):
            service.search_student_byID('23')
        studentRepo.add(student)
        self.assertEqual(service.search_student_byID('23'),student)

    def test_service_search_student_byName(self):
        #testeaza metoda search_student_byName din service
        student=Student(23,'paco',212)
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        with self.assertRaises(Exception):
            service.search_student_byName('')
        with self.assertRaises(Exception):
            service.search_student_byName('paco')
        studentRepo.add(student)
        self.assertEqual(service.search_student_byName('paco'),[student])
        self.assertEqual(service.search_student_byName('pac'),[student])
        
    def test_service_search_problem(self):
        #testeaza metoda search_problem din service
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        with self.assertRaises(Exception):
            service.search_problem('','')
        with self.assertRaises(Exception):
            service.search_problem(7,2)
        problemRepo.add(problem)
        self.assertEqual(service.search_problem(7,2),problem)
        
    def test_service_delete_student(self):
        #testeaza metoda delete_student din service
        student=Student(23,'paco',212)
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        with self.assertRaises(Exception):
            service.delete_student(1)
        with self.assertRaises(Exception):
            service.delete_student('')
        with self.assertRaises(Exception):
            service.delete_student(-3)
        studentRepo.add(student)
        service.delete_student(23)
        self.assertEqual(str(studentRepo),'[]')
        
    def test_service_delete_problem(self):
        #testeaza metoda delete_student din service
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        with self.assertRaises(Exception):
            service.delete_problem(7,2)
        with self.assertRaises(Exception):
            service.delete_problem('','')
        with self.assertRaises(Exception):
            service.delete_problem(7,-3)
        problemRepo.add(problem)
        service.delete_problem(7,2)
        self.assertEqual(str(problemRepo),'[]')
        
    def test_service_modify_student(self):
        #testeaza metoda modify_student din service
        student=Student(23,'paco',212)
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        with self.assertRaises(Exception):
            service.modify_student(23,'paco',212)
        with self.assertRaises(Exception):
            service.modify_student(-23,'paco','212b')
        studentRepo.add(student)
        service.modify_student(23,'anni',214)
        student=Student(23,'anni',214)
        self.assertEqual(studentRepo.search(23),student)
        
    def test_service_add_random_students(self):
        #testeaza metoda add_random_student din service
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        service.add_random_students(1)
        self.assertEqual(len(studentRepo),1)
        service.add_random_students(13)
        self.assertEqual(len(studentRepo),14)
        
    def test_service_assign_lab(self):
        #testeaza metoda assign_lab din service
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        student=Student(23,'paco',212)
        problem=Problem(7,2,'Gestiune laboratoare studenti','23/11/2019')
        grade=Grade(student,problem,None)
        with self.assertRaises(Exception):
            service.assign_lab(23, 7, 2)
        service.add_student(23,'paco',212)
        with self.assertRaises(Exception):
            service.assign_lab(23, 7, 2)
        service.add_problem(7, 2, 'Gestiune teme laborator', '28/11/2019')
        service.assign_lab(23,7,2)
        self.assertEqual(len(gradeRepo),1)
        self.assertEqual(len(gradeRepo),1)
        
    def test_service_grade_lab(self):
        #testeaza metoda grade_lab din service
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        service.add_student(23,'paco',212)
        service.add_problem(7, 2, 'Gestiune teme laborator', '28/11/2019')
        with self.assertRaises(Exception):
            service.grade_lab(23, 7, 2, 10)
        service.assign_lab(23, 7, 2)
        with self.assertRaises(Exception):
            service.grade_lab(23, 7, 2, 13)
        service.grade_lab(23, 7, 2, 10)
        self.assertEqual(gradeRepo.search(23, 7, 2).get_grade(),10)

    def test_service_leaderboard_grade(self):
        #testeaza metoda leaderboard_grade din service
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        service.add_student(23,'paco',212)
        service.add_student(24,'ralu',212)
        service.add_student(25,'elena',212)
        service.add_problem(7, 2, 'Gestiune teme laborator', '28/11/2019')
        service.assign_lab(23,7,2)
        service.assign_lab(24,7,2)
        service.assign_lab(25,7,2)
        service.grade_lab(23,7,2,10)
        service.grade_lab(24,7,2,8)
        service.grade_lab(25,7,2,5)
        self.assertEqual(str(list(map(str,service.leaderboard_grade()))),"['student #23 scored 10.0 on lab#7/p#2', 'student #24 scored 8.0 on lab#7/p#2', 'student #25 scored 5.0 on lab#7/p#2']")
    
    def test_service_leaderboard_name(self):
        #testeaza metoda leaderboard_name din service
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        service.add_student(23,'paco',212)
        service.add_student(24,'ralu',212)
        service.add_student(25,'elena',212)
        service.add_problem(7, 2, 'Gestiune teme laborator', '28/11/2019')
        service.assign_lab(23,7,2)
        service.assign_lab(24,7,2)
        service.assign_lab(25,7,2)
        service.grade_lab(23,7,2,10)
        service.grade_lab(24,7,2,8)
        service.grade_lab(25,7,2,3)
        self.assertEqual(str(list(map(str,service.leaderboard_name()))),"['student #25 scored 3.0 on lab#7/p#2', 'student #23 scored 10.0 on lab#7/p#2', 'student #24 scored 8.0 on lab#7/p#2']")
    
    def test_service_assignments(self):
        #testeaza metoda assignments din service
        studentRepo=StudentRepo()
        problemRepo=ProblemRepo()
        gradeRepo=GradeRepo()
        service=Service(studentRepo,problemRepo,gradeRepo)
        service.add_student(23,'paco',212)
        service.add_problem(7, 2, 'Gestiune teme laborator', '28/11/2019')
        service.add_problem(7, 3, 'Catalog studenti', '28/11/2019')
        service.add_problem(7, 4, 'Biblioteca', '28/11/2019')
        service.assign_lab(23,7,2)
        service.assign_lab(23,7,3)
        service.assign_lab(23,7,4)
        self.assertEqual(str(list(map(str,service.assignments(23)))),"['lab#7/p#2 due 28/11/2019: [Gestiune teme laborator]', 'lab#7/p#3 due 28/11/2019: [Catalog studenti]', 'lab#7/p#4 due 28/11/2019: [Biblioteca]']")
