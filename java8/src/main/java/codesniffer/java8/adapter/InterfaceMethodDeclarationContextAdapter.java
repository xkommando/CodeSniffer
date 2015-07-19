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
import codesniffer.api.type.*;
import codesniffer.java8.*;

public class InterfaceMethodDeclarationContextAdapter implements Adapter<BodyDeclaration, Java8Parser.InterfaceMethodDeclarationContext> {
    public BodyDeclaration adapt(Java8Parser.InterfaceMethodDeclarationContext context, AdapterParameters adapterParameters) {

        /*
        interfaceMethodDeclaration
            :    modifiers (typeParameters)? (type|VOID) Identifier formalParameters (LBRACKET RBRACKET)* (THROWS qualifiedNameList)? SEMI
            ;
         */

        MethodDeclaration methodDeclaration = new MethodDeclaration();
        AdapterUtil.setModifiers(context.modifiers(), methodDeclaration, adapterParameters);
        AdapterUtil.setComments(methodDeclaration, context, adapterParameters);
        AdapterUtil.setPosition(methodDeclaration, context);
        methodDeclaration.setName(context.Identifier().getText());

        if (context.typeParameters() != null) {
            methodDeclaration.setTypeParameters(Adapters.getTypeParametersContextAdapter().adapt(context.typeParameters(), adapterParameters));
        }

        if (context.LBRACKET() != null && context.LBRACKET().size() > 0) {
            methodDeclaration.setArrayCount(context.LBRACKET().size());
        }

        Type returnType = null;
        if (context.VOID() != null) {
            returnType = new VoidType();
        } else if (context.type() != null) {
            returnType = Adapters.getTypeContextAdapter().adapt(context.type(), adapterParameters);
        }
        methodDeclaration.setType(returnType);

        methodDeclaration.setParameters(Adapters.getFormalParametersContextAdapter().adapt(context.formalParameters(), adapterParameters));

        if (context.qualifiedNameList() != null) {
            methodDeclaration.setThrows(Adapters.getQualifiedNameListContextAdapter().adapt(context.qualifiedNameList(), adapterParameters));
        }

        return methodDeclaration;
    }
}
