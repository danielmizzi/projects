#ifndef CPS2006_LEXER_H
#define CPS2006_LEXER_H

#include <string>
#include <vector>

#include "Token.h"

using namespace std;

class Lexer {

    private:
        int pivot = 0;

        string file_name;
        vector<Token *> tokens;

        void setTokenSet(vector<Token *> tokens);

        TokenType identify(string source);

    public:
        Lexer(string file_name);

        void initialise();

        Token * getNextToken();
        Token * getNextToken(int skip);

};

#endif //CPS2006_LEXER_H
