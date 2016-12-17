#include "ASTBlockNode.h"

ASTBlockNode::ASTBlockNode(ASTProgramNode * parent) {
    this->parent = parent;
}

void ASTBlockNode::addStatement(ASTStatementNode * node) {
    statements.push_back(node);
}

vector<ASTStatementNode *> ASTBlockNode::getStatements() {
    return statements;
}

ASTProgramNode * ASTBlockNode::getParent() {
    return parent;
}

int ASTBlockNode::getScope() {
    return parent->getNodeType() == NodeType::TYPE_ASTBlockNode ? dynamic_cast<ASTBlockNode *> (this->getParent())->getScope() + 1 : 0;
}