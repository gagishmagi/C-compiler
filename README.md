# Preconditions:

### run the following code to generate all the needed java files:
### for mac and linux in terminal and windows in cmd

    $ java -cp javacc-7.0.5.jar jjtree -MULTI=true -TRACK_TOKENS=true -VISITOR=true CLang.jj

    $ java -cp javacc-7.0.5.jar javacc CLang.jj.jj

### After that all the the needed files will be generated
### run the following testers

    $ javac DummyParser.java SymbolTableVisitor.java

    $ java DymmyParser test.c

### And / Or

    $ java SymbolTableVisitor test.c

### To test the loops and return uncomment the comments from `test.c`
### For example:

    // return x;

### to 

    return x;

### And re-run

    $ java SymbolTableVisitor test.c

### OR

    $ java DymmyParser test.c
