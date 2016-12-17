#include "Parser.h"

#include "node/ASTExpressionNode.h"
#include "node/ASTBlockNode.h"
#include "node/ASTVarDeclNode.h"
#include "node/ASTAssignmentNode.h"
#include "node/ASTWriteStatementNode.h"
#include "node/ASTReturnStatementNode.h"
#include "node/ASTFunctionParameter.h"
#include "node/ASTFunctionDeclNode.h"
#include "node/ASTIfStatementNode.h"
#include "node/ASTWhileStatementNode.h"

#include "../lexer/TokenInfo.h"

Parser::Parser(Lexer * lexer) {
    this->lexer = lexer;
}

Lexer * Parser::getLexer() {
    return lexer;
}

int Parser::getHighestPrecedenceIndex(vector<Token *> expression) {
    int size = expression.size();

    int opening = 0;

    int count = 0;

    int highestPrecedence = 4;
    int highestPrecedenceIndex = -1;
    for (int i = 0; i < size; i ++) {
        TokenType type = expression[i]->getTokenType();
        if (type == TokenType::TOK_round_bracket_op) {
            opening++;
        } else if (type == TokenType::TOK_round_bracket_cl) {
            opening--;
        }

        string source = expression[i]->getTokenSource();
        if (opening == 0) {

            if (source == "<" || source == ">" || source == "==" || source == "!=" || source == "<=" || source == ">=") {
                count ++;
                if (highestPrecedence > 1) {
                    highestPrecedence = 1;
                    highestPrecedenceIndex = i;
                }
            } else if (source == "+" || source == "-" || source == "or") {
                count ++;
                if (highestPrecedence > 2) {
                    highestPrecedence = 2;
                    highestPrecedenceIndex = i;
                }
            } else if (source == "*" || source == "/" || source == "and") {
                count ++;
                if (highestPrecedence > 3) {
                    highestPrecedence = 3;
                    highestPrecedenceIndex = i;
                }
            }
        }
    }
    return highestPrecedenceIndex;
}

void Parser::construct(ASTExpressionNode * root) {
    vector<Token *> lhsSub = root->getLHSVector();
    vector<Token *> rhsSub = root->getRHSVector();

    int lhsIndex = getHighestPrecedenceIndex(lhsSub);
    int rhsIndex = getHighestPrecedenceIndex(rhsSub);

    if (lhsIndex == -1) { // lhs does not have any other operator
        // check for outer brackets
        if (lhsSub[0]->getTokenType() == TokenType::TOK_round_bracket_op && lhsSub[lhsSub.size() - 1]->getTokenType() == TokenType::TOK_round_bracket_cl) {
            // construct(root);

            vector<Token *> newTokenSet;

            for (int i = 1; i < lhsSub.size() - 1; i ++) {
                newTokenSet.push_back(lhsSub[i]);
            }

            root->setLHSVector(newTokenSet);

            construct(root);
        } else {
            if (lhsSub.size() > 2 && lhsSub[0]->getTokenType() == TOK_identifier && lhsSub[1]->getTokenType() == TOK_round_bracket_op) {

                ASTExpressionNode * newLHS = new ASTExpressionNode();

                newLHS->setHasFunction(true);
                newLHS->setFunctionCall(lhsSub[0]);
                newLHS->setLevel(root->getLevel() + 1);

                vector<vector<Token *>> params;
                vector<Token *> newTokenSet;

                for (int i = 2; i < lhsSub.size() - 1; i ++) {
                    if (lhsSub[i]->getTokenType() == TOK_comma) {
                        params.push_back(newTokenSet);
                        newTokenSet.clear();
                    } else {
                        newTokenSet.push_back(lhsSub[i]);
                    }
                }

                root->setLHS(newLHS);

                //construct(newLHS);
            }
        }
    } else {
        vector<Token *> lhsNewSub;
        vector<Token *> rhsNewSub;

        for (int i = 0; i < lhsIndex; i ++) {
            lhsNewSub.push_back(lhsSub[i]);
        }

        for (int i = lhsIndex + 1; i < lhsSub.size(); i ++) {
            rhsNewSub.push_back(lhsSub[i]);
        }

        ASTExpressionNode * newLHS = new ASTExpressionNode(lhsSub[lhsIndex], lhsNewSub, rhsNewSub);

        newLHS->setLevel(root->getLevel() + 1);

        root->setLHS(newLHS);

        construct(newLHS);
    }



    if (rhsIndex == -1) { // rhs does not have any other operators
        // check for outer brackets
        if (rhsSub[0]->getTokenType() == TokenType::TOK_round_bracket_op && rhsSub[rhsSub.size() - 1]->getTokenType() == TokenType::TOK_round_bracket_cl) {
            // construct(root);

            vector<Token *> newTokenSet;

            for (int i = 1; i < rhsSub.size() - 1; i ++) {
                newTokenSet.push_back(rhsSub[i]);
            }

            root->setRHSVector(newTokenSet);

            construct(root);
        } else {
            if (rhsSub.size() > 2 && rhsSub[0]->getTokenType() == TOK_identifier && rhsSub[1]->getTokenType() == TOK_round_bracket_op) {

                ASTExpressionNode * newRHS = new ASTExpressionNode();

                newRHS->setHasFunction(true);
                newRHS->setFunctionCall(rhsSub[0]);
                newRHS->setLevel(root->getLevel() + 1);

                vector<Token *> newTokenSet;

                vector<vector<Token *>> params;

                for (int i = 2; i < rhsSub.size() - 1; i ++) {
                    if (rhsSub[i]->getTokenType() == TOK_comma) {
                        params.push_back(newTokenSet);
                        newTokenSet.clear();
                    } else {
                        newTokenSet.push_back(rhsSub[i]);
                    }
                }

                root->setRHS(newRHS);

                //construct(newRHS);
            }
        }


    } else {
        vector<Token *> lhsNewSub;
        vector<Token *> rhsNewSub;

        for (int i = 0; i < rhsIndex; i ++) {
            lhsNewSub.push_back(rhsSub[i]);
        }

        for (int i = rhsIndex + 1; i < rhsSub.size(); i ++) {
            rhsNewSub.push_back(rhsSub[i]);
        }

        ASTExpressionNode * newRHS = new ASTExpressionNode(rhsSub[rhsIndex], lhsNewSub, rhsNewSub);

        newRHS->setLevel(root->getLevel() + 1);

        root->setRHS(newRHS);

        construct(newRHS);
    }
}

ASTExpressionNode * Parser::parseExpression(vector<Token *> expression, int prevLevel) {
    int size = expression.size();

    int opening = 0;

    int highestPrecedence = 4;
    int highestPrecedenceIndex = -1;
    for (int i = 0; i < size; i ++) {
        TokenType type = expression[i]->getTokenType();
        if (type == TokenType::TOK_round_bracket_op) {
            opening++;
        } else if (type == TokenType::TOK_round_bracket_cl) {
            opening--;
        }

        string source = expression[i]->getTokenSource();
        if (opening == 0) {
            if (source == "<" || source == ">" || source == "==" || source == "!=" || source == "<=" || source == ">=") {
                if (highestPrecedence > 1) {
                    highestPrecedence = 1;
                    highestPrecedenceIndex = i;
                }
            } else if (source == "+" || source == "-" || source == "or") {
                if (highestPrecedence > 2) {
                    highestPrecedence = 2;
                    highestPrecedenceIndex = i;
                }
            } else if (source == "*" || source == "/" || source == "and") {
                if (highestPrecedence > 3) {
                    highestPrecedence = 3;
                    highestPrecedenceIndex = i;
                }
            }
        }
    }

    if (highestPrecedence == 4) {
        ASTExpressionNode * root = new ASTExpressionNode(new Token("", TokenType::TOK_unknown), NULL, NULL);

        root->setHasOperator(false);

        if (expression.size() > 2 && expression[1]->getTokenType() == TOK_round_bracket_op && expression[expression.size() - 1]->getTokenType() == TOK_round_bracket_cl) {
            root->setHasFunction(true);
            root->setFunctionCall(expression[0]); // function call

            vector<Token *> innerExpression;
            for (int i = 2; i < expression.size() - 1; i ++) {
                innerExpression.push_back(expression[i]);
            }
            expression = innerExpression;
        } else {
            root->setOperand(expression);
        }

        root->setLevel(prevLevel + 1);
        root->setRoot(true);

        if (!root->hasFunction()) {
            return root;
        }
    }

    vector<Token *> lhsVector;

    for (int i = 0; i < highestPrecedenceIndex; i++) {
        lhsVector.push_back(expression[i]);
    }

    vector<Token *> rhsVector;

    for (int i = highestPrecedenceIndex + 1; i < size; i ++) {
        rhsVector.push_back(expression[i]);
    }

    ASTExpressionNode * root = new ASTExpressionNode(expression[highestPrecedenceIndex], lhsVector, rhsVector);

    // check outer operators count

    root->setLevel(prevLevel + 1);
    root->setRoot(true);

    construct(root);

    cout << endl;

    return root;
}

void Parser::parse(ASTProgramNode * root) {

    Token * token;

    Lexer * lexer = getLexer();

    while ((token = lexer->getNextToken())->getTokenType() != TokenType::TOK_eof) {
        TokenType type = token->getTokenType();

        string source = token->getTokenSource();

        if (source == "}" && root->getNodeType() == NodeType::TYPE_ASTBlockNode) {
            break;
        }

        if (type == TokenType::TOK_keyword) {
            if (source == "var") {
                cout << "Parsing variable declaration..." << endl;

                Token * identifier = lexer->getNextToken();

                Token * colon = lexer->getNextToken();

                if (colon->getTokenSource() != ":") {
                    cout << "Syntax error!" << endl;
                }

                Token * type = lexer->getNextToken();

                identifier->setEntry(new Entry(EntryType::VAR_DECL, type->getTokenType()));
                root->getTable()->addEntry(identifier);

                cout << "Identifier: " << identifier->getTokenSource() << endl;
                cout << "Type: " << type->getTokenSource() << endl;
                cout << "Token type: " << TokenInfo::getTokenType(type->getTokenType()) << endl;

                Token * equals = lexer->getNextToken();

                if (equals->getTokenSource() != "=") {
                    cout << "Syntax error!" << endl;
                }

                vector<Token *> expression;
                Token * nextToken;

                while ((nextToken = lexer->getNextToken())->getTokenType() != TokenType::TOK_line_closer) {
                    cout << "Expression token: " << nextToken->getTokenSource() << " of type: " <<
                    nextToken->getTokenName() << endl;
                    expression.push_back(nextToken);
                }

                ASTExpressionNode * expressionNode = parseExpression(expression, root->getLevel() + 1);

                // add identifiers in expression to symbol table

                ASTVarDeclNode * varDeclNode = new ASTVarDeclNode(expressionNode, identifier, type);
                varDeclNode->setLevel(root->getLevel() + 1);
                varDeclNode->setParent(root);

                root->addNode(varDeclNode);

                /*Entry * entry = new Entry(identifier->getTokenSource(), "test value", type->getTokenType(), EntryType::VAR_DECL);
                entry->setIndex(root->getTable()->getEntries().size());
                varDeclNode->setEntryIndex(root->getTable()->getEntries().size());

                varDeclNode->setTable(root->getTable());

                root->getTable()->addEntry(entry);*/
            } else if (source == "set") {
                cout << "Parsing variable assignment" << endl;

                Token * identifier = lexer->getNextToken();

                identifier->setEntry(new Entry(EntryType::VAR_ASS));
                root->getTable()->addEntry(identifier);

                Token * equals = lexer->getNextToken();

                if (equals->getTokenSource() != "=") {
                    cout << "Syntax error!" << endl;
                }

                vector<Token *> expression;
                Token * nextToken;

                while ((nextToken = lexer->getNextToken())->getTokenType() != TokenType::TOK_line_closer) {
                    cout << "Expression token: " << nextToken->getTokenSource() << " of type: " <<
                    nextToken->getTokenName() << endl;
                    expression.push_back(nextToken);
                }

                ASTExpressionNode * expressionNode = parseExpression(expression, root->getLevel() + 1);

                // parse expressionNode identifiers

                ASTAssignmentNode * assignmentNode = new ASTAssignmentNode(identifier, expressionNode);
                assignmentNode->setLevel(root->getLevel() + 1);
                assignmentNode->addExpression(expressionNode);
                assignmentNode->setParent(root);

                root->addNode(assignmentNode);

                /*Entry * entry = new Entry(identifier->getTokenSource(), "test value", TokenType::TOK_unknown, EntryType::VAR_ASS);
                entry->setIndex(root->getTable()->getEntries().size());
                assignmentNode->setEntryIndex(root->getTable()->getEntries().size());

                assignmentNode->setTable(root->getTable());

                root->getTable()->addEntry(entry);*/
            } else if (source == "write") {
                cout << "Parsing write statement" << endl;
                vector<Token *> expression;
                Token * nextToken;

                while ((nextToken = lexer->getNextToken())->getTokenType() != TokenType::TOK_line_closer) {
                    cout << "Expression token: " << nextToken->getTokenSource() << " of type: " <<
                    nextToken->getTokenName() << endl;
                    expression.push_back(nextToken);
                }

                ASTExpressionNode * expressionNode = parseExpression(expression, root->getLevel() + 1);

                // add identifiers to symbol table

                ASTWriteStatementNode * writeNode = new ASTWriteStatementNode(expressionNode);

                writeNode->setLevel(root->getLevel() + 1);
                writeNode->addExpression(expressionNode);
                writeNode->setParent(root);

                root->addNode(writeNode);
            } else if (source == "return") {
                cout << "Parsing return statement" << endl;
                vector<Token *> expression;
                Token * nextToken;

                while ((nextToken = lexer->getNextToken())->getTokenType() != TokenType::TOK_line_closer) {
                    cout << "Expression token: " << nextToken->getTokenSource() << " of type: " <<
                    nextToken->getTokenName() << endl;
                    expression.push_back(nextToken);
                }

                ASTExpressionNode * expressionNode = parseExpression(expression, root->getLevel() + 1);

                // add identifiers to symbol table

                ASTReturnStatementNode * returnNode = new ASTReturnStatementNode(expressionNode);

                returnNode->setLevel(root->getLevel() + 1);
                returnNode->setParent(root);

                root->addNode(returnNode);
            } else if (source == "if") {
                cout << "Parsing if statement" << endl;

                Token * opening_br = lexer->getNextToken();

                if (opening_br->getTokenType() != TokenType::TOK_round_bracket_op) {
                    cout << "Syntax error: expected opening round bracket" << endl;
                }

                vector<Token *> expression;
                Token * nextToken;

                while ((nextToken = lexer->getNextToken())->getTokenType() != TokenType::TOK_round_bracket_cl) {
                    cout << "Expression token: " << nextToken->getTokenSource() << " of type: " << nextToken->getTokenName() << endl;
                    expression.push_back(nextToken);
                }

                Token * curly_op = lexer->getNextToken();

                if (curly_op->getTokenType() != TokenType::TOK_curly_bracket_op) {
                    cout << "Syntax error: expected opening curly bracket" << endl;
                }

                ASTExpressionNode * expressionNode = parseExpression(expression, root->getLevel() + 1);

                // add identifiers to symboltable

                ASTIfStatementNode * ifStatement = new ASTIfStatementNode(expressionNode);

                ASTBlockNode * block = new ASTBlockNode(ifStatement);

                SymbolTable * table = new SymbolTable(root->getTable());
                root->getTable()->addTable(table);
                block->setTable(table);

                block->setScope(root->getScope() + 1);
                block->setLevel(root->getLevel() + 2);
                ifStatement->setLevel(root->getLevel() + 1);
                ifStatement->setParent(root);
                ifStatement->setBlock(block);

                root->addNode(ifStatement);

                block->setParent(root);

                cout << "Parsing if block" << endl;

                parse(block);

            } else if (source == "def") {
                cout << "Parsing function declaration" << endl;

                Token * identifier = lexer->getNextToken();
                Token * opening_br = lexer->getNextToken();

                if (opening_br->getTokenSource() != "(") {
                    cout << "Syntax error!" << endl;
                }

                cout << "Identifier: " << identifier->getTokenSource() << endl;

                vector<Token *> parameters;
                Token * nextToken;

                while ((nextToken = lexer->getNextToken())->getTokenType() != TokenType::TOK_round_bracket_cl) {
                    if (nextToken->getTokenType() != TokenType::TOK_comma) {
                        parameters.push_back(nextToken);
                    }
                }

                vector<ASTFunctionParameter *> functionParameters;

                for (int i = 0; i < parameters.size(); i += 3) {
                    bool error = false;
                    if (parameters[i]->getTokenType() != TokenType::TOK_identifier) {
                        cout << "Syntax error: expected identifier" << endl;
                        error = true;
                    }
                    if (parameters[i + 1]->getTokenType() != TokenType::TOK_colon) {
                        cout << "Syntax error: expected colon" << endl;
                        error = true;
                    }
                    if (parameters[i + 2]->getTokenType() != TokenType::TOK_var_type) {
                        cout << "Syntax error: expected var type" << endl;
                        error = true;
                    }
                    if (!error) {
                        cout << "Identifier: " << parameters[i]->getTokenSource() << endl;
                        cout << "Var type: " << parameters[i + 2]->getTokenSource() << endl;

                        ASTFunctionParameter * functionParam = new ASTFunctionParameter(parameters[i]->getTokenSource(), parameters[i + 2]->getTokenSource());
                        functionParam->setLevel(root->getLevel() + 3);

                        functionParameters.push_back(functionParam);
                    }
                }

                cout << "Parameters size: " << functionParameters.size() << endl;

                Token * colon = lexer->getNextToken();

                if (colon->getTokenType() != TokenType::TOK_colon) {
                    cout << "Syntax error: expected colon" << endl;
                }

                Token * functionType = lexer->getNextToken();

                if (functionType->getTokenType() != TokenType::TOK_var_type) {
                    cout << "Syntax error: expected function return type" << endl;
                } else {
                    cout << "Function return type: " << functionType->getTokenSource() << endl;
                }

                Token * op_bracket = lexer->getNextToken();

                if (op_bracket->getTokenSource() != "{") {
                    cout << "Syntax error: expected {" << endl;
                }

                ASTFunctionDeclNode * functionDeclNode = new ASTFunctionDeclNode(functionParameters, identifier->getTokenSource(), functionType->getTokenSource());

                ASTBlockNode * block = new ASTBlockNode(functionDeclNode);

                SymbolTable * table = new SymbolTable(root->getTable());
                root->getTable()->addTable(table);
                block->setTable(table);

                functionDeclNode->setLevel(root->getLevel() + 1);
                block->setLevel(root->getLevel() + 2);

                functionDeclNode->setBlock(block);
                functionDeclNode->setParent(root);

                block->setParent(root);

                root->addNode(functionDeclNode);

                cout << "Parsing def block" << endl;

                parse(block);
            } else if (source == "while") {
                cout << "Parsing while statement" << endl;

                Token * opening_br = lexer->getNextToken();

                if (opening_br->getTokenType() != TokenType::TOK_round_bracket_op) {
                    cout << "Syntax error: expected opening round bracket" << endl;
                }

                vector<Token *> expression;
                Token * nextToken;

                while ((nextToken = lexer->getNextToken())->getTokenType() != TokenType::TOK_round_bracket_cl) {
                    cout << "Expression token: " << nextToken->getTokenSource() << " of type: " << nextToken->getTokenName() << endl;
                    expression.push_back(nextToken);
                }

                Token * curly_op = lexer->getNextToken();

                if (curly_op->getTokenType() != TokenType::TOK_curly_bracket_op) {
                    cout << "Syntax error: expected opening curly bracket" << endl;
                }

                ASTExpressionNode * expressionNode = parseExpression(expression, root->getLevel() + 1);

                ASTWhileStatementNode * whileStatementNode = new ASTWhileStatementNode(expressionNode);

                ASTBlockNode * block = new ASTBlockNode(whileStatementNode);

                SymbolTable * table = new SymbolTable(root->getTable());
                root->getTable()->addTable(table);
                block->setTable(table);

                whileStatementNode->setLevel(root->getLevel() + 1);
                block->setLevel(root->getLevel() + 2);

                whileStatementNode->setParent(root);

                block->setParent(root);

                root->addNode(whileStatementNode);

                cout << "Parsing while block" << endl;

                parse(block);

            }

        }
    }
}