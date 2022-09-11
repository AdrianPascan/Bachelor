package Model.Types;

import Model.Values.StringValue;
import Model.Values.Value;

public class StringType implements Type {
    @Override
    public boolean equals(Object another){
        return (another instanceof StringType);
    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }
}
