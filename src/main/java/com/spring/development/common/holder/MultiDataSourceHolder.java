package com.spring.development.common.holder;

import com.spring.development.common.enums.DataSourceEnum;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 多数据源控制器
 * @Project development
 * @Package com.spring.development.datasource
 * @Author xuzhenkui
 * @Date 2020/2/26 18:27
 */
public class MultiDataSourceHolder {

    private static final ConcurrentHashMap<String, DataSource> MAP = new ConcurrentHashMap<>();

    /**
     * 根据数据源名称获取数据源
     * @param dsName 数据源名称
     * @return
     */
    public static DataSource getDataSource(String dsName){
        if (StringUtils.hasText(dsName)){
            dsName = dsName.toUpperCase();
            return MAP.get(dsName);
        }
        return null;
    }

    /**
     * 添加数据源
     * @param dsName 数据源名称
     * @param dataSource 数据源
     */
    public static Boolean addDataSource(String dsName, DataSource dataSource){
        if (StringUtils.hasText(dsName) && dataSource != null){
            dsName = dsName.toUpperCase();
//        如果传入key对应的value已经存在，就返回存在的value，不进行替换。如果不存在，就添加key和value，返回null
            return MAP.putIfAbsent(dsName, dataSource) == null;
        }
        return false;
    }

    /**
     * 修改数据源
     * @param dsName 数据源名称
     * @param dataSource 数据源
     */
    public static Boolean modifyDataSource(String dsName, DataSource dataSource){
        if (StringUtils.hasText(dsName) && dataSource != null){
            dsName = dsName.toUpperCase();
            MAP.put(dsName, dataSource);
            return true;
        }
        return false;
    }

    /**
     * 删除数据源
     * @param dsName 数据源名称
     * @param dataSource 数据源
     */
    public static Boolean removeDataSource(String dsName, DataSource dataSource){
        if (StringUtils.hasText(dsName) && dataSource != null){
            dsName = dsName.toUpperCase();
            MAP.remove(dsName);
            return true;
        }
        return false;
    }

    /**
     * 构建数据源
     * @param dbType   数据库类型
     * @param ip       数据库地址
     * @param port     数据库端口
     * @param instance 数据库
     * @param username 用户名
     * @param password 密码
     * @param etc      预留尾部参数配置
     * @return
     */
    public static DataSource buildDataSource(String dbType, String ip, String port, String instance, String username,String password, String etc){
        dbType = dbType.toUpperCase();

        String driverClassName;
        String url;

        if (dbType.equals(DataSourceEnum.MYSQL.getDbType())){
            driverClassName = DataSourceEnum.MYSQL.getDriverClassName();
            url = DataSourceEnum.MYSQL.getPrefix() + ip + ":" + port + "/" + instance + DataSourceEnum.MYSQL.getSuffix() + etc;
        } else if(dbType.equals(DataSourceEnum.ORACLE.getDbType())) {
            driverClassName = DataSourceEnum.ORACLE.getDriverClassName();
            url = DataSourceEnum.ORACLE.getPrefix() + ip + ":" + port + ":" + instance + DataSourceEnum.ORACLE.getSuffix() + etc;
        } else {
            return null;
        }

        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password).build();
    }
}
