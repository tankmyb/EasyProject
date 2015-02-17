package com.easy.persistance.jdbcExector;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.easy.persistance.jdbcExector.utils.ClassUtils;

/**
 * 默认通用类型映射转换
 * 
 * User: liyd
 * Date: 2/12/14
 * Time: 10:02 PM
 */
public class DefaultRowMapper implements RowMapper<Object> {

    /** 转换的目标对象 */
    private Class<?>    clazz;

    /** 名称处理器 */
    private NameHandler nameHandler;

    public DefaultRowMapper(Class<?> clazz, NameHandler nameHandler) {
        this.clazz = clazz;
        this.nameHandler = nameHandler;
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Object entity = ClassUtils.newInstance(this.clazz);
        BeanInfo beanInfo = ClassUtils.getSelfBeanInfo(this.clazz);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String column = nameHandler.getColumnName(pd.getName());
            Method writeMethod = pd.getWriteMethod();
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            try {
                writeMethod.invoke(entity, resultSet.getObject(column));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return entity;
    }
}