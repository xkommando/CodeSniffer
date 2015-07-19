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

import codesniffer.api.expr.*;
import codesniffer.java8.*;

import java.util.*;

public class ElementValueArrayInitializerContextAdapter implements Adapter<ArrayInitializerExpr, Java8Parser.ElementValueArrayInitializerContext> {
    public ArrayInitializerExpr adapt(Java8Parser.ElementValueArrayInitializerContext context, AdapterParameters adapterParameters) {
        /*
            elementValueArrayInitializer
            :   LBRACE
                    (elementValue
                            (COMMA elementValue
                            )*
                    )? (COMMA)? RBRACE
            ;
        */
        ArrayInitializerExpr arrayInitializerExpr = new ArrayInitializerExpr();
        AdapterUtil.setComments(arrayInitializerExpr, context, adapterParameters);
        AdapterUtil.setPosition(arrayInitializerExpr, context);

        List<Expression> expressionList = new LinkedList<Expression>();
        for (Java8Parser.ElementValueContext elementValueContext : context.elementValue()) {
            expressionList.add(Adapters.getElementValueContextAdapter().adapt(elementValueContext, adapterParameters));
        }
        arrayInitializerExpr.setValues(expressionList);

        return arrayInitializerExpr;
    }
}
