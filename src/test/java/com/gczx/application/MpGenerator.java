package com.gczx.application;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.gczx.application.entity.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class MpGenerator {
    //获取项目目录
    private static final String projectPath = System.getProperty("user.dir");

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 1.全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        //是否生成完成后打开资源管理器
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setDateType(DateType.ONLY_DATE); // 日期字段类型默认是java8的日期类型，改为 java.util.date
        // 设置用户名
        gc.setAuthor(System.getProperty("user.name"));
        // 设置主键自增
        gc.setIdType(IdType.AUTO);

        // 自定义文件命名，占位符 %s 会自动填充表实体属性
        gc.setMapperName("I%sMapper");              // mapper接口命名方式
        gc.setXmlName("%sMapper");                  // mapper.xml命名方式
        gc.setServiceName("I%sService");            // service接口命名方式
        gc.setServiceImplName("%sServiceImpl");     // service接口实现类命名方式
        gc.setControllerName("%sController");       // controller命名方式
        gc.setEntityName("%sEntity");               // 实体类命名方式
        mpg.setGlobalConfig(gc);

        // 2.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("admin123");
        dsc.setUrl("jdbc:mysql://10.100.2.1:3306/template?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
        mpg.setDataSource(dsc);

        // 3.包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.gczx.application");
//        pc.setModuleName("");
        pc.setController("controller");
        pc.setMapper("service.dao");
        pc.setEntity("entity");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig in = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                //自定义配置，在模版中cfg.superColums 获取
                map.put("superColums", this.getConfig().getStrategyConfig().getSuperEntityColumns());
                this.setMap(map);
            }
        };
        in.setFileOutConfigList(CollectionUtil.newArrayList(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义mapper xml输出目录
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getXmlName() + StringPool.DOT_XML;
            }
        }));
        mpg.setCfg(in);

        // 4.策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 设置RestController
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix("idpm_");   // 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);    // 字段名生成策略
        strategy.setInclude("idpm_permission");     // 需要生成的表
        // 自定义实体父类
        strategy.setSuperEntityClass(BaseEntity.class);
        // 自定义实体，公共字段
        strategy.setSuperEntityColumns("create_time", "update_time");
        strategy.setEntitySerialVersionUID(true);
        // 是否开启lombok
        strategy.setEntityLombokModel(true);
        // 自定义 service 父类
        strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
        // 自定义 service 父类实现类
        strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        // 自定义 mapper 父类
        strategy.setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        // 设置生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        mpg.setStrategy(strategy);

        // 5.模板设置
        TemplateConfig templateConfig = new TemplateConfig();
        // 不生成controller
        templateConfig.setController(null);
        // 不生成xml文件
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 选择 freemarker 引擎，默认 Velocity
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 执行生成
        mpg.execute();
    }

}