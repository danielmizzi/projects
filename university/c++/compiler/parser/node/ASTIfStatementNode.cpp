#include "ASTIfStatementNode.h"

ASTIfStatementNode::ASTIfStatementNode(ASTExpressionNode * expression) {
    this->expression = expression;
}

ASTExpressionNode * ASTIfStatementNode::getExpression() {
    return expression;
}

void ASTIfStatementNode::setBlock(ASTBlockNode *block) {
    this->block = block;
}

ASTBlockNode * ASTIfStatementNode::getBlock() {
    return block;
}

