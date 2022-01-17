class Grammar:
    def __init__(self):
        file = open('grammar.txt', 'r')
        inp = file.read().split('\n')
        self.reguli = [['S' + str(i)] + inp[i].split('->') for i in range(len(inp))]
        self.neterminali = [self.reguli[i][1] for i in range(len(self.reguli))]
        self.terminali = []
        for regula in self.reguli:
            for char in regula[2]:
                if char not in self.neterminali:
                    self.terminali.append(char)

    # analizor descendent cu reveniri
    def dr(self, secventa):
        # configuratie initiala
        stare = 'q'
        index = 0
        stiva_lucru = []
        stiva_intrare = 'S'

        while True:
            if len(stiva_intrare) == 0 and index == len(secventa):
                # succes
                return stiva_lucru

            if index == len(secventa):
                # insucces de moment
                stare = 'r'

            if stare == 'q':
                if len(stiva_intrare) > 0:
                    if stiva_intrare[0] in self.terminali:
                        if stiva_intrare[0] == secventa[index]:
                            # avans
                            stiva_lucru.append(stiva_intrare[0])
                            stiva_intrare = stiva_intrare[1:]
                            index += 1

                        else:
                            # insucces de moment
                            stare = 'r'

                    elif stiva_intrare[0] in self.neterminali:
                        # expandare
                        regula = []
                        for r in self.reguli:
                            if r[1] == stiva_intrare[0]:
                                regula = r
                                break
                        stiva_lucru.append(regula[0])
                        stiva_intrare = regula[2] + stiva_intrare[1:]

                    else:
                        # esec
                        return 'error'
                else:
                    # insucces de moment
                    stare = 'r'

            else:
                if len(stiva_lucru) > 0:
                    if stiva_lucru[-1] not in self.terminali:
                        # alta incercare
                        regula = int(stiva_lucru[-1][1:])
                        next_regula = -1
                        for i in range(regula + 1, len(self.reguli)):
                            if self.reguli[regula][1] == self.reguli[i][1]:
                                next_regula = i
                                break
                        if next_regula != -1:
                            stiva_lucru[-1] = self.reguli[next_regula][0]
                            stiva_intrare = self.reguli[next_regula][2] + stiva_intrare[len(self.reguli[regula][2]):]
                            stare = 'q'

                        else:
                            if index == 0:
                                # esec
                                return 'error'

                            else:
                                stiva_lucru = stiva_lucru[:-1]
                                stiva_intrare = self.reguli[regula][1] + stiva_intrare[len(self.reguli[regula][2]):]
                    else:
                        # revenire
                        stiva_intrare = stiva_lucru[-1] + stiva_intrare
                        stiva_lucru = stiva_lucru[:-1]
                        index -= 1

                else:
                    # esec
                    return 'error'


if __name__ == '__main__':
    grammar = Grammar()
    print('>>1 2 3 4 5 22 (a) 10 (3) 9 6')  # void main(){int a=3;}
    print(grammar.dr('1 2 3 4 5 22 (a) 10 (3) 9 6'))
    while True:
        print(grammar.dr(input('>>')))
