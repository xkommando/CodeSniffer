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

public class AnnotationContextAdapter implements Adapter<AnnotationExpr, Java8Parser.AnnotationContext> {
    @Override
    public AnnotationExpr adapt(Java8Parser.AnnotationContext context, AdapterParameters adapterParameters) {

        if (context.markerAnnotation() != null) {
            return Adapters.getMarkerAnnotationContextAdapter().adapt(context.markerAnnotation(), adapterParameters);
        } else if (context.normalAnnotation() != null) {
            return Adapters.getNormalAnnotationContextAdapter().adapt(context.normalAnnotation(), adapterParameters);
        } else if (context.singleElementAnnotation() != null) {
            return Adapters.getSingleElementAnnotationContextAdapter().adapt(context.singleElementAnnotation(), adapterParameters);
        }

        throw new RuntimeException("Unexpected Annotation Type");
    }
}
