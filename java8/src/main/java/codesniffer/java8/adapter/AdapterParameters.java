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

import org.antlr.v4.runtime.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 1/10/13
 * Time: 9:37 AM
 */
public class AdapterParameters {

    private BufferedTokenStream tokens;
    private Set<Integer> commentTokensClaimed = new TreeSet<Integer>();

    public BufferedTokenStream getTokens() {
        return tokens;
    }

    public void setTokens(BufferedTokenStream tokens) {
        this.tokens = tokens;
    }

    public void claimCommentToken(Integer tokenId) {
        commentTokensClaimed.add(tokenId);
    }

    public boolean isCommentTokenClaimed(Integer tokenId) {
        return commentTokensClaimed.contains(tokenId);
    }

    public Set<Integer> getCommentTokensClaimed() {
        return commentTokensClaimed;
    }

}
