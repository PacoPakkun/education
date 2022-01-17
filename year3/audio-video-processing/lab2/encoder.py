from math import cos, pi, sqrt

from decoder import revert_subsample


def print_blocks(y, u, v):
    print("First y block:")
    for i in range(8):
        print(round(y[0][0][i][0], 1), "\t", round(y[0][0][i][1], 1), "\t", round(y[0][0][i][2], 1), "\t",
              round(y[0][0][i][3], 1), "\t", round(y[0][0][i][4], 1), "\t", round(y[0][0][i][5], 1), "\t",
              round(y[0][0][i][6], 1), "\t", round(y[0][0][i][7], 1), "\t", )
    print("\nFirst u block:")
    for i in range(4):
        print(u[0][0][i][0], "\t", u[0][0][i][1], "\t", u[0][0][i][2], "\t", u[0][0][i][3], "\t", )
    print("\nFirst v block:")
    for i in range(4):
        print(v[0][0][i][0], "\t", v[0][0][i][1], "\t", v[0][0][i][2], "\t", v[0][0][i][3], "\t", )


# converts a pixel from rgb format to yuv format
def rgb_to_yuv(pixel):
    r = pixel[0]
    g = pixel[1]
    b = pixel[2]
    y = 0.299 * r + 0.587 * g + 0.114 * b
    u = -0.1687 * r - 0.3312 * g + 0.5 * b + 128
    v = 0.5 * r - 0.4186 * g - 0.0813 * b + 128
    return [y, u, v]


# reads a ppm file and returns the yuv matrixes
def read_ppm():
    f = open("input.ppm", "r")
    f.readline()
    f.readline()
    size = f.readline().split()
    f.readline()
    width, height = int(size[0]), int(size[1])

    y, u, v = [], [], []
    for i in range(height):
        y_row, u_row, v_row = [], [], []
        for j in range(width):
            r = int(f.readline())
            g = int(f.readline())
            b = int(f.readline())
            pixel = rgb_to_yuv([r, g, b])
            y_row.append(pixel[0])
            u_row.append(pixel[1])
            v_row.append(pixel[2])
        y.append(y_row)
        u.append(u_row)
        v.append(v_row)
    f.close()
    return y, u, v


# subsamples a block
def subsample(block):
    new_block = [[0, 0, 0, 0] for _ in range(4)]
    for i in range(4):
        for j in range(4):
            sample = int((block[i * 2][j * 2] + block[i * 2][j * 2 + 1] + block[i * 2 + 1][j * 2] + block[i * 2 + 1][
                j * 2 + 1]) / 4)
            new_block[i][j] = sample
    return new_block


# divides a yuv matrix in 8x8 blocks
def divide_to_blocks(matrix, should_subsample=False):
    result = []
    for i in range(int(len(matrix) / 8)):
        blocks = []
        for j in range(int(len(matrix[0]) / 8)):
            block = []
            for k in range(8):
                row = []
                for l in range(8):
                    row.append(matrix[i * 8 + k][j * 8 + l])
                block.append(row)
            if should_subsample:
                block = subsample(block)
            blocks.append(block)
        result.append(blocks)
    return result


# forward discrete cosine transformation
def fdct(matrix, is_subsampled=False):
    if is_subsampled:
        matrix = revert_subsample(matrix)
    matrix = [[matrix[i][j] - 128 for j in range(len(matrix[i]))] for i in range(len(matrix))]
    result = [[0.0 for _ in range(8)] for _ in range(8)]
    for i in range(8):
        for j in range(8):
            average = 0
            for k in range(8):
                for p in range(8):
                    average += matrix[k][p] * cos((2 * k + 1) * i * pi / 16) * cos((2 * p + 1) * j * pi / 16)
            result[i][j] = average / 4
            if i == 0:
                result[i][j] *= (1 / sqrt(2))
            if j == 0:
                result[i][j] *= (1 / sqrt(2))
    return result


# quantization
def quantization(matrix):
    q = [[6, 4, 4, 6, 10, 16, 20, 24],
         [5, 5, 6, 8, 10, 23, 24, 22],
         [6, 5, 6, 10, 16, 23, 28, 22],
         [6, 7, 9, 12, 20, 35, 32, 25],
         [7, 9, 15, 22, 27, 44, 41, 31],
         [10, 14, 22, 26, 32, 42, 45, 37],
         [20, 26, 31, 35, 41, 48, 48, 40],
         [29, 37, 38, 39, 45, 40, 41, 40]]
    return [[round(matrix[i][j] / q[i][j]) for j in range(8)] for i in range(8)]


# ppm encoder
def encode():
    y, u, v = read_ppm()
    y, u, v = divide_to_blocks(y), divide_to_blocks(u, True), divide_to_blocks(v, True)
    # print_blocks(y, u, v)
    y, u, v = [[quantization(fdct(y[i][j])) for j in range(len(y[i]))] for i in range(len(y))], \
              [[quantization(fdct(u[i][j], True)) for j in range(len(u[i]))] for i in range(len(u))], \
              [[quantization(fdct(v[i][j], True)) for j in range(len(v[i]))] for i in range(len(v))]

    return y, u, v
