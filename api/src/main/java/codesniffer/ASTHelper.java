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
package codesniffer;

import codesniffer.api.*;
import codesniffer.api.body.*;
import codesniffer.api.expr.*;
import codesniffer.api.stmt.*;
import codesniffer.api.type.*;

import java.util.*;

/**
 * This class helps to construct new nodes.
 * 
 * @author Julio Vilmar Gesser
 */
public final class ASTHelper {

    public static final PrimitiveType BYTE_TYPE = new PrimitiveType(PrimitiveType.Primitive.Byte);

    public static final PrimitiveType SHORT_TYPE = new PrimitiveType(PrimitiveType.Primitive.Short);

    public static final PrimitiveType INT_TYPE = new PrimitiveType(PrimitiveType.Primitive.Int);

    public static final PrimitiveType LONG_TYPE = new PrimitiveType(PrimitiveType.Primitive.Long);

    public static final PrimitiveType FLOAT_TYPE = new PrimitiveType(PrimitiveType.Primitive.Float);

    public static final PrimitiveType DOUBLE_TYPE = new PrimitiveType(PrimitiveType.Primitive.Double);

    public static final PrimitiveType BOOLEAN_TYPE = new PrimitiveType(PrimitiveType.Primitive.Boolean);

    public static final PrimitiveType CHAR_TYPE = new PrimitiveType(PrimitiveType.Primitive.Char);

    public static final VoidType VOID_TYPE = new VoidType();

    private ASTHelper() {
        // nop
    }

    /**
     * Creates a new {@link codesniffer.api.expr.NameExpr} from a qualified name.<br>
     * The qualified name can contains "." (dot) characters.
     * 
     * @param qualifiedName
     *            qualified name
     * @return instanceof {@link codesniffer.api.expr.NameExpr}
     */
    public static NameExpr createNameExpr(String qualifiedName) {
        String[] split = qualifiedName.split("\\.");
        NameExpr ret = new NameExpr(split[0]);
        for (int i = 1; i < split.length; i++) {
            ret = new QualifiedNameExpr(ret, split[i]);
        }
        return ret;
    }

    /**
     * Creates a new {@link codesniffer.api.body.Parameter}.
     * 
     * @param type
     *            type of the parameter
     * @param name
     *            name of the parameter
     * @return instance of {@link codesniffer.api.body.Parameter}
     */
    public static Parameter createParameter(Type type, String name) {
        return new Parameter(type, new VariableDeclaratorId(name));
    }

    /**
     * Creates a {@link codesniffer.api.body.FieldDeclaration}.
     * 
     * @param modifiers
     *            modifiers
     * @param type
     *            type
     * @param variable
     *            variable declarator
     * @return instance of {@link codesniffer.api.body.FieldDeclaration}
     */
    public static FieldDeclaration createFieldDeclaration(int modifiers, Type type, VariableDeclarator variable) {
        List<VariableDeclarator> variables = new ArrayList<VariableDeclarator>(4);
        variables.add(variable);
        FieldDeclaration ret = new FieldDeclaration(modifiers, type, variables);
        return ret;
    }

    /**
     * Creates a {@link codesniffer.api.body.FieldDeclaration}.
     * 
     * @param modifiers
     *            modifiers
     * @param type
     *            type
     * @param name
     *            field name
     * @return instance of {@link codesniffer.api.body.FieldDeclaration}
     */
    public static FieldDeclaration createFieldDeclaration(int modifiers, Type type, String name) {
        VariableDeclaratorId id = new VariableDeclaratorId(name);
        VariableDeclarator variable = new VariableDeclarator(id);
        return createFieldDeclaration(modifiers, type, variable);
    }

    /**
     * Creates a {@link codesniffer.api.expr.VariableDeclarationExpr}.
     * 
     * @param type
     *            type
     * @param name
     *            name
     * @return instance of {@link codesniffer.api.expr.VariableDeclarationExpr}
     */
    public static VariableDeclarationExpr createVariableDeclarationExpr(Type type, String name) {
        List<VariableDeclarator> vars = new ArrayList<VariableDeclarator>(8);
        vars.add(new VariableDeclarator(new VariableDeclaratorId(name)));
        return new VariableDeclarationExpr(type, vars);
    }

    /**
     * Adds the given parameter to the method. The list of parameters will be
     * initialized if it is <code>null</code>.
     * 
     * @param method
     *            method
     * @param parameter
     *            parameter
     */
    public static void addParameter(MethodDeclaration method, Parameter parameter) {
        List<Parameter> parameters = method.getParameters();
        if (parameters == null) {
            parameters = new ArrayList<Parameter>(4);
            method.setParameters(parameters);
        }
        parameters.add(parameter);
    }

    /**
     * Adds the given argument to the method call. The list of arguments will be
     * initialized if it is <code>null</code>.
     * 
     * @param call
     *            method call
     * @param arg
     *            argument value
     */
    public static void addArgument(MethodCallExpr call, Expression arg) {
        List<Expression> args = call.getArgs();
        if (args == null) {
            args = new ArrayList<Expression>(4);
            call.setArgs(args);
        }
        args.add(arg);
    }

    /**
     * Adds the given type declaration to the compilation unit. The list of
     * types will be initialized if it is <code>null</code>.
     * 
     * @param cu
     *            compilation unit
     * @param type
     *            type declaration
     */
    public static void addTypeDeclaration(CompilationUnit cu, TypeDeclaration type) {
        List<TypeDeclaration> types = cu.getTypes();
        if (types == null) {
            types = new ArrayList<TypeDeclaration>(8);
            cu.setTypes(types);
        }
        types.add(type);
    }

    /**
     * Creates a new {@link codesniffer.api.type.ReferenceType} for a class or interface.
     * 
     * @param name
     *            name of the class or interface
     * @param arrayCount
     *            number os arrays or <code>0</code> if is not a array.
     * @return instanceof {@link codesniffer.api.type.ReferenceType}
     */
    public static ReferenceType createReferenceType(String name, int arrayCount) {
        return new ReferenceType(new ClassOrInterfaceType(name), arrayCount);
    }

    /**
     * Creates a new {@link codesniffer.api.type.ReferenceType} for the given primitive type.
     * 
     * @param type
     *            primitive type
     * @param arrayCount
     *            number os arrays or <code>0</code> if is not a array.
     * @return instanceof {@link codesniffer.api.type.ReferenceType}
     */
    public static ReferenceType createReferenceType(PrimitiveType type, int arrayCount) {
        return new ReferenceType(type, arrayCount);
    }

    /**
     * Adds the given statement to the specified block. The list of statements
     * will be initialized if it is <code>null</code>.
     * 
     * @param block
     * @param stmt
     */
    public static void addStmt(BlockStmt block, Statement stmt) {
        List<Statement> stmts = block.getStmts();
        if (stmts == null) {
            stmts = new ArrayList<Statement>(8);
            block.setStmts(stmts);
        }
        stmts.add(stmt);
    }

    /**
     * Adds the given expression to the specified block. The list of statements
     * will be initialized if it is <code>null</code>.
     * 
     * @param block
     * @param expr
     */
    public static void addStmt(BlockStmt block, Expression expr) {
        addStmt(block, new ExpressionStmt(expr));
    }

    /**
     * Adds the given declaration to the specified type. The list of members
     * will be initialized if it is <code>null</code>.
     * 
     * @param type
     *            type declaration
     * @param decl
     *            member declaration
     */
    public static void addMember(TypeDeclaration type, BodyDeclaration decl) {
        List<BodyDeclaration> members = type.getMembers();
        if (members == null) {
            members = new ArrayList<BodyDeclaration>(8);
            type.setMembers(members);
        }
        members.add(decl);
    }

}
