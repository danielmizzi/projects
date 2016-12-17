#include "ASTFunctionDeclNode.h"

ASTFunctionDeclNode::ASTFunctionDeclNode(vector<ASTFunctionParameter *> parameters, string name, string type) {
    this->parameters = parameters;
    this->name = name;
    this->type = type;
}

string ASTFunctionDeclNode::getName() {
    return name;
}

string ASTFunctionDeclNode::getFunctionType() {
    return type;
}

vector<ASTFunctionParameter *> ASTFunctionDeclNode::getParameters() {
    return parameters;
}

void ASTFunctionDeclNode::setBlock(ASTBlockNode * block) {
    this->block = block;
}

ASTBlockNode * ASTFunctionDeclNode::getBlock() {
    return block;
}