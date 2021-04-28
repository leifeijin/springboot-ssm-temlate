package com.gczx.application.common.utils;

import com.gczx.application.common.properties.DatabaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: leifeijin
 * @Date: 2020/9/25
 * @Description:
 */
@Slf4j
@Component
public class RunSqlScriptUtil {

    private static DatabaseProperties databaseProperties;

    @Autowired
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        RunSqlScriptUtil.databaseProperties = databaseProperties;
    }

    /**
     * 运行指定的sql脚本
     */
    public static void run() {
        try (
                // 建立数据库相关连接
                Connection conn = DriverManager.getConnection(databaseProperties.getUrl(), databaseProperties.getUsername(), databaseProperties.getPassword());
        ) {

            // 创建ScriptRunner，用于执行SQL脚本
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            log.info("======开始执行SQL脚本======");
            // 执行SQL脚本
            runner.runScript(Resources.getResourceAsReader("sql/initDB.sql"));
            // Connection已经实现AutoCloseable，无需手动关闭
            log.info("======结束执行SQL脚本======");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
