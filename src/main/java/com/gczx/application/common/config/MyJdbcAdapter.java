package com.gczx.application.common.config;

import com.gczx.application.common.exception.BaseBusinessException;
import com.gczx.application.entity.CasbinRuleEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.casbin.jcasbin.model.Assertion;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.Helper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * 实现casbin Adapter接口，适配达梦数据库
 * @author leifeijin
 */
@Slf4j
public class MyJdbcAdapter implements Adapter {
    private static final String DATABASE_ORACLE = "Oracle";
    private static final String DATABASE_SQL_SERVER = "Microsoft SQL Server";
    private static final String DATABASE_POSTGRE_SQL = "PostgreSQL";
    private static final String DATABASE_DM = "DM DBMS";

    private DataSource dataSource = null;

    /**
     * 构造方法
     * @param driver   数据驱动, dm.jdbc.driver.DmDriver
     * @param url      数据库url, jdbc:dm://192.168.62.201:5236/DAMENG
     * @param username 用户名
     * @param password 密码
     */
    public MyJdbcAdapter(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
        this(new MyDataSource(driver, url, username, password));
    }

    /**
     * 重载构造方法
     * @param dataSource jdbc数据源
     */
    public MyJdbcAdapter(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        migrate();
    }

    /**
     * casbin表初始化方法,如果表不存在casbin_rule则新增，存在则无操作
     * @throws SQLException sql异常
     */
    private void migrate() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            boolean dmFlag = true;
            String sql = "CREATE TABLE IF NOT EXISTS casbin_rule(id int NOT NULL PRIMARY KEY auto_increment, ptype VARCHAR(100) NOT NULL, v0 VARCHAR(100), v1 VARCHAR(100), v2 VARCHAR(100), v3 VARCHAR(100), v4 VARCHAR(100), v5 VARCHAR(100))";
            String productName = conn.getMetaData().getDatabaseProductName();

            switch (productName) {
                case DATABASE_ORACLE:
                    sql = "declare begin execute immediate 'CREATE TABLE casbin_rule(id NUMBER(5, 0) not NULL primary key, ptype VARCHAR(100) not NULL, v0 VARCHAR(100), v1 VARCHAR(100), v2 VARCHAR(100), v3 VARCHAR(100), v4 VARCHAR(100), v5 VARCHAR(100))'; " +
                            "exception when others then " +
                            "if SQLCODE = -955 then " +
                            "null; " +
                            "else raise; " +
                            "end if; " +
                            "end;";
                    break;
                case DATABASE_SQL_SERVER:
                    sql = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='casbin_rule' and xtype='U') CREATE TABLE casbin_rule(id int NOT NULL primary key identity(1, 1), ptype VARCHAR(100) NOT NULL, v0 VARCHAR(100), v1 VARCHAR(100), v2 VARCHAR(100), v3 VARCHAR(100), v4 VARCHAR(100), v5 VARCHAR(100))";
                    break;
                case DATABASE_POSTGRE_SQL:
                    sql = "CREATE SEQUENCE IF NOT EXISTS CASBIN_SEQUENCE START 1;";
                    break;
                case DATABASE_DM:
                    dmFlag = false;
                    sql = "CREATE TABLE casbin_rule(id INT IDENTITY(1, 1) NOT NULL PRIMARY KEY, ptype VARCHAR2(255),v0 VARCHAR2(255),v1 VARCHAR2(255),v2 VARCHAR2(255),v3 VARCHAR2(255),v4 VARCHAR2(255),v5 VARCHAR2(255))";
                    DatabaseMetaData meta = conn.getMetaData();
                    String[] type = {"TABLE"};
                    String tableName = "casbin_rule";
                    ResultSet rs = meta.getTables(null, null, tableName, type);
                    Set<String> tableNames = new TreeSet<>();
                    while (rs.next()) {
                        String tn = rs.getString("TABLE_NAME");
                        tableNames.add(tn);
                    }
                    if (!tableNames.contains(tableName)) {
                        dmFlag = true;
                    }
                    break;
                default:
                    break;
            }

            if (dmFlag) {
                stmt.executeUpdate(sql);
            }
            if (DATABASE_ORACLE.equals(productName)) {
                sql = "declare " +
                        "V_NUM number;" +
                        "BEGIN " +
                        "V_NUM := 0;  " +
                        "select count(0) into V_NUM from user_sequences where sequence_name = 'CASBIN_SEQUENCE';" +
                        "if V_NUM > 0 then " +
                        "null;" +
                        "else " +
                        "execute immediate 'CREATE SEQUENCE casbin_sequence increment by 1 start with 1 nomaxvalue nocycle nocache';" +
                        "end if;END;";
                stmt.executeUpdate(sql);
                sql = "declare " +
                        "V_NUM number;" +
                        "BEGIN " +
                        "V_NUM := 0;" +
                        "select count(0) into V_NUM from user_triggers where trigger_name = 'CASBIN_ID_AUTOINCREMENT';" +
                        "if V_NUM > 0 then " +
                        "null;" +
                        "else " +
                        "execute immediate 'create trigger casbin_id_autoincrement before " +
                        "                        insert on casbin_rule for each row " +
                        "                        when (new.id is null) " +
                        "                        begin " +
                        "                        select casbin_sequence.nextval into:new.id from dual;" +
                        "                        end;';" +
                        "end if;" +
                        "END;";
                stmt.executeUpdate(sql);
            } else if (DATABASE_POSTGRE_SQL.equals(productName)) {
                sql = "CREATE TABLE IF NOT EXISTS casbin_rule(id int NOT NULL PRIMARY KEY default nextval('CASBIN_SEQUENCE'::regclass), ptype VARCHAR(100) NOT NULL, v0 VARCHAR(100), v1 VARCHAR(100), v2 VARCHAR(100), v3 VARCHAR(100), v4 VARCHAR(100), v5 VARCHAR(100))";
                stmt.executeUpdate(sql);
            }
        }
    }

    /**
     * 加载策略
     * @param line  casbin规则
     * @param model 策略模型
     */
    private void loadPolicyLine(CasbinRuleEntity line, Model model) {
        String lineText = line.ptype;
        if (StringUtils.isNotBlank(line.v0)) {
            lineText += ", " + line.v0;
        }
        if (StringUtils.isNotBlank(line.v1)) {
            lineText += ", " + line.v1;
        }
        if (StringUtils.isNotBlank(line.v2)) {
            lineText += ", " + line.v2;
        }
        if (StringUtils.isNotBlank(line.v3)) {
            lineText += ", " + line.v3;
        }
        if (StringUtils.isNotBlank(line.v4)) {
            lineText += ", " + line.v4;
        }
        if (StringUtils.isNotBlank(line.v5)) {
            lineText += ", " + line.v5;
        }

        Helper.loadPolicyLine(lineText, model);
    }

    /**
     * loadPolicy loads all policy rules from the storage.
     */
    @Override
    public void loadPolicy(Model model) {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rSet = stmt.executeQuery("SELECT * FROM casbin_rule")
        ) {
            ResultSetMetaData rData = rSet.getMetaData();
            while (rSet.next()) {
                CasbinRuleEntity line = new CasbinRuleEntity();
                for (int i = 1; i <= rData.getColumnCount(); i++) {
                    if (i == 1) {
                        // 1：表示第一列id
                        line.id = rSet.getObject(i) == null ? null : Long.valueOf(rSet.getObject(i).toString());
                    } else if (i == 2) {
                        // 2:表示第二列ptype
                        line.ptype = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 3) {
                        // 3:表示第二列v0
                        line.v0 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 4) {
                        // 4:表示第二列v1
                        line.v1 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 5) {
                        // 5:表示第二列v2
                        line.v2 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 6) {
                        // 6:表示第二列v3
                        line.v3 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 7) {
                        // 7:表示第二列v4
                        line.v4 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 8) {
                        // 8:表示第二列v5
                        line.v5 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    }
                }
                loadPolicyLine(line, model);
            }
        } catch (SQLException e) {
            throw new BaseBusinessException(e.getMessage());
        }
    }

    private CasbinRuleEntity savePolicyLine(String ptype, List<String> rule) {
        CasbinRuleEntity line = new CasbinRuleEntity();

        line.ptype = ptype;
        if (!rule.isEmpty()) {
            line.v0 = rule.get(0);
        }
        if (rule.size() > 1) {
            line.v1 = rule.get(1);
        }
        if (rule.size() > 2) {
            line.v2 = rule.get(2);
        }
        if (rule.size() > 3) {
            line.v3 = rule.get(3);
        }
        if (rule.size() > 4) {
            line.v4 = rule.get(4);
        }
        if (rule.size() > 5) {
            line.v5 = rule.get(5);
        }

        return line;
    }

    /**
     * savePolicy saves all policy rules to the storage.
     */
    @Override
    public void savePolicy(Model model) {
        String cleanSql = "delete from casbin_rule";
        String addSql = "INSERT INTO casbin_rule (ptype,v0,v1,v2,v3,v4,v5) VALUES(?,?,?,?,?,?,?)";

        try (
                Connection conn = dataSource.getConnection();
                Statement statement = conn.createStatement();
                PreparedStatement ps = conn.prepareStatement(addSql)
        ) {
            conn.setAutoCommit(false);

            final int batchSize = 1000;
            int count = 0;
            statement.execute(cleanSql);
            for (Map.Entry<String, Assertion> entry : model.model.get("p").entrySet()) {
                String ptype = entry.getKey();
                Assertion ast = entry.getValue();

                for (List<String> rule : ast.policy) {
                    CasbinRuleEntity line = savePolicyLine(ptype, rule);
                    ps.setString(1, line.ptype);
                    ps.setString(2, line.v0);
                    ps.setString(3, line.v1);
                    ps.setString(4, line.v2);
                    ps.setString(5, line.v3);
                    ps.setString(6, line.v4);
                    ps.setString(7, line.v5);
                    ps.addBatch();
                    if (++count % batchSize == 0) {
                        ps.executeBatch();
                    }
                }
            }
            for (Map.Entry<String, Assertion> entry : model.model.get("g").entrySet()) {
                String ptype = entry.getKey();
                Assertion ast = entry.getValue();
                for (List<String> rule : ast.policy) {
                    CasbinRuleEntity line = savePolicyLine(ptype, rule);
                    ps.setString(1, line.ptype);
                    ps.setString(2, line.v0);
                    ps.setString(3, line.v1);
                    ps.setString(4, line.v2);
                    ps.setString(5, line.v3);
                    ps.setString(6, line.v4);
                    ps.setString(7, line.v5);

                    ps.addBatch();
                    if (++count % batchSize == 0) {
                        ps.executeBatch();
                    }
                }
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            log.error("MyJdbcAdapter.savePolicy:" + e.getMessage(), e);
            throw new BaseBusinessException(e.getMessage());
        }
    }

    /**
     * addPolicy adds a policy rule to the storage.
     */
    @Override
    public void addPolicy(String sec, String ptype, List<String> rule) {
        if (null == rule || rule.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO casbin_rule (ptype,v0,v1,v2,v3,v4,v5) VALUES(?,?,?,?,?,?,?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            CasbinRuleEntity line = savePolicyLine(ptype, rule);
            ps.setString(1, line.ptype);
            ps.setString(2, line.v0);
            ps.setString(3, line.v1);
            ps.setString(4, line.v2);
            ps.setString(5, line.v3);
            ps.setString(6, line.v4);
            ps.setString(7, line.v5);
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            log.error("MyJdbcAdapter.addPolicy:" + e.getMessage(), e);
            throw new BaseBusinessException(e.getMessage());
        }
    }

    /**
     * removePolicy removes a policy rule from the storage.
     */
    @Override
    public void removePolicy(String sec, String ptype, List<String> rule) {
        if (null == rule || rule.isEmpty()) {
            return;
        }
        removeFilteredPolicy(sec, ptype, 0, rule.toArray(new String[0]));
    }

    /**
     * removeFilteredPolicy removes policy rules that match the filter from the storage.
     */
    @Override
    public void removeFilteredPolicy(String sec, String ptype, int fieldIndex, String... fieldValues) {
        List<String> values = Optional.of(Arrays.asList(fieldValues)).orElse(new ArrayList<>());
        if (values.isEmpty()) {
            return;
        }
        String sql = "DELETE FROM casbin_rule WHERE ptype = ?";
        int columnIndex = fieldIndex;
        for (int i = 0; i < values.size(); i++) {
            sql = String.format("%s%s%s%s", sql, " AND v", columnIndex, " = ?");
            columnIndex++;
        }
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ptype);
            for (int j = 0; j < values.size(); j++) {
                ps.setString(j + 2, values.get(j));
            }
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            log.error("MyJdbcAdapter.removeFilteredPolicy:" + e.getMessage(), e);
            throw new BaseBusinessException(e.getMessage());
        }
    }
}
