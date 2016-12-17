#ifndef CPS2006_ASTFUNCTIONDECLNODE_H
#define CPS2006_ASTFUNCTIONDECLNODE_H

#include "ASTStatementNode.h"
#include "ASTFunctionParameter.h"

class ASTFunctionDeclNode: public ASTStatementNode {

    private:
        vector<ASTFunctionParameter *> parameters;

        string name;
        string type;

        ASTBlockNode * block;

    public:
        ASTFunctionDeclNode(vector<ASTFunctionParameter *> parameters, string name, string type);

        vector<ASTFunctionParameter *> getParameters();

        string getName();
        string getFunctionType();

        void setBlock(ASTBlockNode * block);

        ASTBlockNode * getBlock();

        NodeType getNodeType() {
            return NodeType::TYPE_ASTFunctionDeclNode;
        }

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTFUNCTIONDECLNODE_H
