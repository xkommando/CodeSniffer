

lexer grammar Ada05Lexer;


@lexer::header {
package codesniffer.ada05;
}


@lexer::members {
public static final int WHITESPACE = 1;
public static final int COMMENTS = 2;
}


/////////////////////////////////////////////////////
/* 	&    '    (    )    *    +    ,    -    .    /    :    ;    <    =    >   
	=>    ..    **    :=    /=    >=    <=    <<    >>    <>
*/
AMP          : '&';
TICK         : '\'';
SEMI         : ';';
COMMA        : ',';
GT           : '>';

EQ           : '=';
LT           : '<';
STAR         : '*';
COLON        : ':';
LPAREN       : '(';

RPAREN       : ')';
DOT          : '.';
SLASH        : '/';
PLUS         : '+';
SUB          : '-';

BAR          : '|';

ARROW        : '=>';
DOTDOT      : '..';
STARSTAR    : '**';
ASSIGN       : ':=';
NQ           : '/=';

LE           : '<=';
GE           : '>=';
BITSL        : '<<';
BITSR        : '>>';
BOX          : '<>';

fragment A:('a'|'A');
fragment B:('b'|'B');
fragment C:('c'|'C');
fragment D:('d'|'D');
fragment E:('e'|'E');
fragment F:('f'|'F');
fragment G:('g'|'G');
fragment H:('h'|'H');
fragment I:('i'|'I');
fragment J:('j'|'J');
fragment K:('k'|'K');
fragment L:('l'|'L');
fragment M:('m'|'M');
fragment N:('n'|'N');
fragment O:('o'|'O');
fragment P:('p'|'P');
fragment Q:('q'|'Q');
fragment R:('r'|'R');
fragment S:('s'|'S');
fragment T:('t'|'T');
fragment U:('u'|'U');
fragment V:('v'|'V');
fragment W:('w'|'W');
fragment X:('x'|'X');
fragment Y:('y'|'Y');
fragment Z:('z'|'Z');

ABORT   :  A B O R T;
ABS   :  A B S;
ABSTRACT   :  A B S T R A C T;
ACCEPT   :  A C C E P T;
ACCESS   :  A C C E S S;
ALIASED   :  A L I A S E D;
ALL   :  A L L;
AND   :  A N D;
ARRAY   :  A R R A Y;
AT   :  A T;
BEGIN   :  B E G I N;
BODY   :  B O D Y;
CASE   :  C A S E;
CONSTANT   :  C O N S T A N T;
DECLARE   :  D E C L A R E;
DELAY   :  D E L A Y;
DELTA   :  D E L T A;
DIGITS   :  D I G I T S;
DO   :  D O;
ELSE   :  E L S E;
ELSIF   :  E L S I F;
END   :  E N D;
ENTRY   :  E N T R Y;
EXCEPTION   :  E X C E P T I O N;
EXIT   :  E X I T;
FOR   :  F O R;
FUNCTION   :  F U N C T I O N;
GENERIC   :  G E N E R I C;
GOTO   :  G O T O;
IF   :  I F;
IN   :  I N;
INTERFACE   :  I N T E R F A C E;
IS   :  I S;
LIMITED   :  L I M I T E D;
LOOP   :  L O O P;
MOD   :  M O D;
NEW   :  N E W;
NOT   :  N O T;
NULL   :  N U L L;
OF   :  O F;
OR   :  O R;
OTHERS   :  O T H E R S;
OUT   :  O U T;
OVERRIDING   :  O V E R R I D I N G;
PACKAGE   :  P A C K A G E;
PRAGMA   :  P R A G M A;
PRIVATE   :  P R I V A T E;
PROCEDURE   :  P R O C E D U R E;
PROTECTED   :  P R O T E C T E D;
RAISE   :  R A I S E;
RANGE   :  R A N G E;
RECORD   :  R E C O R D;
REM   :  R E M;
RENAMES   :  R E N A M E S;
REQUEUE   :  R E Q U E U E;
RETURN   :  R E T U R N;
REVERSE   :  R E V E R S E;
SELECT   :  S E L E C T;
SEPARATE   :  S E P A R A T E;
SOME   :  S O M E;
SUBTYPE   :  S U B T Y P E;
SYNCHRONIZED   :  S Y N C H R O N I Z E D;
TAGGED   :  T A G G E D;
TASK   :  T A S K;
TERMINATE   :  T E R M I N A T E;
THEN   :  T H E N;
TYPE   :  T Y P E;
UNTIL   :  U N T I L;
WHEN   :  W H E N;
WHILE   :  W H I L E;
XOR   :  X O R;

USE   :  U S E;
WITH   :  W I T H;


/////////////////////////////////////////////// NUMBERS ///////////////////////////////////////

fragment
Sign
    :    PLUS
    |    SUB
    ;


fragment
NonZeroDigit
    :    '1'..'9'
    ;

fragment
Digit
    :    '0'
    |    NonZeroDigit
    ;

fragment
DigitOrUnderscoreOrSharp
    :    Digit
    |    '_'
    |    '#'
    ;

fragment
DigitsAndUnderscores
    :    DigitOrUnderscoreOrSharp+
    ;

fragment
Digits
    :    Digit
    |    Digit DigitsAndUnderscores? Digit
    ;

///////////////////////////////////////////////////// integer
fragment
SignedInteger
    :    Sign? Digits
    ;

fragment
Underscores
    :    '_'+
    ;

fragment
DecimalNumeral
    :    '0'
    |    NonZeroDigit Digits?
    |    NonZeroDigit Underscores Digits
    ;


fragment
ExponentPart
    :    ('e' | 'E') SignedInteger
    ;
///////////////////////////////////////////////////// binary

fragment
BinaryDigitOrUnderscore
    :    BinaryDigit
    |    '_'
    ;

fragment
BinaryDigitsAndUnderscores
    :    BinaryDigitOrUnderscore+
    ;

fragment
BinaryDigit
    :    '0'..'1'
    ;

fragment
BinaryDigits
    :    BinaryDigit
    |    BinaryDigit BinaryDigitsAndUnderscores? BinaryDigit
    ;

fragment
BinaryNumeral
    :    '0' 'b' BinaryDigits
    |    '0' 'B' BinaryDigits
    ;

IntegerLiteral
    :    DecimalNumeral
    |    BinaryNumeral
    ;

///////////////////////////////////////////////////// float

DecimalFloatingPointLiteral
    :    Digits DOT Digits? ExponentPart? 
    |    Digits ExponentPart
    ;


///////////////////////////////////////////////////// Identifier

fragment
AdaLetter                  // any Unicode character that is a Ada letter (see below)
    :    '\u0041'..'\u005a' // A-Z
    |    '\u0061'..'\u007a' // a-z
    |    '\u00c0'..'\u00d6' // À-Ö
    |    '\u00d8'..'\u00f6' // Ø-ö
    |    '\u00f8'..'\u00ff' // ø-ÿ
    |    '\u0100'..'\u1fff'
    |    '\u3040'..'\u318f'
    |    '\u3300'..'\u337f'
    |    '\u3400'..'\u3d2d'
    |    '\u4e00'..'\u9fff' // Chinese: common
    |    '\u3400'..'\u4DFF' // Chinese: rare
    |    '\u20000'..'\u2A6DF' // Chinese: rare, historic
    |    '\uf900'..'\ufaff' // Chinese: duplicates, unifiable variants, corporate characters
    |    '\u2F800'..'\u42FA1F' // Chinese: Unifiable variants
    ;

// WARN: '_' sequencial underscores!!!
//   a__b -> Identifier

fragment
AdaLetterOrDigit
    :    AdaLetter
    |    Digit
    ;

fragment
AdaLetterOrDigitOrUnderscore
    :   AdaLetterOrDigit 
    |   '_'
    ;

Identifier
    : AdaLetter AdaLetterOrDigitOrUnderscore*
    ;

/////////////////////////////////////////////////////// string


fragment
RawInputCharacter   // any Unicode character
    :    '\u0024'
    |    '\u0041'..'\u005a'
    |    '\u005f'
    |    '\u0061'..'\u007a'
    |    '\u00c0'..'\u00d6'
    |    '\u00d8'..'\u00f6'
    |    '\u00f8'..'\u00ff'
    |    '\u0100'..'\u1fff'
    |    '\u3040'..'\u318f'
    |    '\u3300'..'\u337f'
    |    '\u3400'..'\u3d2d'
    |    '\u4e00'..'\u9fff' // Chinese: common
    |    '\u3400'..'\u4DFF' // Chinese: rare
    |    '\u20000'..'\u2A6DF' // Chinese: rare, historic
    |    '\uf900'..'\ufaff' // Chinese: duplicates, unifiable variants, corporate characters
    |    '\u2F800'..'\u42FA1F' // Chinese: Unifiable variants
    ;

fragment
EscapeSequence
    :    '\\b' /* \u0008: backspace BS */
    |    '\\t' /* \u0009: horizontal tab HT */
    |    '\\n' /* \u000a: linefeed LF */
    |    '\\f' /* \u000c: form feed FF */
    |    '\\r' /* \u000d: carriage return CR */
    |    '\\"' /* \u0022: double quote " */
    |    '\\\'' /* \u0027: single quote ' */
    |    '\\\\' /* \u005c: backslash \ */
    ;

fragment
StringCharacter
    :    EscapeSequence
    |    ~('"'|'\\')
    ;

fragment
StringCharacters
    :    StringCharacter+                                                                                               // StringCharacter
    ;

fragment
SingleCharacter
    :    ~['\\]
    ;

CharacterLiteral
    :    '\'' SingleCharacter '\''                                                                                           // ' SingleCharacter '
    |    '\'' EscapeSequence '\''                                                                                            // ' EscapeSequence '
    ;

StringLiteral
    :    '"' StringCharacter*? '"'
    ;


LineTerminator
    :    ('\r\n'
    |    '\n'
    |    '\r')          ->  skip //channel(WHITESPACE)
    ;

WhiteSpace
    :    (' '
    |    '\u0009'                                        // the ASCII HT character, also known as "horizontal tab"
    |    '\u000C'                                        // the ASCII FF character, also known as "form feed"
    |    LineTerminator)  -> skip //channel(WHITESPACE)                                // LineTerminator
    ;


EndOfLineComment
    :    '--' .*? LineTerminator+ -> channel(COMMENTS)
    ;