package by.dvn.scooterrental.service;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.handlerexception.*;
import by.dvn.scooterrental.model.IModelObject;

import java.util.List;

public interface IService<T extends IModelObject, E extends IDtoObject> {
    boolean create(T obj) throws HandleBadRequestPath, HandleBadRequestBody, HandleBadCondition, HandleNotModified, HandleNotFoundExeption;

    E read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption;

    List<E> readAll() throws HandleNotFoundExeption;

    boolean update(T obj) throws HandleBadRequestBody, HandleBadCondition, HandleNotModified;

    boolean delete(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption;

    boolean checkObject(T obj, boolean findById) throws HandleBadCondition;
}
