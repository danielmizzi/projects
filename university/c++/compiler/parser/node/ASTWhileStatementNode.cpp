#include "ASTWhileStatementNode.h"

ASTWhileStatementNode::ASTWhileStatementNode(ASTExpressionNode * expression) {
    this->expression;
}

ASTExpressionNode * ASTWhileStatementNode::getExpression() {
    return expression;
}

void ASTWhileStatementNode::setBlock(ASTBlockNode * block) {
    this->block = block;
}

ASTBlockNode * ASTWhileStatementNode::getBlock() {
    return block;
}

