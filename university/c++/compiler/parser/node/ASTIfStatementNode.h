#ifndef CPS2006_ASTIFSTATEMENTNODE_H
#define CPS2006_ASTIFSTATEMENTNODE_H

#include "ASTStatementNode.h"

class ASTIfStatementNode: public ASTStatementNode {

    private:
        ASTExpressionNode * expression;

        ASTBlockNode * block;

    public:
        ASTIfStatementNode(ASTExpressionNode * expression);

        ASTExpressionNode * getExpression();

        void setBlock(ASTBlockNode * block);

        ASTBlockNode * getBlock();

        NodeType getNodeType() {
            return NodeType::TYPE_ASTIfStatementNode;
        }

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTIFSTATEMENTNODE_H
