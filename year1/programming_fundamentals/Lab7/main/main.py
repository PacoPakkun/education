from tests.tests import Test
from repo.student_repo import StudentRepo
from repo.problem_repo import ProblemRepo
from repo.grade_repo import GradeRepo
from fileRepo.file_student_repo import FileStudentRepo
from fileRepo.file_problem_repo import FileProblemRepo
from fileRepo.file_grade_repo import FileGradeRepo
from service.srv import Service
from ui.ui import UI

def main():
    #prog principal
    #instanteaza interfata din ui, respectiv entitatile repo si srv pe care le va manipula programul
    studentRepo=FileStudentRepo('student_repo.txt')
    problemRepo=FileProblemRepo('problem_repo.txt')
    gradeRepo=FileGradeRepo('grade_repo.txt')
    service=Service(studentRepo,problemRepo,gradeRepo)
    ui=UI(service)
    ui.run()
    
main()