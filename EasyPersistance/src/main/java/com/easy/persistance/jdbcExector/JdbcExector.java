package com.easy.persistance.jdbcExector;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.easy.persistance.jdbcExector.utils.SqlUtils;

public  class JdbcExector  {
	/** 具体操作的实体类对象 */

    /** 名称加工处理器 */
    private NameHandler    nameHandler;

    /** spring jdbcTemplate 对象 */
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * 构造方法，获取运行时的具体实体对象
     */
    public JdbcExector() {
    }

    /**
     * 获取实际运行时的名称处理器
     *
     * @return
     */
    private NameHandler getActualNameHandler() {
        if (nameHandler == null) {
            synchronized (this) {
                if (nameHandler == null) {
                    nameHandler = this.getNameHandler();
                }
            }
        }
        return nameHandler;
    }

    /**
     * 得到名称处理器，子类覆盖此方法实现自己的名称转换处理器
     *
     * @return
     */
    protected NameHandler getNameHandler() {
        return new DefaultNameHandler();
    }

    /**
     * 插入一条记录
     *
     * @param entity
     */
    public <T> Long insert(T entity) {
        final SqlContext sqlContext = SqlUtils.buildInsertSql(entity, this.getActualNameHandler());
        System.out.println(entity+"==="+sqlContext.getSql());
        /*KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sqlContext.getSql().toString(),
                    new String[] { sqlContext.getPrimaryKey() });
                int index = 0;
                for (Object param : sqlContext.getParams()) {
                    index++;
                    ps.setObject(index, param);
                }
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();*/
        return null;
    }

   
}