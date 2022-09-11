package Model.Values;

import Model.Types.IntegerType;
import Model.Types.Type;

public class IntegerValue implements Value {
    private int value;

    public IntegerValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof IntegerValue);
    }

    @Override
    public String toString(){
        return Integer.toString(value);
    }

    @Override
    public Type getType() {
        return new IntegerType();
    }

    @Override
    public boolean isEqual(Value value) {
        IntegerValue auxiliary = (IntegerValue) value;
        return this.value == auxiliary.getValue();
    }
}
