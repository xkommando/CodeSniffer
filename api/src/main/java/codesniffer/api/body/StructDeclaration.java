package codesniffer.api.body;

import codesniffer.api.*;
import codesniffer.api.type.*;
import codesniffer.api.visitor.*;

import java.util.*;

/**
 * Created by Bowen Cai on 7/23/2015.
 */
public class StructDeclaration extends TypeDeclaration implements NamedNode {

    private List<TypeParameter> typeParameters;

    private List<ClassOrInterfaceType> extendsList;

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }
}
