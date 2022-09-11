package flcd.lab2.symbolTable;

import flcd.lab2.hashTable.HashTable;
import flcd.lab2.hashTable.HashTableImpl;
import lombok.ToString;

@ToString(callSuper = true)
public class ConstantSymbolTable extends HashTableImpl implements HashTable {

    public String toPrintable() {
        return "Constant ST: \n[\n" +
                super.toPrintable(1) +
                "\n]\n";
    }
}
