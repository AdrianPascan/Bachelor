package Model.Values;

import Model.Types.ReferenceType;
import Model.Types.Type;

public class ReferenceValue implements Value {
    private int address;
    private Type locationType;

    public ReferenceValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof ReferenceValue){
            ReferenceValue auxiliary = (ReferenceValue) another;
            return locationType == auxiliary.getLocationType();
        }
        return false;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(address) + ", " + locationType.toString() + ")";
    }

    @Override
    public boolean isEqual(Value value) {
        ReferenceValue auxiliary = (ReferenceValue) value;
        return address == auxiliary.getAddress() && locationType == auxiliary.getLocationType();
    }
}
