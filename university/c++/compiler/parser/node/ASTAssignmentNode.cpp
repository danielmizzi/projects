#include "ASTAssignmentNode.h"

ASTAssignmentNode::ASTAssignmentNode(Token * identifier, ASTExpressionNode * expression) {
    this->identifier = identifier;
    this->expression = expression;
}

void ASTAssignmentNode::setIdentifier(Token * identifier) {
    this->identifier = identifier;
}

Token * ASTAssignmentNode::getIdentifier() {
    return identifier;
}

void ASTAssignmentNode::setExpression(ASTExpressionNode *expression) {
    this->expression = expression;
}

ASTExpressionNode * ASTAssignmentNode::getExpression() {
    return expression;
}