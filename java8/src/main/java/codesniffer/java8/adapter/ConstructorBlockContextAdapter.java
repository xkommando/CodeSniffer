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

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mike De Haan
 * Date: 12/16/12
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConstructorBlockContextAdapter implements Adapter<BlockStmt, Java8Parser.ConstructorBlockContext> {
    @Override
    public BlockStmt adapt(Java8Parser.ConstructorBlockContext context, AdapterParameters adapterParameters) {

        /*
        constructorBlock
            :    LBRACE explicitConstructorInvocation? blockStatement* RBRACE
            ;
         */


        BlockStmt blockStmt = new BlockStmt();
        AdapterUtil.setComments(blockStmt, context, adapterParameters);
        AdapterUtil.setPosition(blockStmt, context);

        List<Statement> statementList = new ArrayList<>(4);

        if (context.explicitConstructorInvocation() != null) {
            statementList.add(Adapters.getExplicitConstructorInvocationContextAdapter().adapt(context.explicitConstructorInvocation(), adapterParameters));
        }

        if (context.blockStatement() != null && context.blockStatement().size() > 0) {
            for (Java8Parser.BlockStatementContext blockStatementContext : context.blockStatement()) {
                statementList.add(Adapters.getBlockStatementContextAdapter().adapt(blockStatementContext, adapterParameters));
            }
        }

        blockStmt.setStmts(statementList);

        return blockStmt;
    }
}
