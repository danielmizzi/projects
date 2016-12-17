#include <iostream>
#include <string>

#ifndef QUADTREE
	#define QUADTREE
	#include "QuadTree.h"
#endif

using namespace std;

const char * types[] = {"INTERMEDIATE", "BLACK", "WHITE", "NIL"};

QuadTree::QuadTree(unsigned width, unsigned height) {
	this->width = width;
	this->height = height;
	this->level = 0;
	this->x = 0;
	this->y = 0;
	for (unsigned i = 0; i < 4; i ++) {
		children[i] = NULL;
	}
}

QuadTree::QuadTree(unsigned width, unsigned height, unsigned level, unsigned x, unsigned y) {
	this->width = width;
	this->height = height;
	this->level = level;
	this->x = x;
	this->y = y;
	for (unsigned i = 0; i < 4; i ++) {
		children[i] = NULL;
	}
}

void QuadTree::setWidth(unsigned width) {
	this->width = width;
}

unsigned QuadTree::getWidth() {
	return width;
}

void QuadTree::setHeight(unsigned height) {
	this->height = height;
}

unsigned QuadTree::getHeight() {
	return height;
}

void QuadTree::setLevel(unsigned level) {
	this->level = level;
}

unsigned QuadTree::getLevel() {
	return level;
}

void QuadTree::setX(unsigned x) {
	this->x = x;
}

unsigned QuadTree::getX() {
	return x;
}

void QuadTree::setY(unsigned y) {
	this->y = y;
}

unsigned QuadTree::getY() {
	return y;
}

void QuadTree::setValue(char value) {
	this->value = value;
}

char QuadTree::getValue() {
	return value;
}

void QuadTree::setType(NodeType type) {
	this->type = type;
}

NodeType QuadTree::getType() {
	return type;
}

void QuadTree::setExpand(bool expand) {
	this->expand = expand;
}

bool QuadTree::isExpandable() {
	return expand;
}

QuadTree * * QuadTree::getChildren() {
	return children;
}

void QuadTree::initializeChildren() {
	int parentX = getX();
	int parentY = getY();

	int width = getWidth();
	int height = getHeight();

	int newWidth = width / 2;
	int newHeight = height / 2;

	int diffWidth = width - newWidth;
	int diffHeight = height - newHeight;

	children[0] = new QuadTree(diffWidth, diffHeight, level + 1, parentX, parentY);

	if (newWidth != 0) {
		children[1] = new QuadTree(newWidth, diffHeight, level + 1, parentX + diffWidth, parentY);
	}

	if (newWidth != 0 && newHeight != 0) {
		children[2] = new QuadTree(newWidth, newHeight, level + 1, parentX + diffWidth, parentY + diffHeight);
	}

	if (newHeight != 0) {
		children[3] = new QuadTree(diffWidth, newHeight, level + 1, parentX, parentY + diffHeight);
	}


}

void QuadTree::print() {
	string s = "";
	for (unsigned i = 0; i < level; i ++) {
		s += "\t";
	}
	s += types[type];
	cout << s << '\n' << endl;
	
	if (expand) {
		for (unsigned i = 0; i < 4; i ++) {
			if (children[i] != NULL && children[i]->getType() != NodeType::NIL) {
				children[i]->print();
			}
		}
	}
}

