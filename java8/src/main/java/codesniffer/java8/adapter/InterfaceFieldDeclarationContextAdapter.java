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
import codesniffer.java8.*;

import java.util.*;

public class InterfaceFieldDeclarationContextAdapter implements Adapter<BodyDeclaration, Java8Parser.InterfaceFieldDeclarationContext> {
    public BodyDeclaration adapt(Java8Parser.InterfaceFieldDeclarationContext context, AdapterParameters adapterParameters) {

        /*
        interfaceFieldDeclaration
            :   modifiers type variableDeclarator
                (COMMA variableDeclarator
                )*
                SEMI
            ;
         */

        FieldDeclaration fieldDeclaration = new FieldDeclaration();
        AdapterUtil.setModifiers(context.modifiers(), fieldDeclaration, adapterParameters);
        AdapterUtil.setComments(fieldDeclaration, context, adapterParameters);
        AdapterUtil.setPosition(fieldDeclaration, context);
        fieldDeclaration.setType(Adapters.getTypeContextAdapter().adapt(context.type(), adapterParameters));

        List<Java8Parser.VariableDeclaratorContext> vls = context.variableDeclarator();
        List<VariableDeclarator> variableDeclaratorList = new ArrayList<>(vls.size());
        for (Java8Parser.VariableDeclaratorContext variableDeclaratorContext : vls) {
            variableDeclaratorList.add(Adapters.getVariableDeclaratorContextAdapter().adapt(variableDeclaratorContext, adapterParameters));
        }
        fieldDeclaration.setVariables(variableDeclaratorList);

        return fieldDeclaration;
    }
}
