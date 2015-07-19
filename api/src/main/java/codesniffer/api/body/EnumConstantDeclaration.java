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

package codesniffer.api.body;

import codesniffer.api.*;
import codesniffer.api.comments.*;
import codesniffer.api.expr.*;
import codesniffer.api.visitor.*;

import java.util.*;
/**
 * @author Julio Vilmar Gesser
 */
public final class EnumConstantDeclaration extends BodyDeclaration implements DocumentableNode, NamedNode {

    private String name;

    private List<Expression> args;

    private List<BodyDeclaration> classBody;

    public EnumConstantDeclaration() {
    }

    public EnumConstantDeclaration(String name) {
        setName(name);
    }

    public EnumConstantDeclaration(List<AnnotationExpr> annotations, String name, List<Expression> args, List<BodyDeclaration> classBody) {
        super(annotations);
        setName(name);
        setArgs(args);
        setClassBody(classBody);
    }

    public EnumConstantDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, List<AnnotationExpr> annotations, String name, List<Expression> args, List<BodyDeclaration> classBody) {
        super(beginLine, beginColumn, endLine, endColumn, annotations);
        setName(name);
        setArgs(args);
        setClassBody(classBody);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    public List<Expression> getArgs() {
        return args;
    }

    public List<BodyDeclaration> getClassBody() {
        return classBody;
    }

    public String getName() {
        return name;
    }

    public void setArgs(List<Expression> args) {
        this.args = args;
		setAsParentNodeOf(this.args);
    }

    public void setClassBody(List<BodyDeclaration> classBody) {
        this.classBody = classBody;
		setAsParentNodeOf(this.classBody);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDocComment(DocumentComment documentComment) {
        this.documentComment = documentComment;
    }

    @Override
    public DocumentComment getDocComment() {
        return documentComment;
    }

    private DocumentComment documentComment;
}
