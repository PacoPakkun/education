#include <algorithm>
#include <vector>
#include <fstream>
#include <iostream>
#include <unordered_map>
#include <map>
#include <sstream>
#include <string>
#include <queue>

using namespace std;

struct Node {
	char character;
	int frequency;
	Node* left;
	Node* right;
};

Node* node_init(const char character, const int frequency, Node* left, Node* right) {
	Node* node = new Node();
	node->character = character;
	node->frequency = frequency;
	node->left = left;
	node->right = right;
	return node;
}

struct cmp {
	bool operator() (Node* one, Node* other) {
		return one->frequency > other->frequency;
	}
};

void encode(Node* root, string code, map<char, string> &huffman_code) {
	if (root == nullptr)
		return;
	if (!root->left && !root->right) {
		huffman_code[root->character] = code;
	}
	encode(root->left, code + "0", huffman_code);
	encode(root->right, code + "1", huffman_code);
}

void decode(Node* root, int &index, string code, ostream& out) {
	if (root == nullptr) {
		return;
	}
	if (!root->left && !root->right) {
		out << root->character;
		return;
	}
	index++;
	if (code[index] == '0')
		decode(root->left, index, code, out);
	else
		decode(root->right, index, code, out);
}

// codare & decodare huffman
void codare_huffman(const string& text) {
	unordered_map<char, int> frequencies;
	for (char character : text) {
		frequencies[character]++;
	}
	priority_queue<Node*, vector<Node*>, cmp> queue;
	for (auto pair : frequencies) {
		queue.push(node_init(pair.first, pair.second, nullptr, nullptr));
	}
	while (queue.size() != 1)
	{
		Node *left = queue.top(); 
		queue.pop();
		Node *right = queue.top();	
		queue.pop();
		int sum = left->frequency + right->frequency;
		queue.push(node_init('\0', sum, left, right));
	}
	Node* root = queue.top(); // radacina arborelui format
	map<char, string> huffman_code;
	encode(root, "", huffman_code);
	
	cout << "Fisierul de iesire: ";
	string file;
	cin >> file;
	ofstream out(file);
	out << huffman_code.size() << "\n";
	for (auto pair : huffman_code) {
		out << pair.first << " " << pair.second << '\n';
	}
	string code = "";
	for (char character : text) {
		code += huffman_code[character];
	}
	out << code << "\n";

	// decodare huffman
	int index = -1;
	while (index < (int)code.size() - 2) {
		decode(root, index, code, out);
	}
	out.close();
}

void citire_text(string& text) {
	cout << "Fisierul de intrare: ";
	string file;
	cin >> file;
	ifstream in(file);
	if (in.fail()) {
		cout << "EROARE la citirea din fisier";
		return;
	}
	stringstream buffer;
	buffer << in.rdbuf();
	text = buffer.str();
	in.close();
}

int main() {
	string text;
	citire_text(text);
	codare_huffman(text);
	return 0;
}