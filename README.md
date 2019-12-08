# Generator
mybatis-plus代码生成器

# 使用说明
+ 在CodeGenerator.java中设置数据库url+username+password
+ 执行CodeGenerator.java文件
+ 先输入模块(就是生成到com下的哪个文件夹下)
+ 输入以逗号分隔多表的字符串,如:      user,user_info,project
+ generate.py 是生成controller层的程序，支持命令行生成   例: python generate.py entity
