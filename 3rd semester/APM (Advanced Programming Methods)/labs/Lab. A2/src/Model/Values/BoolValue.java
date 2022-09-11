package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {
    private boolean value;

    public BoolValue(boolean value){
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof BoolValue);
    }

    @Override
    public String toString(){
        return Boolean.toString(value);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean isEqual(Value value) {
        BoolValue auxiliary = (BoolValue) value;
        return this.value == auxiliary.getValue();
    }
}
