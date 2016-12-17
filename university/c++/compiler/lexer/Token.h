#ifndef CPS2006_TOKEN_H
#define CPS2006_TOKEN_H

#include "TokenType.h"

class SymbolTable;
class Entry;

#include <string>

using namespace std;

class Token {

    private:
        string token_source;
        TokenType token_type;
    
        Entry * entry;
        SymbolTable * table;
    
    public:
        Token();
        Token(string token_source, TokenType token_type);

        void setEntry(Entry * entry);
        void setTable(SymbolTable * table);
    
        string getTokenSource();
        TokenType getTokenType();

        string getTokenName();
    
        Entry * getEntry();
        SymbolTable * getTable();

};

#endif //CPS2006_TOKEN_H
