import re


class AutomatFinit:
    def __init__(self, file):
        f = open(file, "r")
        self.alfabet = f.readline().split()
        self.stari = f.readline().split()
        self.tranzitii = []
        self.stari_finale = []
        for _ in range(len(self.stari)):
            line = f.readline().split()
            self.tranzitii.append(line[:-1])
            self.stari_finale.append(line[-1])
        f.close()

    # verifica daca o secventa este acceptata de automatul finit
    # sau returneaza cea mai mare subsecventa acceptata daca exista
    def check_secventa(self, secventa):
        stare = 0
        prefix = -1
        for i in range(len(secventa)):
            if secventa[i] not in self.alfabet:
                # print('prefix ' + str(secventa[:prefix + 1]) + ' acceptat' if prefix > -1 else
                #       ('epsilon' if stari_finale[0] == '1' else 'secventa incorecta'))
                return False
            else:
                pos = self.alfabet.index(secventa[i])
                if self.tranzitii[stare][pos] == '-1':
                    # print('prefix ' + str(secventa[:prefix + 1]) + ' acceptat' if prefix > -1 else
                    #       ('epsilon' if stari_finale[0] == '1' else 'secventa incorecta'))
                    return False
                else:
                    stare = self.stari.index(self.tranzitii[stare][pos])
                    if self.stari_finale[stare] == '1':
                        prefix = i
        if self.stari_finale[stare] == '1':
            # print('secventa', secventa, 'acceptata')
            return True
        else:
            # print('prefix ' + str(secventa[:prefix + 1]) + ' acceptat' if prefix > -1 else
            #       ('epsilon' if stari_finale[0] == '1' else 'secventa incorecta'))
            return False


# automatele finite, atomii lexicali, tabelele asociate
global text
automat_constante, automat_identificatori = AutomatFinit("constante.txt"), AutomatFinit("identificatori.txt")
cuvinte_cheie = ['void', 'main', 'read', 'print', 'if', 'else', 'for', 'while', 'int', 'bool', 'string', 'float']
operatori = ['=', '+', '-', '*', '<', '>', '<=', '>=', '!=']
separatori = ['(', ')', '{', '}', ';']
atomi = {'_ID': 0, '_CONST': 1}
fip = []
ts = {}


# citeste programul mlp din fisier
def read_input():
    f = open('date.txt', "r")
    global text
    text = f.read()
    f.close()


# pregateste codurile atomilor din mlp
def generate_atomi():
    pos = 2
    for atom in cuvinte_cheie + operatori + separatori:
        atomi[atom] = pos
        pos += 1


# genereaza tabela de simboluri ordonata lexicografic
def generate_ts():
    for atom in [value for value in
                 re.split('(=)|(\+)|(-)|(\*)|(<)|(>)|(<=)|(>=)|(!=)|(\()|(\))|({)|(})|(;)| |\n|\t', text) if
                 value not in ['', None]]:
        if atom not in cuvinte_cheie + operatori + separatori and atom not in ts:
            minkey = 0
            for key in ts:
                if atom < key:
                    if minkey == 0 or ts[key] < minkey:
                        minkey = ts[key]
                    ts[key] += 1
            ts[atom] = minkey


# genereaza forma interna a programului pe baza tabelelor si arunca exceptie in cazul erorilor lexicale
def generate_fip():
    for atom in [value for value in
                 re.split('(=)|(\+)|(-)|(\*)|(<)|(>)|(<=)|(>=)|(!=)|(\()|(\))|({)|(})|(;)|( )|(\n)|(\t)', text) if
                 value not in ['', ' ', '\n', '\t', None]]:
        if atom in cuvinte_cheie + operatori + separatori:
            fip.append({atomi[atom]: '-'})
        else:
            if automat_constante.check_secventa(atom):
                fip.append({atomi['_CONST']: ts[atom]})
            elif automat_identificatori.check_secventa(atom):
                fip.append({atomi['_ID']: ts[atom]})
            else:
                raise Exception('eroare lexicala la atomul', atom)


# afiseaza tabelele rezultate in fisier
def write_to_file():
    out = open("result.txt", "w")
    out.write('////// CODURI ATOMI //////\n')
    for atom in atomi:
        out.write(atom + '\t' + str(atomi[atom]) + '\n')
    out.write('\n////// FIP //////\n')
    for atom in fip:
        out.write(str(atom) + '\n')
    out.write('\n////// TS //////\n')
    for atom in sorted(ts.items()):
        out.write(str(atom) + '\n')


# citeste un program din fisier si genereaza atomii si tabelele lexicale
def main():
    read_input()
    generate_atomi()
    generate_ts()
    generate_fip()
    write_to_file()


if __name__ == '__main__':
    main()
