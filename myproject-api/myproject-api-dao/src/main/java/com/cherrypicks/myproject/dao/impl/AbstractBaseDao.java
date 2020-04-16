package com.cherrypicks.myproject.dao.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.myproject.jdbc.Jdbc;
import com.cherrypicks.myproject.jdbc.StatementParameter;
import com.cherrypicks.myproject.model.BaseModel;

@Transactional(readOnly = true)
public abstract class AbstractBaseDao<T extends BaseModel> extends Jdbc {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Class<T> baseModelClass;
    protected String tableName;

    @Override
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        super.init();

        this.baseModelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.tableName = underscoreName(baseModelClass.getSimpleName());
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case. Any upper case letters are converted to lower
     * case with a preceding underscore.
     *
     * @param name
     *            the string containing original name
     * @return the converted name
     */
    protected String underscoreName(final String name) {
        final StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            result.append(name.substring(0, 1).toUpperCase());
            for (int i = 1; i < name.length(); i++) {
                final String s = name.substring(i, i + 1);
                if (s.equals(s.toUpperCase(Locale.ENGLISH))) {
                    result.append("_");
                    result.append(s);
                } else {
                    result.append(s.toUpperCase());
                }
            }
        }
        return result.toString();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public T add(final T object) {

        final SqlStatementParameter sqlStatementParameter = addSqlStatementParameter(object);
        object.setId(super.insertForKey(sqlStatementParameter.getSql(), sqlStatementParameter.getParam()));

        return object;
    }

    @SuppressWarnings("rawtypes")
    private SqlStatementParameter addSqlStatementParameter(final T object) {
        final StringBuilder sql = new StringBuilder("INSERT INTO ").append(this.tableName).append("(");
        final StringBuilder placeholder = new StringBuilder();
        final StatementParameter param = new StatementParameter();

        final PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(baseModelClass);
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);

        boolean flag = false;
        for (final PropertyDescriptor pd : pds) {
            final String propertyName = pd.getName();
            final String underscoredName = underscoreName(propertyName);
            final Class propertyType = beanWrapper.getPropertyType(propertyName);
            final Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (!propertyName.equals("class")) {
                if (flag) {
                    sql.append(",");
                    placeholder.append(",");
                }
                sql.append(underscoredName);
                placeholder.append("?");

                if (propertyValue != null) {
                    if (propertyType.equals(Time.class)) {
                        param.setTime((Time) propertyValue);
                    } else if (propertyType.equals(Date.class)) {
                        param.setDate((Date) propertyValue);
                    } else if (propertyType.equals(String.class)) {
                        param.setString((String) propertyValue);
                    } else if (propertyType.equals(Boolean.class)) {
                        param.setBool((Boolean) propertyValue);
                    } else if (propertyType.equals(Integer.class)) {
                        param.setInt((Integer) propertyValue);
                    } else if (propertyType.equals(Long.class)) {
                        param.setLong((Long) propertyValue);
                    } else if (propertyType.equals(Double.class)) {
                        param.setDouble((Double) propertyValue);
                    } else if (propertyType.equals(Float.class)) {
                        param.setFloat((Float) propertyValue);
                    } else {
                        param.setString(propertyValue.toString());
                    }
                } else {
                    param.setNull();
                }
                flag = true;
            }
        }

        sql.append(") VALUES(");
        sql.append(placeholder);
        sql.append(")");

        return new SqlStatementParameter(sql.toString(), param);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean del(final T object) {
        return del(object.getId());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int delAll() {
        final StringBuilder sql = new StringBuilder("DELETE FROM ").append(this.tableName);
        return super.execute(sql.toString());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean del(final Long id) {
        final StringBuilder sql = new StringBuilder("DELETE FROM ").append(this.tableName).append(" WHERE id = ?");
        final StatementParameter param = new StatementParameter();
        param.setLong(id);
        return super.updateForBoolean(sql.toString(), param);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean delByIds(final Collection<Object> keys) {
        final StringBuilder sql = new StringBuilder("DELETE FROM ").append(this.tableName)
                .append(" WHERE ID in (:keys)");
        final Map<String, Collection<Object>> paramMap = Collections.singletonMap("keys", keys);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean update(final T object) {
        final SqlStatementParameter updateSqlStatementParameter = updateSqlStatementParameter(object);

        if (updateSqlStatementParameter.getParam().size() > 0) {
            return super.updateForBoolean(updateSqlStatementParameter.getSql(), updateSqlStatementParameter.getParam());
        } else {
            return false;
        }
    }

    @SuppressWarnings("rawtypes")
    private SqlStatementParameter updateSqlStatementParameter(final T object) {
        final StringBuilder sql = new StringBuilder("UPDATE ").append(this.tableName).append(" SET");
        final StatementParameter param = new StatementParameter();

        final PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(baseModelClass);
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        boolean flag = false;
        for (final PropertyDescriptor pd : pds) {
            final String propertyName = pd.getName();
            final String underscoredName = underscoreName(propertyName);
            final Class propertyType = beanWrapper.getPropertyType(propertyName);
            final Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (!propertyName.equals("id") && !propertyName.equals("class")) {
                if (flag) {
                    sql.append(",");
                }

                sql.append(" " + underscoredName + "=?");
                if (propertyValue != null) {

                    if (propertyType.equals(Time.class)) {
                        param.setTime((Time) propertyValue);
                    } else if (propertyType.equals(Date.class)) {
                        param.setDate((Date) propertyValue);
                    } else if (propertyType.equals(String.class)) {
                        param.setString((String) propertyValue);
                    } else if (propertyType.equals(Boolean.class)) {
                        param.setBool((Boolean) propertyValue);
                    } else if (propertyType.equals(Integer.class)) {
                        param.setInt((Integer) propertyValue);
                    } else if (propertyType.equals(Long.class)) {
                        param.setLong((Long) propertyValue);
                    } else if (propertyType.equals(Double.class)) {
                        param.setDouble((Double) propertyValue);
                    } else if (propertyType.equals(Float.class)) {
                        param.setFloat((Float) propertyValue);
                    } else {
                        param.setString(propertyValue.toString());
                    }
                } else {
                    param.setNull();
                    // sql.append(" " + underscoredName + "=NULL");
                }

                flag = true;
            }
        }

        sql.append(" WHERE id=?");
        param.setLong(object.getId());

        return new SqlStatementParameter(sql.toString(), param);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public T get(final Long id) {
        final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.tableName).append(" WHERE id = ?");
        final StatementParameter param = new StatementParameter();
        param.setLong(id);
        return super.query(sql.toString(), this.baseModelClass, param);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<T> findAll() {
        final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.tableName);
        return super.queryForList(sql.toString(), this.baseModelClass);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<T> findByIds(final Collection<Object> keys) {
        final StringBuilder sql = new StringBuilder("SELECT * FROM ").append(this.tableName)
                .append(" WHERE ID in (:keys)");
        final Map<String, Collection<Object>> paramMap = Collections.singletonMap("keys", keys);
        return super.queryForListWithNamedParam(sql.toString(), this.baseModelClass, paramMap);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int batchAdd(final List<T> list) {
        int iRet = 0;
        String sql = "";
        final List<StatementParameter> paramList = new ArrayList<StatementParameter>();

        if (list != null && !list.isEmpty()) {
            for (final T t : list) {
                final SqlStatementParameter addSqlStatementParameter = addSqlStatementParameter(t);
                sql = addSqlStatementParameter.getSql();
                paramList.add(addSqlStatementParameter.getParam());
            }

            final int[] result = super.batchUpdate(sql, paramList);

            if (result != null && result.length > 0) {
                for (final int i : result) {
                    iRet += i;
                }
            }
        }

        return iRet;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int batchUpdate(final List<T> list) {
        int iRet = 0;
        String sql = "";
        final List<StatementParameter> paramList = new ArrayList<StatementParameter>();

        if (list != null && !list.isEmpty()) {
            for (final T t : list) {
                final SqlStatementParameter updateSqlStatementParameter = updateSqlStatementParameter(t);
                sql = updateSqlStatementParameter.getSql();
                paramList.add(updateSqlStatementParameter.getParam());
            }

            final int[] result = super.batchUpdate(sql, paramList);

            if (result != null && result.length > 0) {
                for (final int i : result) {
                    iRet += i;
                }
            }
        }

        return iRet;
    }

    private class SqlStatementParameter {
        private String sql;
        private StatementParameter param;

        SqlStatementParameter(final String sql, final StatementParameter param) {
            setSql(sql);
            setParam(param);
        }

        public String getSql() {
            return sql;
        }

        public void setSql(final String sql) {
            this.sql = sql;
        }

        public StatementParameter getParam() {
            return param;
        }

        public void setParam(final StatementParameter param) {
            this.param = param;
        }
    }

}
