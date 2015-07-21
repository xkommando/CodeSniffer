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

import codesniffer.api.type.*;
import codesniffer.java8.*;

import java.util.*;

public class ClassOrInterfaceTypeContextAdapter implements Adapter<ClassOrInterfaceType, Java8Parser.ClassOrInterfaceTypeContext> {
    public ClassOrInterfaceType adapt(Java8Parser.ClassOrInterfaceTypeContext context, AdapterParameters adapterParameters) {

        ClassOrInterfaceType classOrInterfaceType = new ClassOrInterfaceType();
        AdapterUtil.setComments(classOrInterfaceType, context, adapterParameters);
        AdapterUtil.setPosition(classOrInterfaceType, context);


        List<Java8Parser.IdentifierTypeArgumentContext> ils = context.identifierTypeArgument();
        List<ClassOrInterfaceType> scopes = new ArrayList<>(ils.size());

        for (Java8Parser.IdentifierTypeArgumentContext identifierTypeArgumentContext : ils)  {

            ClassOrInterfaceType scope = new ClassOrInterfaceType();
            scope.setTypeArgs(Adapters.getTypeArgumentsContextAdapter().adapt(identifierTypeArgumentContext.typeArguments(), adapterParameters));
            scope.setName(identifierTypeArgumentContext.Identifier().getText());

            scopes.add(scope);
        }

        // This is a weird way of handling this, but should take care of the scope hierarchy
        classOrInterfaceType = scopes.get(scopes.size() - 1);
        ClassOrInterfaceType currentScope = classOrInterfaceType;
        for (int i = (scopes.size() - 2); i >= 0; i--) {
            ClassOrInterfaceType scope = scopes.get(i);
            currentScope.setScope(scope);
            currentScope = scope;
        }

        return classOrInterfaceType;
    }
}
