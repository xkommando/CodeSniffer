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

public class ClassBodyDeclarationContextAdapter implements Adapter<BodyDeclaration, Java8Parser.ClassBodyDeclarationContext> {
    public BodyDeclaration adapt(Java8Parser.ClassBodyDeclarationContext context, AdapterParameters adapterParameters) {

        if (context.block() != null) {
            InitializerDeclaration initializerDeclaration = new InitializerDeclaration();
            AdapterUtil.setComments(initializerDeclaration, context, adapterParameters);
            AdapterUtil.setPosition(initializerDeclaration, context);
            initializerDeclaration.setStatic(context.STATIC() != null);
            initializerDeclaration.setBlock(Adapters.getBlockContextAdapter().adapt(context.block(), adapterParameters));
            return initializerDeclaration;
        } else if (context.memberDecl() != null) {
            return Adapters.getMemberDeclContextAdapter().adapt(context.memberDecl(), adapterParameters);
        } else if (context.SEMI() != null) {
            BodyDeclaration bodyDeclaration = new EmptyMemberDeclaration();
            AdapterUtil.setComments(bodyDeclaration, context, adapterParameters);
            AdapterUtil.setPosition(bodyDeclaration, context);
            return bodyDeclaration;
        }

        return null;
    }
}
