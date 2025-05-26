package com.nish.lox;

import java.util.ArrayList;
import java.util.List;

import com.nish.lox.Expr.Assign;
import com.nish.lox.Expr.Call;
import com.nish.lox.Expr.Get;
import com.nish.lox.Expr.Logical;
import com.nish.lox.Expr.Set;
import com.nish.lox.Expr.This;
import com.nish.lox.Expr.Variable;
import com.nish.lox.Stmt.Block;
import com.nish.lox.Stmt.Class;
import com.nish.lox.Stmt.Expression;
import com.nish.lox.Stmt.Function;
import com.nish.lox.Stmt.If;
import com.nish.lox.Stmt.Print;
import com.nish.lox.Stmt.Return;
import com.nish.lox.Stmt.Var;
import com.nish.lox.Stmt.While;

class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {

    String print(Expr expr) {
        return expr.accept(this);
    }

    String print(Stmt statement) {
        return statement.accept(this);
    }

    String print(List<Stmt> statements) {
        StringBuilder builder = new StringBuilder();
        for (Stmt statement : statements) {
            builder.append(statement.accept(this)).append('\n');
        }

        return builder.toString();
    }

    String printFunction(List<Stmt.Function> statements) {
        StringBuilder builder = new StringBuilder();
        for (Stmt statement : statements) {
            builder.append(statement.accept(this)).append('\n');
        }

        return builder.toString();
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null)
            return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    private String parenthesize(String name, List<Expr> exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            if (expr == null) {
                builder.append("null");
            } else {
                builder.append(expr.accept(this));
            }
        }
        builder.append(")");

        return builder.toString();
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            if (expr == null) {
                builder.append("null");
            } else {
                builder.append(expr.accept(this));
            }
        }
        builder.append(")");

        return builder.toString();
    }

    public static void main(String[] args) { // test function
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(new Expr.Literal(45.67)));

        Expr printExpr = new Expr.Binary(new Expr.Literal(1), new Token(TokenType.PLUS, "+", null, 1),
                new Expr.Literal(68));

        List<Stmt> statements = new ArrayList<Stmt>();
        statements.add(new Stmt.Expression(expression));
        statements.add(new Stmt.Print(printExpr));

        System.out.println(new AstPrinter().print(statements));
    }

    @Override
    public String visitExpressionStmt(Expression stmt) {
        return parenthesize("ExprStmt", stmt.expression);
    }

    @Override
    public String visitPrintStmt(Print stmt) {
        return parenthesize("PrintStmt", stmt.expression);
    }

    @Override
    public String visitVarStmt(Var stmt) {
        return parenthesize("VarStmt [" + stmt.name.lexeme + "]", stmt.intializer);
    }

    @Override
    public String visitAssignExpr(Assign expr) {
        return parenthesize("AssignExpr [" + expr.name + "]", expr.value);
    }

    @Override
    public String visitVariableExpr(Variable expr) {
        return "(variable [" + expr.name + "])";
    }

    @Override
    public String visitBlockStmt(Block stmt) {
        return "(Block " + print(stmt.statements) + ")";
    }

    @Override
    public String visitIfStmt(If stmt) {
        String string = "(If condition=[" + print(stmt.condition) +
                "] then=[" + print(stmt.thenBranch) + "]";

        if (stmt.elseBranch != null) {
            string += "else=[" + print(stmt.elseBranch) + "]";
        }

        return string + ")";
    }

    @Override
    public String visitLogicalExpr(Logical expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitWhileStmt(While stmt) {
        return "(while condition=[" + print(stmt.condition) +
                "] body=[" + print(stmt.body) + "])";
    }

    @Override
    public String visitFunctionStmt(Function stmt) {
        return "(FunctionStmt name=" + stmt.name.lexeme + " param=[" + stmt.params.toString() + "] body=["
                + print(stmt.body)
                + "])";
    }

    @Override
    public String visitCallExpr(Call expr) {
        return parenthesize("call callee=[" + print(expr.callee) + "] arguments=", expr.arguments);
    }

    @Override
    public String visitReturnStmt(Return stmt) {
        return parenthesize("Return", stmt.value);
    }

    @Override
    public String visitClassStmt(Class stmt) {
        return "(Class  " + stmt.name.lexeme + printFunction(stmt.methods) + ")";
    }

    @Override
    public String visitGetExpr(Get expr) {
        return "(Get name=[" + expr.name.lexeme + "] object=[" + print(expr.Object) + "])";
    }

    @Override
    public String visitSetExpr(Set expr) {
        return "(Set name=[" + expr.name.lexeme + "] object=[" + print(expr.object) + "] value=[" + print(expr.value)
                + "])";
    }

    @Override
    public String visitThisExpr(This expr) {
        return "(This)";
    }
}
