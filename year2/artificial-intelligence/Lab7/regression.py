from utils import *

class Regression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = [0.0, 0.0]

    @staticmethod
    def inmultire(X, Y):
        if len(X[0]) != len(Y):
            return -1

        matrix = []

        for i in range(0, len(X)):
            row = []
            for j in range(0, len(Y[0])):
                sum = 0
                for k in range(0, len(Y)):
                    sum += X[i][k] * Y[k][j]
                row.append(sum)
            matrix.append(row)
        return matrix

    @staticmethod
    def transpusa(X):
        rez = [[X[j][i] for j in range(len(X))] for i in range(len(X[0]))]
        return rez

    @staticmethod
    def cofactor(a, i, j):
        b = []
        for linie in range((len(a))):
            if linie != i:
                l = []
                for coloana in range(len(a[0])):
                    if coloana != j:
                        l.append(a[linie][coloana])
                b.append(l)
        return b

    @staticmethod
    def determinant(A, total=0):
        # store indices in list for row referencing
        indices = list(range(len(A)))

        # Section 2: when at 2x2 submatrices recursive calls end
        if len(A) == 2 and len(A[0]) == 2:
            val = A[0][0] * A[1][1] - A[1][0] * A[0][1]
            return val

        for fc in indices:  # for each focus column
            # find the submatrix ...
            As = A  # copy matrix
            As = As[1:]  # remove the first row
            height = len(As)  # D)

            for i in range(height):
                # for each remaining row of submatrix ...
                # remove the focus column elements
                # delete current column
                As[i] = As[i][0:fc] + As[i][fc + 1:]

            sign = (-1) ** (fc % 2)  # F)
            # pass submatrix recursively
            sub_det = Regression.determinant(As)

            # total all returns from recursion
            total += sign * A[0][fc] * sub_det

        return total

    @staticmethod
    def inversa(X):
        identity = []
        rowsNo = len(X)
        colsNo = len(X[0])
        if rowsNo != colsNo:
            print('Not squared matrix')
            return
        det = Regression.determinant(X)
        if det == 0:
            print('Matrix is not identity')
            return
        trans = Regression.transpusa(X)
        for i in range(rowsNo):
            line = []
            for j in range(colsNo):
                d = (-1) ** (i + j) * Regression.determinant(Regression.cofactor(trans, i, j))
                inv = d * 1 / det
                line.append(inv)
            identity.append(line)
        return identity

    def fit(self, x, y):
        pass

        X = [[1, x[i][0], x[i][1]] for i in range(0, len(x))]
        Y = [[y[i]] for i in range(0, len(y))]

        beta = self.transpusa(X)
        beta = self.inmultire(beta, X)
        beta = self.inversa(beta)
        beta = self.inmultire(beta, self.transpusa(X))
        beta = self.inmultire(beta, Y)

        w0 = beta[0][0]
        w1 = beta[1][0]
        w2 = beta[2][0]

        self.intercept_, self.coef_ = w0, [w1, w2]

    def predict(self, x):
        if (isinstance(x[0], list)):
            return [self.intercept_ + self.coef_[0] * val[0] + self.coef_[1] * val[1] for val in x]
        else:
            return self.intercept_ + self.coef_[0] * x[0] + self.coef_[1] * x[1]

    def meanSquareError(self, myinput, myoutput):
        error = 0.0
        for t1, t2 in zip(self.predict(myinput), myoutput):
            error += (t1 - t2) ** 2
        error = error / len(myoutput)
        return error