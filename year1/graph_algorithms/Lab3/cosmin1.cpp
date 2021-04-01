#include <string>
#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

#define INFINIT 10000000

typedef struct
{
	int vertex, weight;
} Node;

int minDistance(int distance[], bool visited[], int numberOfVertices)
{
	int lowestDistance = INFINIT;
	int vertexLowestDistance = 0;
	for (int index = 0; index < numberOfVertices; index++)
		if (visited[index] == false && distance[index] < lowestDistance)
		{
			lowestDistance = distance[index];
			vertexLowestDistance = index;
		}
	return vertexLowestDistance;
}

void Dijkstra(vector<Node> adjList[], int distance[], int sourceNode, int numberVertices)
{
	bool* visited = new bool[numberVertices];
	for (int index = 0; index < numberVertices; index++)
	{
		visited[index] = false;
		distance[index] = INFINIT;
	}
	distance[sourceNode] = 0;
	for (int index = 1; index <= numberVertices - 1; index++)
	{
		int nodeLowestDistance = minDistance(distance, visited, numberVertices);
		visited[nodeLowestDistance] = true;
		for (auto adjacentNode : adjList[nodeLowestDistance])
			if (visited[adjacentNode.vertex] == false && adjacentNode.weight + distance[nodeLowestDistance] < distance[adjacentNode.vertex])
				distance[adjacentNode.vertex] = adjacentNode.weight + distance[nodeLowestDistance];
	}
	delete[] visited;
}

int main()
{
	string caleInput;
	string caleOutput;
	cin >> caleInput;
	cin >> caleOutput;
	ifstream fin(caleInput);
	ofstream fout(caleOutput);
	vector<Node> adjList[10000];
	int distance[10000]; 
	int numberOfVertices{ 0 }, numberOfEdges{ 0 }, sourceNode{ 0 };
	fin >> numberOfVertices >> numberOfEdges >> sourceNode;
	for (int index = 1; index <= numberOfEdges; index++)
	{
		int x, y, weight;
		fin >> x >> y >> weight;
		Node node;
		node.vertex = y;
		node.weight = weight;
		adjList[x].push_back(node);
	}
	Dijkstra(adjList, distance, sourceNode, numberOfVertices);
	for (int index = 0; index < numberOfVertices; index++)
		if (distance[index] == INFINIT) fout << "INF ";
		else fout << distance[index] << " ";
	fin.close();
	fout.close();
	return 0;
}