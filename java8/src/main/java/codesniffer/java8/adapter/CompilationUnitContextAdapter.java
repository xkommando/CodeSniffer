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

import codesniffer.api.*;
import codesniffer.api.body.*;
import codesniffer.java8.*;

import java.util.*;

public class CompilationUnitContextAdapter implements Adapter<CompilationUnit, Java8Parser.CompilationUnitContext> {
    public CompilationUnit adapt(Java8Parser.CompilationUnitContext context, AdapterParameters adapterParameters) {
        CompilationUnit compilationUnit = new CompilationUnit();

        compilationUnit.setPackage(Adapters.getPackageDeclarationContextAdapter().adapt(context.packageDeclaration(), adapterParameters));
        List<Java8Parser.ImportDeclarationContext> ils = context.importDeclaration();
        List<ImportDeclaration> importDeclarationList = new ArrayList<>(ils.size());

        for (Java8Parser.ImportDeclarationContext importDeclarationContext : ils) {
            importDeclarationList.add(Adapters.getImportDeclarationContextAdapter().adapt(importDeclarationContext, adapterParameters));
        }

        compilationUnit.setImports(importDeclarationList);

        List<Java8Parser.TypeDeclarationContext> tls = context.typeDeclaration();
        List<TypeDeclaration> typeDeclarationList = new ArrayList<>(tls.size());
        for (Java8Parser.TypeDeclarationContext typeDeclarationContext : tls) {
            typeDeclarationList.add(Adapters.getTypeDeclarationContextAdapter().adapt(typeDeclarationContext, adapterParameters));
        }

        compilationUnit.setTypes(typeDeclarationList);

        return compilationUnit;
    }
}
