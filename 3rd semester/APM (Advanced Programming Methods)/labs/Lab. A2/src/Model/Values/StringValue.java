package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {
    private String value;

    public StringValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof StringValue);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean isEqual(Value value) {
        StringValue auxiliary = (StringValue) value;
        return this.value.equals( auxiliary.getValue() );
    }
}
