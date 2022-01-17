#include <iostream>
#include <fstream>
#include <thread>
#include <chrono>
#include <vector>
#include <math.h>
#include <mutex>
using namespace std;
using namespace std::chrono;

class my_barrier
{
public:
	my_barrier(int count) : thread_count(count), counter(0), waiting(0)
	{}

	void wait()
	{
		//fence mechanism
		std::unique_lock<std::mutex> lk(m);
		++counter;
		++waiting;
		cv.wait(lk, [&] {return counter >= thread_count; });
		cv.notify_one();
		--waiting;
		if (waiting == 0)
		{  //reset barrier
			counter = 0;
		}
		lk.unlock();
	}

private:
	std::mutex m;
	std::condition_variable cv;
	int counter;
	int waiting;
	int thread_count;
};

my_barrier* barrier;

//void subthread(int n, int m, int f[N][M], double w[N][M], int v[N][M], int start, int stop) {
void subthread(int thread, int p, int N, int M, int n, int m, int** f, double** w, int start, int stop) {
	int** temp1 = new int* [m / 2];
	int** temp2 = new int* [m / 2];
	for (int i = 0; i < m / 2; i++) {
		temp1[i] = new int[M];
		temp2[i] = new int[M];
	}

	if (thread != 0) {
		for (int i = start - m / 2; i < start; i++) {
			if (i >= 0)
				for (int j = 0; j < M; j++) {
					temp1[i - start + m / 2][j] = f[i][j];
				}
		}
	}
	if (thread != p - 1) {
		for (int i = stop; i < stop + m / 2; i++) {
			if (i < N)
				for (int j = 0; j < M; j++) {
					temp2[i - stop][j] = f[i][j];
				}
		}
	}
	(*barrier).wait();

	int** temp = new int* [N];
	for (int i = 0; i < N; i++) {
		temp[i] = new int[M];
	}
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

					if (pos1 < start) {
						suma += temp1[pos1 - start + m / 2][pos2] * w[pos1][pos2];
					}
					else {
						if (pos1 > stop - 1) {
							suma += temp2[pos1 - stop][pos2] * w[pos1][pos2];
						}
						else {
							suma += f[pos1][pos2] * w[pos1][pos2];
						}
					}
				}
			}
			temp[i][j] = suma;
		}
	}
	for (int i = start; i < stop; i++) {
		for (int j = 0; j < M; j++) {
			f[i][j] = temp[i][j];
		}
	}
}

//void paralel(int n, int m, int f[N][M], double w[N][M], int v[N][M], int p) {
void paralel(int N, int M, int n, int m, int** f, double** w, int p) {
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
		threads[i] = thread(subthread, i, p, N, M, n, m, f, w, start, stop);
		start = stop;
	}

	for (int i = 0; i < p; i++) {
		threads[i].join();
	}
	delete[] threads;
}

//void secvential(int n, int m, int f[N][M], double w[N][M], int v[N][M]) {
void secvential(int N, int M, int n, int m, int** f, double** w, int** v) {
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

void show(int N, int M, int** m) {
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
bool check(int N, int M, int** a, int** b) {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			if (a[i][j] != b[i][j])
				return false;
		}
	}
	return true;
}
void populare(int N, int M, int n, int m, int p) {
	ofstream out("date.txt");
	out << N << ' ' << M << ' ' << n << ' ' << m << ' ' << p << endl;

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
int main(int argc, char* argv[])
{
	/* POPULARE */
	//populare(atoi(argv[1]), atoi(argv[2]), atoi(argv[3]), atoi(argv[4]), atoi(argv[5]));
	//populare(3, 3, 2);

	/* citire */
	ifstream in("date.txt");
	int nn, mm, n, m, p;
	in >> nn >> mm >> n >> m >> p;
	//int f[n][m];
	//double w[n][m];
	//int v1[n][m];
	//int v2[n][m];
	int** f = new int* [nn];
	double** w = new double* [nn];
	int** v = new int* [nn];
	for (int i = 0; i < nn; i++) {
		f[i] = new int[mm];
		w[i] = new double[mm];
		v[i] = new int[mm];
	}
	for (int i = 0; i < nn; i++) {
		for (int j = 0; j < mm; j++) {
			in >> f[i][j];
		}
	}
	for (int i = 0; i < nn; i++) {
		for (int j = 0; j < mm; j++) {
			in >> w[i][j];
		}
	}
	in.close();

	/* executie */
	// secvential
	auto time_start = steady_clock::now();
	secvential(nn, mm, n, m, f, w, v);
	auto time_end = steady_clock::now();

	barrier = new my_barrier(p);
	// paralel
	time_start = steady_clock::now();
	paralel(nn, mm, n, m, f, w, p);
	time_end = steady_clock::now();
	cout << chrono::duration <double, milli>(time_end - time_start).count();

	if (!check(n, m, v, f))
		throw invalid_argument("error");
}