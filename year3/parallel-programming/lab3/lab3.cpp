#include <mpi.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <thread>
#include <chrono>
#include <vector>
#include <math.h>
#include <mutex>
using namespace std;
using namespace std::chrono;

// suma secventiala a numerelor
void secvential(int n, int n1, int n2, int* numar1, int* numar2, int* suma) {
	// suma secventiala
	int carry = 0;
	for (int i = 0; i < n; i++) {
		int s = numar1[i] + numar2[i];
		suma[i] = (s + carry) % 10;
		carry = (s + carry) / 10;
	}
}

// verifica corectitudinea sumei
bool check(int n, int* v1, int* v2) {
	// verifica daca rezultatul paralelizat e corect (identic cu cel secvential)
	for (int i = 0; i < n; i++) {
		if (v1[i] != v2[i])
			return false;
	}
	return true;
}

// varianta 1 (send + receive)
int main(int argc, char** argv) {
	// initializare mpi
	auto time_start = steady_clock::now();
	MPI_Init(&argc, &argv);
	int rank, size;
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	// initializare lungime numar
	int n = 0, n1 = 0, n2 = 0;
	ifstream in1("numar1.txt");
	ifstream in2("numar2.txt");
	in1 >> n1;
	in2 >> n2;
	if (n1 > n2)
		n = n1 + 1;
	else
		n = n2 + 1;

	if (size == 1) {
		// secvential
		int* numar1 = new int[n];
		int* numar2 = new int[n];
		int* suma = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			if (i >= n1)
				numar1[i] = 0;
			else
				in1 >> numar1[i];
			if (i >= n2)
				numar2[i] = 0;
			else
				in2 >> numar2[i];
		}
		in1.close();
		in2.close();
		secvential(n, n1, n2, numar1, numar2, suma);

		// afisare rezultat
		ofstream o("suma.txt");
		for (int i = n - 1; i >= 0; i--)
			if (i == n - 1) {
				if (suma[i] != 0)
					o << suma[i];
			}
			else
				o << suma[i];
		o.close();
		auto time_end = steady_clock::now();
		cout << chrono::duration <double, milli>(time_end - time_start).count();
	}
	else {
		int cat = n / (size - 1), rest = n % (size - 1);
		if (rank == 0) {
			// procesul 0
			int* numar1 = new int[n];
			int* numar2 = new int[n];
			int* suma = new int[n];

			int* caturi = new int[size];
			caturi[0] = 0;
			for (int i = 1; i < size; i++) {
				if (i <= rest) {
					caturi[i] = cat + 1;
				}
				else {
					caturi[i] = cat;
				}
			}

			// citire numere din fisier
			int current_process = size - 1, count = 0;
			for (int i = n - 1; i >= 0; i--) {
				count++;
				if (i >= n1)
					numar1[i] = 0;
				else
					in1 >> numar1[i];
				if (i >= n2)
					numar2[i] = 0;
				else
					in2 >> numar2[i];

				// trimite subliste catre procese
				if (count == caturi[current_process]) {
					MPI_Send(numar1 + i, caturi[current_process], MPI_INT, current_process, 20, MPI_COMM_WORLD);
					MPI_Send(numar2 + i, caturi[current_process], MPI_INT, current_process, 30, MPI_COMM_WORLD);
					current_process--;
					count = 0;
				}
			}
			in1.close();
			in2.close();

			// primeste sumele partiale de la procese
			MPI_Status out;
			for (int i = 1; i < size; i++) {
				int offset = 0;
				for (int j = 0; j < i; j++) {
					offset += caturi[j];
				}
				MPI_Recv(suma + offset, caturi[i], MPI_INT, i, 40, MPI_COMM_WORLD, &out);
			}

			// afisare rezultat
			ofstream o("suma.txt");
			for (int i = n - 1; i >= 0; i--)
				if (i == n - 1) {
					if (suma[i] != 0)
						o << suma[i];
				}
				else
					o << suma[i];
			o.close();

			// evaluare performanta
			auto time_end = steady_clock::now();
			int* suma_secvential = new int[n];
			secvential(n, n1, n2, numar1, numar2, suma_secvential);
			if (check(n, suma, suma_secvential)) {
				cout << chrono::duration <double, milli>(time_end - time_start).count();
			}
			else {
				throw invalid_argument("error");
			}

			//for (int i = n - 1; i >= 0; i--)
			//	if (i > n1 - 1)
			//		cout << " ";
			//	else
			//		cout << numar1[i];
			//cout << " +" << endl;
			//for (int i = n - 1; i >= 0; i--)
			//	if (i > n2 - 1)
			//		cout << " ";
			//	else
			//		cout << numar2[i];
			//cout << " =" << endl;
			//for (int i = n - 1; i >= 0; i--)
			//	if (i == n - 1)
			//		if (suma[i] != 0)
			//			cout << suma[i];
			//		else
			//			cout << " ";
			//	else
			//		cout << suma[i];
			//cout << endl;
		}
		else {
			// initializare variabile
			in1.close();
			in2.close();
			if (rank <= rest)
				cat++;
			int* subnumar1 = new int[cat];
			int* subnumar2 = new int[cat];
			int* subsuma = new int[cat];

			// receive subliste de numere
			MPI_Status out;
			MPI_Recv(subnumar1, cat, MPI_INT, 0, 20, MPI_COMM_WORLD, &out);
			MPI_Recv(subnumar2, cat, MPI_INT, 0, 30, MPI_COMM_WORLD, &out);

			// suma numerelor
			int carry = 0;
			for (int i = 0; i < cat; i++)
			{
				int s = subnumar1[i] + subnumar2[i];
				subsuma[i] = (s + carry) % 10;
				carry = (s + carry) / 10;
			}

			// actualizarea sumei cu carry de la procesul precedent
			int received_carry = 0, j = 0;
			if (rank != 1)
				MPI_Recv(&received_carry, 1, MPI_INT, rank - 1, 50, MPI_COMM_WORLD, &out);
			while (received_carry != 0 && j < cat) {
				int s = subnumar1[j] + subnumar2[j];
				subsuma[j] = (s + received_carry) % 10;
				received_carry = (s + received_carry) / 10;
				j++;
			}
			if (j == cat)
				carry = received_carry;

			// trimite carry la procesul urmator
			if (rank != size - 1)
				MPI_Send(&carry, 1, MPI_INT, rank + 1, 50, MPI_COMM_WORLD);

			// trimite suma la procesul 0
			MPI_Send(subsuma, cat, MPI_INT, 0, 40, MPI_COMM_WORLD);
		}
	}

	MPI_Finalize();
}

// varianta 2 (scatter + gather)
int main1(int argc, char** argv) {
	// initializare mpi
	auto time_start = steady_clock::now();
	MPI_Init(&argc, &argv);
	int rank, size;
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	// variabile comune
	int n = 0, n1 = 0, n2 = 0;
	ifstream in1("numar1.txt");
	ifstream in2("numar2.txt");
	in1 >> n1;
	in2 >> n2;
	if (n1 > n2)
		n = n1 + 1;
	else
		n = n2 + 1;
	int cat = n / size, rest = n % size;
	int* caturi = new int[size];
	int* displ = new int[size];
	for (int i = 0; i < size; i++) {
		if (i < rest) {
			caturi[i] = cat + 1;
			displ[i] = i * cat + i;
		}
		else {
			caturi[i] = cat;
			displ[i] = i * cat + rest;
		}
	}
	int* numar1 = new int[n];
	int* numar2 = new int[n];
	int* suma = new int[n];
	int* subnumar1 = new int[caturi[rank]];
	int* subnumar2 = new int[caturi[rank]];
	int* subsuma = new int[caturi[rank]];

	// citire numere din fisier
	if (rank == 0) {
		for (int i = n - 1; i >= 0; i--) {
			if (i >= n1)
				numar1[i] = 0;
			else
				in1 >> numar1[i];
			if (i >= n2)
				numar2[i] = 0;
			else
				in2 >> numar2[i];
		}
	}
	in1.close();
	in2.close();

	// suma pe threaduri
	MPI_Scatterv(numar1, caturi, displ, MPI_INT, subnumar1, caturi[rank], MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Scatterv(numar2, caturi, displ, MPI_INT, subnumar2, caturi[rank], MPI_INT, 0, MPI_COMM_WORLD);
	int carry = 0;
	MPI_Status out;
	for (int i = 0; i < caturi[rank]; i++)
	{
		int s = subnumar1[i] + subnumar2[i];
		subsuma[i] = (s + carry) % 10;
		carry = (s + carry) / 10;
	}
	int received_carry = 0, j = 0;
	if (rank != 0)
		MPI_Recv(&received_carry, 1, MPI_INT, rank - 1, 30, MPI_COMM_WORLD, &out);
	while (received_carry != 0 && j < caturi[rank]) {
		int s = subnumar1[j] + subnumar2[j];
		subsuma[j] = (s + received_carry) % 10;
		received_carry = (s + received_carry) / 10;
		j++;
	}
	if (j == caturi[rank])
		carry = received_carry;
	if (rank != size - 1)
		MPI_Send(&carry, 1, MPI_INT, rank + 1, 30, MPI_COMM_WORLD);
	MPI_Gatherv(subsuma, caturi[rank], MPI_INT, suma, caturi, displ, MPI_INT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		// afisare rezultat
		ofstream out("suma.txt");
		for (int i = n - 1; i >= 0; i--)
			if (i == n - 1) {
				if (suma[i] != 0)
					out << suma[i];
			}
			else
				out << suma[i];
		out.close();

		// evaluare performanta
		auto time_end = steady_clock::now();
		int* suma_secvential = new int[n];
		secvential(n, n1, n2, numar1, numar2, suma_secvential);
		if (check(n, suma, suma_secvential)) {
			cout << chrono::duration <double, milli>(time_end - time_start).count();
		}
		else {
			throw invalid_argument("error");
		}

		//for (int i = n - 1; i >= 0; i--)
		//	if (i > n1 - 1)
		//		cout << " ";
		//	else
		//		cout << numar1[i];
		//cout << " +" << endl;
		//for (int i = n - 1; i >= 0; i--)
		//	if (i > n2 - 1)
		//		cout << " ";
		//	else
		//		cout << numar2[i];
		//cout << " =" << endl;
		//for (int i = n - 1; i >= 0; i--)
		//	if (i == n - 1)
		//		if (suma[i] != 0)
		//			cout << suma[i];
		//		else
		//			cout << " ";
		//	else
		//		cout << suma[i];
		//cout << endl;
	}

	MPI_Finalize();
	return 0;
}

// script de generare a numerelor
//#include <stdlib.h>
//#include <fstream>
//using namespace std;
//
//int main(int argc, char** argv) {
//	int n1 = atoi(argv[1]), n2 = atoi(argv[2]);
//	ofstream out1("numar1.txt");
//	ofstream out2("numar2.txt");
//	out1 << n1 << endl;
//	out2 << n2 << endl;
//
//	if (n1 == 18 && n2 == 18) {
//		out1 << "1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9" << endl;
//		out2 << "1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9" << endl;
//	}
//	else {
//		for (int i = 0; i < n1; i++) {
//			out1 << rand() % 10 << ' ';
//		}
//		out1 << endl;
//
//		for (int i = 0; i < n2; i++) {
//			out2 << rand() % 10 << ' ';
//		}
//		out2 << endl;
//	}
//	out1.close();
//	out2.close();
//}