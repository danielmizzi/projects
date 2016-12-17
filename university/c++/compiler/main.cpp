#include <iostream>
#include <string>
#include <vector>

#include "llvm/ADT/STLExtras.h"
#include "llvm/IR/IRBuilder.h"
#include "llvm/IR/LLVMContext.h"
#include "llvm/IR/Module.h"
#include "llvm/IR/Verifier.h"

#include "lexer/Lexer.h"
#include "lexer/Token.h"

#include "parser/Parser.h"

#include "visitor/XMLPrint.h"
#include "visitor/SemanticAnalyser.h"

#include <cctype>


using namespace std;

int main() {
    string path = "";

    cout << "Enter full path of program (including file name with extension), or simply press enter to load default program..." << endl;

    getline(cin, path);

    if (path.size() == 0) {
        cout << "Loading default program..." << endl;
    }

    Lexer * lexer = new Lexer(path.size() == 0 ? "" : path);
    lexer->initialise();

    ASTProgramNode * root = new ASTProgramNode();

    SymbolTable * table = new SymbolTable();
    root->setTable(table);
    root->setParent(root);

    Parser * parser = new Parser(lexer);
    parser->parse(root);

    XMLPrint * xmlPrint = new XMLPrint();
    root->accept(xmlPrint);

    SemanticAnalyser * semanticAnalyser = new SemanticAnalyser();
    root->accept(semanticAnalyser);

    llvm::Module * module = new llvm::Module("MiniLangCompiler", llvm::getGlobalContext());

    return 0;
}