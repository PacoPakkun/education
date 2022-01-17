from repo import Reader
from service_emotions import ServiceEmotions
from service_iris import ServiceIris


def main():
    inputs, output = Reader().read_iris()
    ServiceIris(inputs, output).run()

    # inputs, output = Reader().read_text()
    # ServiceEmotions(inputs, output).run()


if __name__ == '__main__':
    main()
