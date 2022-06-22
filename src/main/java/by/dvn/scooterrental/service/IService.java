package by.dvn.scooterrental.service;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.handlerexception.HandleBadRequestBody;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.IModelObject;

import java.util.List;

public interface IService<T extends IModelObject, E extends IDtoObject> {
    boolean create(T obj) throws HandleBadRequestPath, HandleBadRequestBody;

    E read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption;

    List<E> readAll() throws HandleNotFoundExeption;

    boolean update(T obj) throws HandleBadRequestBody;

    boolean delete(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption;

}
