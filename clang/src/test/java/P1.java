import codesniffer.clang.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.junit.*;

import java.io.*;

/**
 * Created by Bowen Cai on 7/23/2015.
 */
public class P1 {

    @Test
    public void pase() throws Throwable {

        ANTLRInputStream antlrStream = new ANTLRInputStream(new FileInputStream(
                "D:\\usr\\code\\linux-3.8\\linux-3.8\\include\\math-emu\\op-common.h"));
        CLexer lex = new CLexer(antlrStream);
        CommonTokenStream tokens = new CommonTokenStream(lex);

        CParser cp = new CParser(tokens);

        cp.getInterpreter().setPredictionMode(PredictionMode.SLL);

        CParser.CompilationUnitContext cuctx = cp.compilationUnit();
        System.out.println(cuctx);
    }
}
