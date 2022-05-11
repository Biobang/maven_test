package com.atguigu.imperial.court.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @description:
 *   功能1：从数据源获取数据库连接
 *   功能2：从数据库获取到数据库连接后，绑定到本地线程（借助 ThreadLocal）
 *   功能3：释放线程时和本地线程解除绑定
 * @author: 1223
 **/

public class JDBCUtils {
    private static DataSource dataSource;

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    static {
        try {

            InputStream stream = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(stream);
             dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() {
        Connection connection = null;

        //尝试从当前线程检查是否存在已获取Connection绑定
        try {
            connection = threadLocal.get();

            if(connection == null){
                //如果connection为空，则从数据源获取连接
                connection = dataSource.getConnection();

                //然后在本地线程加入连接
                threadLocal.set(connection);

            }
        } catch (Exception e) {
            // 为了调用工具方法方便，编译时异常不往外抛
            // 为了不掩盖问题，捕获到的编译时异常封装为运行时异常抛出
            e.printStackTrace();
            throw new RuntimeException(e);
        }
return connection;
    }
//释放数据库连接
    public static void releaseConnection(Connection connection){
        if(connection !=null){
            try {
                // 在数据库连接池中将当前连接对象标记为空闲
                connection.close();
                // 将当前数据库连接从当前线程上移除
                threadLocal.remove();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }


        }

    }
}
