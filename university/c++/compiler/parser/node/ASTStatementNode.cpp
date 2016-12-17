#include "ASTStatementNode.h"

#include <iostream>

using namespace std;

void ASTStatementNode::addExpression(ASTExpressionNode * expression) {
    this->expressions.push_back(expression);
}

vector<ASTExpressionNode *> ASTStatementNode::getExpressions() {
    return expressions;
}