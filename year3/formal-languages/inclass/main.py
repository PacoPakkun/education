class Gramatica:
    def __init__(self, file):
        f = open(file, 'r')
        self.reguli = f.read().split('\n')
        self.neterminali, self.terminali = [], []
        for regula in self.reguli:
            self.neterminali.append(regula.split('->')[0])
        for regula in self.reguli:
            for char in regula.split('->')[1]:
                if char not in self.neterminali:
                    self.terminali.append(char)


if __name__ == '__main__':
    Gramatica('date.txt')
