from reader import Reader
from service import Service


def main():
    inputs, output = Reader('data.csv').read(['Economy..GDP.per.Capita.', 'Freedom'], 'Happiness.Score')
    Service(inputs, output).run()


if __name__ == '__main__':
    main()
