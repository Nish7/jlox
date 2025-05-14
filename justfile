compile:
    mkdir -p out
    javac -d out src/com/nish/lox/*.java

run FILE:
    just clean
    just compile 
    java -cp out com.nish.lox.Lox {{FILE}}

repl:
    just compile 
    java -cp out com.nish.lox.Lox 

generateAst:
    mkdir -p out
    javac -d out tool/GenerateAST.java
    java -cp out GenerateAST ./src/com/nish/lox

test NAME:
    just run src/test/{{NAME}}

clean:
    rm -rf out
