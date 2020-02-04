package com;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    static String author = "fish";
    static String userDir = "user.dir";
    static String javaDir = "/src/main/java";
    static String url = "jdbc:mysql://127.0.0.1:3306/login?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
    static String driverName = "com.mysql.jdbc.Driver";
    static String username = "root";
    static String password = "4b3c1c04ea63";
    static String package_parent = "com";
    //com和mapper下的包名
    static String moduleName = "";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty(userDir);
        globalConfig.setOutputDir(projectPath + javaDir);
        globalConfig.setAuthor(author);
        globalConfig.setServiceName("I%sService");
        globalConfig.setServiceImplName("%sService");
        globalConfig.setIdType(IdType.ID_WORKER);
        globalConfig.setOpen(false);
        globalConfig.setBaseColumnList(true);
        globalConfig.setFileOverride(true);
        globalConfig.setBaseResultMap(true);
        globalConfig.setActiveRecord(true);
        // globalConfig.setSwagger2(true); 实体属性 Swagger2 注解

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(url);
        // dataSourceConfig.setSchemaName("public");
        dataSourceConfig.setDriverName(driverName);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(moduleName);
        packageConfig.setParent(package_parent);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setServiceImpl("templates/serviceImpl.java");
        templateConfig.setService("templates/service.java");
        templateConfig.setController("templates/controller.java");
        // templateConfig.setXml("templates/mapper.xml");
        templateConfig.setXml(null);

        autoGenerator.setTemplate(templateConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        // 自定义配置会被优先输出
        fileOutConfigs.add(new FileOutConfig("templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        //此注释代码块可以从自定义模板生成代码到自定义路径
//        fileOutConfigs.add(new FileOutConfig("templates/diy.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "DIY" + StringPool.DOT_XML;
//            }
//        });
        injectionConfig.setFileOutConfigList(fileOutConfigs);
        autoGenerator.setCfg(injectionConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);//开启数据表名下划线转java的Entity驼峰命名
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);//开启数据表的字段名下划线转java的字段驼峰命名
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);//Boolean类型字段是否移除is前缀（默认 false）<br>
        // strategyConfig.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategyConfig.setEntityLombokModel(true);//设置使用Lombok注解,@Data
        strategyConfig.setEntityTableFieldAnnotationEnable(true);//开启表字段注解
        strategyConfig.setRestControllerStyle(true);
        // 公共父类
        // strategyConfig.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
        // strategyConfig.setSuperEntityColumns("id");
        strategyConfig.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");

        //设置全局配置
        autoGenerator.setGlobalConfig(globalConfig);
        //设置数据源
        autoGenerator.setDataSource(dataSourceConfig);
        //设置包
        autoGenerator.setPackageInfo(packageConfig);
        //设置策略
        autoGenerator.setStrategy(strategyConfig);
        //设置模板引擎
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        //执行
        autoGenerator.execute();
    }
}
