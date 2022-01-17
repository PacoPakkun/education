from decoder import decode, dequantization
from encoder import encode, quantization

if __name__ == '__main__':
    y, u, v = encode()
    decode(y, u, v)
