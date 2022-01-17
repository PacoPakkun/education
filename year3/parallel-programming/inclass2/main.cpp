#include <iostream>
#include <thread>
#include <chrono>
#include <vector>
#include <math.h>
using namespace std;
using namespace std::chrono;

void sumaSecvential(double* a, double* b, double* c, int n)
{
for (int i = 0; i < n; i++)
//c[i] = a[i] + b[i];
c[i] = sqrt(pow(a[i], 4) + pow(b[i], 4));
}

void functieRun(double* a, double* b, double* c, int begin, int end)
{
for (int i = begin; i < end; i++)
//c[i] = a[i] + b[i];
c[i] = sqrt(pow(a[i], 4) + pow(b[i], 4));
}

void functieRun2(double* a, double* b, double* c, int begin, int end, int p)
{
for (int i = begin; i < end; i += p)
c[i] = sqrt(pow(a[i], 4) + pow(b[i], 4));
}

void sumaParalel(double* a, double* b, double* c, int n, int p)
{
thread* threads = new thread[p];
int begin = 0, end;
int cat = n / p, rest = n % p;

for (int i = 0; i < p; i++)
{
if (rest != 0) {
end = begin + cat + 1;
rest--;
}
else
end = begin + cat;

threads[i] = thread(functieRun, a, b, c, begin, end);
begin = end;
}

for (int i = 0; i < p; i++)
threads[i].join();

delete[] threads;
}

void sumaParalel2(double* a, double* b, double* c, int n, int p)
{
thread* threads = new thread[p];
//thread** threads = new thread * [p];

for (int i = 0; i < p; i++)
threads[i] = thread(functieRun2, a, b, c, i, n, p);
//threads[i] = new thread(functieRun2, a, b, c, i, n, p);

for (int i = 0; i < p; i++)
{
threads[i].join();
/*threads[i]->join();
delete threads[i];*/
}

delete[] threads;
}

void printVector(double* vector, int n)
{
for (int i = 0; i < n; i++)
cout << vector[i] << " ";
cout << endl;
}

bool egalVector(double* a, double* b, int n)
{
for (int i = 0; i < n; i++)
if (a[i] != b[i])
return false;
return true;
}

int main()
{
int n = 5000000;
int p = 4;

double *a = new double[n];
double *b = new double[n];
double *c = new double[n];
double* d = new double[n];
double* e = new double[n];

for (int i = 0; i < n; i++)
{
a[i] = i;
b[i] = n - i;
}

auto t1 = steady_clock::now();
sumaSecvential(a, b, c, n);
auto t2 = steady_clock::now();
//printVector(c, n);

auto diff = t2 - t1;
cout << chrono::duration <double, milli>(diff).count() << endl;

t1 = steady_clock::now();
sumaParalel(a, b, d, n, p);
t2 = steady_clock::now();

diff = t2 - t1;
cout << chrono::duration <double, milli>(diff).count() << endl;
cout << egalVector(c, d, n) << endl;

t1 = steady_clock::now();
sumaParalel2(a, b, e, n, p);
t2 = steady_clock::now();

diff = t2 - t1;
cout << chrono::duration <double, milli>(diff).count() << endl;
cout << egalVector(c, d, n) << endl;

return 0;
}