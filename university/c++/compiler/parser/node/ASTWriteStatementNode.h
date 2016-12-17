#ifndef CPS2006_ASTWRITESTATEMENTNODE_H
#define CPS2006_ASTWRITESTATEMENTNODE_H

#include "ASTStatementNode.h"
#include "../../visitor/Visitor.h"

class ASTWriteStatementNode: public ASTStatementNode {

    private:
        ASTExpressionNode * expression;

    public:
        ASTWriteStatementNode(ASTExpressionNode * expression);

        ASTExpressionNode * getExpression();

        NodeType getNodeType() {
            return NodeType::TYPE_ASTWriteStatementNode;
        }

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTWRITESTATEMENTNODE_H
