#ifndef CPS2006_SYMBOLTABLE_H
#define CPS2006_SYMBOLTABLE_H

#include "Entry.h"
#include "../../lexer/Token.h"
#include "../../visitor/Visitor.h"

#include <vector>

using namespace std;

class SymbolTable {

    private:
        SymbolTable * parent;

        vector<Token *> entries;
        vector<SymbolTable *> tables;

    public:
        SymbolTable();
        SymbolTable(SymbolTable * parent);

        vector<Token *> getEntries();
        vector<SymbolTable *> getTables();

        SymbolTable * getParent();

        void addEntry(Token * entry);
        void addTable(SymbolTable * table);

};

#endif //CPS2006_SYMBOLTABLE_H
