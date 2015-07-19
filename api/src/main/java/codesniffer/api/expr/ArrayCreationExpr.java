/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2015 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JavaParser.  If not, see <http://www.gnu.org/licenses/>.
 */

package codesniffer.api.expr;


import codesniffer.api.type.*;
import codesniffer.api.visitor.*;

import java.util.*;

/**
 * @author Julio Vilmar Gesser
 */
public final class ArrayCreationExpr extends Expression {

    private Type type;

    private int arrayCount;

    private ArrayInitializerExpr initializer;

    private List<Expression> dimensions;

    private List<List<AnnotationExpr>> arraysAnnotations;

    public ArrayCreationExpr() {
    }

    public ArrayCreationExpr(Type type, int arrayCount, ArrayInitializerExpr initializer) {
        setType(type);
        setArrayCount(arrayCount);
        setInitializer(initializer);
        setDimensions(null);
    }

    public ArrayCreationExpr(int beginLine, int beginColumn, int endLine, int endColumn, Type type, int arrayCount, ArrayInitializerExpr initializer) {
        super(beginLine, beginColumn, endLine, endColumn);
        setType(type);
        setArrayCount(arrayCount);
        setInitializer(initializer);
        setDimensions(null);
    }

    public ArrayCreationExpr(Type type, List<Expression> dimensions, int arrayCount) {
        setType(type);
        setArrayCount(arrayCount);
        setDimensions(dimensions);
        setInitializer(null);
    }

    public ArrayCreationExpr(int beginLine, int beginColumn, int endLine, int endColumn, Type type, List<Expression> dimensions, int arrayCount) {
        super(beginLine, beginColumn, endLine, endColumn);
        setType(type);
        setArrayCount(arrayCount);
        setDimensions(dimensions);
        setInitializer(null);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    public int getArrayCount() {
        return arrayCount;
    }

    public List<Expression> getDimensions() {
        return dimensions;
    }

    public ArrayInitializerExpr getInitializer() {
        return initializer;
    }

    public Type getType() {
        return type;
    }

    public void setArrayCount(int arrayCount) {
        this.arrayCount = arrayCount;
    }

    public void setDimensions(List<Expression> dimensions) {
        this.dimensions = dimensions;
		setAsParentNodeOf(this.dimensions);
    }

    public void setInitializer(ArrayInitializerExpr initializer) {
        this.initializer = initializer;
		setAsParentNodeOf(this.initializer);
    }

    public void setType(Type type) {
        this.type = type;
		setAsParentNodeOf(this.type);
    }

    public List<List<AnnotationExpr>> getArraysAnnotations() {
        return arraysAnnotations;
    }

    public void setArraysAnnotations(
            List<List<AnnotationExpr>> arraysAnnotations) {
        this.arraysAnnotations = arraysAnnotations;
    }
}
