package ro.ubb.domain;

public class BaseEntity<ID> {
    private ID id; //what's this and why is it private??

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
