#ifndef CPS2006_ASTFUNCTIONPARAMETER_H
#define CPS2006_ASTFUNCTIONPARAMETER_H

#include <string>
#include "../../visitor/Visitor.h"

using namespace std;

class ASTFunctionParameter {

    private:
        string name;
        string type;

        int level;

    public:
        ASTFunctionParameter(string name, string type);

        string getName();
        string getType();

        void setLevel(int level);
        int getLevel();

        virtual void accept(Visitor * visitor) {
            visitor->visit(this);
        }
};

#endif //CPS2006_ASTFUNCTIONPARAMETER_H
