#ifndef CPS2006_TOKENINFO_H
#define CPS2006_TOKENINFO_H

#include <string>

#include "TokenType.h"

using namespace std;

class TokenInfo {

    public:

        static string getLiteralName(TokenType type);
        static string getTokenType(TokenType type);

};

#endif //CPS2006_TOKENINFO_H
