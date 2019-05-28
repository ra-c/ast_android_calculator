package com.example.calculator;
import java.util.List;

class Parser {
    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr parse() {
        return expression();
    }

    private Expr expression() {
        return term();
    }

    private Expr term() {
        Expr expr = factor();

        while (match("+", "-")) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor(){
        Expr expr = unary();

        while(match("*", "/")){
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr unary() {
        if (match("-", "+")) {
            Token operator = previous();
            Expr right = exponentiation();
            return new Expr.Unary(right, operator);
        }
        return exponentiation();
    }

    private Expr exponentiation() {
        Expr expr = primary();

        while (match("^")) {
            Token operator = previous();
            Expr right = primary();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr primary() throws RuntimeException {
        if (peek().isNumber()){
            advance();
            return new Expr.Literal(previous().value);
        }

        if(match("sin","cos","sqrt", "log", "ln")){
            Token function = previous();
            Expr expr = primary();
            return new Expr.Function(function, expr);
        }

        if(match("(")){
            Expr expr = expression();
            try {
                consume(")", "Error");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Expr.Grouping(expr);
        }
        throw new RuntimeException("Parsing error");

    }



    private boolean match(String... tokens) { //controlla che il token corrente sia uguale a uno degli specificati, poi avanza se true
        for (String token : tokens) {
            if (check(token)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(String token, String message) throws Exception { //come match, ma in caso sia false dà errore
        if (check(token)) return advance();

        throw new Exception(message);
    }

    private boolean check(String token) { //controlla che il tipo del token corrente sia quello specificato
        if (isAtEnd()) return false;
        return peek().type != null && peek().type.equals(token);
    }

    private Token advance() { //avanza nella lista e stampa il precedente
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type != null && peek().type.equals("EOF");
    }

    private Token peek() { //ottieni corrente
        return tokens.get(current);
    }

    private Token previous() {  //ottieni precedente
        return tokens.get(current - 1);
    }
}    