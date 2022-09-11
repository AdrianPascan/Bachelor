package flcd.lab2.scanner;

import flcd.lab2.PIF.PIF;
import flcd.lab2.automaton.FA;
import flcd.lab2.symbolTable.ConstantSymbolTable;
import flcd.lab2.symbolTable.IdentifierSymbolTable;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;

@Getter
@Setter
public class LexicalAnalyser {
    private PIF pif;
    private IdentifierSymbolTable identifierSymbolTable;
    private ConstantSymbolTable constantSymbolTable;
    private Map<String, Integer> reservedTokenToCode;
    private FA faIdentifier;
    private FA faIntConst;

    public LexicalAnalyser(String reservedTokenFilePath) {
        pif = null;
        identifierSymbolTable = null;
        constantSymbolTable = null;

        reservedTokenToCode = readReservedTokenToCode(reservedTokenFilePath);

        faIdentifier = new FA("src/main/java/flcd/lab2/scanner/input/fa_iden.in");
        faIntConst = new FA("src/main/java/flcd/lab2/scanner/input/fa_int.in");
    }

    public void scanning(String fileName, String programDirectoryPath, String outputDirectoryPath) {
        pif = null;
        identifierSymbolTable = null;
        constantSymbolTable = null;

        try {
            List<LAEntry> laEntries = detectTokens(programDirectoryPath + "/" + fileName);
            classifyAndCodifyTokens(laEntries);

            System.out.println(fileName + "-> Lexically correct!");
            output(fileName, outputDirectoryPath);
        } catch (LexicalError le) {
            System.out.println(fileName + "-> " + le.getMessage());
        }
    }

    private static List<LAEntry> detectTokens(String programFilePath) {
        List<LAEntry> laEntries = new ArrayList<>();

        try {
            File file = new File(programFilePath);
            Scanner scanner = new Scanner(file);

            int lineNo = 0;
            while (scanner.hasNextLine()) {
                lineNo++;
                String line = scanner.nextLine();

                String[] lineTokens = line.strip().split(" ");
                for (String lineToken : lineTokens) {
                    laEntries.add(new LAEntry(lineToken, lineNo));
                }
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        return laEntries;
    }

    private void classifyAndCodifyTokens(List<LAEntry> laEntries) throws LexicalError {
        PIF pif = new PIF();
        IdentifierSymbolTable identifierSymbolTable = new IdentifierSymbolTable();
        ConstantSymbolTable constantSymbolTable = new ConstantSymbolTable();

        for (LAEntry laEntry : laEntries) {
            if (reservedTokenToCode.containsKey(laEntry.getToken())) {
                pif.generate(laEntry.getToken(), -1);
            }
            else if(isIdentifier(laEntry.getToken())) {
                int index = identifierSymbolTable.search(laEntry.getToken());
                if (index == -1) {
                    index = identifierSymbolTable.add(laEntry.getToken());
                }
                pif.generate(laEntry.getToken(), index);
            }
            else if(isConstant(laEntry.getToken())) {
                int index = constantSymbolTable.search(laEntry.getToken());
                if (index == -1) {
                    index = constantSymbolTable.add(laEntry.getToken());
                }
                pif.generate(laEntry.getToken(), index);
            }
            else {
                throw new LexicalError("LEXICAL ERROR: token '" + laEntry.getToken()
                        + "' cannot be classified (line " + laEntry.getLine() + ")");
            }
        }

        this.pif = pif;
        this.identifierSymbolTable = identifierSymbolTable;
        this.constantSymbolTable = constantSymbolTable;
    }

    private boolean isIdentifier(String token) {
        return faIdentifier.isAccepted(token);
//    old version:
//        return token.matches("[a-zA-Z][a-zA-Z0-9]+");
    }

    private boolean isConstant(String token) {
        // integer constant
        if (faIntConst.isAccepted(token)) {
            return true;
        }
//    old version:
//        try {
//            Integer.parseInt(token);
//            return true;
//        } catch (NumberFormatException nfe) {
//        }

        // string constant
        if ( !(token.startsWith("\"") && token.endsWith("\"")) || !token.matches("[a-zA-Z0-9 _.,:;!?'#]*")) {
            return false;
        }

        return true;
    }

    private void output(String fileName, String outputDirectoryPath) {
        String fileNameWithoutExtension = fileName.split("[.]")[0];

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputDirectoryPath + "/" + fileNameWithoutExtension + "_PIF.out"))) {
            writer.write(pif.toPrintable());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputDirectoryPath + "/" + fileNameWithoutExtension + "_ST.out"))) {
            writer.write(identifierSymbolTable.toPrintable());
            writer.newLine();
            writer.write(constantSymbolTable.toPrintable());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static Map<String, Integer> readReservedTokenToCode(String reservedTokenFilePath) {
        Map<String, Integer> reservedTokenToCode = new HashMap<>();

        try {
            File file = new File(reservedTokenFilePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineTokens = line.split(",");
                String token = lineTokens[0];
                int code = Integer.parseInt(lineTokens[1]);

                reservedTokenToCode.put(token, code);
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        return reservedTokenToCode;
    }
}
