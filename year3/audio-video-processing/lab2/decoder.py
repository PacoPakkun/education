from math import cos, pi, sqrt


# dequantization
def dequantization(matrix):
    q = [[6, 4, 4, 6, 10, 16, 20, 24],
         [5, 5, 6, 8, 10, 23, 24, 22],
         [6, 5, 6, 10, 16, 23, 28, 22],
         [6, 7, 9, 12, 20, 35, 32, 25],
         [7, 9, 15, 22, 27, 44, 41, 31],
         [10, 14, 22, 26, 32, 42, 45, 37],
         [20, 26, 31, 35, 41, 48, 48, 40],
         [29, 37, 38, 39, 45, 40, 41, 40]]
    return [[matrix[i][j] * q[i][j] for j in range(8)] for i in range(8)]


# inverse discrete cosine transformation
def idct(matrix):
    result = [[0.0 for _ in range(8)] for _ in range(8)]
    for i in range(8):
        for j in range(8):
            average = 0
            for k in range(8):
                for p in range(8):
                    s = matrix[k][p] * cos((2 * i + 1) * k * pi / 16) * cos((2 * j + 1) * p * pi / 16)
                    if k == 0:
                        s *= (1 / sqrt(2))
                    if p == 0:
                        s *= (1 / sqrt(2))
                    average += s
            result[i][j] = average / 4
    return [[result[i][j] + 128 for j in range(len(result[i]))] for i in range(len(result))]


# reverts a subsampled block
def revert_subsample(block):
    new_block = [[0, 0, 0, 0, 0, 0, 0, 0] for _ in range(8)]
    for i in range(4):
        for j in range(4):
            new_block[i * 2][j * 2] = block[i][j]
            new_block[i * 2][j * 2 + 1] = block[i][j]
            new_block[i * 2 + 1][j * 2] = block[i][j]
            new_block[i * 2 + 1][j * 2 + 1] = block[i][j]
    return new_block


# recomposes 8x8 blocks in a yuv matrix
def compose_from_blocks(matrix, is_subsampled=False):
    result = [[0 for _ in range(len(matrix[0]) * 8)] for _ in range(len(matrix) * 8)]
    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            block = matrix[i][j]
            if is_subsampled:
                block = revert_subsample(block)
            for k in range(8):
                for l in range(8):
                    result[i * 8 + k][j * 8 + l] = block[k][l]
    return result


# converts a pixel from yuv to rgb format
def yuv_to_rgb(pixel):
    y = pixel[0]
    u = pixel[1]
    v = pixel[2]
    r = (y + 1.402 * (v - 128))
    if r < 0:
        r = 0
    if r > 255:
        r = 255
    r = int(r)

    g = (y - 0.344 * (u - 128) - 0.714 * (v - 128))
    if g < 0:
        g = 0
    if g > 255:
        g = 255
    g = int(g)

    b = (y + 1.772 * (u - 128))
    if b < 0:
        b = 0
    if b > 255:
        b = 255
    b = int(b)
    return [r, g, b]


# writes a yuv pixel matrix to file in ppm format
def write_ppm(y, u, v):
    width, height = len(y[0]), len(y)
    f = open("output.ppm", "w")
    f.write("P3\n")
    f.write("# CREATOR: gIMP PNM Filter version 1.1\n")
    f.write(f"{width} {height}\n")
    f.write("255\n")

    for i in range(height):
        for j in range(width):
            pixel = [y[i][j], u[i][j], v[i][j]]
            pixel = yuv_to_rgb(pixel)
            f.write(f"{str(pixel[0])}\n")
            f.write(f"{str(pixel[1])}\n")
            f.write(f"{str(pixel[2])}\n")
    f.close()


# writes y matrix to file
def writey(y, file):
    width, height = len(y[0]), len(y)
    f = open(file, "w")
    f.write("P3\n")
    f.write("# CREATOR: gIMP PNM Filter version 1.1\n")
    f.write(f"{width} {height}\n")
    f.write("255\n")

    for i in range(height):
        for j in range(width):
            f.write(f"{str(int(y[i][j]))}\n")
            f.write(f"{str(int(y[i][j]))}\n")
            f.write(f"{str(int(y[i][j]))}\n")
    f.close()


# ppm decoder
def decode(y, u, v):
    y, u, v = [[idct(dequantization(y[i][j])) for j in range(len(y[i]))] for i in range(len(y))], \
              [[idct(dequantization(u[i][j])) for j in range(len(u[i]))] for i in range(len(u))], \
              [[idct(dequantization(v[i][j])) for j in range(len(v[i]))] for i in range(len(v))]
    y, u, v = compose_from_blocks(y), compose_from_blocks(u), compose_from_blocks(v)
    write_ppm(y, u, v)
    # writey(y, "y.ppm")
