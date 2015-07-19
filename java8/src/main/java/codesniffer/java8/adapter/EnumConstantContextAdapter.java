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

public class EnumConstantContextAdapter implements Adapter<EnumConstantDeclaration, Java8Parser.EnumConstantContext> {
    public EnumConstantDeclaration adapt(Java8Parser.EnumConstantContext context, AdapterParameters adapterParameters) {

        /*
        enumConstant
            :   (annotations)? Identifier(arguments)? (classBody)?
            ;
         */

        EnumConstantDeclaration enumConstantDeclaration = new EnumConstantDeclaration();
        AdapterUtil.setComments(enumConstantDeclaration, context, adapterParameters);
        AdapterUtil.setPosition(enumConstantDeclaration, context);

        if (context.annotations() != null) {
            enumConstantDeclaration.setAnnotations(Adapters.getAnnotationsContextAdapter().adapt(context.annotations(), adapterParameters));
        }

        enumConstantDeclaration.setName(context.Identifier().getText());

        if (context.arguments() != null) {
            enumConstantDeclaration.setArgs(Adapters.getArgumentsContextAdapter().adapt(context.arguments(), adapterParameters));
        }

        if (context.classBody() != null) {
            enumConstantDeclaration.setClassBody(Adapters.getClassBodyContextAdapter().adapt(context.classBody(), adapterParameters));
        }

        return enumConstantDeclaration;
    }
}
