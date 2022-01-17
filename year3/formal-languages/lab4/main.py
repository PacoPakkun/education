# citire automat finit din fisier
f = open("date.txt", "r")
alfabet = f.readline().split()
stari = f.readline().split()
tranzitii = []
stari_finale = []
for _ in range(len(stari)):
    line = f.readline().split()
    tranzitii.append(line[:-1])
    stari_finale.append(line[-1])
f.close()


# verifica daca o secventa este acceptata de automatul finit
# sau returneaza cea mai mare subsecventa acceptata daca exista
def check_secventa(secventa):
    stare = 0
    prefix = -1
    for i in range(len(secventa)):
        if secventa[i] not in alfabet:
            print('prefix ' + str(secventa[:prefix + 1]) + ' acceptat' if prefix > -1 else
                  ('epsilon' if stari_finale[0] == '1' else 'secventa incorecta'))
            return
        else:
            pos = alfabet.index(secventa[i])
            if tranzitii[stare][pos] == '-1':
                print('prefix ' + str(secventa[:prefix + 1]) + ' acceptat' if prefix > -1 else
                      ('epsilon' if stari_finale[0] == '1' else 'secventa incorecta'))
                return
            else:
                stare = stari.index(tranzitii[stare][pos])
                if stari_finale[stare] == '1':
                    prefix = i
    if stari_finale[stare] == '1':
        print('secventa', secventa, 'acceptata')
    else:
        print('prefix ' + str(secventa[:prefix + 1]) + ' acceptat' if prefix > -1 else
              ('epsilon' if stari_finale[0] == '1' else 'secventa incorecta'))


# program principal in consola unde utilizatorul poate apela functionalitatile disponibile
def main():
    print('1 -> citire automat finit')
    print('2 -> alfabet')
    print('3 -> stari')
    print('4 -> tranzitii')
    print('5 -> stari finale')
    print('6 -> verificare secventa')
    print('0 -> exit')
    while True:
        command = input('>> ')
        if command == '1':
            global alfabet, stari, tranzitii, stari_finale, line
            alfabet = input('insert alfabet:\n>>').split()
            stari = input('insert stari:\n>>').split()
            tranzitii = []
            stari_finale = []
            print('insert tranzitii & stari finale:')
            for _ in range(len(stari)):
                line = input('>>').split()
                tranzitii.append(line[:-1])
                stari_finale.append(line[-1])
        elif command == '2':
            print('alfabet:', alfabet)
        elif command == '3':
            print('stari:', stari)
        elif command == '4':
            print('tranzitii:')
            print('\t\t', alfabet)
            print('\t\t------------')
            for t in range(len(tranzitii)):
                print('\t', stari[t], '|', tranzitii[t])
        elif command == '5':
            print('stari finale:', stari_finale)
        elif command == '6':
            secventa = input('insert secventa: ')
            check_secventa([element for element in secventa])
        elif command == '0':
            break
        else:
            print('invalid command')


if __name__ == '__main__':
    main()
