import re

# text = 'if(a>b){a=b;} else{b=a;}'
text = 'void main(){\
    float r = 0;\
    float p = 0;\
    float a = 0;\
    read(r);\
    p = r*2;\
    p = p*3.14;\
    a = r*r;\
    a = a*3.14;\
    print(p);\
    print(a);\
}'

cuvinte_cheie = ['void', 'main', 'read', 'print', 'if', 'else', 'for', 'while', 'int', 'bool', 'string', 'float']
operatori = ['=', '\+', '-', '\*', '<', '>', '<=', '>=', '!=']
separatori = ['\(', '\)', '{', '}', ';', ' ', '\t']

regex = ''
for element in operatori + separatori:
    regex += '(' + element + ')|'


def main():
    for atom in [value for value in re.split(regex[:-1], text) if
                 value not in ['', ' ', None]]:
        print(atom)


if __name__ == '__main__':
    main()
