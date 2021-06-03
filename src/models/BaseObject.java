package models;

public abstract class BaseObject<TKey> {
    protected Boolean logicalDelete = false;
    //Para el user es el DNI,
    //Para Room es el Numero de Habitacion
    protected TKey id;

    public TKey getId() {
        return id;
    }

    public Boolean getLogicalDelete() {
        return logicalDelete;
    }

    public void setLogicalDelete(Boolean logicalDelete) {
        this.logicalDelete = logicalDelete;
    }
}
