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

import java.util.*;

public class NormalClassDeclarationContextAdapter implements Adapter<TypeDeclaration, Java8Parser.NormalClassDeclarationContext> {
    public TypeDeclaration adapt(Java8Parser.NormalClassDeclarationContext context, AdapterParameters adapterParameters) {

        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = new ClassOrInterfaceDeclaration();
        AdapterUtil.setModifiers(context.modifiers(), classOrInterfaceDeclaration, adapterParameters);
        AdapterUtil.setComments(classOrInterfaceDeclaration, context, adapterParameters);
        AdapterUtil.setPosition(classOrInterfaceDeclaration, context);
        classOrInterfaceDeclaration.setInterface(false);
        classOrInterfaceDeclaration.setName(context.Identifier().getText());

        if (context.type() != null) {
            List<ClassOrInterfaceType> classOrInterfaceTypeList = new LinkedList<ClassOrInterfaceType>();

            // In this case, context.type() has to be a reference type since you cannot extend from a primitive
            // Though the declaration is expecting a ClassOrInterfaceType rather than a ReferenceType
            ReferenceType referenceType = (ReferenceType) Adapters.getTypeContextAdapter().adapt(context.type(), adapterParameters);

            ClassOrInterfaceType extendsClassOrInterfaceType = (ClassOrInterfaceType)referenceType.getType();
            classOrInterfaceTypeList.add(extendsClassOrInterfaceType);
            classOrInterfaceDeclaration.setExtends(classOrInterfaceTypeList);
        }

        List<Type> typeList = Adapters.getTypeListContextAdapter().adapt(context.typeList(), adapterParameters);
        classOrInterfaceDeclaration.setImplements(AdapterUtil.convertTypeList(typeList));
        classOrInterfaceDeclaration.setTypeParameters(Adapters.getTypeParametersContextAdapter().adapt(context.typeParameters(), adapterParameters));

        classOrInterfaceDeclaration.setMembers(Adapters.getClassBodyContextAdapter().adapt(context.classBody(), adapterParameters));

        return classOrInterfaceDeclaration;
    }
}
