from reader import Reader
from service import Service


def main():
    inputs, outputs = Reader('data.csv').read(['Economy..GDP.per.Capita.', 'Freedom'], ['Happiness.Score','Health..Life.Expectancy.'])
    Service(inputs, outputs).run()


if __name__ == '__main__':
    main()
