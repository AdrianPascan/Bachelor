package Model.Types;

import Model.Values.ReferenceValue;
import Model.Values.Value;

public class ReferenceType implements Type {
    private Type inner;

    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof ReferenceType){
//            return true;
            return inner.equals(((ReferenceType) another).getInner());
        }
        return false;
    }

    @Override
    public Value getDefaultValue() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public String toString() {
        return "Reference(" + inner.toString() + ")";
    }
}
