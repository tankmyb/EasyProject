package com.easy.persistance.jdbcExector;

import com.easy.persistance.util.ColumnFormatUtil;

/**
 * 默认名称处理handler
 * 
 * User: liyd
 * Date: 2/12/14
 * Time: 4:51 PM
 */
public class DefaultNameHandler implements NameHandler {


    /** 主键后缀 */
    private static final String PRI_SUFFIX = "_id";

    /**
     * 根据实体名获取表名
     *
     * @param entityName
     * @return
     */
    @Override
    public String getTableName(String entityName) {
        //Java属性的骆驼命名法转换回数据库下划线“_”分隔的格式
        return ColumnFormatUtil.underLineName(entityName);
    }

    /**
     * 根据表名获取主键名
     *
     * @param entityName
     * @return
     */
    @Override
    public String getPrimaryName(String entityName) {
        String underlineName = ColumnFormatUtil.underLineName(entityName);
        //正如前面说到的，数据库列名统一以“_”开始，主键以表名加上“_id” 如user表主键即“_user_id”
        return  underlineName + PRI_SUFFIX;
    }

    /**
     * 根据属性名获取列名
     *
     * @param fieldName
     * @return
     */
    @Override
    public String getColumnName(String fieldName) {
        String underlineName = ColumnFormatUtil.underLineName(fieldName);
        //数据库列名统一以“_”开始
        return  underlineName;
    }
}