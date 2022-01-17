from reader import Reader
from service import Service


def main():
    inputs, output = Reader('iris.data').read()
    Service(inputs, output).run()


if __name__ == '__main__':
    main()
