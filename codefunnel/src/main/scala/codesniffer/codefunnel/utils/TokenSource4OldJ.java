package codesniffer.codefunnel.utils;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;

/**
 * Created by superbow on 4/3/2016.
 */
public class TokenSource4OldJ implements TokenSource {

    private final TokenSource underlying;

    public TokenSource4OldJ(TokenSource underlying) {
        this.underlying = underlying;
    }
    // t
    @Override
    public Token nextToken() {
        Token tk = underlying.nextToken();
        if (tk.getType() == 64 && "enum".equals(tk.getText())) {
//            System.out.println("==================> " + tk.getType());
            return new Token4OldJ(tk, "$_", 100);
        } else
           return tk;
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
    public CharStream getInputStream() {
        return underlying.getInputStream();
    }

    @Override
    public String getSourceName() {
        return underlying.getSourceName();
    }

    @Override
    public void setTokenFactory(TokenFactory<?> tokenFactory) {
        underlying.setTokenFactory(tokenFactory);
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return underlying.getTokenFactory();
    }
}
