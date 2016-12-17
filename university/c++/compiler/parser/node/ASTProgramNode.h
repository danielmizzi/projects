#ifndef CPS2006_ASTPROGRAMNODE_H
#define CPS2006_ASTPROGRAMNODE_H

#include <vector>

#include "NodeType.h"
#include "../../visitor/Visitor.h"
#include "../semantic/SymbolTable.h"

using namespace std;

class ASTProgramNode {

    private:
        int level = 1;
        int scope = 0;
        int entryIndex = 0;

        vector<ASTProgramNode *> nodes;

        ASTProgramNode * parent;

        SymbolTable * table;

    public:
        ASTProgramNode();

        virtual NodeType getNodeType() {
            return NodeType::TYPE_ASTProgramNode;
        }

        void addNode(ASTProgramNode * node);
        void setLevel(int level);
        void setScope(int scope);
        void setEntryIndex(int entryIndex);
        void setParent(ASTProgramNode * parent);
        void setTable(SymbolTable * table);

        vector<ASTProgramNode *> getNodes();
        int getLevel();
        int getScope();
        int getEntryIndex();
        ASTProgramNode * getParent();
        SymbolTable * getTable();

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTPROGRAMNODE_H
