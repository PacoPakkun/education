#include <string>
#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

#define INFINIT 100000

typedef struct
{
	int x, y;
} Edge;

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

void Dijkstra(vector<int> weightList, vector<Edge> edgeList, int distance[], int parent[], int sourceNode, int numberVertices, int numberEdges)
{
	bool* visited = new bool[numberVertices];
	for (int index = 0; index < numberVertices; index++)
	{
		visited[index] = false;
		parent[index] = -1;
		distance[index] = INFINIT;
	}
	distance[sourceNode] = 0; 
	for (int index = 1; index <= numberVertices - 1; index++)
	{
		int nodeLowestDistance = minDistance(distance, visited, numberVertices);
		visited[nodeLowestDistance] = true;
		for (int indexEdge = 0; indexEdge < numberEdges; indexEdge++)
			if (edgeList[indexEdge].x == nodeLowestDistance)
			{
				int y = edgeList[indexEdge].y;
				if (visited[y] == false && weightList[indexEdge] + distance[nodeLowestDistance] < distance[y])
				{
					distance[y] = weightList[indexEdge] + distance[nodeLowestDistance];
					parent[y] = nodeLowestDistance;
				}
			}
	}
	delete[] visited;
}

bool Bellman_Ford(vector<int> weightList, vector<Edge> edgeList, int distance[], int numberOfVertices, int numberOfEdges, int sourceNode)
{
	for (int index = 0; index < numberOfVertices; index++)
		distance[index] = INFINIT;
	distance[sourceNode] = 0; 
	for (int index = 0; index < numberOfVertices; index++)
		for (int j = 0; j < numberOfEdges; j++) 
			if (distance[edgeList[j].y] > distance[edgeList[j].x] + weightList[j])
				distance[edgeList[j].y] = distance[edgeList[j].x] + weightList[j];
	for (int index = 0; index < numberOfVertices; index++)
		for (int j = 0; j < numberOfEdges; j++)
			if (distance[edgeList[j].y] > distance[edgeList[j].x] + weightList[j])
					return false;
	return true;
}

int main()
{
	string caleInput;
	string caleOutput;
	cin >> caleInput;
	cin >> caleOutput;
	ifstream fin(caleInput);
	ofstream fout(caleOutput);
	vector<int> weightList;
	vector<int> ajustedWeightList;
	vector<Edge> edgeList;
	vector<Node> adjList[1005];
	int parent[1005]; 
	int distance[1005]; 
	int numberOfVertices{ 0 }, numberOfEdges{ 0 };
	fin >> numberOfVertices >> numberOfEdges;
	for (int index = 1; index <= numberOfEdges; index++)
	{
		int x, y, weight;
		fin >> x >> y >> weight;
		Edge edge{ x, y };
		edgeList.push_back(edge);
		weightList.push_back(weight);
		adjList[x].push_back(Node{ y, weight });
	}
	int sourceNode = 1001;
	for (int index = 0; index < numberOfVertices; index++)
	{
		Edge edge{ sourceNode, index };
		edgeList.push_back(edge);
		weightList.push_back(0);
	}

	bool has_negativeCycles = Bellman_Ford(weightList, edgeList, distance, numberOfVertices, numberOfEdges + numberOfVertices, sourceNode);
	if (has_negativeCycles == false)
	{
		fout << "-1";
		fin.close();
		fout.close();
		return 0;
	}

	ajustedWeightList = weightList; 
	for (int index = 0; index < numberOfEdges; index++)
		ajustedWeightList[index] =  ajustedWeightList[index] + distance[edgeList[index].x] - distance[edgeList[index].y];
	for (int index = 0; index < numberOfEdges; index++)
		fout << edgeList[index].x << " " << edgeList[index].y << " " << ajustedWeightList[index] << "\n";

	for (int index = 0; index < numberOfVertices; index++)
	{
		Dijkstra(ajustedWeightList, edgeList, distance, parent, index, numberOfVertices, numberOfEdges);
		for (int i = 0; i < numberOfVertices; i++)
		{
			int distanceToSource = 0;
			if (i == index) fout << "0 "; 
			else if (parent[i] == -1) fout << "INF ";
			else
			{
				int node = i;
				while (node != -1)
				{
					for (auto currentNode : adjList[parent[node]])
						if (currentNode.vertex == node)
							distanceToSource += currentNode.weight;
					node = parent[node];
				}
				fout << distanceToSource << " ";
			}
		}
		fout << "\n";
	}
	fin.close();
	fout.close();
	return 0;
}