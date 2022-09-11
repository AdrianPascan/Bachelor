package Model.Values;

import Model.Types.Type;

public interface Value {
    public Type getType();
    public boolean isEqual(Value value);
}
