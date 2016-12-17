#ifndef CPS2006_ASTBLOCKNODE_H
#define CPS2006_ASTBLOCKNODE_H

#include "ASTStatementNode.h"

class ASTBlockNode: public ASTStatementNode {

    private:

        ASTProgramNode * parent = NULL;
        ASTStatementNode * type = NULL;

        vector<ASTStatementNode *> statements;

    public:
        ASTBlockNode(ASTProgramNode * parent);

        void addStatement(ASTStatementNode * node);

        vector<ASTStatementNode *> getStatements();

        ASTProgramNode * getParent();

        int getScope();

        NodeType getNodeType() {
            return NodeType::TYPE_ASTBlockNode;
        }

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTBLOCKNODE_H
