#ifndef CPS2006_VISITOR_H
#define CPS2006_VISITOR_H

class ASTProgramNode;
class ASTAssignmentNode;
class ASTBlockNode;
class ASTExpressionNode;
class ASTFunctionDeclNode;
class ASTFunctionParameter;
class ASTIfStatementNode;
class ASTReturnStatementNode;
class ASTStatementNode;
class ASTVarDeclNode;
class ASTWhileStatementNode;
class ASTWriteStatementNode;

class Visitor {

    public:
        virtual void visit(ASTProgramNode *) = 0;
        virtual void visit(ASTAssignmentNode *) = 0;
        virtual void visit(ASTBlockNode *) = 0;
        virtual void visit(ASTExpressionNode *) = 0;
        virtual void visit(ASTFunctionDeclNode *) = 0;
        virtual void visit(ASTFunctionParameter *) = 0;
        virtual void visit(ASTIfStatementNode *) = 0;
        virtual void visit(ASTReturnStatementNode *) = 0;
        virtual void visit(ASTStatementNode *) = 0;
        virtual void visit(ASTVarDeclNode *) = 0;
        virtual void visit(ASTWhileStatementNode *) = 0;
        virtual void visit(ASTWriteStatementNode *) = 0;

};

#endif //CPS2006_VISITOR_H
