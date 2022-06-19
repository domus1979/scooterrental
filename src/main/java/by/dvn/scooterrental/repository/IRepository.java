package by.dvn.scooterrental.repository;

import by.dvn.scooterrental.model.IModelObject;

import java.util.List;

public interface IRepository<T extends IModelObject> {
    void create(T obj);

    T read(Integer id);

    List<T> readAll();

    boolean update(T obj);

    boolean delete(Integer id);
}
