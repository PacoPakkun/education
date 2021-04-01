from domain.produs import Produs
from repo.repo import ProdusRepo
from service.service import Service
from ui.ui import UI

def main():
    repo=ProdusRepo('repo.txt')
    service=Service(repo)
    ui=UI(service)
    ui.run()
    
main()