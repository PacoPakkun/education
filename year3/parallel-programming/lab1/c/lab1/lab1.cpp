#include <iostream>
#include <fstream>
#include <thread>
#include <chrono>
#include <vector>
#include <math.h>
using namespace std;
using namespace std::chrono;
#define N 10
#define M 10

//void subthread(int n, int m, int f[N][M], double w[N][M], int v[N][M], int start, int stop) {
void subthread(int n, int m, int** f, double** w, int** v, int start, int stop) {
	for (int i = start; i < stop; i++) {
		for (int j = 0; j < M; j++) {
			int suma = 0;
			for (int k = -n / 2; k <= n / 2; k++) {
				for (int l = -m / 2; l <= m / 2; l++) {
					int pos1 = i + k, pos2 = j + l;
					if (pos1 < 0)
						pos1 = 0;
					if (pos1 > N - 1)
						pos1 = N - 1;
					if (pos2 < 0)
						pos2 = 0;
					if (pos2 > M - 1)
						pos2 = M - 1;
					suma += f[pos1][pos2] * w[pos1][pos2];
				}
			}
			v[i][j] = suma;
		}
	}
}

//void paralel(int n, int m, int f[N][M], double w[N][M], int v[N][M], int p) {
void paralel(int n, int m, int** f, double** w, int** v, int p) {
	thread* threads = new thread[p]; int cat = N / p;
	int rest = N % p;
	int start = 0;
	int stop;

	for (int i = 0; i < p; i++) {
		if (rest > 0) {
			stop = start + cat + 1;
			rest--;
		}
		else
			stop = start + cat;
		threads[i] = thread(subthread, n, m, f, w, v, start, stop);
		start = stop;
	}

	for (int i = 0; i < p; i++) {
		threads[i].join();
	}
	delete[] threads;
}

//void secvential(int n, int m, int f[N][M], double w[N][M], int v[N][M]) {
void secvential(int n, int m, int** f, double** w, int** v) {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			int suma = 0;
			for (int k = -n / 2; k <= n / 2; k++) {
				for (int l = -m / 2; l <= m / 2; l++) {
					int pos1 = i + k, pos2 = j + l;
					if (pos1 < 0)
						pos1 = 0;
					if (pos1 > N - 1)
						pos1 = N - 1;
					if (pos2 < 0)
						pos2 = 0;
					if (pos2 > M - 1)
						pos2 = M - 1;
					suma += f[pos1][pos2] * w[pos1][pos2];
				}
			}
			v[i][j] = suma;
		}
	}
}

void populare(int n, int m, int p) {
	ofstream out("date.txt");
	out << n << ' ' << m << ' ' << p << endl;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			out << rand() % 100 << ' ';
		}
		out << endl;
	}
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			out << (double)rand() / RAND_MAX << ' ';
		}
		out << endl;
	}

	out.close();
}

void show(int** m) {
	//void show(int m[N][M]) {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			cout << m[i][j] << " ";
		}
		cout << endl;
	}
	cout << endl;
}

//bool check(int a[N][M], int b[N][M]) {
bool check(int** a, int** b) {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			if (a[i][j] != b[i][j])
				return false;
		}
	}
	return true;
}

int main(int argc, char* argv[])
{
	/* POPULARE */
	//populare(atoi(argv[1]), atoi(argv[2]), atoi(argv[3]));
	populare(3, 3, 2);

	/* CITIRE */
	ifstream in("date.txt");
	int n, m, p;
	in >> n >> m >> p;
	//int f[N][M];
	//double w[N][M];
	//int v1[N][M];
	//int v2[N][M];
	int** f = new int* [N];
	double** w = new double* [N];
	int** v1 = new int* [N];
	int** v2 = new int* [N];
	for (int i = 0; i < N; i++) {
		f[i] = new int[M];
		w[i] = new double[M];
		v1[i] = new int[M];
		v2[i] = new int[M];
	}
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			in >> f[i][j];
		}
	}
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			in >> w[i][j];
		}
	}
	in.close();

	/* EXECUTIE */
	// secvential
	auto time_start = steady_clock::now();
	secvential(n, m, f, w, v1);
	auto time_end = steady_clock::now();
	cout << "secvential: " << chrono::duration <double, milli>(time_end - time_start).count() << endl;

	// paralel
	time_start = steady_clock::now();
	paralel(n, m, f, w, v2, p);
	time_end = steady_clock::now();
	cout << "paralel: " << chrono::duration <double, milli>(time_end - time_start).count() << endl;

	if (!check(v1, v2))
		throw invalid_argument("error");
}