//
// Created by cps200x on 5/6/16.
//

#ifndef CPS2006_TOKENTYPE_H
#define CPS2006_TOKENTYPE_H

enum TokenType {

    TOK_round_bracket_op, TOK_round_bracket_cl, TOK_curly_bracket_op,  TOK_curly_bracket_cl, TOK_string_opener, TOK_string_closer, TOK_string_literal, TOK_arithmetic, TOK_line_closer, TOK_var_type,
    TOK_boolean_literal, TOK_relation, TOK_comparator, TOK_keyword, TOK_unknown, TOK_identifier, TOK_assignment, TOK_colon, TOK_int_literal, TOK_real_literal, TOK_comma, TOK_function_call, TOK_eof,
    TOK_var_type_real, TOK_var_type_int, TOK_var_type_bool, TOK_var_type_string
};

#endif //CPS2006_TOKENTYPE_H