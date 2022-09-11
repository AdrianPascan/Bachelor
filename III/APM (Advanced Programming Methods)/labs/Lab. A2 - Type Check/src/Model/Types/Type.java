package Model.Types;

import Model.Values.Value;

public interface Type {
    @Override
    public boolean equals(Object another);

    public Value getDefaultValue();
}
