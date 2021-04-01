#include <iostream>
#include <fstream>
#include <queue>
#include <functional>

using namespace std;

struct Node{
        char ch;
        int fr;
        Node* left;
        Node* right;
};

void clean(Node* n){
        if(n->left!=nullptr || n->right!=nullptr){
                clean(n->left);
                clean(n->right);
                delete n;
        }
        else
                delete n;
}

int main(int argc,char* argv[]){
        //citire fisier
        string in=argv[1];
        string out=argv[2];
        ifstream fin(in);
        ofstream fout(out);
        int len=0;
        fin>>len;
        priority_queue<Node*,vector<Node*>,function<bool(Node* n1,Node* n2)>> q([](Node* n1, Node* n2){if(n1->ch!='\0' && n2->ch!='\0' && n1->fr==n2->fr) return n1->ch>n2->ch; return n1->fr>n2->fr;});
        for(int i=0;i<len;i++){
                char c;
                int fr;
                fin>>c>>fr;
                Node* n=new Node{c,fr,nullptr,nullptr};
                q.push(n);
        }
        string text;
        fin>>text;
        //decodare huffman
        for(int i=0;i<len-1;i++){
                Node* st=q.top();
                q.pop();
                Node* dr=q.top();
                q.pop();
                Node* z=new Node{'\0',st->fr+dr->fr,st,dr};
                q.push(z);
        }
        Node* root=q.top();
        int i=0;
        while(i<text.length()){
                Node* n=root;
                while(n->left!=nullptr || n->right!=nullptr){
                        if(text[i]=='0'){
                                n=n->left;
                                i++;
                        }
                        else{
                                n=n->right;
                                i++;
                        }
                }
                fout<<n->ch;
        }
        clean(root);
        fout<<"\n";
        fin.close();
        fout.close();
        return 0;
}