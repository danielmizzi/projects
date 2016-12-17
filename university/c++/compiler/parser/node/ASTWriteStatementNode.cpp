#include "ASTWriteStatementNode.h"

ASTWriteStatementNode::ASTWriteStatementNode(ASTExpressionNode * expression) {
    this->expression = expression;
}

ASTExpressionNode * ASTWriteStatementNode::getExpression() {
    return expression;
}