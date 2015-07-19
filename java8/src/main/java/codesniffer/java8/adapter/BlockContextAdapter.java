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
 * @author Bowen Cai
 *
 */
public class BlockContextAdapter implements Adapter<BlockStmt, Java8Parser.BlockContext> {
    public BlockStmt adapt(Java8Parser.BlockContext context, AdapterParameters adapterParameters) {
        BlockStmt blockStmt = new BlockStmt();
        AdapterUtil.setComments(blockStmt, context, adapterParameters);
        AdapterUtil.setPosition(blockStmt, context);

        List<Statement> blockStmtList = new ArrayList<>(8);
        for (Java8Parser.BlockStatementContext blockStatementContext : context.blockStatement()) {
            blockStmtList.add(Adapters.getBlockStatementContextAdapter().adapt(blockStatementContext, adapterParameters));
        }

        if (blockStmtList.size() > 0) {
            blockStmt.setStmts(blockStmtList);
        } else {
            // This is a block with no statements
            // We still need to go in and grab the comments
            AdapterUtil.setInternalComments(blockStmt, context, adapterParameters);
        }

        return blockStmt;
    }
}
