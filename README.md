# TruffleGuu 
simple AST interpreter for Guu language

Guu program is a collection of procedures. Every procedure begins with the `sub` keyword and the name of the procedure and ends with other procedure declaration (or with file end if the procedure is final). Execution starts from the `main` procedure

A procedure body is an instructions sequence. At the start of a line can be any count of spaces and tabs. Empty lines should be ignored.
Guu has a one line comment blocks with `#` symbol.

Guu has only three statements:
- `set (varname) (new value)` - set a new value to variable. 
- `call (subname)` - call a procedure (calls can be recursive).
- `print (varname)` - print a value of variable.

Variables in Guu language exist only in the global scope. So, the next program will print `2`: 

 ```
# this is `main` procedure
sub main:
    set a 1  # create `a` variable
    call foo # call procedure
    print a  # print expression

# this is procedure which is called in `main` procedure
sub foo:
    set a 2  # assign 2 as `a` variable value
 ```
