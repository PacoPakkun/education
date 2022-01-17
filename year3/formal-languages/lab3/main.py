import re

# programul mlp citit din fisier
f = open("date.txt", "r")
text = f.read()
f.close()

# atomii lexicali si tabelele asociate
cuvinte_cheie = ['void', 'main', 'read', 'print', 'if', 'else', 'for', 'while', 'int', 'bool', 'string', 'float']
operatori = ['=', '+', '-', '*', '<', '>', '<=', '>=', '!=']
separatori = ['(', ')', '{', '}', ';']
atomi = {'_ID': 0, '_CONST': 1}
fip = []
ts = {}


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
            if re.match('^[+-]?([0-9]*[.])?[0-9]+$', atom):
                fip.append({atomi['_CONST']: ts[atom]})
            elif re.match('^[a-zA-Z]{1,250}$', atom):
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
    generate_atomi()
    generate_ts()
    generate_fip()
    write_to_file()


if __name__ == '__main__':
    main()
