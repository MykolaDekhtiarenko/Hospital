package com.mdekhtiarenko.models.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable{
    List<T> findAll();
    T findById(Integer id);
    T update(T item);
    T delete(Integer id);
    boolean create(T item);
}
