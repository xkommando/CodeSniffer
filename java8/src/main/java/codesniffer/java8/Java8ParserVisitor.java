// Generated from Java8Parser.g4 by ANTLR 4.5

package codesniffer.java8;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link codesniffer.java8.Java8Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Java8ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(Java8Parser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#packageDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageDeclaration(Java8Parser.PackageDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(Java8Parser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedImportName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedImportName(Java8Parser.QualifiedImportNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(Java8Parser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#classOrInterfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceDeclaration(Java8Parser.ClassOrInterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#modifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifiers(Java8Parser.ModifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(Java8Parser.ModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#variableModifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableModifiers(Java8Parser.VariableModifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(Java8Parser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalClassDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameters(Java8Parser.TypeParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(Java8Parser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeBound(Java8Parser.TypeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#additionalBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditionalBound(Java8Parser.AdditionalBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDeclaration(Java8Parser.EnumDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBody(Java8Parser.EnumBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumConstants}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstants(Java8Parser.EnumConstantsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstant(Java8Parser.EnumConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBodyDeclarations(Java8Parser.EnumBodyDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalInterfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(Java8Parser.TypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(Java8Parser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBody(Java8Parser.InterfaceBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(Java8Parser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#memberDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDecl(Java8Parser.MemberDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#constructorBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorBlock(Java8Parser.ConstructorBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(Java8Parser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(Java8Parser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBodyDeclaration(Java8Parser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodDeclaration(Java8Parser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#defaultInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultInterfaceMethodDeclaration(Java8Parser.DefaultInterfaceMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceFieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceFieldDeclaration(Java8Parser.InterfaceFieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(Java8Parser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#identifierTypeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierTypeArgument(Java8Parser.IdentifierTypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(Java8Parser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(Java8Parser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(Java8Parser.TypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedNameList(Java8Parser.QualifiedNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(Java8Parser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#formalParameterDecls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterDecls(Java8Parser.FormalParameterDeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalParameterDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalParameterDecl(Java8Parser.NormalParameterDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#ellipsisParameterDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEllipsisParameterDecl(Java8Parser.EllipsisParameterDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitConstructorInvocation(Java8Parser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(Java8Parser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotations(Java8Parser.AnnotationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(Java8Parser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#markerAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMarkerAnnotation(Java8Parser.MarkerAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleElementAnnotation(Java8Parser.SingleElementAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalAnnotation(Java8Parser.NormalAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValuePairs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePairs(Java8Parser.ElementValuePairsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePair(Java8Parser.ElementValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValue(Java8Parser.ElementValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueArrayInitializer(Java8Parser.ElementValueArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeDeclaration(Java8Parser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeBody(Java8Parser.AnnotationTypeBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationTypeElementDeclaration(Java8Parser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationMethodDeclaration(Java8Parser.AnnotationMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(Java8Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(Java8Parser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(Java8Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#emptyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement(Java8Parser.EmptyStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#switchBlockStatementGroups}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroups(Java8Parser.SwitchBlockStatementGroupsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(Java8Parser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(Java8Parser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#trystatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrystatement(Java8Parser.TrystatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#tryWithResources}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryWithResources(Java8Parser.TryWithResourcesContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#resourceSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceSpecification(Java8Parser.ResourceSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#resources}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResources(Java8Parser.ResourcesContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#resource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResource(Java8Parser.ResourceContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#catches}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatches(Java8Parser.CatchesContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#catchClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchClause(Java8Parser.CatchClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#catchFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchFormalParameter(Java8Parser.CatchFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#forstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForstatement(Java8Parser.ForstatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#foreachStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeachStatement(Java8Parser.ForeachStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#normalForStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalForStatement(Java8Parser.NormalForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(Java8Parser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#parExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(Java8Parser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(Java8Parser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(Java8Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(Java8Parser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalOrExpression(Java8Parser.ConditionalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalAndExpression(Java8Parser.ConditionalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusiveOrExpression(Java8Parser.InclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveOrExpression(Java8Parser.ExclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(Java8Parser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(Java8Parser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#notEqualityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEqualityExpression(Java8Parser.NotEqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#instanceOfExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceOfExpression(Java8Parser.InstanceOfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(Java8Parser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#relationalOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalOp(Java8Parser.RelationalOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#shiftExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(Java8Parser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#shiftOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftOp(Java8Parser.ShiftOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#additiveOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveOp(Java8Parser.AdditiveOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#multiplicativeOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeOp(Java8Parser.MultiplicativeOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(Java8Parser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#unaryExpressionNotPlusMinus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpressionNotPlusMinus(Java8Parser.UnaryExpressionNotPlusMinusContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(Java8Parser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(Java8Parser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#superSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperSuffix(Java8Parser.SuperSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#thisSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisSuffix(Java8Parser.ThisSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#identifierSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierSuffix(Java8Parser.IdentifierSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeParamCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParamCall(Java8Parser.TypeParamCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(Java8Parser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(Java8Parser.CreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayCreator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreator(Java8Parser.ArrayCreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(Java8Parser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(Java8Parser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#createdName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatedName(Java8Parser.CreatedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#innerCreator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerCreator(Java8Parser.InnerCreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#classCreatorRest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassCreatorRest(Java8Parser.ClassCreatorRestContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonWildcardTypeArguments(Java8Parser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(Java8Parser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(Java8Parser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#classHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassHeader(Java8Parser.ClassHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#enumHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumHeader(Java8Parser.EnumHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#interfaceHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceHeader(Java8Parser.InterfaceHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#annotationHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationHeader(Java8Parser.AnnotationHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeHeader(Java8Parser.TypeHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#methodHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodHeader(Java8Parser.MethodHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#fieldHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldHeader(Java8Parser.FieldHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#localVariableHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableHeader(Java8Parser.LocalVariableHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#inferredFormalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInferredFormalParameterList(Java8Parser.InferredFormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#inferredFormalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInferredFormalParameters(Java8Parser.InferredFormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(Java8Parser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameters(Java8Parser.LambdaParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#lambdaBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaBody(Java8Parser.LambdaBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#methodReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodReference(Java8Parser.MethodReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#referenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceType(Java8Parser.ReferenceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeVariable(Java8Parser.TypeVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(Java8Parser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(Java8Parser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link codesniffer.java8.Java8Parser#dims}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDims(Java8Parser.DimsContext ctx);
}