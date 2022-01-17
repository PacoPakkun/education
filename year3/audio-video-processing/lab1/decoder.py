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
    y, u, v = compose_from_blocks(y), compose_from_blocks(u, True), compose_from_blocks(v, True)
    write_ppm(y, u, v)
    # writey(y, "y.ppm")
