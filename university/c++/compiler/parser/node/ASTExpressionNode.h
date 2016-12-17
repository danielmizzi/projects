#ifndef CPS2006_ASTEXPRESSIONNODE_H
#define CPS2006_ASTEXPRESSIONNODE_H

#include "../../visitor/Visitor.h"
#include "../../lexer/Token.h"

#include <vector>

using namespace std;

class ASTExpressionNode {

    private:
        int level = 0;

        bool root = false;
        bool hasOp = true;
        bool hasFunc = false;

        Token * op;
        Token * functionCall;
        vector<Token *> operand;

        vector<Token *> lhsVector;
        vector<Token *> rhsVector;

        vector<vector<Token *>> params;

        ASTExpressionNode * lhs = NULL;
        ASTExpressionNode * rhs = NULL;

    public:
        ASTExpressionNode();
        ASTExpressionNode(Token * op, vector<Token *> operand);
        ASTExpressionNode(Token * op, vector<Token *> lhs, vector<Token *> rhs);
        ASTExpressionNode(Token * op, ASTExpressionNode * lhs, ASTExpressionNode * rhs);

        Token * getOperator();
        Token * getFunctionCall();
        vector<Token *> getOperand();

        vector<Token *> getLHSVector();
        vector<Token *> getRHSVector();

        vector<vector<Token *>> getParameters();

        void setLHSVector(vector<Token *> lhsVector);
        void setRHSVector(vector<Token *> rhsVector);

        void setParameters(vector<vector<Token *>> params);

        ASTExpressionNode * getLHS();
        ASTExpressionNode * getRHS();

        void setOperator(Token * op);
        void setOperand(vector<Token *> operand);

        void setHasFunction(bool hasFunc);
        void setHasOperator(bool hasOp);

        void setLHS(ASTExpressionNode * lhs);
        void setRHS(ASTExpressionNode * rhs);

        void setLevel(int level);
        void setRoot(bool root);

        void setFunctionCall(Token * functionCall);

        int getLevel();

        bool isRoot();
        bool hasFunction();
        bool hasOperator();

        void print();
        void print(bool left);

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTEXPRESSIONNODE_H_H
