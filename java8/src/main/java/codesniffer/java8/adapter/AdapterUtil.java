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

import codesniffer.*;
import codesniffer.api.*;
import codesniffer.api.body.*;
import codesniffer.api.comments.*;
import codesniffer.api.expr.*;
import codesniffer.api.type.*;
import codesniffer.java8.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mike De Haan
 * Date: 12/4/12
 * Time: 7:07 PM
 */
public final class AdapterUtil {

    private AdapterUtil() {
    }

    public static String dottedIdentifier(List<TerminalNode> terminalNodeList) {
        String identifier = "";

        for (int i = 0; i < terminalNodeList.size(); i++) {
            identifier += (i > 0 ? "." : "") + terminalNodeList.get(i).getText();
        }

        return identifier;
    }

    public static NameExpr qualifiedName(List<TerminalNode> terminalNodeList) {
        String qualifiedName = dottedIdentifier(terminalNodeList);
        return ASTHelper.createNameExpr(qualifiedName);
    }

    public static void setVariableModifiers(Java8Parser.VariableModifiersContext context, AdapterParameters adapterParameters) {
        List<AnnotationExpr> annotations = getAnnotations(context, adapterParameters);

        int modifiers = 0;
        if (hasModifier(context.FINAL())) {
            modifiers |= ModifierSet.FINAL;
        }
    }

    public static void setVariableModifiers(Java8Parser.VariableModifiersContext context, Parameter parameter, AdapterParameters adapterParameters) {
        List<AnnotationExpr> annotations = getAnnotations(context, adapterParameters);

        int modifiers = 0;
        if (hasModifier(context.FINAL())) {
            modifiers |= ModifierSet.FINAL;
        }

        parameter.setAnnotations(annotations);
        parameter.setModifiers(modifiers);
    }

    public static void setVariableModifiers(Java8Parser.VariableModifiersContext context, MultiTypeParameter parameter, AdapterParameters adapterParameters) {
        /*
        variableModifiers
        :   annotation* FINAL? annotation*
        ;
        */
        int modifiers = 0;
        List<AnnotationExpr> annotations = getAnnotations(context, adapterParameters);

        if (hasModifier(context.FINAL())) {
            modifiers |= ModifierSet.FINAL;
        }

        parameter.setAnnotations(annotations);
        parameter.setModifiers(modifiers);
    }

    private static List<AnnotationExpr> getAnnotations(Java8Parser.VariableModifiersContext context, AdapterParameters adapterParameters) {
        List<AnnotationExpr> annotations = new LinkedList<AnnotationExpr>();
        for (Java8Parser.AnnotationContext annotationContext : context.annotation()) {
            AnnotationExpr annotationExpr = Adapters.getAnnotationContextAdapter().adapt(annotationContext, adapterParameters);
            annotations.add(annotationExpr);
        }
        return annotations;
    }

    private static void setModifiersByType(BodyDeclaration bodyDeclaration, int modifiers) {
        if (bodyDeclaration instanceof TypeDeclaration) {
            ((TypeDeclaration)bodyDeclaration).setModifiers(modifiers);
        } else if (bodyDeclaration instanceof MethodDeclaration) {
            ((MethodDeclaration)bodyDeclaration).setModifiers(modifiers);
        } else if (bodyDeclaration instanceof ConstructorDeclaration) {
            ((ConstructorDeclaration)bodyDeclaration).setModifiers(modifiers);
        } else if (bodyDeclaration instanceof FieldDeclaration) {
            ((FieldDeclaration)bodyDeclaration).setModifiers(modifiers);
        } else if (bodyDeclaration instanceof AnnotationMemberDeclaration) {
            ((AnnotationMemberDeclaration)bodyDeclaration).setModifiers(modifiers);
        } else {
            throw new RuntimeException("Unknown type to set modifiers: " + bodyDeclaration.getClass().getName());
        }
    }

    public static void setModifiers(Java8Parser.ModifiersContext modifiersContext, BodyDeclaration typeDeclaration, AdapterParameters adapterParameters) {
        if (modifiersContext != null && modifiersContext.modifier() != null) {
            setModifiers(modifiersContext.modifier(), typeDeclaration, adapterParameters);
        }
    }

    public static void setModifiers(List<Java8Parser.ModifierContext> modifierList, BodyDeclaration typeDeclaration, AdapterParameters adapterParameters) {
        int modifiers = 0;
        List<AnnotationExpr> annotations = new LinkedList<AnnotationExpr>();
        for (Java8Parser.ModifierContext modifierContext : modifierList) {
            if (hasModifier(modifierContext.PUBLIC())) {
                modifiers |= ModifierSet.PUBLIC;
            }

            if (hasModifier(modifierContext.PROTECTED())) {
                modifiers |= ModifierSet.PROTECTED;
            }

            if (hasModifier(modifierContext.PRIVATE())) {
                modifiers |= ModifierSet.PRIVATE;
            }

            if (hasModifier(modifierContext.ABSTRACT())) {
                modifiers |= ModifierSet.ABSTRACT;
            }

            if (hasModifier(modifierContext.STATIC())) {
                modifiers |= ModifierSet.STATIC;
            }

            if (hasModifier(modifierContext.FINAL())) {
                modifiers |= ModifierSet.FINAL;
            }

            if (hasModifier(modifierContext.STRICTFP())) {
                modifiers |= ModifierSet.STRICTFP;
            }

            if (hasModifier(modifierContext.TRANSIENT())) {
                modifiers |= ModifierSet.TRANSIENT;
            }

            if (modifierContext.annotation() != null) {
                AnnotationExpr annotationExpr = Adapters.getAnnotationContextAdapter().adapt(modifierContext.annotation(), adapterParameters);
                annotations.add(annotationExpr);
            }

            // Any comments that were not trapped by an annotation should be captured here
            AdapterUtil.setComments(typeDeclaration, modifierContext, adapterParameters);
        }

        typeDeclaration.setAnnotations(annotations);

        setModifiersByType(typeDeclaration, modifiers);
    }

    private static boolean hasModifier(TerminalNode modifier) {
        return modifier != null;
    }

    public static List<ClassOrInterfaceType> convertTypeList(List<Type> typeList) {

        if (typeList == null) {
            return null;
            //throw new RuntimeException("typeList cannot be null");
        }

        List<ClassOrInterfaceType> classOrInterfaceTypeList = new LinkedList<ClassOrInterfaceType>();
        for (Type type : typeList) {

            if (type instanceof ReferenceType) {
                ReferenceType referenceType = (ReferenceType)type;
                classOrInterfaceTypeList.add((ClassOrInterfaceType)referenceType.getType());
            } else {
                classOrInterfaceTypeList.add((ClassOrInterfaceType)type);
            }
        }

        return classOrInterfaceTypeList;
    }

    /**
     *
     * @param adapter
     * @param contextList
     * @param operator
     * @param <C> Context Type
     * @return Expression
     */
    public static <C> Expression handleExpression(Adapter<Expression, C> adapter, List<C> contextList, BinaryExpr.Operator operator, AdapterParameters adapterParameters) {
        Expression expression = adapter.adapt(contextList.get(0), adapterParameters);
        AdapterUtil.setComments(expression, (ParserRuleContext) contextList.get(0), adapterParameters);

        // This expression represents more than one consecutive OR expression
        if (contextList.size() > 1) {
            BinaryExpr root = new BinaryExpr();

            root.setLeft(expression);
            root.setOperator(operator);
            BinaryExpr currentExpression = root;

            for (int i = 1; i < contextList.size(); i++) {
                Expression rightExpression = adapter.adapt(contextList.get(i), adapterParameters);
                AdapterUtil.setComments(rightExpression, (ParserRuleContext) contextList.get(i), adapterParameters);
                currentExpression.setRight(rightExpression);

                // On the last one, do not create a tail.
                if (i < contextList.size() - 1) {
                    BinaryExpr binaryExpr = new BinaryExpr();
                    binaryExpr.setLeft(currentExpression);
                    binaryExpr.setOperator(operator);
                    currentExpression = binaryExpr;
                }
            }

            return currentExpression;
        }

        return expression;
    }

    public static <T> List<T> singleElementList(T element) {
        List<T> newElementList = new LinkedList<T>();
        newElementList.add(element);
        return newElementList;
    }

    /**
     * If there are no statements within a block, we need a special method to grab any comments that
     * might exist between braces.
     *
     * @param node
     * @param parserRuleContext
     * @param adapterParameters
     */
    public static void setInternalComments(Node node, ParserRuleContext parserRuleContext, AdapterParameters adapterParameters) {
        BufferedTokenStream tokens = adapterParameters.getTokens();

        if (node == null || parserRuleContext == null || tokens == null) {
            throw new IllegalArgumentException("Parameters must not be null");
        }

        Token startToken = parserRuleContext.getStart();
        Token stopToken = parserRuleContext.getStop();

        List<Token> commentTokens;
        List<Comment> internalCommentList = new LinkedList<Comment>();

        // Checking to the right of the start token will check inside the statement
        commentTokens = tokens.getHiddenTokensToRight(startToken.getTokenIndex(), Java8Lexer.COMMENTS);
        if (commentTokens != null) {
            for (Token commentToken : commentTokens) {

                // Skip already claimed comments (prevents comment repeats)
                if (adapterParameters.isCommentTokenClaimed(commentToken.getTokenIndex())) {
                    continue;
                } else {
                    // Claim it
                    adapterParameters.claimCommentToken(commentToken.getTokenIndex());
                }

                if (commentToken.getText().startsWith("/**")) {
                    DocumentComment javadocComment = new DocumentComment(commentToken.getText());
                    internalCommentList.add(javadocComment);
                } else if (commentToken.getText().startsWith("/*")) {
                    BlockComment blockComment = new BlockComment(commentToken.getText());
                    internalCommentList.add(blockComment);
                } else if (commentToken.getText().startsWith("//")) {
                    LineComment lineComment = new LineComment(commentToken.getText());
                    internalCommentList.add(lineComment);
                }
            }
        }
        if (internalCommentList.size() > 0) {
            if (node.getOrphanComments() != null) {
                node.getOrphanComments().addAll(internalCommentList);
            } else {
                node.setOrphanComments(internalCommentList);
            }
        }
//        if (internalCommentList.size() > 0) {
//            if (node.getInternalComments() != null) {
//                node.getInternalComments().addAll(internalCommentList);
//            } else {
//                node.setInternalComments(internalCommentList);
//            }
//        }
    }

    public static void setPosition(Node node, ParserRuleContext ctx) {
        int beginLine = ctx.getStart().getLine();
        int beginColumn = ctx.getStart().getCharPositionInLine();
        int endLine = ctx.getStop().getLine();
        int endTokenLength = ctx.getStop().getStopIndex() - ctx.getStop().getStartIndex();
        int endColumn = ctx.getStop().getCharPositionInLine() + endTokenLength;

        node.setBeginLine(beginLine);
        node.setBeginColumn(beginColumn);
        node.setEndLine(endLine);
        node.setEndColumn(endColumn);
    }

    public static void setComments(Node node, ParserRuleContext parserRuleContext, AdapterParameters adapterParameters) {
        BufferedTokenStream tokens = adapterParameters.getTokens();

        if (node == null || parserRuleContext == null || tokens == null) {
            // Just return
            return;
        }

        Token startToken = parserRuleContext.getStart();
        List<Token> commentTokens;
        commentTokens = tokens.getHiddenTokensToLeft(startToken.getTokenIndex(), Java8Lexer.COMMENTS);

        if (commentTokens != null && commentTokens.size() > 0) {
            Iterator<Token> iter =  commentTokens.iterator();
            Token ct = iter.next();
            int idx = ct.getTokenIndex();
            if (adapterParameters.isCommentTokenClaimed(idx)) {
                adapterParameters.claimCommentToken(idx);

                node.setComment(extractComment(ct));

                if (iter.hasNext()) {
                    List<Comment> orf = new ArrayList<>(8);
                    while (iter.hasNext()) {
                        orf.add(extractComment(iter.next()));
                    }
                    node.setOrphanComments(orf);
                }

            }
        }

        /**
         * obsolete api. bowen 7/19
         */
//        List<Comment> beginCommentList = new LinkedList<Comment>();
//        List<Comment> endCommentList = new LinkedList<Comment>();
//        if (commentTokens != null) {
//            for (Token commentToken : commentTokens) {
//
//                // Skip already claimed comments (prevents comment repeats)
//                if (adapterParameters.isCommentTokenClaimed(commentToken.getTokenIndex())) {
//                    continue;
//                } else {
//                    // Claim it
//                    adapterParameters.claimCommentToken(commentToken.getTokenIndex());
//                }
//
//                if (commentToken.getText().trim().startsWith("/**")) {
//                    DocumentComment javadocComment = new DocumentComment(commentToken.getText());
//                    beginCommentList.add(javadocComment);
//                } else if (commentToken.getText().trim().startsWith("/*")) {
//                    BlockComment blockComment = new BlockComment(commentToken.getText());
//                    beginCommentList.add(blockComment);
//                } else if (commentToken.getText().trim().startsWith("//")) {
//                    LineComment lineComment = new LineComment(commentToken.getText());
//                    beginCommentList.add(lineComment);
//                }
//            }
//        }

//        commentTokens = tokens.getHiddenTokensToRight(stopToken.getTokenIndex(), Java8Lexer.COMMENTS);
//        if (commentTokens != null) {
//            for (Token commentToken : commentTokens) {
//
//                if (commentToken.getLine() == stopToken.getLine()) {
//
//                    // Skip already claimed comments (prevents comment repeats)
//                    if (adapterParameters.isCommentTokenClaimed(commentToken.getTokenIndex())) {
//                        continue;
//                    } else {
//                        // Claim it
//                        adapterParameters.claimCommentToken(commentToken.getTokenIndex());
//                    }
//
//                    if (commentToken.getText().trim().startsWith("/**")) {
//                        DocumentComment javadocComment = new DocumentComment(commentToken.getText());
//                        endCommentList.add(javadocComment);
//                    } else if (commentToken.getText().trim().startsWith("/*")) {
//                        BlockComment blockComment = new BlockComment(commentToken.getText());
//                        endCommentList.add(blockComment);
//                    } else if (commentToken.getText().trim().startsWith("//")) {
//                        LineComment lineComment = new LineComment(commentToken.getText());
//                        endCommentList.add(lineComment);
//                    }
//                }
//            }
//        }

//        if (beginCommentList.size() > 0) {
//            if (node.getBeginComments() != null) {
//                node.getBeginComments().addAll(beginCommentList);
//            } else {
//                node.setBeginComments(beginCommentList);
//            }
//        }
//
//        if (endCommentList.size() > 0) {
//            if (node.getEndComments() != null) {
//                node.getEndComments().addAll(endCommentList);
//            } else {
//                node.setEndComments(endCommentList);
//            }
//        }
    }

    private static Comment extractComment(Token ct) {
        String txt = ct.getText().trim();
        if (txt.startsWith("/**")) {
            return new DocumentComment(ct.getText());
        } else if (txt.startsWith("/*")) {
            return new BlockComment(ct.getText());
        } else if (txt.startsWith("//")) {
            return new LineComment(ct.getText());
        }
        throw new IllegalStateException();
    }
}
