#ifndef CPS2006_ASTWHILESTATEMENTNODE_H
#define CPS2006_ASTWHILESTATEMENTNODE_H

#include "ASTStatementNode.h"

class ASTWhileStatementNode: public ASTStatementNode {

    private:
        ASTExpressionNode * expression;

        ASTBlockNode * block;

    public:
        ASTWhileStatementNode(ASTExpressionNode * expression);

        ASTExpressionNode * getExpression();

        void setBlock(ASTBlockNode *);

        ASTBlockNode * getBlock();

        NodeType getNodeType() {
            return NodeType::TYPE_ASTWhileStatementNode;
        }

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTWHILESTATEMENTNODE_H
