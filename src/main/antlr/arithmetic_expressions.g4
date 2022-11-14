grammar arithmetic_expressions;
statement: variable ';' | expression ';';
expression: expression '*' expression | expression '+' expression | '('expression')' | VARIABLE | INT;
variable: VARIABLE '=' expression;

INT          : ('0'..'9')+;
VARIABLE     : ( '_' | 'a'..'z' | 'A'..'Z')+ ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9')*;
SPACEREMOVER : [ \t\n]+ -> skip;