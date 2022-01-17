from decoder import decode, dequantization, revert_zigzag
from encoder import encode, quantization, zigzag, size

if __name__ == '__main__':
    code = encode()
    decode(code)
