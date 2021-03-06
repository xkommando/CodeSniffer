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

import codesniffer.api.*;
import codesniffer.java8.*;

import java.util.*;

public class TypeParametersContextAdapter implements Adapter<List<TypeParameter>, Java8Parser.TypeParametersContext> {
    public List<TypeParameter> adapt(Java8Parser.TypeParametersContext context, AdapterParameters adapterParameters) {

        if (context == null) {
            return null;
        }

        List<Java8Parser.TypeParameterContext> tls = context.typeParameter();
        List<TypeParameter> typeParameterList = new ArrayList<>(tls.size());
        for (Java8Parser.TypeParameterContext typeParameterContext : tls) {
            typeParameterList.add(Adapters.getTypeParameterContextAdapter().adapt(typeParameterContext, adapterParameters));
        }

        return typeParameterList;
    }
}
