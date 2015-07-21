/*
 * Copyright (C) 2015 Julio Vilmar Gesser and Mike DeHaan
 *
 * This file is part of antlr-java-parser.
 *
 * antlr-java-parser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * antlr-java-parser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with antlr-java-parser.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package codesniffer.java8;

import codesniffer.*;
import codesniffer.api.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;


public class JavaParser {

    private JavaParser() {
        // hide the constructor
    }

    /**
     * Parses the Java code contained in the {@link java.io.InputStream} and returns
     * a {@link codesniffer.api.CompilationUnit} that represents it.
     * @param in {@link java.io.InputStream} containing Java source code
     * @return CompilationUnit representing the Java source code
     */
    public static CompilationUnit parse(InputStream in) throws IOException, ParseException {
        return parse(in, null);
    }

    /**
     * Parses the Java code contained in the {@link java.io.InputStream} and returns
     * a {@link codesniffer.api.CompilationUnit} that represents it.
     * @param in {@link java.io.InputStream} containing Java source code
     * @param in {@link ParserConfigurator} to configure the parser before processing.
     * @return CompilationUnit representing the Java source code
     */
    public static CompilationUnit parse(InputStream in, ParserConfigurator parserConfigurator) throws IOException, ParseException {
        Java8Lexer lex = new Java8Lexer(new ANTLRInputStream(in));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        Java8Parser parser = new Java8Parser(tokens);

        // Define new cache
        PredictionContextCache cache = new PredictionContextCache();

        // Define new/clean DFA array
        DFA[] decisionToDFA = new DFA[parser.getATN().getNumberOfDecisions()];
        for (int i = 0; i < parser.getATN().getNumberOfDecisions(); i++) {
            decisionToDFA[i] = new DFA(parser.getATN().getDecisionState(i), i);
        }

        parser.setInterpreter(new ParserATNSimulator(parser, parser.getATN(), decisionToDFA, cache));

        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);

        // If a configurator was passed in, call it to allow the parser to be configured
        if (parserConfigurator != null) {
            parserConfigurator.configure(parser);
        }

        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();

        // Fills out the compilationUnit object
        CompilationUnitListener listener = new CompilationUnitListener(tokens);
        walker.walk(listener, tree);
        return listener.getCompilationUnit();
    }

    /**
     * Parses the Java code contained in a {@link java.io.File} and returns
     * a {@link codesniffer.api.CompilationUnit} that represents it.
     * @param file {@link java.io.File} containing Java source code
     * @return CompilationUnit representing the Java source code
     * @throws java.io.IOException
     */
    public static CompilationUnit parse(File file) throws IOException, ParseException {
        return parse(new FileInputStream(file));
    }

    /**
     * Parses a UTF-8 encoded string as java source.
     *
     * @param javaSource
     * @return
     * @throws java.io.IOException
     * @throws ParseException
     */
    public static CompilationUnit parse(String javaSource) throws IOException, ParseException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(javaSource.getBytes("UTF-8"));
        return parse(inputStream);
    }

    public static void printLex(InputStream in) throws Exception {
        Java8Lexer lex = new Java8Lexer(new ANTLRInputStream(in));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        tokens.fill();

        for (Token token : tokens.getTokens()) {
            System.out.println(token.getType() + " " + token.getText());
        }
    }

}
