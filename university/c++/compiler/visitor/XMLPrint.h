#ifndef CPS2006_XMLPRINT_H
#define CPS2006_XMLPRINT_H

#include "Visitor.h"

#include "../parser/node/ASTProgramNode.h"
#include "../parser/node/ASTAssignmentNode.h"
#include "../parser/node/ASTBlockNode.h"
#include "../parser/node/ASTExpressionNode.h"
#include "../parser/node/ASTFunctionDeclNode.h"
#include "../parser/node/ASTFunctionParameter.h"
#include "../parser/node/ASTIfStatementNode.h"
#include "../parser/node/ASTReturnStatementNode.h"
#include "../parser/node/ASTStatementNode.h"
#include "../parser/node/ASTVarDeclNode.h"
#include "../parser/node/ASTWhileStatementNode.h"
#include "../parser/node/ASTWriteStatementNode.h"

class XMLPrint: public Visitor {

    public:
        XMLPrint();

        virtual void visit(ASTProgramNode *);
        virtual void visit(ASTAssignmentNode *);
        virtual void visit(ASTBlockNode *);
        virtual void visit(ASTExpressionNode *);
        virtual void visit(ASTFunctionDeclNode *);
        virtual void visit(ASTFunctionParameter *);
        virtual void visit(ASTIfStatementNode *);
        virtual void visit(ASTReturnStatementNode *);
        virtual void visit(ASTStatementNode *);
        virtual void visit(ASTVarDeclNode *);
        virtual void visit(ASTWhileStatementNode *);
        virtual void visit(ASTWriteStatementNode *);

        void printTab(int iterations);

};

#endif //CPS2006_XMLPRINT_H
