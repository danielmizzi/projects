#include <iostream>
#include <fstream>
#include <string.h>
#include <stdio.h>
#include <vector>

#ifndef QUADTREE
	#define	QUADTREE
	#include "QuadTree.h"
#endif

using namespace std;

bool hasEnding(string const &fullString, string const &ending) {
	if (fullString.length() >= ending.length()) {
		return (0 == fullString.compare(fullString.length() - ending.length(), ending.length(), ending));
	} else {
		return false;
	}
}

void setTypes(QuadTree * root) {
	NodeType first = NodeType::NIL;
	bool reGet = false;
	
	QuadTree * * children = root->getChildren();

	for (unsigned i = 0; i < 4; i ++) {
		if (children[i] == NULL) {
			continue;
		}
		NodeType childType = children[i]->getType();
		if (childType == NodeType::NIL) {
			setTypes(children[i]);
			reGet = true;
		}
		if (reGet) {
			childType = children[i]->getType();
			reGet = false;
		}
		if (first == NodeType::NIL) {
			first = childType;
		}
		if (first != NodeType::NIL) {
			NodeType rootType = root->getType();
			if (rootType == NodeType::NIL) {
				if (first != childType) {
					root->setType(NodeType::INTERMEDIATE);
					root->setExpand(true);
				} else if (i == 3) {
					root->setType(childType);
					root->setExpand(false);
				}
			}
		}
	}
}

QuadTree * getAt(unsigned x, unsigned y, QuadTree * root) {
	if (root != NULL) {
		if (root->getWidth() == 1 && root->getHeight() == 1) {
			if (root->getX() == x && root->getY() == y) {
				return root;
			}
		} else {
			QuadTree * * children = root->getChildren();
			for (unsigned i = 0; i < 4; i ++) {
				QuadTree * result = getAt(x, y, children[i]);
				if (result != NULL) {
					return result;
				}
			}
		}
	}
	return NULL;
}

void generate(QuadTree * root) {
	if (root->getWidth() > 1 && root->getHeight() > 1) {
		root->initializeChildren();
		QuadTree * * children = root->getChildren();
		for (unsigned i = 0; i < 4; i ++) {
			if (children[i] != NULL) {
				generate(children[i]);
			}
		}
	}
}

int main() {
	QuadTree * loadedTree = NULL;
	string s;
	while (true) {
		getline(cin, s);
		int result = s.compare("quit");
		if (result == 0) {
			break;
		}

		const char * toC = s.c_str();

		if (strncmp(toC, "load ", 5) == 0) { 
			// load a quad tree
			// if file extension ends with .4, loads a quad tree
			// otherwise parses .txt file and generates the quad tree
			
			string fileName = s.substr(5);
			if (hasEnding(s, ".txt")) {
				cout << "Generating quad tree from .txt file" << endl;
				ifstream inFile;
				inFile.open("save/" + fileName, ios::in);
				if (!inFile.is_open()) {
					cerr << "Unable to open hello.txt (file not found)" << endl;
				} else {
					vector<string> lines;

					string line;
					while (getline(inFile, line)) {
						lines.push_back(line);
					}
					inFile.close();

					unsigned width = lines.at(0).size();
					unsigned height = lines.size();

					QuadTree * root = new QuadTree(width, height, 0, 0, 0);
					generate(root);

					int bits[width][height];

					for (unsigned y = 0; y < height; y ++) {
						for (unsigned x = 0; x < lines.at(y).size(); x ++) {
							QuadTree * child = getAt(x, y, root);
							char value = lines.at(y).at(x);

							child->setValue(value);
							child->setType(value == 'F' ? NodeType::BLACK : NodeType::WHITE);
						}
					}

					setTypes(root);

					loadedTree = root; // set the loaded tree to the context

					cout << "Successfully loaded QuadTree!" << endl;
				}
			} else if (hasEnding(s, ".4")) {
				cout << "Loading a generated quadtree (.4)" << endl;

				ifstream inFile;
				inFile.open("save/" + fileName, ios::in);
				if (!inFile.is_open()) {
					cerr << "Unable to open hello.txt (file not found)" << endl;
				} else {
					vector<string> lines;

					string line;
					while (getline(inFile, line)) {
						lines.push_back(line);
					}
					inFile.close();
			
					unsigned width = atoi(lines.at(0).c_str());
					unsigned height = atoi(lines.at(0).c_str());

					QuadTree * temp = new QuadTree(width, height, 0, 0, 0);

					generate(temp);

					for (unsigned i = 2; i < lines.size(); i ++) {
						string line = lines.at(i);
						// 0 is x value
						// 2 is y value
						// 4 is actual value
						unsigned x = line.at(0) - '0'; // since encodings for digits are all in order from 48 (for 0) to 57 (for 9)
						unsigned y = line.at(2) - '0';
						char val = line.at(4);
					
						QuadTree * child = getAt(x, y, temp);
						child->setValue(val);
						child->setType(val == 'F' ? NodeType::BLACK : NodeType::WHITE);
					}

					setTypes(temp);

					loadedTree = temp;
					
					cout << "QuadTree has been loaded into memory!" << endl;
				}
			} else {
				cout << "Unsupported file extension (only .4 and .txt are supported)" << endl;
			}		
		} else if (strncmp(toC, "save ", 5) == 0) { // saves as .4
			if (loadedTree != NULL) {
				string fileName = s.substr(5);
				cout << "Saving QuadTree to " + fileName + ".4" << endl;
				if (fileName.size() > 0) {
					ofstream outFile;
					outFile.open("save/" + fileName + ".4", ios::out);
					outFile << loadedTree->getWidth() << "\n";
					outFile << loadedTree->getHeight() << "\n";
					for (unsigned x = 0; x < loadedTree->getWidth(); x ++) {
						for (unsigned y = 0; y < loadedTree->getHeight(); y ++) {
							outFile << x << "|" << y << "|" << getAt(x, y, loadedTree)->getValue() << "\n";
						}
					}
					outFile.close();
				} else {
					cout << "Please enter a valid file name!" << endl;
				}
			} else {
				cout << "No QuadTree in memory to save, please load one using the 'load FILENAME.EXTENSION' command..." << endl; 
			}
		} else if (strncmp(toC, "display_recomposition", 21) == 0) {
			if (loadedTree != NULL) {
				cout << "Displaying currently loaded QuadTree (recomposition)...\n" << endl;
				for (unsigned x = 0; x < loadedTree->getWidth(); x ++) {
					for (unsigned y = 0; y < loadedTree->getHeight(); y ++) {
						cout << getAt(x, y, loadedTree)->getValue();
					}
					cout << endl;
				}
				cout << endl;
			} else {
				cout << "No QuadTree in memory to display, please load one using the 'load FILENAME.EXTENSION' command..." << endl;
			}
		} else if (strncmp(toC, "display_tree", 12) == 0) {
			if (loadedTree != NULL) {
				cout << "Printing QuadTree..\n" << endl;
				loadedTree->print();
			} else {
				cout << "No QuadTree in memory to display, please load one using the 'load FILENAME.EXTENSION' command..." << endl;
			}
		} else {
			cout << "Unknown command... only the following commands are supported:" << endl;
			cout << " 'save FILENAME' (no extension required)" << endl;
			cout << " 'load FILENAME.EXTENSION' (only .4 or .txt)" << endl;
			cout << " 'display_recomposition'" << endl;
			cout << " 'display_tree'" << endl;
			cout << " 'quit'\n" << endl;
		}
	}
	if (loadedTree != NULL) {
		delete(loadedTree);
	}
}
