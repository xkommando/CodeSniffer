// Generated from Java8Parser.g4 by ANTLR 4.5

package codesniffer.java8;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link codesniffer.java8.Java8Parser}.
 */
public interface Java8ParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(Java8Parser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(Java8Parser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPackageDeclaration(Java8Parser.PackageDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPackageDeclaration(Java8Parser.PackageDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(Java8Parser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(Java8Parser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedImportName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedImportName(Java8Parser.QualifiedImportNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedImportName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedImportName(Java8Parser.QualifiedImportNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(Java8Parser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(Java8Parser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#classOrInterfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceDeclaration(Java8Parser.ClassOrInterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#classOrInterfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceDeclaration(Java8Parser.ClassOrInterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#modifiers}.
	 * @param ctx the parse tree
	 */
	void enterModifiers(Java8Parser.ModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#modifiers}.
	 * @param ctx the parse tree
	 */
	void exitModifiers(Java8Parser.ModifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(Java8Parser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(Java8Parser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#variableModifiers}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifiers(Java8Parser.VariableModifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#variableModifiers}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifiers(Java8Parser.VariableModifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(Java8Parser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(Java8Parser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#normalClassDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalClassDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(Java8Parser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(Java8Parser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(Java8Parser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(Java8Parser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeBound}.
	 * @param ctx the parse tree
	 */
	void enterTypeBound(Java8Parser.TypeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeBound}.
	 * @param ctx the parse tree
	 */
	void exitTypeBound(Java8Parser.TypeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#additionalBound}.
	 * @param ctx the parse tree
	 */
	void enterAdditionalBound(Java8Parser.AdditionalBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#additionalBound}.
	 * @param ctx the parse tree
	 */
	void exitAdditionalBound(Java8Parser.AdditionalBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEnumDeclaration(Java8Parser.EnumDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#enumBody}.
	 * @param ctx the parse tree
	 */
	void enterEnumBody(Java8Parser.EnumBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumBody}.
	 * @param ctx the parse tree
	 */
	void exitEnumBody(Java8Parser.EnumBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#enumConstants}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstants(Java8Parser.EnumConstantsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumConstants}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstants(Java8Parser.EnumConstantsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstant(Java8Parser.EnumConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstant(Java8Parser.EnumConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterEnumBodyDeclarations(Java8Parser.EnumBodyDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitEnumBodyDeclarations(Java8Parser.EnumBodyDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#normalInterfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalInterfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(Java8Parser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(Java8Parser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(Java8Parser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(Java8Parser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBody(Java8Parser.InterfaceBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBody(Java8Parser.InterfaceBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(Java8Parser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(Java8Parser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#memberDecl}.
	 * @param ctx the parse tree
	 */
	void enterMemberDecl(Java8Parser.MemberDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#memberDecl}.
	 * @param ctx the parse tree
	 */
	void exitMemberDecl(Java8Parser.MemberDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#constructorBlock}.
	 * @param ctx the parse tree
	 */
	void enterConstructorBlock(Java8Parser.ConstructorBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#constructorBlock}.
	 * @param ctx the parse tree
	 */
	void exitConstructorBlock(Java8Parser.ConstructorBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(Java8Parser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(Java8Parser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBodyDeclaration(Java8Parser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBodyDeclaration(Java8Parser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodDeclaration(Java8Parser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodDeclaration(Java8Parser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#defaultInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterDefaultInterfaceMethodDeclaration(Java8Parser.DefaultInterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#defaultInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitDefaultInterfaceMethodDeclaration(Java8Parser.DefaultInterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceFieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceFieldDeclaration(Java8Parser.InterfaceFieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceFieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceFieldDeclaration(Java8Parser.InterfaceFieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(Java8Parser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(Java8Parser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#identifierTypeArgument}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierTypeArgument(Java8Parser.IdentifierTypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#identifierTypeArgument}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierTypeArgument(Java8Parser.IdentifierTypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(Java8Parser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(Java8Parser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(Java8Parser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(Java8Parser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(Java8Parser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(Java8Parser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(Java8Parser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(Java8Parser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(Java8Parser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(Java8Parser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#formalParameterDecls}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterDecls(Java8Parser.FormalParameterDeclsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#formalParameterDecls}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterDecls(Java8Parser.FormalParameterDeclsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#normalParameterDecl}.
	 * @param ctx the parse tree
	 */
	void enterNormalParameterDecl(Java8Parser.NormalParameterDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalParameterDecl}.
	 * @param ctx the parse tree
	 */
	void exitNormalParameterDecl(Java8Parser.NormalParameterDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#ellipsisParameterDecl}.
	 * @param ctx the parse tree
	 */
	void enterEllipsisParameterDecl(Java8Parser.EllipsisParameterDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#ellipsisParameterDecl}.
	 * @param ctx the parse tree
	 */
	void exitEllipsisParameterDecl(Java8Parser.EllipsisParameterDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitConstructorInvocation(Java8Parser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitConstructorInvocation(Java8Parser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(Java8Parser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(Java8Parser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#annotations}.
	 * @param ctx the parse tree
	 */
	void enterAnnotations(Java8Parser.AnnotationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotations}.
	 * @param ctx the parse tree
	 */
	void exitAnnotations(Java8Parser.AnnotationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(Java8Parser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(Java8Parser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#markerAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterMarkerAnnotation(Java8Parser.MarkerAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#markerAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitMarkerAnnotation(Java8Parser.MarkerAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterSingleElementAnnotation(Java8Parser.SingleElementAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitSingleElementAnnotation(Java8Parser.SingleElementAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#normalAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterNormalAnnotation(Java8Parser.NormalAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitNormalAnnotation(Java8Parser.NormalAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValuePairs}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePairs(Java8Parser.ElementValuePairsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValuePairs}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePairs(Java8Parser.ElementValuePairsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePair(Java8Parser.ElementValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePair(Java8Parser.ElementValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValue}.
	 * @param ctx the parse tree
	 */
	void enterElementValue(Java8Parser.ElementValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValue}.
	 * @param ctx the parse tree
	 */
	void exitElementValue(Java8Parser.ElementValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterElementValueArrayInitializer(Java8Parser.ElementValueArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitElementValueArrayInitializer(Java8Parser.ElementValueArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeDeclaration(Java8Parser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeDeclaration(Java8Parser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeBody(Java8Parser.AnnotationTypeBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeBody(Java8Parser.AnnotationTypeBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementDeclaration(Java8Parser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementDeclaration(Java8Parser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationMethodDeclaration(Java8Parser.AnnotationMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationMethodDeclaration(Java8Parser.AnnotationMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(Java8Parser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(Java8Parser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(Java8Parser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(Java8Parser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(Java8Parser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(Java8Parser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(Java8Parser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(Java8Parser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#switchBlockStatementGroups}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroups(Java8Parser.SwitchBlockStatementGroupsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#switchBlockStatementGroups}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroups(Java8Parser.SwitchBlockStatementGroupsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(Java8Parser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(Java8Parser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(Java8Parser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(Java8Parser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#trystatement}.
	 * @param ctx the parse tree
	 */
	void enterTrystatement(Java8Parser.TrystatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#trystatement}.
	 * @param ctx the parse tree
	 */
	void exitTrystatement(Java8Parser.TrystatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#tryWithResources}.
	 * @param ctx the parse tree
	 */
	void enterTryWithResources(Java8Parser.TryWithResourcesContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#tryWithResources}.
	 * @param ctx the parse tree
	 */
	void exitTryWithResources(Java8Parser.TryWithResourcesContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void enterResourceSpecification(Java8Parser.ResourceSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void exitResourceSpecification(Java8Parser.ResourceSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#resources}.
	 * @param ctx the parse tree
	 */
	void enterResources(Java8Parser.ResourcesContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#resources}.
	 * @param ctx the parse tree
	 */
	void exitResources(Java8Parser.ResourcesContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#resource}.
	 * @param ctx the parse tree
	 */
	void enterResource(Java8Parser.ResourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#resource}.
	 * @param ctx the parse tree
	 */
	void exitResource(Java8Parser.ResourceContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#catches}.
	 * @param ctx the parse tree
	 */
	void enterCatches(Java8Parser.CatchesContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#catches}.
	 * @param ctx the parse tree
	 */
	void exitCatches(Java8Parser.CatchesContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#catchClause}.
	 * @param ctx the parse tree
	 */
	void enterCatchClause(Java8Parser.CatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#catchClause}.
	 * @param ctx the parse tree
	 */
	void exitCatchClause(Java8Parser.CatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#catchFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterCatchFormalParameter(Java8Parser.CatchFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#catchFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitCatchFormalParameter(Java8Parser.CatchFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#forstatement}.
	 * @param ctx the parse tree
	 */
	void enterForstatement(Java8Parser.ForstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#forstatement}.
	 * @param ctx the parse tree
	 */
	void exitForstatement(Java8Parser.ForstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#foreachStatement}.
	 * @param ctx the parse tree
	 */
	void enterForeachStatement(Java8Parser.ForeachStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#foreachStatement}.
	 * @param ctx the parse tree
	 */
	void exitForeachStatement(Java8Parser.ForeachStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#normalForStatement}.
	 * @param ctx the parse tree
	 */
	void enterNormalForStatement(Java8Parser.NormalForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalForStatement}.
	 * @param ctx the parse tree
	 */
	void exitNormalForStatement(Java8Parser.NormalForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(Java8Parser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(Java8Parser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(Java8Parser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(Java8Parser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(Java8Parser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(Java8Parser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(Java8Parser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(Java8Parser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(Java8Parser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(Java8Parser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterInclusiveOrExpression(Java8Parser.InclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitInclusiveOrExpression(Java8Parser.InclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveOrExpression(Java8Parser.ExclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveOrExpression(Java8Parser.ExclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(Java8Parser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(Java8Parser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(Java8Parser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(Java8Parser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#notEqualityExpression}.
	 * @param ctx the parse tree
	 */
	void enterNotEqualityExpression(Java8Parser.NotEqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#notEqualityExpression}.
	 * @param ctx the parse tree
	 */
	void exitNotEqualityExpression(Java8Parser.NotEqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#instanceOfExpression}.
	 * @param ctx the parse tree
	 */
	void enterInstanceOfExpression(Java8Parser.InstanceOfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#instanceOfExpression}.
	 * @param ctx the parse tree
	 */
	void exitInstanceOfExpression(Java8Parser.InstanceOfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(Java8Parser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(Java8Parser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#relationalOp}.
	 * @param ctx the parse tree
	 */
	void enterRelationalOp(Java8Parser.RelationalOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#relationalOp}.
	 * @param ctx the parse tree
	 */
	void exitRelationalOp(Java8Parser.RelationalOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(Java8Parser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(Java8Parser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#shiftOp}.
	 * @param ctx the parse tree
	 */
	void enterShiftOp(Java8Parser.ShiftOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#shiftOp}.
	 * @param ctx the parse tree
	 */
	void exitShiftOp(Java8Parser.ShiftOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#additiveOp}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveOp(Java8Parser.AdditiveOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#additiveOp}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveOp(Java8Parser.AdditiveOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#multiplicativeOp}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeOp(Java8Parser.MultiplicativeOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#multiplicativeOp}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeOp(Java8Parser.MultiplicativeOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(Java8Parser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(Java8Parser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#unaryExpressionNotPlusMinus}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#unaryExpressionNotPlusMinus}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(Java8Parser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(Java8Parser.CastExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(Java8Parser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(Java8Parser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void enterSuperSuffix(Java8Parser.SuperSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void exitSuperSuffix(Java8Parser.SuperSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#thisSuffix}.
	 * @param ctx the parse tree
	 */
	void enterThisSuffix(Java8Parser.ThisSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#thisSuffix}.
	 * @param ctx the parse tree
	 */
	void exitThisSuffix(Java8Parser.ThisSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#identifierSuffix}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierSuffix(Java8Parser.IdentifierSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#identifierSuffix}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierSuffix(Java8Parser.IdentifierSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParamCall}.
	 * @param ctx the parse tree
	 */
	void enterTypeParamCall(Java8Parser.TypeParamCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParamCall}.
	 * @param ctx the parse tree
	 */
	void exitTypeParamCall(Java8Parser.TypeParamCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(Java8Parser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(Java8Parser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(Java8Parser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(Java8Parser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayCreator}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreator(Java8Parser.ArrayCreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayCreator}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreator(Java8Parser.ArrayCreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(Java8Parser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(Java8Parser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(Java8Parser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(Java8Parser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#createdName}.
	 * @param ctx the parse tree
	 */
	void enterCreatedName(Java8Parser.CreatedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#createdName}.
	 * @param ctx the parse tree
	 */
	void exitCreatedName(Java8Parser.CreatedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#innerCreator}.
	 * @param ctx the parse tree
	 */
	void enterInnerCreator(Java8Parser.InnerCreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#innerCreator}.
	 * @param ctx the parse tree
	 */
	void exitInnerCreator(Java8Parser.InnerCreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterClassCreatorRest(Java8Parser.ClassCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitClassCreatorRest(Java8Parser.ClassCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 */
	void enterNonWildcardTypeArguments(Java8Parser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 */
	void exitNonWildcardTypeArguments(Java8Parser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(Java8Parser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(Java8Parser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(Java8Parser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(Java8Parser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#classHeader}.
	 * @param ctx the parse tree
	 */
	void enterClassHeader(Java8Parser.ClassHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#classHeader}.
	 * @param ctx the parse tree
	 */
	void exitClassHeader(Java8Parser.ClassHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#enumHeader}.
	 * @param ctx the parse tree
	 */
	void enterEnumHeader(Java8Parser.EnumHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumHeader}.
	 * @param ctx the parse tree
	 */
	void exitEnumHeader(Java8Parser.EnumHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceHeader}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceHeader(Java8Parser.InterfaceHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceHeader}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceHeader(Java8Parser.InterfaceHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationHeader}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationHeader(Java8Parser.AnnotationHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationHeader}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationHeader(Java8Parser.AnnotationHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeHeader}.
	 * @param ctx the parse tree
	 */
	void enterTypeHeader(Java8Parser.TypeHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeHeader}.
	 * @param ctx the parse tree
	 */
	void exitTypeHeader(Java8Parser.TypeHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#methodHeader}.
	 * @param ctx the parse tree
	 */
	void enterMethodHeader(Java8Parser.MethodHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#methodHeader}.
	 * @param ctx the parse tree
	 */
	void exitMethodHeader(Java8Parser.MethodHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#fieldHeader}.
	 * @param ctx the parse tree
	 */
	void enterFieldHeader(Java8Parser.FieldHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#fieldHeader}.
	 * @param ctx the parse tree
	 */
	void exitFieldHeader(Java8Parser.FieldHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableHeader}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableHeader(Java8Parser.LocalVariableHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableHeader}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableHeader(Java8Parser.LocalVariableHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#inferredFormalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterInferredFormalParameterList(Java8Parser.InferredFormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#inferredFormalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitInferredFormalParameterList(Java8Parser.InferredFormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#inferredFormalParameters}.
	 * @param ctx the parse tree
	 */
	void enterInferredFormalParameters(Java8Parser.InferredFormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#inferredFormalParameters}.
	 * @param ctx the parse tree
	 */
	void exitInferredFormalParameters(Java8Parser.InferredFormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(Java8Parser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(Java8Parser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void enterLambdaParameters(Java8Parser.LambdaParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void exitLambdaParameters(Java8Parser.LambdaParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void enterLambdaBody(Java8Parser.LambdaBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void exitLambdaBody(Java8Parser.LambdaBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#methodReference}.
	 * @param ctx the parse tree
	 */
	void enterMethodReference(Java8Parser.MethodReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#methodReference}.
	 * @param ctx the parse tree
	 */
	void exitMethodReference(Java8Parser.MethodReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#referenceType}.
	 * @param ctx the parse tree
	 */
	void enterReferenceType(Java8Parser.ReferenceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#referenceType}.
	 * @param ctx the parse tree
	 */
	void exitReferenceType(Java8Parser.ReferenceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeVariable}.
	 * @param ctx the parse tree
	 */
	void enterTypeVariable(Java8Parser.TypeVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeVariable}.
	 * @param ctx the parse tree
	 */
	void exitTypeVariable(Java8Parser.TypeVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(Java8Parser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(Java8Parser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayType}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(Java8Parser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(Java8Parser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link codesniffer.java8.Java8Parser#dims}.
	 * @param ctx the parse tree
	 */
	void enterDims(Java8Parser.DimsContext ctx);
	/**
	 * Exit a parse tree produced by {@link codesniffer.java8.Java8Parser#dims}.
	 * @param ctx the parse tree
	 */
	void exitDims(Java8Parser.DimsContext ctx);
}