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

package codesniffer.api;

import codesniffer.api.body.*;
import codesniffer.api.comments.*;
import codesniffer.api.visitor.*;

import java.util.*;
/**
 * <p>
 * This class represents the entire compilation unit. Each java file denotes a
 * compilation unit.
 * </p>
 * The CompilationUnit is constructed following the syntax:<br>
 * <pre>
 * {@code
 * CompilationUnit ::=  ( }{@link PackageDeclaration}{@code )?
 *                      ( }{@link ImportDeclaration}{@code )*
 *                      ( }{@link TypeDeclaration}{@code )*
 * }
 * </pre>
 * @author Julio Vilmar Gesser
 */
public final class CompilationUnit extends Node {

    private PackageDeclaration pakage;

    private List<ImportDeclaration> imports;

    private List<TypeDeclaration> types;

    private List<BodyDeclaration> members;

    public CompilationUnit() {
    }

    public CompilationUnit(PackageDeclaration pakage, List<ImportDeclaration> imports, List<TypeDeclaration> types) {
        setPackage(pakage);
        setImports(imports);
        setTypes(types);
    }

    public CompilationUnit(int beginLine, int beginColumn, int endLine, int endColumn, PackageDeclaration pakage, List<ImportDeclaration> imports, List<TypeDeclaration> types) {
        super(beginLine, beginColumn, endLine, endColumn);
        setPackage(pakage);
        setImports(imports);
        setTypes(types);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    public List<BodyDeclaration> getMembers() {
        return members;
    }

    public void setMembers(List<BodyDeclaration> members) {
        this.members = members;
    }

    /**
     * Return a list containing all comments declared in this compilation unit.
     * Including javadocs, line comments and block comments of all types,
     * inner-classes and other members.<br>
     * If there is no comment, <code>null</code> is returned.
     * 
     * @return list with all comments of this compilation unit or
     *         <code>null</code>
     * @see codesniffer.api.comments.DocumentComment
     */
    public List<Comment> getComments() {
        return this.getAllContainedComments();
    }

    /**
     * Retrieves the list of imports declared in this compilation unit or
     * <code>null</code> if there is no import.
     * 
     * @return the list of imports or <code>null</code> if there is no import
     */
    public List<ImportDeclaration> getImports() {
        return imports;
    }

    /**
     * Retrieves the package declaration of this compilation unit.<br>
     * If this compilation unit has no package declaration (default package),
     * <code>null</code> is returned.
     * 
     * @return the package declaration or <code>null</code>
     */
    public PackageDeclaration getPackage() {
        return pakage;
    }

    /**
     * Return the list of types declared in this compilation unit.<br>
     * If there is no types declared, <code>null</code> is returned.
     * 
     * @return the list of types or <code>null</code> null if there is no type
     * @see AnnotationDeclaration
     * @see ClassOrInterfaceDeclaration
     * @see EmptyTypeDeclaration
     * @see EnumDeclaration
     */
    public List<TypeDeclaration> getTypes() {
        return types;
    }

    /**
     * Sets the list of comments of this compilation unit.
     * 
     * @param comments
     *            the list of comments
     */
    public void setComments(List<Comment> comments) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Sets the list of imports of this compilation unit. The list is initially
     * <code>null</code>.
     * 
     * @param imports
     *            the list of imports
     */
    public void setImports(List<ImportDeclaration> imports) {
        this.imports = imports;
		setAsParentNodeOf(this.imports);
    }

    /**
     * Sets or clear the package declarations of this compilation unit.
     * 
     * @param pakage
     *            the pakage declaration to set or <code>null</code> to default
     *            package
     */
    public void setPackage(PackageDeclaration pakage) {
        this.pakage = pakage;
		setAsParentNodeOf(this.pakage);
    }

    /**
     * Sets the list of types declared in this compilation unit.
     * 
     * @param types
     *            the lis of types
     */
    public void setTypes(List<TypeDeclaration> types) {
        this.types = types;
		setAsParentNodeOf(this.types);
    }
}
