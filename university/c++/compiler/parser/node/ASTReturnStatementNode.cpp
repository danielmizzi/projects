#include "ASTReturnStatementNode.h"

ASTReturnStatementNode::ASTReturnStatementNode(ASTExpressionNode * expression) {
    this->expression = expression;
}

ASTExpressionNode * ASTReturnStatementNode::getExpression() {
    return expression;
}