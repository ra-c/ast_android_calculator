package com.example.calculator;

abstract class Expr {
    abstract public double evaluate();
    abstract public String print();

    static class Binary extends Expr {
        final Expr left;
        final Token operator;
        final Expr right;

        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        public double evaluate() {
            if(operator.type.equals("+")){
                return left.evaluate() + right.evaluate();
            }
            if(operator.type.equals("-")){
                return left.evaluate() - right.evaluate();
            }
            if(operator.type.equals("*")){
                return left.evaluate() * right.evaluate();
            }
            if(operator.type.equals("/")){
                return left.evaluate() / right.evaluate();
            }
            if(operator.type.equals("^")){
                return Math.pow(left.evaluate(), right.evaluate());
            }
            return 0;
        }

        @Override
        public String print() {
            return "( " + left.print() + " " + operator.type + " " + right.print() + " )";
        }
    }

    static class Unary extends Expr {
        final Expr operand;
        final Token operator;

        Unary(Expr operand, Token operator){
            this.operand = operand;
            this.operator = operator;
        }

        @Override
        public double evaluate() {
            if(operator.type.equals("-")){
                return -operand.evaluate();
            }
            if(operator.type.equals("+")){
                return operand.evaluate();
            }
            return 0;
        }

        public String print() {
            return "( " + operator.type + " " + operand.print() + " )";
        }
    }

    static class Literal extends Expr {
        final Double value;

        Literal(Double value){ this.value = value; }

        @Override
        public double evaluate() {
            return this.value;
        }

        public String print() {
            return "( " + value + " )";
        }
    }

    static class Function extends Expr {
        final Token function;
        final Expr argument;

        Function(Token function, Expr argument){ this.function = function; this.argument = argument; }

        @Override
        public double evaluate() {
            if(function.type.equals("sin")){
                return Math.sin(argument.evaluate());
            }
            if(function.type.equals("cos")){
                return Math.cos(argument.evaluate());
            }
            if(function.type.equals("sqrt")){
                return Math.sqrt(argument.evaluate());
            }
            if(function.type.equals("log")){
                return Math.log10(argument.evaluate());
            }
            if(function.type.equals("ln")){
                return Math.log(argument.evaluate());
            }
            return 0;
        }

        public String print() {
            return "( " + function.type + " " + argument.print() + " )";
        }
    }

    static class Grouping extends Expr {
        final Expr branch;

        Grouping(Expr branch) {this.branch = branch;}


        @Override
        public double evaluate() {
            return branch.evaluate();
        }

        public String print() {
            return "( [" + branch.print() + "] )";
        }
    }
}