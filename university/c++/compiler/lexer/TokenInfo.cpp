#include "TokenInfo.h"

string TokenInfo::getLiteralName(TokenType type) {
    switch (type) {
        case TOK_string_literal:
            return "String";

        case TOK_int_literal:
            return "Integer";

        case TOK_boolean_literal:
            return "Boolean";

        case TOK_real_literal:
            return "Real";

        case TOK_identifier:
            return "Identifier";
    }
    return "n/a";
}

string TokenInfo::getTokenType(TokenType type) {
    switch (type) {
        case TOK_round_bracket_op:
            return "TOK_round_bracket_op";

        case TOK_round_bracket_cl:
            return "TOK_round_bracket_cl";

        case TOK_curly_bracket_op:
            return "TOK_curly_bracket_op";

        case TOK_curly_bracket_cl:
            return "TOK_curly_bracket_cl";

        case TOK_string_opener:
            return "TOK_string_opener";

        case TOK_string_closer:
            return "TOK_string_closer";

        case TOK_string_literal:
            return "TOK_string_literal";

        case TOK_arithmetic:
            return "TOK_arithmetic";

        case TOK_line_closer:
            return "TOK_line_closer";

        case TOK_var_type_bool:
            return "TOK_var_type_bool";

        case TOK_var_type_real:
            return "TOK_var_type_real";

        case TOK_var_type_int:
            return "TOK_var_type_int";

        case TOK_var_type_string:
            return "TOK_var_type_string";

        case TOK_boolean_literal:
            return "TOK_boolean_literal";

        case TOK_real_literal:
            return "TOK_real_literal";

        case TOK_int_literal:
            return "TOK_int_literal";

        case TOK_relation:
            return "TOK_relation";

        case TOK_comparator:
            return "TOK_comparator";

        case TOK_keyword:
            return "TOK_keyword";

        case TOK_unknown:
            return "TOK_unknown";

        case TOK_identifier:
            return "TOK_identifier";

        case TOK_assignment:
            return "TOK_assignment";

        case TOK_function_call:
            return "TOK_function_call";

        case TOK_comma:
            return "TOK_comma";

        case TOK_colon:
            return "TOK_colon";

        case TOK_eof:
            return "TOK_eof";
    }
    return "n/a";
}