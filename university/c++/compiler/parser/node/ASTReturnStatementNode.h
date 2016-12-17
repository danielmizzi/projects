#ifndef CPS2006_ASTRETURNSTATEMENTNODE_H
#define CPS2006_ASTRETURNSTATEMENTNODE_H

#include "ASTStatementNode.h"

class ASTReturnStatementNode: public ASTStatementNode {

    private:
        ASTExpressionNode * expression;

    public:
        ASTReturnStatementNode(ASTExpressionNode * expression);

        ASTExpressionNode * getExpression();

        NodeType getNodeType() {
            return NodeType::TYPE_ASTReturnStatementNode;
        }

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTRETURNSTATEMENTNODE_H
