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

public class TypeBoundContextAdapter implements Adapter<List<ClassOrInterfaceType>, Java8Parser.TypeBoundContext> {
    public List<ClassOrInterfaceType> adapt(Java8Parser.TypeBoundContext context, AdapterParameters adapterParameters) {

        if (context == null) {
            return null;
        }

        List<Type> typeList = new LinkedList<Type>();
        List<ClassOrInterfaceType> classOrInterfaceTypeList = new LinkedList<ClassOrInterfaceType>();

        for (Java8Parser.TypeContext typeContext : context.type()) {
            typeList.add(Adapters.getTypeContextAdapter().adapt(typeContext, adapterParameters));
        }

        // All instances should be classOrInterfaceType
        return AdapterUtil.convertTypeList(typeList);
    }
}
