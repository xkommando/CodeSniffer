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

import codesniffer.api.body.*;
import codesniffer.api.expr.*;
import codesniffer.api.stmt.*;
import codesniffer.java8.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mike De Haan
 * Date: 12/20/12
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForeachStatementContextAdapter implements Adapter<ForeachStmt, Java8Parser.ForeachStatementContext> {
    @Override
    public ForeachStmt adapt(Java8Parser.ForeachStatementContext context, AdapterParameters adapterParameters) {

        /*
            foreachStatement
                :    FOR LPAREN variableModifiers type Identifier COLON expression RPAREN statement
                ;
         */

        ForeachStmt foreachStmt = new ForeachStmt();
        AdapterUtil.setComments(foreachStmt, context, adapterParameters);
        AdapterUtil.setPosition(foreachStmt, context);

        VariableDeclarationExpr variableDeclarationExpr = new VariableDeclarationExpr();

        int modifiers = 0;
        ArrayList<AnnotationExpr> annotations = new ArrayList<>();
        if (context.variableModifiers() != null) {
            List<Java8Parser.AnnotationContext> als = context.variableModifiers().annotation();
            annotations.ensureCapacity(als.size());
            for (Java8Parser.AnnotationContext annotationContext : als) {
                AnnotationExpr annotationExpr = Adapters.getAnnotationContextAdapter().adapt(annotationContext, adapterParameters);
                annotations.add(annotationExpr);
            }

            if (context.variableModifiers().FINAL() != null) {
                modifiers |= ModifierSet.FINAL;
            }

            variableDeclarationExpr.setModifiers(modifiers);
        }

        // Set the loop variable
        variableDeclarationExpr.setAnnotations(annotations);
        variableDeclarationExpr.setType(Adapters.getTypeContextAdapter().adapt(context.type(), adapterParameters));

        List<VariableDeclarator> variableDeclaratorList = new ArrayList<>(1);
        VariableDeclarator variableDeclarator = new VariableDeclarator();
        VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId();
        variableDeclaratorId.setName(context.Identifier().getText());
        variableDeclarator.setId(variableDeclaratorId);
        variableDeclaratorList.add(variableDeclarator);
        variableDeclarationExpr.setVars(variableDeclaratorList);

        foreachStmt.setVariable(variableDeclarationExpr);

        foreachStmt.setBody(Adapters.getStatementContextAdapter().adapt(context.statement(), adapterParameters));
        foreachStmt.setIterable(Adapters.getExpressionContextAdapter().adapt(context.expression(), adapterParameters));

        return foreachStmt;
    }
}
