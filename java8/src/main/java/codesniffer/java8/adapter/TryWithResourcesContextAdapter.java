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

import codesniffer.api.stmt.*;
import codesniffer.java8.*;

public class TryWithResourcesContextAdapter implements Adapter<Statement, Java8Parser.TryWithResourcesContext> {
    public Statement adapt(Java8Parser.TryWithResourcesContext context, AdapterParameters adapterParameters) {

        /*
        tryWithResources
            :    TRY resourceSpecification block catches? (FINALLY block)?
            ;

        resourceSpecification
            :    '(' resources (';')? ')'                                                                      // ( Resources [;] )
            ;

        resources
            :    resource ( ';' resource )*                                                                // Resource { ; Resource }
            ;

        resource
            :    variableModifiers? type Identifier EQ expression                       // {VariableModifier} ReferenceType VariableDeclaratorId = Expression
            ;

         */

        TryStmt tryStmt = new TryStmt();
        AdapterUtil.setComments(tryStmt, context, adapterParameters);
        AdapterUtil.setPosition(tryStmt, context);
        tryStmt.setTryBlock(Adapters.getBlockContextAdapter().adapt(context.block(0), adapterParameters));

//        // Set resources
        if (context.resourceSpecification() != null) {
            tryStmt.setResources(Adapters.getResourcesContextAdapter().adapt(context.resourceSpecification().resources(), adapterParameters));
        }

        if (context.catches() != null) {
            tryStmt.setCatchs(Adapters.getCatchesContextAdapter().adapt(context.catches(), adapterParameters));
        }

        if (context.FINALLY() != null) {
            tryStmt.setFinallyBlock(Adapters.getBlockContextAdapter().adapt(context.block(1), adapterParameters));
        }

        return tryStmt;
    }
}
