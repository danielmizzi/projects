#include "XMLPrint.h"
#include "../lexer/TokenInfo.h"

#include <iostream>

using namespace std;

XMLPrint::XMLPrint() { }

void XMLPrint::visit(ASTProgramNode * node) {
    printTab(node->getLevel() - 1);
    cout << "<ASTProgramNode>" << endl;

    for (ASTProgramNode * n : node->getNodes()) {
        printTab(node->getLevel());
        cout << "<ASTStatementNode>" << endl;
        n->accept(this);
        printTab(node->getLevel());
        cout << "</ASTStatementNode>" << endl;
    }


    printTab(node->getLevel() - 1);
    cout << "</ASTProgramNode>" << endl;
}

void XMLPrint::visit(ASTAssignmentNode * node) {
    printTab(node->getLevel());
    cout << "<ASTAssignmentNode>" << endl;

    printTab(node->getLevel() + 1);
    cout << "<Identifier>" << node->getIdentifier()->getTokenSource() << "</Identifier>" << endl;

    node->getExpression()->accept(this);

    printTab(node->getLevel());
    cout << "</ASTAssignmentNode>" << endl;
}

void XMLPrint::visit(ASTBlockNode * node) {
    printTab(node->getLevel());
    cout << "<ASTBlockNode>" << endl;

    //type->accept(this);

    for (ASTProgramNode * n : node->getNodes()) {
         n->accept(this);
    }


    //dynamic_cast<ASTVarDeclNode *> (node->getNodes()[0])->accept(this);
    //node->getNodes()[1]->accept(this);

    printTab(node->getLevel());
    cout << "</ASTBlockNode>" << endl;
}

void XMLPrint::visit(ASTExpressionNode * node) {

    printTab(node->getLevel());
    cout << "<ASTExpressionNode";

    if (node->hasFunction()) {
        cout << " func=\"" << node->getFunctionCall()->getTokenSource() << "\">" << endl;
    } else {

        if (node->hasOperator()) {
            cout << " Op = \"" << node->getOperator()->getTokenSource() << "\">" << endl;
            ASTExpressionNode *lhs = node->getLHS();

            if (lhs != NULL) {
                lhs->accept(this);
            } else {
                printTab(node->getLevel() + 1);

                string literal = TokenInfo::getLiteralName(node->getLHSVector()[0]->getTokenType());

                cout << "<" << literal << ">";
                for (Token *token : node->getLHSVector()) {
                    cout << token->getTokenSource();
                }
                cout << "</" << literal << ">" << endl;
            }
            //node->print();

            ASTExpressionNode *rhs = node->getRHS();

            if (rhs != NULL) {
                rhs->accept(this);
            } else {
                printTab(node->getLevel() + 1);

                string literal = TokenInfo::getLiteralName(node->getRHSVector()[0]->getTokenType());

                cout << "<" << literal << ">";
                for (Token *token : node->getRHSVector()) {
                    cout << token->getTokenSource();
                }
                cout << "</" << literal << ">" << endl;
            }
        } else {

            cout << ">" << endl;

            string literal = TokenInfo::getLiteralName(node->getOperand()[0]->getTokenType());

            printTab(node->getLevel() + 1);
            cout << "<" << literal << ">";
            for (Token *token : node->getOperand()) {
                cout << token->getTokenSource();
            }
            cout << "</" << literal << ">" << endl;
        }
    }



    printTab(node->getLevel());
    cout << "</ASTExpressionNode>" << endl;
}

void XMLPrint::visit(ASTFunctionDeclNode * node) {
    printTab(node->getLevel());
    cout << "<ASTFunctionDeclNode>" << endl;

    printTab(node->getLevel() + 1);
    cout << "<Identifier>" << node->getName() << "</Identifier>" << endl;

    printTab(node->getLevel() + 1);
    cout << "<FunctionType>" << node->getFunctionType() << "</FunctionType>" << endl;

    printTab(node->getLevel() + 1);
    cout << "<FormalParameters>" << endl;

    for (ASTFunctionParameter * parameter : node->getParameters()) {
        parameter->accept(this);
    }

    printTab(node->getLevel() + 1);
    cout << "</FormalParameters>" << endl;

    node->getBlock()->accept(this);

    printTab(node->getLevel());
    cout << "</ASTFunctionDeclNode>" << endl;
}

void XMLPrint::visit(ASTFunctionParameter * node) {
    printTab(node->getLevel());
    cout << "<ASTFunctionParameter>" << endl;

    printTab(node->getLevel() + 1);
    cout << "<Identifier>" << node->getName() << "</Identifier>" << endl;

    printTab(node->getLevel() + 1);
    cout << "<Type>" << node->getType() << "</Type>" << endl;

    printTab(node->getLevel());
    cout << "</ASTFunctionParameter>" << endl;
}

void XMLPrint::visit(ASTIfStatementNode * node) {
    printTab(node->getLevel());
    cout << "<ASTIfStatementNode>" << endl;

    node->getExpression()->accept(this);

    node->getBlock()->accept(this);

    printTab(node->getLevel());
    cout << "</ASTIfStatementNode>" << endl;
}

void XMLPrint::visit(ASTReturnStatementNode * node) {
    printTab(node->getLevel());
    cout << "<ASTReturnStatementNode>" << endl;

    node->getExpression()->accept(this);

    printTab(node->getLevel());
    cout << "</ASTReturnStatementNode>" << endl;
}

void XMLPrint::visit(ASTStatementNode * node) {
    printTab(node->getLevel());
    cout << "<ASTStatementNode>" << endl;

    printTab(node->getLevel());
    cout << "</ASTStatementNode>" << endl;
}

void XMLPrint::visit(ASTVarDeclNode * node) {
    printTab(node->getLevel());
    cout << "<ASTVarDeclNode>" << endl;

    printTab(node->getLevel() + 1);
    cout << "<Identifier>" << node->getIdentifier()->getTokenSource() << "</Identifier>" << endl;

    printTab(node->getLevel() + 1);
    cout << "<Type>" << node->getVarType()->getTokenSource() << "</Type>" << endl;

    node->getExpression()->accept(this);

    printTab(node->getLevel());
    cout << "</ASTVarDeclNode>" << endl;
}

void XMLPrint::visit(ASTWhileStatementNode * node) {
    printTab(node->getLevel());
    cout << "<ASTWhileStatementNode>" << endl;

    node->getExpression()->accept(this);

    node->getBlock()->accept(this);

    printTab(node->getLevel());
    cout << "</ASTWhileStatementNode>" << endl;
}

void XMLPrint::visit(ASTWriteStatementNode * node) {
    printTab(node->getLevel());
    cout << "<ASTWriteStatementNode>" << endl;

    node->getExpression()->accept(this);

    printTab(node->getLevel());
    cout << "</ASTWriteStatementNode>" << endl;
}

void XMLPrint::printTab(int iterations) {
    for (int i = 0; i < iterations; i ++) {
        cout << "\t";
    }
}
