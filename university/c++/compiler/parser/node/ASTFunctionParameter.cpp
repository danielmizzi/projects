#include "ASTFunctionParameter.h"

ASTFunctionParameter::ASTFunctionParameter(string name, string type) {
    this->name = name;
    this->type = type;
}

string ASTFunctionParameter::getName() {
    return name;
}

string ASTFunctionParameter::getType() {
    return type;
}

void ASTFunctionParameter::setLevel(int level) {
    this->level = level;
}

int ASTFunctionParameter::getLevel() {
    return level;
}