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

/**
 * Element with a declaration representable as a String.
 *
 * @author Federico Tomassetti
 * @since July 2014
 */
public interface WithDeclaration {

    /**
     * As {@link WithDeclaration#getDeclarationAsString(boolean, boolean, boolean)} including
     * the modifiers, the throws clause and the parameters with both type and name.
     * @return String representation of declaration
     */
    String getDeclarationAsString();

    /**
     * As {@link WithDeclaration#getDeclarationAsString(boolean, boolean, boolean)} including
     * the parameters with both type and name.
     * @param includingModifiers flag to include the modifiers (if present) in the string produced
     * @param includingThrows flag to include the throws clause (if present) in the string produced
     * @return String representation of declaration based on parameter flags
     */
    String getDeclarationAsString(boolean includingModifiers, boolean includingThrows);

    /**
     * A simple representation of the element declaration.
     * It should fit one string.
     * @param includingModifiers flag to include the modifiers (if present) in the string produced
     * @param includingThrows flag to include the throws clause (if present) in the string produced
     * @param includingParameterName flag to include the parameter name (while the parameter type is always included) in the string produced
     * @return String representation of declaration based on parameter flags
     */
    String getDeclarationAsString(boolean includingModifiers, boolean includingThrows, boolean includingParameterName);
}
