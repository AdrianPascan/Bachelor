package flcd.lab2.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Grammar {
    public static final String SEPARATOR = " ";
    public static final String PRODUCTION_SEPARATOR = ":";
    public static final String PRODUCTION_OUTPUT_SEPARATOR = "\\|";

    private Set<String> nonterminals;
    private Set<String> terminals;
    private Map<String, List<List<String>>> productions;
    private String startSymbol;

    public Grammar() {
        nonterminals = new HashSet<>();
        terminals = new HashSet<>();
        productions = new HashMap<>();
        startSymbol = null;
    }

    public Grammar(String filePath) {
        nonterminals = new HashSet<>();
        terminals = new HashSet<>();
        productions = new HashMap<>();
        startSymbol = null;

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // nonterminals
            if (!scanner.hasNextLine()) {
                throw new GrammarException("ParserException: no data for nonterminals, terminals, start symbol, productions..");
            }
            List<String> nonterminals = Arrays.asList(scanner.nextLine().split(SEPARATOR));
            this.nonterminals.addAll(nonterminals);

            // terminals
            if (!scanner.hasNextLine()) {
                throw new GrammarException("ParserException: no data for terminals, start symbol, productions..");
            }
            List<String> terminals = Arrays.asList(scanner.nextLine().split(SEPARATOR));
            this.terminals.addAll(terminals);

            // start symbol
            if (!scanner.hasNextLine()) {
                throw new GrammarException("ParserException: no data for start symbol, productions..");
            }
            this.startSymbol = scanner.nextLine();
            if (!nonterminals.contains(this.startSymbol)) {
                throw new GrammarException("ParserException: start symbol must be contained by nonterminals");
            }

            // productions
            if (!scanner.hasNextLine()) {
                throw new GrammarException("ParserException: no data for productions..");
            }
            while(scanner.hasNextLine()) {
                List<String> tokens = Arrays.asList(scanner.nextLine().trim().split(PRODUCTION_SEPARATOR));
                if (tokens.size() != 2) {
                    throw new GrammarException("ParserException: invalid production: " + tokens);
                }

                String nonterminal = tokens.get(0).trim();
                this.productions.putIfAbsent(nonterminal, new ArrayList<>());

                List<String> productions = Arrays.asList(tokens.get(1).trim().split(PRODUCTION_OUTPUT_SEPARATOR));
                productions.forEach(production -> {
                    List<String> symbols = Arrays.asList(production.trim().split(SEPARATOR));

                    symbols.forEach(symbol -> {
                        if (! (nonterminals.contains(symbol) || terminals.contains(symbol))) {
                            throw new GrammarException("ParserException: " + symbol + " is neither a nonterminal or termminal in production '" + production + "'..");
                        }
                    });

                    this.productions.get(nonterminal).add(symbols);
                });

            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
