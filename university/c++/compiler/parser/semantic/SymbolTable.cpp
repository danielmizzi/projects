#include "SymbolTable.h"

SymbolTable::SymbolTable() {
    this->parent = NULL;
}

SymbolTable::SymbolTable(SymbolTable * parent) {
    this->parent = parent;
}

vector<Token *> SymbolTable::getEntries() {
    return entries;
}

vector<SymbolTable *> SymbolTable::getTables() {
    return tables;
}

SymbolTable * SymbolTable::getParent() {
    return parent;
}

void SymbolTable::addEntry(Token * entry) {
    entries.push_back(entry);
    entry->setTable(this);
    entry->getEntry()->setIndex(entries.size() - 1);
}

void SymbolTable::addTable(SymbolTable * table) {
    tables.push_back(table);
}