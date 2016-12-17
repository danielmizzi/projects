#ifndef CPS2006_ASTVARDECLNODE_H
#define CPS2006_ASTVARDECLNODE_H

#include "ASTStatementNode.h"

using namespace std;

class ASTVarDeclNode: public ASTStatementNode {

    private:
        Token * identifier;
        Token * type;

        ASTExpressionNode * expression;

    public:
        ASTVarDeclNode(ASTExpressionNode * expression, Token * identifier, Token * type);

        NodeType getNodeType() {
            return NodeType::TYPE_ASTVarDeclNode;
        }

        Token * getIdentifier();
        Token * getVarType();

        ASTExpressionNode * getExpression();

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }

};

#endif //CPS2006_ASTVARDECLNODE_H
