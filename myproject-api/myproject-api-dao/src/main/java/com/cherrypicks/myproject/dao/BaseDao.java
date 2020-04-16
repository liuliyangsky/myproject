package com.cherrypicks.myproject.dao;

import java.util.Collection;
import java.util.List;

import com.cherrypicks.myproject.model.BaseModel;

public interface BaseDao<T extends BaseModel> {

    T add(T t);

    int delAll();

    boolean del(Long id);

    boolean delByIds(Collection<Object> keys);

    boolean update(T t);

    boolean updateSpecifyField(T t);

    T get(Long id);

    List<T> findAll();

    List<T> findByBaseId(Long baseId);

    T getByLang(Long id, String lang);

    List<T> findByIds(Collection<Object> ids);

    int batchAdd(List<T> list);

    int batchUpdate(List<T> list);

    T updateForVersion(T object);
}
