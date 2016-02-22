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

import codesniffer.api.*;
import codesniffer.java8.adapter.*;
import org.antlr.v4.runtime.*;

/**
 *
 */
public class CompilationUnitListener extends Java8ParserBaseListener {

    private CompilationUnit compilationUnit;
    private BufferedTokenStream tokens;

    public CompilationUnitListener(BufferedTokenStream tokens) {
        this.tokens = tokens;
    }

    @Override
    public void exitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
        AdapterParameters adapterParameters = new AdapterParameters();
        adapterParameters.setTokens(tokens);
        compilationUnit = Adapters.getCompilationUnitContextAdapter().adapt(ctx, adapterParameters);
        //for (Integer claimedCommentTokenIndex : adapterParameters.getCommentTokensClaimed()) {
            //System.out.println("Claimed Token Index: " + claimedCommentTokenIndex + " ================\n" + tokens.get(claimedCommentTokenIndex).getText());
        //}
    }
    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }
}
