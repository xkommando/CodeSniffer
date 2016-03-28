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

package codesniffer.api.stmt;

import codesniffer.api.expr.*;
import codesniffer.api.visitor.*;

import java.util.*;

/**
 * @author Julio Vilmar Gesser
 */
public final class SwitchEntryStmt extends Statement {

	private Expression label;

	private List<Statement> stmts;

	public SwitchEntryStmt() {
	}

	public SwitchEntryStmt(final Expression label, final List<Statement> stmts) {
		setLabel(label);
		setStmts(stmts);
	}

	public SwitchEntryStmt(final int beginLine, final int beginColumn,
			final int endLine, final int endColumn, final Expression label,
			final List<Statement> stmts) {
		super(beginLine, beginColumn, endLine, endColumn);
		setLabel(label);
		setStmts(stmts);
	}

	@Override
	public <R, A> R accept(final GenericVisitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	@Override
	public <A> void accept(final VoidVisitor<A> v, final A arg) {
		v.visit(this, arg);
	}

	public Expression getLabel() {
		return label;
	}

	public List<Statement> getStmts() {
		return stmts;
	}

	public void setLabel(final Expression label) {
		this.label = label;
		setAsParentNodeOf(this.label);
	}

	public void setStmts(final List<Statement> stmts) {
		this.stmts = stmts;
		setAsParentNodeOf(this.stmts);
	}
}
