#include "Entry.h"

Entry::Entry(EntryType entryType) {
    this->entryType = entryType;
}

Entry::Entry(string value, EntryType entryType) {
    this->value = value;
    this->entryType = entryType;
}

Entry::Entry(EntryType entryType, TokenType tokenType) {
    this->entryType = entryType;
    this->tokenType = tokenType;
}

string Entry::getValue() {
    return value;
}

EntryType Entry::getEntryType() {
    return entryType;
}

TokenType Entry::getVarType() {
    return tokenType;
}

void Entry::setIndex(int index) {
    this->index = index;
}

int Entry::getIndex() {
    return index;
}