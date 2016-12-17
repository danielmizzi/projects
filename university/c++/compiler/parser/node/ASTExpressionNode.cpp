#include "ASTExpressionNode.h"
#include <iostream>

using namespace std;

ASTExpressionNode::ASTExpressionNode() {

}

ASTExpressionNode::ASTExpressionNode(Token * op, vector<Token *> operand) {
    this->op = op;
    this->operand = operand;
}

ASTExpressionNode::ASTExpressionNode(Token * op, vector<Token *> lhsVector, vector<Token *> rhsVector) {
    this->op = op;
    this->lhsVector = lhsVector;
    this->rhsVector = rhsVector;
}

ASTExpressionNode::ASTExpressionNode(Token * op, ASTExpressionNode * lhs, ASTExpressionNode * rhs) {
    this->op = op;
    this->lhs = lhs;
    this->rhs = rhs;
}

Token * ASTExpressionNode::getOperator() {
    return op;
}

void ASTExpressionNode::setOperator(Token * op) {
    this->op = op;
}

void ASTExpressionNode::setOperand(vector<Token *> operand) {
    this->operand = operand;
}

void ASTExpressionNode::setHasFunction(bool hasFunc) {
    this->hasFunc = hasFunc;
}

void ASTExpressionNode::setHasOperator(bool hasOp) {
    this->hasOp = hasOp;
}

vector<Token *> ASTExpressionNode::getOperand() {
    return operand;
}

vector<Token *> ASTExpressionNode::getLHSVector() {
    return lhsVector;
}

vector<Token *> ASTExpressionNode::getRHSVector() {
    return rhsVector;
}

vector<vector<Token *>> ASTExpressionNode::getParameters() {
    return params;
}

void ASTExpressionNode::setLHSVector(vector<Token *> lhsVector) {
    this->lhsVector = lhsVector;
}

void ASTExpressionNode::setRHSVector(vector<Token *> rhsVector) {
    this->rhsVector = rhsVector;
}

void ASTExpressionNode::setParameters(vector<vector<Token *>> params) {
    this->params = params;
}

ASTExpressionNode * ASTExpressionNode::getLHS() {
    return lhs;
}

ASTExpressionNode * ASTExpressionNode::getRHS() {
    return rhs;
}

void ASTExpressionNode::setLHS(ASTExpressionNode * lhs) {
    this->lhs = lhs;
}

void ASTExpressionNode::setRHS(ASTExpressionNode * rhs) {
    this->rhs = rhs;
}

void ASTExpressionNode::setLevel(int level) {
    this->level = level;
}

int ASTExpressionNode::getLevel() {
    return level;
}

void ASTExpressionNode::setRoot(bool root) {
    this->root = root;
}

bool ASTExpressionNode::hasOperator() {
    return hasOp;
}

bool ASTExpressionNode::hasFunction() {
    return hasFunc;
}

bool ASTExpressionNode::isRoot() {
    return root;
}

void ASTExpressionNode::setFunctionCall(Token * functionCall) {
    this->functionCall = functionCall;
}

Token * ASTExpressionNode::getFunctionCall() {
    return functionCall;
}

void ASTExpressionNode::print() {
    this->print(false);
}

void ASTExpressionNode::print(bool left) {
    string tabs = "";
    string s = "";
    for (int i = 0; i < getLevel(); i ++) {
        tabs.append("\t");
    }

    s.append(tabs);

    s.append(isRoot() ? "expression" : left ? "left_child" : "right_child");
    s.append(" = expression_node (operation = \"");
    s.append(getOperator()->getTokenSource());
    s.append("\")");

    cout << s << endl;

    ASTExpressionNode * lhs = getLHS();
    if (lhs != NULL) {
        lhs->print(true);
    } else {
        cout << tabs << "\tleft_child = " << getLHSVector()[0]->getTokenSource() << endl;
    }

    ASTExpressionNode * rhs = getRHS();
    if (rhs != NULL) {
        rhs->print();
    } else {
        cout << tabs << "\tright_child = " << getRHSVector()[0]->getTokenSource() << endl;
    }
}