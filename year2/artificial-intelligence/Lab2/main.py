def read():
    global n, mat, src, dest
    f = open("hard_01_tsp.txt", "r")
    n = int(f.readline())
    mat = [[0 for column in range(n)]
           for row in range(n)]
    for i in range(n):
        mat[i] = [int(j) for j in f.readline().split(",")]
    src = int(f.readline()) - 1
    dest = int(f.readline()) - 1
    f.close()


def write():
    global n, path_all, weight_path_all, length_path_dest, path_dest, weight_path_dest
    f = open("out.txt", "w")
    f.write(str(n) + '\n')
    f.write(str(path_all) + '\n')
    f.write(str(weight_path_all) + '\n')
    f.write(str(length_path_dest) + '\n')
    f.write(str(path_dest) + '\n')
    f.write(str(weight_path_dest))


def nextt(current, visited, path):
    global n, mat
    pos = -1
    minim = 99
    for i in range(n):
        if mat[current][i] != 0 and visited[i] is False and mat[current][i] < minim:
            pos = i
            minim = mat[current][i]
    visited[pos] = True
    path.append(pos + 1)
    return pos


def nearest_neighbour(visit_all):
    global n, mat, path_all, weight_path_all, path_dest, length_path_dest, weight_path_dest
    count = 0
    weight = 0
    if visit_all is True:
        current = 0
        path = [1]
        visited = [False] * n
        visited[0] = True
        while count != n - 1:
            new = nextt(current, visited, path)
            weight += mat[current][new]
            current = new
            count += 1
        weight += mat[current][0]
        path_all = path
        weight_path_all = weight
    else:
        visited = [False] * n
        visited[src] = True
        current = src
        path = [src + 1]
        while current != dest:
            new = nextt(current, visited, path)
            weight += mat[current][new]
            current = new
            count += 1
        path_dest = path
        length_path_dest = count + 1
        weight_path_dest = weight


def run():
    global n, path_all, weight_path_all, length_path_dest, path_dest, weight_path_dest
    nearest_neighbour(True)
    nearest_neighbour(False)
    print(n)
    print(path_all)
    print(weight_path_all)
    print(length_path_dest)
    print(path_dest)
    print(weight_path_dest)


# in
n = None
mat = None
src = None
dest = None
# out
path_all = None
weight_path_all = None
path_dest = None
weight_path_dest = None
length_path_dest = None

if __name__ == '__main__':
    read()
    run()
    write()
