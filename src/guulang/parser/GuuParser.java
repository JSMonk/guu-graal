package guulang.parser;

import com.oracle.truffle.api.source.Source;
import guulang.GuuLang;
import guulang.nodes.ProgramEntry;

public abstract class GuuParser {
    private GuuAstBuilder builder;

    public GuuAstBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(GuuAstBuilder builder) {
        this.builder = builder;
    }

    public static ProgramEntry parse(GuuLang language, Source source) throws ParseException {
       var parser = new GuuParserImpl(source.getCharacters().toString());
       parser.setBuilder(new GuuAstBuilder(language, parser::getToken));
       parser.Program();
       return parser.getBuilder().build();
    }
}
