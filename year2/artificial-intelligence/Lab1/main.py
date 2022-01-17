import math


class Problems:

    # Problema 1
    # Gaseste ultimul cuvant (lexicografic) care apare intr-un text
    # text: string, format din cuvinte separate prin spatii
    # Returneaza un string continand ultimul cuvant din text
    def p1(self, text):
        cuv = text.split(' ')
        ultim = ''
        for c in cuv:
            if ultim.lower() < c.lower():
                ultim = c
        return ultim

    # Problema 2
    # Gaseste distanta euclidiana dintre 2 puncte in plan
    # a, b: perechi (int, int), cele doua puncte, reprezentate prin abscisa si ordonata
    # Returneaza un float reprezentand distanta euclidiana
    def p2(self, a, b):
        return math.sqrt((a[0] - b[0]) ** 2 + (a[1] - b[1]) ** 2)

    # Problema 3
    # Determina produsul scalar a 2 vectori rari care contin numere reale
    # l, x: liste de numere intregi
    # Returneaza un numar intreg, reprezentand produsul scalar al celor 2 vectori
    def p3(self, v1, v2):
        assert len(v1) == len(v2)
        produs = 0
        for i in range(len(v1)):
            produs += v1[i] * v2[i]
        return produs

    # Problema 4
    # Determina cuvintele dintr-un text care apar o singura data in acel text
    # text: string, format din cuvinte separate prin spatii
    # Returneaza o lista de cuvinte, reprezentand cuvintele ce apar o singura data in textul initial
    def p4(self, text):
        singles = []
        words = text.split(' ')
        for word in words:
            count = 0
            for w in words:
                if word == w:
                    count = count + 1
            if count == 1:
                singles.append(word)
        return singles

    # Problema 5
    # Determina o valoare dintr-un sir de numere de la 1 la n - 1 care se repeta de 2 ori
    # list: obiect reprezentand o lista de numere, de la 1 la n - 1
    # Returneaza un numar natural, reprezentand cel ce se repeta de 2 ori
    def p5(self, list):
        freq = []
        for i in range(len(list)):
            freq.append(0)
        no = 0
        for no in list:
            freq[no] += 1
            if freq[no] == 2:
                break
        return no

    # Problema 6
    # Determina elementul majoritar dintr-un sir de numere (trebuie sa apara de mai mult de n / 2 ori)
    # list: obiect reprezentand o lista de numere intregi
    # Returneaza un numar natural, reprezentand elementul cu cea mai mare frecventa sau 0 daca acesta nu exista
    def p6(self, list):
        dict = {}
        count = 0
        element = ''
        for no in list:
            dict[no] = dict.get(no, 0) + 1
            if dict[no] >= count:
                count, element = dict[no], no
        if element != '' and dict[element] > len(list) // 2:
            return element
        return 0

    # Problema 7
    # Determina al k-lea cel mai mare element al unui sir cu n elemente naturale
    # list: obiect, reprezentand o lista de numere intregi
    # k: numar intreg, reprezentand indicele elementului dorit
    # Returneaza al k-lea cel mai mare element, un numar intreg
    def p7(self, list, k):
        max = 0
        for i in range(k):
            max = float('-inf')
            for j in range(len(list)):
                if list[j] > max:
                    max = list[j]
            while max in list:
                list.remove(max)

        return max

    # Problema 8
    # Genereaza toate numerele in reprezentare binara de la 1 la un n dat
    # n: numar natural
    # Returneaza o lista cu toate reprezentarile binare ale numerelor
    def p8(self, n):
        binaries = []
        binary = [1]
        binaries.append(''.join(str(no) for no in binary))
        for i in range(n - 1):
            pos = len(binary) - 1
            while pos >= 0 and binary[pos] == 1:
                binary[pos] = 0
                pos -= 1
            if pos <= 0:
                binary.insert(0, 1)
            else:
                binary[pos] = 1
            binaries.append(''.join(str(no) for no in binary))
        return binaries

    # Problema 9

    # Determina suma elementelor din submatricile determinate de niste perechi de coordonate
    # matx: matrice de numere intregi
    # list: lista de liste de perechi de numere intregi
    # Returneaza o lista formata din numere intregi, reprezentand suma numerelor din submatricile mentionate

    def p9(self, matx, list):
        sums = []
        for pair in list:
            sum = 0
            first = pair[0]
            second = pair[1]
            for i in range(first[0], second[0] + 1):
                for j in range(first[1], second[1] + 1):
                    sum += matx[i][j]
            sums.append(sum)
        return sums

    # Problema 10

    # Determina indexul liniei unei matrici ce contine cele mai multe cifre de 1
    # matx: matrice de numere intregi
    # Returneaza un numar natural, reprezentand indexul liniei cu cei mai multi 1

    def p10(self, matx):
        global j
        no, max = 0, -1
        for i in range(len(matx)):
            for j in range(len(matx[i])):
                if matx[i][j] == 1:
                    break
            if len(matx[i]) - j >= no:
                no, max = len(matx[i]) - j, i
        return max

    def run(self):

        assert self.p1("Ana are 1 mar") == "mar"
        assert self.p1("Ana are mere rosii si galbene") == "si"
        assert self.p1("Ana are mere zece rosii si galbene") == "zece"
        print("test 1 ok")

        assert self.p2((1, 1), (3, 1)) == 2
        assert self.p2((5, 5), (2, 1)) == 5
        assert self.p2((2, 1), (5, 1)) == 3
        print("test 2 ok")

        assert self.p3([1, 0, 2, 0, 3], [1, 2, 0, 3, 1]) == 4
        assert self.p3([8, 0], [0, 1]) == 0
        assert self.p3([1], [0]) == 0
        print("test 3 ok")

        assert self.p4("ana are ana are mere rosi") == ['mere', 'rosi']
        assert self.p4("ana nu are mere") == ['ana', 'nu', 'are', 'mere']
        assert self.p4("ea nu e ana") == ['ea', 'nu', 'e', 'ana']
        print("test 4 ok")

        assert self.p5([1, 2, 3, 4, 5, 3]) == 3
        assert self.p5([1, 2, 3, 4, 2]) == 2
        assert self.p5([1, 2, 1]) == 1
        print("test 5 ok")

        assert self.p6([7, 9, 1, 2, 3, 1, 5, 6]) == 0
        assert self.p6([0, 1, 1, 1, 4, 1, 6]) == 1
        assert self.p6([2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]) == 2
        print("test 6 ok")

        assert self.p7([7, 4, 6, 9, 1, 3], 1) == 9
        assert self.p7([7, 4, 6, 9, 1, 3], 2) == 7
        assert self.p7([7, 4, 6, 9, 1, 3], 6) == 1
        print("test 7 ok")

        assert self.p8(4) == ['1', '10', '11', '100']
        assert self.p8(5) == ['1', '10', '11', '100', '101']
        assert self.p8(10) == ['1', '10', '11', '100', '101', '110', '111', '1000', '1001', '1010']
        print("test 8 ok")

        matx = [[0, 2, 5, 4, 1], [4, 8, 2, 3, 7], [6, 3, 4, 6, 2], [7, 3, 1, 8, 3], [1, 5, 7, 9, 4]]
        assert self.p9(matx, [[(0, 0), (1, 1)], [(1, 1), (3, 3)], [(2, 2), (4, 4)]]) == [14, 38, 44]
        assert self.p9(matx, [[(1, 1), (3, 3)], [(2, 2), (4, 4)]]) == [38, 44]
        assert self.p9(matx, [[(0, 0), (1, 1)]]) == [14]
        print("test 9 ok")

        assert self.p10([[1, 1, 1, 1, 1], [0, 1, 1, 1, 1], [0, 0, 1, 1, 1]]) == 0
        assert self.p10([[0, 0, 1, 1, 1], [0, 1, 1, 1, 1], [0, 0, 1, 1, 1]]) == 1
        assert self.p10([[0, 0, 0, 1], [0, 1, 1, 1], [0, 0, 1, 1]]) == 1
        print("test 10 ok")


if __name__ == '__main__':
    Problems().run()
