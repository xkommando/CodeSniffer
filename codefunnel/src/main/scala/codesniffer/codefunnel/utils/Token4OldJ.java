package codesniffer.codefunnel.utils;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;

/**
 * Created by superbow on 4/3/2016.
 */
public class Token4OldJ implements Token {

    private final Token underlying;
    private final String suffix;
    private final int tp;

    public Token4OldJ(Token underlying, String prefix, int tp) {
        this.underlying = underlying;
        this.suffix = prefix;
        this.tp = tp;
    }

    @Override
    public String getText() {
        return underlying.getText();
    }

    @Override
    public int getType() {
        return tp;
    }

    @Override
    public int getLine() {
        return underlying.getLine();
    }

    @Override
    public int getCharPositionInLine() {
        return underlying.getCharPositionInLine();
    }

    @Override
    public int getChannel() {
        return underlying.getChannel();
    }

    @Override
    public int getTokenIndex() {
        return underlying.getTokenIndex();
    }

    @Override
    public int getStartIndex() {
        return underlying.getStartIndex();
    }

    @Override
    public int getStopIndex() {
        return underlying.getStopIndex();
    }

    @Override
    public TokenSource getTokenSource() {
        return underlying.getTokenSource();
    }

    @Override
    public CharStream getInputStream() {
        return underlying.getInputStream();
    }
}
