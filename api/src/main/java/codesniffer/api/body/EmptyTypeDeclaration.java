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
import codesniffer.api.visitor.*;
/**
 * @author Julio Vilmar Gesser
 */
public final class EmptyTypeDeclaration extends TypeDeclaration implements DocumentableNode {

    public EmptyTypeDeclaration() {
        super(null, 0, null, null);
    }

    public EmptyTypeDeclaration(int beginLine, int beginColumn, int endLine, int endColumn) {
        super(beginLine, beginColumn, endLine, endColumn, null, 0, null, null);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

    @Override
    public void setDocComment(DocumentComment documentComment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    @Override
    public DocumentComment getDocComment() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
