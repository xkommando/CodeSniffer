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
import codesniffer.api.stmt.*;
import codesniffer.api.visitor.*;
/**
 * @author Julio Vilmar Gesser
 */
public final class InitializerDeclaration extends BodyDeclaration implements DocumentableNode {

    private boolean isStatic;

    private BlockStmt block;

    public InitializerDeclaration() {
    }

    public InitializerDeclaration(boolean isStatic, BlockStmt block) {
        super(null);
        setStatic(isStatic);
        setBlock(block);
    }

    public InitializerDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, boolean isStatic, BlockStmt block) {
        super(beginLine, beginColumn, endLine, endColumn, null);
        setStatic(isStatic);
        setBlock(block);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    public BlockStmt getBlock() {
        return block;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setBlock(BlockStmt block) {
        this.block = block;
		setAsParentNodeOf(this.block);
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
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
