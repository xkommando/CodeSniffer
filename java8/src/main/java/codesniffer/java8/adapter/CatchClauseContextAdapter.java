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

import codesniffer.api.body.*;
import codesniffer.api.stmt.*;
import codesniffer.api.type.*;
import codesniffer.java8.*;

import java.util.*;

public class CatchClauseContextAdapter implements Adapter<CatchClause, Java8Parser.CatchClauseContext> {
    public CatchClause adapt(Java8Parser.CatchClauseContext context, AdapterParameters adapterParameters) {

        /*
        catchClause
            :   CATCH LPAREN catchFormalParameter RPAREN block
            ;

        catchFormalParameter
            :    variableModifiers type (BAR type)* Identifier (LBRACKET RBRACKET)*
            ;

        */

        CatchClause catchClause = new CatchClause();
        AdapterUtil.setComments(catchClause, context, adapterParameters);
        AdapterUtil.setPosition(catchClause, context);

        MultiTypeParameter parameter = new MultiTypeParameter();
        AdapterUtil.setComments(parameter, context.catchFormalParameter(), adapterParameters);
        AdapterUtil.setPosition(parameter, context);
        AdapterUtil.setVariableModifiers(context.catchFormalParameter().variableModifiers(), parameter, adapterParameters);

        List<Type> typeList = new LinkedList<Type>();
        for (Java8Parser.TypeContext typeContext : context.catchFormalParameter().type()) {
            typeList.add(Adapters.getTypeContextAdapter().adapt(typeContext, adapterParameters));
        }
        parameter.setTypes(typeList);

        VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId();
        variableDeclaratorId.setName(context.catchFormalParameter().Identifier().getText());
        parameter.setId(variableDeclaratorId);

        // TODO: Figure out how this is handled with multi-catch
        /*
        if (context.catchFormalParameter().LBRACKET() != null && context.catchFormalParameter().LBRACKET().size() > 0) {
            ReferenceType referenceType = (ReferenceType)parameter.getType();
            referenceType.setArrayCount(referenceType.getArrayCount() + context.catchFormalParameter().LBRACKET().size());
        }
        */

        catchClause.setExcept(parameter);
        catchClause.setCatchBlock(Adapters.getBlockContextAdapter().adapt(context.block(), adapterParameters));

        return catchClause;
    }
}
