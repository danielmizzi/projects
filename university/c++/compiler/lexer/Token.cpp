//
// Created by cps200x on 5/7/16.
//

#include "Token.h"
#include "TokenInfo.h"

Token::Token() { }

Token::Token(string token_source, TokenType token_type) {
    this->token_source = token_source;
    this->token_type = token_type;
}

string Token::getTokenSource() {
    return token_source;
}

TokenType Token::getTokenType() {
    return token_type;
}

string Token::getTokenName() {
    return TokenInfo::getTokenType(token_type);
}

void Token::setEntry(Entry * entry) {
    this->entry = entry;
}

Entry * Token::getEntry() {
    return entry;
}

void Token::setTable(SymbolTable * table) {
    this->table = table;
}

SymbolTable * Token::getTable() {
    return table;
}