from reader import Reader
from service import Service


def main():
    repo = Reader()
    service = Service(repo)
    service.run()


if __name__ == '__main__':
    main()
