#include <iostream>
#include <fstream>
#include <map>
#include <unordered_map>
#include <queue>
#include <functional>

using namespace std;

struct Node{
        char c;
        int fr;
        Node* left;
        Node* right;
        Node(char ch,int f,Node* l,Node* r):c{ch},fr{f},left{l},right{r}{};
};

void encode(unordered_map<char,string>* codemap,Node* n,string cod){
        if(n->left!= nullptr || n->right!=nullptr){
                if(n->left!=nullptr)
                        encode(codemap,n->left,cod+"0");
                if(n->right!=nullptr)
                        encode(codemap,n->right,cod+"1");
                delete n;
        }
        else{
                codemap->find(n->c)->second=cod;
                delete n;
        }
}

int main(int argc,char* argv[]){
        //citire fisier
        string in=argv[1];
        string out=argv[2];
        ifstream fin(in);
        ofstream fout(out);
        string text="";
        fin>>text;
        //constr alfabet
        map<char,int> alfabet;
        for(char c:text){
                auto it=alfabet.find(c);
                if(it!=alfabet.end())
                        it->second++;
                else
                        alfabet.insert({c,1});
        }
        int len=alfabet.size();
        fout<<len<<"\n";
        for(auto el: alfabet)
                fout<<el.first<<" "<<el.second<<"\n";
        //codare huffman
        priority_queue<Node*,vector<Node*>,function<bool(Node* n1,Node* n2)>> q([](Node* n1, Node* n2){if(n1->c!='\0' && n2->c!='\0' && n1->fr==n2->fr) return n1->c>n2->c; return n1->fr>n2->fr;});
        for(auto el: alfabet){
                Node* n=new Node{el.first,el.second,nullptr,nullptr};
                q.push(n);
        }
        for(int i=0;i<len-1;i++){
                Node* st=q.top();
                q.pop();
                Node* dr=q.top();
                q.pop();
                Node* z=new Node{'\0',st->fr+dr->fr,st,dr};
                q.push(z);
        }
        Node* root=q.top();
        unordered_map<char,string> cod;
        for(auto el:alfabet)
                cod.insert({el.first,""});
        encode(&cod,root,"");
        string huff="";
        for(char c:text)
                huff+=cod.find(c)->second;
        fout<<huff<<"\n";
        fin.close();
        fout.close();
        return 0;
}