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
import codesniffer.api.type.*;
import codesniffer.api.visitor.*;

import java.util.*;
/**
 * @author Julio Vilmar Gesser
 */
public final class EnumDeclaration extends TypeDeclaration implements DocumentableNode {

    private List<ClassOrInterfaceType> implementsList;

    private List<EnumConstantDeclaration> entries;

    public EnumDeclaration() {
    }

    public EnumDeclaration(int modifiers, String name) {
        super(modifiers, name);
    }

    public EnumDeclaration(int modifiers, List<AnnotationExpr> annotations, String name, List<ClassOrInterfaceType> implementsList, List<EnumConstantDeclaration> entries, List<BodyDeclaration> members) {
        super(annotations, modifiers, name, members);
        setImplements(implementsList);
        setEntries(entries);
    }

    public EnumDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, int modifiers, List<AnnotationExpr> annotations, String name, List<ClassOrInterfaceType> implementsList, List<EnumConstantDeclaration> entries, List<BodyDeclaration> members) {
        super(beginLine, beginColumn, endLine, endColumn, annotations, modifiers, name, members);
        setImplements(implementsList);
        setEntries(entries);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }


    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    public List<EnumConstantDeclaration> getEntries() {
        return entries;
    }

    public List<ClassOrInterfaceType> getImplements() {
        return implementsList;
    }

    public void setEntries(List<EnumConstantDeclaration> entries) {
        this.entries = entries;
		setAsParentNodeOf(this.entries);
    }

    public void setImplements(List<ClassOrInterfaceType> implementsList) {
        this.implementsList = implementsList;
		setAsParentNodeOf(this.implementsList);
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
