package com.gczx.application.common.config;

import com.gczx.application.common.exception.BaseBusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.casbin.jcasbin.model.Assertion;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.Helper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

class CasbinRule {
    Long id;
    String ptype;
    String v0;
    String v1;
    String v2;
    String v3;
    String v4;
    String v5;
}

/**
 * @Author: leifeijin
 * @Date: 2020/9/21
 * @Description: 实现casbin Adapter接口，适配达梦数据库
 */
public class MyJdbcAdapter implements Adapter {
    private DataSource dataSource = null;

    /**
     * 构造方法
     *
     * @param driver   数据驱动, dm.jdbc.driver.DmDriver
     * @param url      数据库url, jdbc:dm://192.168.62.201:5236/DAMENG
     * @param username 用户名
     * @param password 密码
     */
    public MyJdbcAdapter(String driver, String url, String username, String password) throws Exception {
        this(new MyDataSource(driver, url, username, password));
    }

    /**
     * 重载构造方法
     *
     * @param dataSource jdbc数据源
     */
    public MyJdbcAdapter(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        migrate();
    }

    /**
     * casbin表初始化方法,如果表不存在casbin_rule则新增，存在则无操作
     *
     * @throws SQLException
     */
    private void migrate() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            String sql = "CREATE TABLE IF NOT EXISTS casbin_rule(ptype VARCHAR(100) NOT NULL, v0 VARCHAR(100), v1 VARCHAR(100), v2 VARCHAR(100), v3 VARCHAR(100), v4 VARCHAR(100), v5 VARCHAR(100))";
            String productName = conn.getMetaData().getDatabaseProductName();

            if ("Oracle".equals(productName)) {
                sql = "declare begin execute immediate 'CREATE TABLE CASBIN_RULE(ptype VARCHAR(100) not NULL, v0 VARCHAR(100), v1 VARCHAR(100), v2 VARCHAR(100), v3 VARCHAR(100), v4 VARCHAR(100), v5 VARCHAR(100))'; exception when others then if SQLCODE = -955 then null; else raise; end if; end;";
                stmt.executeUpdate(sql);
            } else if ("Microsoft SQL Server".equals(productName)) {
                sql = "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='casbin_rule' and xtype='U') CREATE TABLE casbin_rule(ptype VARCHAR(100) NOT NULL, v0 VARCHAR(100), v1 VARCHAR(100), v2 VARCHAR(100), v3 VARCHAR(100), v4 VARCHAR(100), v5 VARCHAR(100))";
                stmt.executeUpdate(sql);
            } else if ("DM DBMS".equals(productName)) {
                sql = "CREATE TABLE casbin_rule(ptype VARCHAR(100) NOT NULL, v0 VARCHAR(100), v1 VARCHAR(100), v2 VARCHAR(100), v3 VARCHAR(100), v4 VARCHAR(100), v5 VARCHAR(100))";
                DatabaseMetaData meta = conn.getMetaData();
                String[] type = {"TABLE"};
                ResultSet rs = meta.getTables(null, null, "casbin_rule", type);
                boolean isExist = rs.next();
                if (!isExist) {
                    stmt.executeUpdate(sql);
                }
            } else {
                stmt.executeUpdate(sql);
            }
        }
    }

    /**
     * 加载策略
     * @param line  casbin规则
     * @param model
     */
    private void loadPolicyLine(CasbinRule line, Model model) {
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
                CasbinRule line = new CasbinRule();
                for (int i = 1; i <= rData.getColumnCount(); i++) {
                    if (i == 1) {
                        line.id = rSet.getObject(i) == null ? null : Long.valueOf(rSet.getObject(i).toString());
                    } else if (i == 2) {
                        line.ptype = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 3) {
                        line.v0 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 4) {
                        line.v1 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 5) {
                        line.v2 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 6) {
                        line.v3 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 7) {
                        line.v4 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    } else if (i == 8) {
                        line.v5 = rSet.getObject(i) == null ? "" : (String) rSet.getObject(i);
                    }
                }
                loadPolicyLine(line, model);
            }
        } catch (SQLException e) {
            throw new BaseBusinessException(e.getMessage());
        }
    }

    private CasbinRule savePolicyLine(String ptype, List<String> rule) {
        CasbinRule line = new CasbinRule();

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

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            final int batchSize = 1000;
            int count = 0;

            try (
                    Statement statement = conn.createStatement();
                    PreparedStatement ps = conn.prepareStatement(addSql)
            ) {
                statement.execute(cleanSql);
                for (Map.Entry<String, Assertion> entry : model.model.get("p").entrySet()) {
                    String ptype = entry.getKey();
                    Assertion ast = entry.getValue();

                    for (List<String> rule : ast.policy) {
                        CasbinRule line = savePolicyLine(ptype, rule);
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
                        CasbinRule line = savePolicyLine(ptype, rule);
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
                conn.rollback();

                e.printStackTrace();
                throw new Error(e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    /**
     * addPolicy adds a policy rule to the storage.
     */
    @Override
    public void addPolicy(String sec, String ptype, List<String> rule) {
        if (CollectionUtils.isEmpty(rule)) {
            return;
        }
        String sql = "INSERT INTO casbin_rule (ptype,v0,v1,v2,v3,v4,v5) VALUES(?,?,?,?,?,?,?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            CasbinRule line = savePolicyLine(ptype, rule);
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
            e.printStackTrace();
            throw new Error(e);
        }
    }

    /**
     * removePolicy removes a policy rule from the storage.
     */
    @Override
    public void removePolicy(String sec, String ptype, List<String> rule) {
        if (CollectionUtils.isEmpty(rule)) {
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
        if (CollectionUtils.isEmpty(values)) {
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
            e.printStackTrace();
            throw new Error(e);
        }
    }
}
