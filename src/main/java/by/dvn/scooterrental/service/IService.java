package by.dvn.scooterrental.service;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.model.IModelObject;

import java.util.List;

public interface IService<T extends IModelObject, E extends IDtoObject> {
    void create(T obj);

    E read(Integer id);

    List<E> readAll();

    boolean update(T obj);

    boolean delete(Integer id);

}
