#include "ASTProgramNode.h"

ASTProgramNode::ASTProgramNode() {

}

void ASTProgramNode::addNode(ASTProgramNode * node) {
    nodes.push_back(node);
}

void ASTProgramNode::setLevel(int level) {
    this->level = level;
}

void ASTProgramNode::setScope(int scope) {
    this->scope = scope;
}

void ASTProgramNode::setEntryIndex(int entryIndex) {
    this->entryIndex = entryIndex;
}

void ASTProgramNode::setParent(ASTProgramNode *parent) {
    this->parent = parent;
}

void ASTProgramNode::setTable(SymbolTable * table) {
    this->table = table;
}

vector<ASTProgramNode *> ASTProgramNode::getNodes() {
    return nodes;
}

int ASTProgramNode::getLevel() {
    return level;
}

int ASTProgramNode::getScope() {
    return scope;
}

int ASTProgramNode::getEntryIndex() {
    return entryIndex;
}

ASTProgramNode * ASTProgramNode::getParent() {
    return parent;
}

SymbolTable * ASTProgramNode::getTable() {
    return table;
}