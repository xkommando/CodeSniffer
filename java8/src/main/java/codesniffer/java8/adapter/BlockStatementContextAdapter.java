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
package codesniffer.java8.adapter;

import codesniffer.api.stmt.*;
import codesniffer.java8.*;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;

public class BlockStatementContextAdapter implements Adapter<Statement, Java8Parser.BlockStatementContext> {

    public Statement adapt(Java8Parser.BlockStatementContext context, AdapterParameters adapterParameters) {

        if (context.classOrInterfaceDeclaration() != null) {
            TypeDeclarationStmt typeDeclarationStmt = new TypeDeclarationStmt();
            AdapterUtil.setComments(typeDeclarationStmt, context.classOrInterfaceDeclaration(), adapterParameters);
            AdapterUtil.setPosition(typeDeclarationStmt, context);
            typeDeclarationStmt.setTypeDeclaration(Adapters.getClassOrInterfaceDeclarationContextAdapter().adapt(context.classOrInterfaceDeclaration(), adapterParameters));
            return typeDeclarationStmt;

        } else if (context.localVariableDeclarationStatement() != null) {
            return Adapters.getLocalVariableDeclarationStatementContextAdapter().adapt(context.localVariableDeclarationStatement(), adapterParameters);

        } else if (context.statement() != null) {
            return Adapters.getStatementContextAdapter().adapt(context.statement(), adapterParameters);
        }

        System.err.println("\r\n======================================================");
        System.err.println(context.exception);
        System.err.println(context.getParent().getText());
//        System.err.println(context.getText());// empty
//        System.err.println(context.statement());// null
        System.err.println(adapterParameters.getTokens());

//        BufferedTokenStream tks = adapterParameters.getTokens();
//        System.err.println(tks.getSourceName());
//        for (Token tk : tks.getTokens()) {
//            System.err.print(tk.getText() +  "  ");
//        }

        System.err.println("\r\n======================================================");

        throw new RuntimeException("Unknown statement type");
    }
}
