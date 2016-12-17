#include <iostream>
#include <fstream>

#include "Lexer.h"
#include "TokenInfo.h"

Lexer::Lexer(string file_name) {
    this->file_name = file_name;
}

void Lexer::setTokenSet(vector<Token *> tokens) {
    this->tokens = tokens;
}

void Lexer::initialise() {
    ifstream inFile;

    inFile.open(file_name, ios::in);

    cout << "Searching for file: " << file_name << endl;

    if (!inFile.is_open() && file_name.size() > 0) {
        // error
        cout << "Error, file not found or could not be opened... loading default program..." << endl;
    } else {
        string source = "";

        if (file_name.size() > 0) {

            string line;
            while (getline(inFile, line)) {
                source.append(line);
                source.append("\n");
            }
        } else {
            source = "//3 + 2 * 5 - 1\n"
                    "var y : int = 0;\n"
                    "var y : int = 1 + 2 - 3 - funcPow(5 + 2);\n"
                    "set y = 2 * 5;\n"
                    "var x : int = 4 + 3;\n"
                    "/*\n"
                    " multi-line\n"
                    " */\n"
                    "set y = 3 + 3 * 5 - 1 + 6;\n"
                    "set y = 3 + (2 * (5 - 1)) - 4;\n"
                    "\n"
                    "write \"hey\";\n"
                    "\n"
                    "def funcPow(x : real, n : int) : real {\n"
                    "    var y : real = 5 -10;\n"
                    "    if (y > 5) {\n"
                    "        set y = 10;\n"
                    "    }\n"
                    "    return y;\n"
                    "}";
        }

        cout << "Total string: " << endl << source << endl;
        inFile.close();

        vector<Token *> tokens;

        string current = "";

        bool readingString = false;

        bool inComment = false;
        bool multi = false;

        // real, int, bool, string, true, false, and, or, ==, !=, <=, >=, not, set, if, write, return, def

        for (int i = 0; i < source.size(); i ++) {
            if (!readingString && source[i] == '/' && !inComment) {
                if ((i + 1) < source.size()) {
                    if (source[i + 1] == '/' || source[i + 1] == '*') {
                        cout << "[LEXER] Reading comment at index: " << i << endl;
                        inComment = true;
                        multi = (source[i + 1] == '*');
                        continue;
                    }
                }
            }
            if (inComment) {
                if (!multi && source[i] == '\n' || (multi && source[i] == '*' && (i + 1) < source.size() && source[i + 1] == '/')) {
                    cout << "[LEXER] No longer reading " << (multi ? "multi-line" : "single-line") << " comment at index: " << i << endl;
                    inComment = false;
                    if (multi) {
                        i++;
                    }
                }
                continue;
            }

            if (source[i] == '"') { // if we encounter a double quote
                if (readingString) {
                    tokens.push_back(new Token(current, TokenType::TOK_string_literal));
                    cout << "[LEXER] Pushed token: name '" << tokens[tokens.size() - 1]->getTokenSource() << "', type: " << TokenInfo::getTokenType(tokens[tokens.size() - 1]->getTokenType()) << endl;
                } else {
                    TokenType type = identify(current);

                    if (type != TokenType::TOK_unknown) {
                        tokens.push_back(new Token(current, type));
                    } else {
                        cout << "[LEXER] Found unknown token: " << current << endl;
                    }

                    //tokens.push_back(Token(string(1, source[i]), TokenType::TOK_string_opener));
                }
                current = "";
                readingString = !readingString;
                continue;
            }

            if (readingString) {
                current += source[i];
                continue;
            }

            if (source[i] == ' ' || source[i] == '\n') {
                continue;
            }

            // get the current source
            // match it with all keywords
            // if match, add token and reset current string
            // if no match,

            TokenType type = identify(current);
            if (type != TokenType::TOK_unknown) {
                tokens.push_back(new Token(current, type));
                current = string(1, source[i]);
            } else {
                type = identify(string(1, source[i]));
                bool skipNext = false;
                if (type != TokenType::TOK_unknown) {
                    string toAdd = string(1, source[i]);
                    if (source[i] == '<' || source[i] == '>' || source[i] == '=') {
                        if ((i + 1) < source.size()) {
                            if (source[i + 1] == '=') {
                                string newS = string(1, source[i]) + string(1, source[i + 1]);
                                type = identify(newS);
                                toAdd = newS;
                                skipNext = true;
                            }
                        }
                    }
                    if (current.size() > 0) {
                        if (isdigit(current[0])) { // check if starts with number, if starts w/ number is value, otherwise identifier
                            if (current.find(".") != string::npos) { //
                                tokens.push_back(new Token(current, TokenType::TOK_real_literal));
                                cout << "[LEXER] Pushed token: name '" << tokens[tokens.size() - 1]->getTokenSource() << "', type: " << TokenInfo::getTokenType(tokens[tokens.size() - 1]->getTokenType()) << endl;

                            } else {
                                tokens.push_back(new Token(current, TokenType::TOK_int_literal));
                                cout << "[LEXER] Pushed token: name '" << tokens[tokens.size() - 1]->getTokenSource() << "', type: " << TokenInfo::getTokenType(tokens[tokens.size() - 1]->getTokenType()) << endl;
                            }
                        } else {
                            tokens.push_back(new Token(current, TokenType::TOK_identifier));
                            cout << "[LEXER] Pushed token: name '" << tokens[tokens.size() - 1]->getTokenSource() << "', type: " << TokenInfo::getTokenType(tokens[tokens.size() - 1]->getTokenType()) << endl;
                        }
                    }
                    tokens.push_back(new Token(toAdd, type));
                    cout << "[LEXER] Pushed token: name '" << tokens[tokens.size() - 1]->getTokenSource() << "', type: " << TokenInfo::getTokenType(tokens[tokens.size() - 1]->getTokenType()) << endl;
                    current = "";
                    if (skipNext) {
                        i++;
                    }
                } else {
                    current += source[i];
                }
            }
        }
        tokens.push_back(new Token("", TokenType::TOK_eof));
        cout << "[LEXER] Pushed token: name '" << tokens[tokens.size() - 1]->getTokenSource() << "', type: " << TokenInfo::getTokenType(tokens[tokens.size() - 1]->getTokenType()) << endl;

        setTokenSet(tokens);
    }
}

TokenType Lexer::identify(string source) {
    if (source == "{") {
        return TokenType::TOK_curly_bracket_op;
    } else if (source == "}") {
        return TokenType::TOK_curly_bracket_cl;
    } else if (source == "(") {
        return TokenType::TOK_round_bracket_op;
    } else if (source == ")") {
        return TokenType::TOK_round_bracket_cl;
    } else if (source == ";") {
        return TokenType::TOK_line_closer;
    } else if (source == "-" || source == "+" || source == "/" || source == "*") {
        return TokenType::TOK_arithmetic;
    } else if (source == "real") {
        return TokenType::TOK_var_type_real;
    } else if (source == "int") {
        return TokenType::TOK_var_type_int;
    } else if (source == "bool") {
        return TokenType::TOK_var_type_bool;
    } else if (source == "string") {
        return TokenType::TOK_var_type_string;
    } else if (source == "real" || source == "int" || source == "bool" || source == "string") {
        return TokenType::TOK_var_type;
    } else if (source == "true" || source == "false") {
        return TokenType::TOK_boolean_literal;
    } else if (source == "and" || source == "or" || source == "not") {
        return TokenType::TOK_relation;
    } else if (source == "==" || source == "!=" || source == "<=" || source == ">=" || source == "<" || source == ">") {
        return TokenType::TOK_comparator;
    } else if (source == "set" || source == "if" || source == "write" || source == "return" || source == "def" || source == "var" || source == "else") {
        return TokenType::TOK_keyword;
    } else if (source == ";") {
        return TokenType::TOK_line_closer;
    } else if (source == "=") {
        return TokenType::TOK_assignment;
    } else if (source == ":") {
        return TokenType::TOK_colon;
    } else if (source == ",") {
        return TokenType::TOK_comma;
    }
    return TokenType::TOK_unknown;
}

Token * Lexer::getNextToken() {
    if (pivot < tokens.size()) {
        pivot++;
        return tokens[pivot - 1];
    }
    return tokens[tokens.size() - 1];
}

Token * Lexer::getNextToken(int skip) {
    for (int i = 0; i < skip; i ++) {
        getNextToken();
    }
    return getNextToken();
}