package Model.Types;

import Model.Values.IntegerValue;
import Model.Values.Value;

public class IntegerType implements Type {
    @Override
    public boolean equals (Object another){
        return (another instanceof IntegerType);
    }

    @Override
    public String toString(){
        return "Integer";
    }

    @Override
    public Value getDefaultValue() {
        return new IntegerValue(0);
    }
}
