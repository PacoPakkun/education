from decoder import decode
from encoder import encode

if __name__ == '__main__':
    y, u, v = encode()
    decode(y, u, v)
