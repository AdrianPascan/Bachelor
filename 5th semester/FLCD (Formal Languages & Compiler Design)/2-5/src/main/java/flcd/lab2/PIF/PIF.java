package flcd.lab2.PIF;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class PIF {
    private List<PIFEntry> pifEntries;

    public PIF() {
        pifEntries = new ArrayList<>();
    }

    public void generate(String token, int index) {
        pifEntries.add(new PIFEntry(token, index));
    }

    public String toPrintable() {
        return "PIF: \n[" +
                pifEntries.stream()
                        .map(pifEntry -> pifEntry.getToken() + " ->  " + pifEntry.getIndex())
                        .reduce("", (accumulator, pifEntryString) -> accumulator += "\n\t" + pifEntryString) +
                "\n]\n";
    }
}
