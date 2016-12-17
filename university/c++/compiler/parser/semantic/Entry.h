#ifndef CPS2006_ENTRY_H
#define CPS2006_ENTRY_H

#include <string>

#include "EntryType.h"

#include "../../lexer/TokenType.h"

class SymbolTable;

using namespace std;

class Entry {

    private:
        int index;

        string value;
        EntryType entryType;
        TokenType tokenType;

        SymbolTable * parent;

    public:
        Entry(EntryType entryType);
        Entry(string value, EntryType entryType);
        Entry(EntryType entryType, TokenType tokenType);

        string getValue();
        EntryType getEntryType();
        TokenType getVarType();

        void setIndex(int index);
        void setTable(SymbolTable * table);

        int getIndex();

        SymbolTable * getTable();

        string to_string();

};

#endif //CPS2006_ENTRY_H
