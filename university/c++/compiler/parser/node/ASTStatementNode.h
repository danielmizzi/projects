#ifndef CPS2006_ASTSTATEMENTNODE_H
#define CPS2006_ASTSTATEMENTNODE_H

#include "ASTProgramNode.h"
#include "ASTExpressionNode.h"

class ASTStatementNode: public ASTProgramNode {

    private:
        vector<ASTExpressionNode *> expressions;

    public:
        NodeType getType() {
            return NodeType::TYPE_ASTStatementNode;
        }

        void addExpression(ASTExpressionNode * expression);

        vector<ASTExpressionNode *> getExpressions();

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTSTATEMENTNODE_H
