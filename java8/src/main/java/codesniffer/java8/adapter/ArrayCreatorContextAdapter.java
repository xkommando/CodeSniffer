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

public class ArrayCreatorContextAdapter implements Adapter<Expression, Java8Parser.ArrayCreatorContext> {
    public Expression adapt(Java8Parser.ArrayCreatorContext context, AdapterParameters adapterParameters) {

        /*
        arrayCreator
            :   NEW createdName LBRACKET RBRACKET (LBRACKET RBRACKET)* arrayInitializer
            |   NEW createdName LBRACKET expression RBRACKET (LBRACKET expression RBRACKET)* (LBRACKET RBRACKET)*
            ;

        new MyClass[] {};

         */

        ArrayCreationExpr arrayCreationExpr = new ArrayCreationExpr();
        AdapterUtil.setComments(arrayCreationExpr, context, adapterParameters);
        AdapterUtil.setPosition(arrayCreationExpr, context);
        arrayCreationExpr.setType(Adapters.getCreatedNameContextAdapter().adapt(context.createdName(), adapterParameters));

        if (context.arrayInitializer() != null) {
            arrayCreationExpr.setInitializer(Adapters.getArrayInitializerContextAdapter().adapt(context.arrayInitializer(), adapterParameters));
        } else if (context.expression() != null) {
            List<Expression> expressionList = new LinkedList<Expression>();
            for (Java8Parser.ExpressionContext expressionContext : context.expression()) {
                expressionList.add(Adapters.getExpressionContextAdapter().adapt(expressionContext, adapterParameters));
            }
            arrayCreationExpr.setDimensions(expressionList);
        }

        // Subtract the brackets used as dimensions
        if (arrayCreationExpr.getDimensions() != null && arrayCreationExpr.getDimensions().size() > 0) {
            arrayCreationExpr.setArrayCount(context.LBRACKET().size() - arrayCreationExpr.getDimensions().size());
        } else {
            arrayCreationExpr.setArrayCount(context.LBRACKET().size());
        }

        return arrayCreationExpr;
    }
}
