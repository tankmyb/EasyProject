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

public abstract class BaseDaoImpl<T>  {
	/** 具体操作的实体类对象 */
    private Class<T>       entityClass;

    /** 名称加工处理器 */
    private NameHandler    nameHandler;

    /** spring jdbcTemplate 对象 */
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * 构造方法，获取运行时的具体实体对象
     */
    public BaseDaoImpl() {
        Type superclass = getClass().getGenericSuperclass();
        ParameterizedType type = (ParameterizedType) superclass;
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
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
    public Long insert(T entity) {
        final SqlContext sqlContext = SqlUtils.buildInsertSql(entity, this.getActualNameHandler());
        System.out.println(entity+"==="+sqlContext.getSql());
        KeyHolder keyHolder = new GeneratedKeyHolder();
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
        return keyHolder.getKey().longValue();
    }

    /**
     * 更新记录
     * 
     * @param entity
     */
    public void update(T entity) {
        SqlContext sqlContext = SqlUtils.buildUpdateSql(entity, this.getActualNameHandler());
        jdbcTemplate.update(sqlContext.getSql().toString(), sqlContext.getParams().toArray());
    }

    /**
     * 删除记录
     *
     * @param id
     */
    public void delete(Serializable id) {
        String tableName = this.getActualNameHandler().getTableName(entityClass.getSimpleName());
        String primaryName = this.getNameHandler().getPrimaryName(entityClass.getSimpleName());
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryName + " = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * 删除所有记录
     */
    public void deleteAll() {
        String tableName = this.getActualNameHandler().getTableName(entityClass.getSimpleName());
        String sql = " TRUNCATE TABLE " + tableName;
        jdbcTemplate.execute(sql);
    }

    /**
     * 得到记录
     *
     * @param id
     * @return
     */
    public T getById(Serializable id) {
        String tableName = this.getNameHandler().getTableName(entityClass.getSimpleName());
        String primaryName = this.getNameHandler().getPrimaryName(entityClass.getSimpleName());
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryName + " = ?";
        return (T) jdbcTemplate.query(sql,
            new DefaultRowMapper(entityClass, this.getActualNameHandler()), id).get(0);
    }

    /**
     * 查询所有记录
     * 
     * @return
     */
    public List<T> findAll() {
        String sql = "SELECT * FROM "
                     + this.getActualNameHandler().getTableName(entityClass.getSimpleName());
        return (List<T>) jdbcTemplate.query(sql,
            new DefaultRowMapper(entityClass, this.getActualNameHandler()));
    }

    /**
     * 查询记录数
     * 
     * @param entity
     * @return
     */
    public int queryCount(T entity) {
        String tableName = this.getActualNameHandler().getTableName(entityClass.getSimpleName());
        StringBuilder countSql = new StringBuilder("select count(*) from ");
        countSql.append(tableName);
        SqlContext sqlContext = SqlUtils.buildQueryCondition(entity, this.getActualNameHandler());
        if (sqlContext.getSql().length() > 0) {
            countSql.append(" where ");
            countSql.append(sqlContext.getSql());
        } 
        return jdbcTemplate.queryForInt(countSql.toString(), sqlContext.getParams().toArray());
    }

   /* *//**
     * 查询分页列表
     * 
     * @param entity
     * @return
     *//*
    public Pager queryPageList(T entity) {
        Pager pager = new Pager();
        PagingOrder pagingOrder = (PagingOrder) entity;
        pager.setCurPage(pagingOrder.getCurPage());
        pager.setItemsPerPage(pagingOrder.getItemsPerPage());

        String tableName = this.getActualNameHandler().getTableName(entityClass.getSimpleName());
        String primaryName = this.getActualNameHandler()
            .getPrimaryName(entityClass.getSimpleName());
        StringBuilder querySql = new StringBuilder("select * from ");
        StringBuilder countSql = new StringBuilder("select count(*) from ");
        querySql.append(tableName);
        countSql.append(tableName);
        //不调用queryCount方法，条件共同组装一次，减少反射获取的次数
        SqlContext sqlContext = SqlUtils.buildQueryCondition(entity, this.getActualNameHandler());
        if (sqlContext.getSql().length() > 0) {
            querySql.append(" where ");
            countSql.append(" where ");
            querySql.append(sqlContext.getSql());
            countSql.append(sqlContext.getSql());
        }
        querySql.append(" order by ");
        querySql.append(primaryName);
        querySql.append(" desc ");
        querySql.append("limit ?,?");
        List<Object> queryParams = new ArrayList<Object>(sqlContext.getParams());
        queryParams.add(pager.getBeginIndex());
        queryParams.add(pager.getItemsPerPage());

        List<T> list = (List<T>) jdbcTemplate.query(querySql.toString(), queryParams.toArray(),
            new DefaultRowMapper(entityClass, this.getActualNameHandler()));

        int totalCount = jdbcTemplate.queryForInt(countSql.toString(), sqlContext.getParams()
            .toArray());
        pager.setList(list);
        pager.setItems(totalCount);
        return pager;
    }*/

}