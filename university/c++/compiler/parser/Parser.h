#ifndef CPS2006_PARSER_H
#define CPS2006_PARSER_H

#include "../lexer/Lexer.h"

#include "node/ASTProgramNode.h"

#include <iostream>

using namespace std;

class Parser {

    private:
        Lexer * lexer;

        int getHighestPrecedenceIndex(vector<Token *> expression);

        void construct(ASTExpressionNode * node);

        ASTExpressionNode * parseExpression(vector<Token *> expression, int prevLevel);

        Lexer * getLexer();

    public:
        Parser(Lexer * lexer);

        void parse(ASTProgramNode * node);

};

#endif //CPS2006_PARSER_H
