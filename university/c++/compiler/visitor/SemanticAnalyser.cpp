#include "SemanticAnalyser.h"

#include "../lexer/TokenInfo.h"

#include <iostream>

using namespace std;

SemanticAnalyser::SemanticAnalyser() { }

void SemanticAnalyser::visit(ASTProgramNode * node) {
    for (ASTProgramNode * n : node->getNodes()) {
        n->accept(this);
    }
}

// check if variable is already defined
// type check of lhs
void SemanticAnalyser::visit(ASTVarDeclNode * node) {
    Token * identifier = node->getIdentifier();
    Token * varType = node->getVarType();

    cout << "[SEMANTIC ANALYSER] Analysing variable declaration node; identifier: " << identifier->getTokenSource() << ", type: " << TokenInfo::getTokenType(varType->getTokenType()) << endl;

    TokenType declared = isDeclared(identifier);

    if (declared == TOK_unknown) {
        cout << "\t [SUCCESS] variable has not been previously declared..." << endl;
    } else {
        cout << "\t [ERROR] variable has already been declared..." << endl;
    }
}

// check that variable is declared
// check that RHS is of correct type
void SemanticAnalyser::visit(ASTAssignmentNode * node) {
    cout << "Node: " << node->getIdentifier()->getTokenSource() << " index: " << node->getEntryIndex() << endl;

    Token * identifier = node->getIdentifier();

    cout << "Type of node: " << TokenInfo::getTokenType(identifier->getTokenType()) << endl;

    // check that variable is declared
    // check type with right hand side

    int entryIndex = node->getEntryIndex();

    TokenType declared = isDeclared(identifier);

    cout << "[SEMANTIC ANALYSER] Analysing variable assignment node; identifier: " << identifier->getTokenSource() << endl;

    if (declared == TokenType::TOK_unknown) {
        cout << "\t [ERROR] variable not been declared..." << endl;
    } else {
        cout << "\t [SUCCESS] variable has been previously declared..." << endl;
    }

    bool typeMatches = typeCheck(declared, node->getExpression());

    if (typeMatches) {
        cout << "Type matches!" << endl;
    } else {
        cout << "Type mismatch! ERROR" << endl;
    }
}

// check if function has already been declared in current scope
void SemanticAnalyser::visit(ASTFunctionDeclNode * node) { }

// no visit
void SemanticAnalyser::visit(ASTFunctionParameter * node) { }

// check expression
void SemanticAnalyser::visit(ASTIfStatementNode * node) { }

// check that return type matches the function type
void SemanticAnalyser::visit(ASTReturnStatementNode * node) {

}

void SemanticAnalyser::visit(ASTWhileStatementNode * node) { }

void SemanticAnalyser::visit(ASTWriteStatementNode * node) { }

void SemanticAnalyser::visit(ASTBlockNode * node) { }

void SemanticAnalyser::visit(ASTStatementNode * node) { }

void SemanticAnalyser::visit(ASTExpressionNode * node) { }

bool SemanticAnalyser::typeCheck(TokenType expectedType, ASTExpressionNode * node) {
    TokenType expectedNodeType = expectedType == TOK_var_type_int ? TOK_int_literal : expectedType == TOK_var_type_bool ? TOK_boolean_literal : expectedType == TOK_var_type_real ? TOK_real_literal : TOK_string_literal;

    cout << "Checking for token type: " << TokenInfo::getTokenType(expectedNodeType) << endl;

    if (!node->hasOperator()) {
        for (Token * token : node->getOperand()) {
            cout << "Comparing with: " << TokenInfo::getTokenType(token->getTokenType()) << endl;
            if (token->getTokenType() != expectedNodeType) {
                return false;
            }
        }

    } else {

        ASTExpressionNode * lhs = node->getLHS();
        ASTExpressionNode * rhs = node->getRHS();

        if (lhs == NULL) {
            for (Token * token : node->getLHSVector()) {
                cout << "Comparing with: " << TokenInfo::getTokenType(token->getTokenType()) << " sourcE: " << token->getTokenSource() << endl;
                if (token->getTokenType() != expectedNodeType) {
                    return false;
                }
            }
        } else {
            typeCheck(expectedType, lhs);
        }

        if (rhs == NULL) {
            for (Token * token : node->getRHSVector()) {
                cout << "Comparing with: " << TokenInfo::getTokenType(token->getTokenType()) << " source: " << token->getTokenSource() << endl;
                if (token->getTokenType() != expectedNodeType) {
                    return false;
                }
            }
        } else {
            typeCheck(expectedType, rhs);
        }
    }
    return true;
}

TokenType SemanticAnalyser::isDeclared(Token * identifier) {
    SymbolTable * table = identifier->getTable();

    while (table != NULL) {
        vector<Token *> entries = table->getEntries();
        int entryIndex = identifier->getEntry()->getIndex();
        if (entryIndex > 0) {
            for (int i = entryIndex - 1; i >= 0; i --) {
                Token * token = entries[i];
                Entry * entry = token->getEntry();

                if (entry->getEntryType() == EntryType::VAR_DECL && token->getTokenSource() == identifier->getTokenSource()) {
                    return entry->getVarType();
                }
            }
        }
        table = table->getParent();
    }
    return TokenType::TOK_unknown;
}