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

public class CreatorContextAdapter implements Adapter<Expression, Java8Parser.CreatorContext> {
    public Expression adapt(Java8Parser.CreatorContext context, AdapterParameters adapterParameters) {

        /*
            creator
                :   NEW nonWildcardTypeArguments classOrInterfaceType classCreatorRest
                |   NEW classOrInterfaceType classCreatorRest
                |   arrayCreator
                ;

            nonWildcardTypeArguments
                :   LT typeList GT
                ;

            classCreatorRest
                :   arguments
                    (classBody
                    )?
                ;

         */

        if (context.nonWildcardTypeArguments() != null) {
            ObjectCreationExpr objectCreationExpr = new ObjectCreationExpr();
            AdapterUtil.setComments(objectCreationExpr, context, adapterParameters);
            AdapterUtil.setPosition(objectCreationExpr, context);
            objectCreationExpr.setTypeArgs(Adapters.getTypeListContextAdapter().adapt(context.nonWildcardTypeArguments().typeList(), adapterParameters));
            objectCreationExpr.setType(Adapters.getClassOrInterfaceTypeContextAdapter().adapt(context.classOrInterfaceType(), adapterParameters));
            objectCreationExpr.setArgs(Adapters.getArgumentsContextAdapter().adapt(context.classCreatorRest().arguments(), adapterParameters));

            if (context.classCreatorRest().classBody() != null) {
                objectCreationExpr.setAnonymousClassBody(Adapters.getClassBodyContextAdapter().adapt(context.classCreatorRest().classBody(), adapterParameters));
            }

            return objectCreationExpr;
        } else if (context.classOrInterfaceType() != null) {
            ObjectCreationExpr objectCreationExpr = new ObjectCreationExpr();
            AdapterUtil.setComments(objectCreationExpr, context, adapterParameters);
            AdapterUtil.setPosition(objectCreationExpr, context);
            objectCreationExpr.setType(Adapters.getClassOrInterfaceTypeContextAdapter().adapt(context.classOrInterfaceType(), adapterParameters));
            objectCreationExpr.setArgs(Adapters.getArgumentsContextAdapter().adapt(context.classCreatorRest().arguments(), adapterParameters));

            if (context.classCreatorRest().classBody() != null) {
                objectCreationExpr.setAnonymousClassBody(Adapters.getClassBodyContextAdapter().adapt(context.classCreatorRest().classBody(), adapterParameters));
            }

            return objectCreationExpr;
        } else if (context.arrayCreator() != null) {
            return Adapters.getArrayCreatorContextAdapter().adapt(context.arrayCreator(), adapterParameters);
        }

        throw new RuntimeException("Unknown creator type");
    }
}
