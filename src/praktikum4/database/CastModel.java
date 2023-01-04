package database;

import model.CastPK;

public interface CastModel extends EmptyModel {
    public CastPK getId();

    public void setId(CastPK id);
}
