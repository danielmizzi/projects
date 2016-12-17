#ifndef CPS2006_ASTASSIGNMENTNODE_H
#define CPS2006_ASTASSIGNMENTNODE_H

#include "ASTStatementNode.h"

class ASTAssignmentNode: public ASTStatementNode {

    private:
        Token * identifier;
        ASTExpressionNode * expression;

    public:
        ASTAssignmentNode(Token * identifier, ASTExpressionNode * expression);

        NodeType getNodeType() {
            return NodeType::TYPE_ASTAssignmentNode;
        }

        void setIdentifier(Token * identiier);
        void setExpression(ASTExpressionNode * expression);

        Token * getIdentifier();
        ASTExpressionNode * getExpression();

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }

};

#endif //CPS2006_ASTASSIGNMENTNODE_H
