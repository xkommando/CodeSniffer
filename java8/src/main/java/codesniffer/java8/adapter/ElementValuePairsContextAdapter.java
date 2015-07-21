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

import codesniffer.api.expr.*;
import codesniffer.java8.*;

import java.util.*;

public class ElementValuePairsContextAdapter implements Adapter<List<MemberValuePair>, Java8Parser.ElementValuePairsContext> {
    public List<MemberValuePair> adapt(Java8Parser.ElementValuePairsContext context, AdapterParameters adapterParameters) {

        /*
        elementValuePairs
            :   elementValuePair
                (COMMA elementValuePair
                )*
            ;
         */


        List<Java8Parser.ElementValuePairContext> els = context.elementValuePair();
        List<MemberValuePair> memberValuePairList = new ArrayList<>(els.size());
        for (Java8Parser.ElementValuePairContext elementValuePairContext : els) {
            memberValuePairList.add(Adapters.getElementValuePairContextAdapter().adapt(elementValuePairContext, adapterParameters));
        }

        return memberValuePairList;
    }
}
