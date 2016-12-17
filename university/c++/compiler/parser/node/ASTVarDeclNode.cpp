#include "ASTVarDeclNode.h"

ASTVarDeclNode::ASTVarDeclNode(ASTExpressionNode * expression, Token * identifier, Token * type) {
    this->expression = expression;
    this->identifier = identifier;
    this->type = type;
}
Token * ASTVarDeclNode::getIdentifier() {
    return identifier;
}

Token * ASTVarDeclNode::getVarType() {
    return type;
}

ASTExpressionNode * ASTVarDeclNode::getExpression() {
    return expression;
}
