package com.example.calculator;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static List<Token> tokenize(String s) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');
        List<Token> tokBuf = new ArrayList<Token>();
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch(tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    tokBuf.add(new Token(tokenizer.nval));
                    break;
                case StreamTokenizer.TT_WORD:
                    tokBuf.add(new Token(tokenizer.sval));
                    break;
                default:
                    tokBuf.add(new Token(String.valueOf((char) tokenizer.ttype)));
            }
        }
        tokBuf.add(new Token("EOF"));
        return tokBuf;
    }
}