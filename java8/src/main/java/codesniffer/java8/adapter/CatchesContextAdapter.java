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

public class CatchesContextAdapter implements Adapter<List<CatchClause>, Java8Parser.CatchesContext> {
    public List<CatchClause> adapt(Java8Parser.CatchesContext context, AdapterParameters adapterParameters) {

        List<CatchClause> catchClauseList = new LinkedList<CatchClause>();
        for (Java8Parser.CatchClauseContext catchClauseContext : context.catchClause()) {
            catchClauseList.add(Adapters.getCatchClauseContextAdapter().adapt(catchClauseContext, adapterParameters));
        }

        return catchClauseList;
    }
}
