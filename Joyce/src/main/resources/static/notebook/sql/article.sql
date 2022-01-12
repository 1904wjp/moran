/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-01-12 11:23:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `author` varchar(12) DEFAULT NULL COMMENT '作者名',
  `class_id` bigint(32) DEFAULT NULL COMMENT '分类主键',
  `content` text COMMENT '文章的内容',
  `pv_content` text COMMENT '上一版本文章的内容',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='文章编辑表';

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', 'xdr', '2021-12-18 21:00:47', '0', 'xdr', '2021-12-19 14:33:23', 'xdr', null, '# 错误：找不到或者无法加载主类xxx\n![bug图片](/static/1/img/photo/20211219/98788a4df7b4e51ce5f19ff6e9_16398941 \"bug图片\")\n解决方案：\n#### 一、File—》Invalidate Caches/Restart 选择Invalidate and Restart 或者 只是Invalidate，清除掉缓存，然后 >Rebuild Project\n![示例图片](/static/1/img/photo/20211219/ba9b7349dab258ca1ce5493d5e_16398944 \"实例图片\")\n\n![示例图片](/static/1/img/photo/20211219/051f7e41ccb575eab5907fd95e_16398947 \"示例图片\")\n#### 二、file—>Project Structure 然后 点击Project Settings中的Moudles—>点击减号将所有的Moudle删除-》点击+号重新引入Moudle-》找到项目的父文件夹—>OK（一直NEXT即可）（其他的项目可以寻找pom文件）-》Rebuild Project\n\n![示例图片](/static/1/img/photo/20211219/5efc7c44ed8812abcc90500dc2_1b81c954 \"示例图片\")\n\n![示例图片](/static/1/img/photo/20211219/7138c1415cb2f3582cfb2e32c2_a484b5be \"示例图片\")\n\n![示例图片](/static/1/img/photo/20211219/2e1c9c4c3db22d186da0ef0443_ad42fa14 \"示例图片\")\n\n![示例图片](/static/1/img/photo/20211219/1404ac436496e82899169fc472_cc6cfef7 \"示例图片\")\n\n![示例图片](/static/1/img/photo/20211219/31602748c18b415a34ec907c42_e229fbea \"示例图片\")\n\n![示例图片](/static/1/img/photo/20211219/3623ee4b37b9f409ee34c03473_ed8c3300 \"示例图片\")\n', null, '错误：找不到或者无法加载主类xxx', '1');
INSERT INTO `article` VALUES ('2', 'xdr', '2021-12-19 20:54:05', '0', 'xdr', '2021-12-22 14:35:04', 'xdr', null, '#  Python笔记（1）：中文编码\n中文使用\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\n#中文需要引入utf-8，否则报错 \nprint( \"你好，世界\" )\n```\n软件的使用\n\n![](/static/1/img/photo/20211219/628c5047649607ff9e370c4992_47.png)', '#  Python笔记（1）：中文编码\n中文使用\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\n#中文需要引入utf-8，否则报错 \nprint( \"你好，世界\" )\n```\n软件的使用\n\n![](/static/1/img/photo/20211219/628c5047649607ff9e370c4992_47.png)', 'Python笔记（1）：中文编码', '1');
INSERT INTO `article` VALUES ('3', 'xdr', '2021-12-19 21:13:25', '0', 'xdr', '2021-12-22 14:27:27', 'xdr', null, '#  python(2):变量\n1.赋值\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\n \ncounter = 100 # 赋值整型变量\nmiles = 1000.0 # 浮点型\nname = \"John\" # 字符串\n \nprint counter\nprint miles\nprint name\n```\n打印\n```\n100\n1000.0\nJohn\n```\n多变量赋值\n创建一个整型对象，值为1，三个变量被分配到相同的内存空间上。\n```python\na = b = c = 1\n```\n多个对象指定多个变量\n```python\na, b, c = 1, 2, \"john\"\n```\n### 标准数据类型\n在内存中存储的数据可以有多种类型。\n例如，一个人的年龄可以用数字来存储，他的名字可以用字符来存储。\nPython 定义了一些标准类型，用于存储各种类型的数据。\nPython有五个标准的数据类型：\n```\n    Numbers（数字）\n    String（字符串）\n    List（列表）\n    Tuple（元组）\n    Dictionary（字典）\n```\n##### python数字类型\n不可改变的数据类型，这意味着改变数字数据类型会分配一个新的对象。\n当你指定一个值时，Number 对象就会被创建：\n```python\nvar1 = 1\nvar2 = 10\n```\ndel语句删除单个或多个对象的引用\n```python\ndel var\ndel var_a, var_b\n\n```\n###### Python支持四种不同的数字类型：\n```python\nint（有符号整型）\nlong（长整型，也可以代表八进制和十六进制）\nfloat（浮点型）\ncomplex（复数）\n```\n![](/static/1/img/photo/20211219/ae68f347a3a4d3886a9ced0ff3_05.png)\n长整型也可以使用小写 l，但是还是建议您使用大写 L，避免与数字 1 混淆。Python使用 L 来显示长整型。\nPython 还支持复数，复数由实数部分和虚数部分构成，可以用 a + bj,或者 complex(a,b) 表示， 复数的实部 a 和虚部 b 都是浮点型。\n```\n\n注意：long 类型只存在于 Python2.X 版本中，在 2.2 以后的版本中，int 类型数据溢出后会自动转为long类型。在 Python3.X 版本中 long 类型被移除，使用 int 替代。\n\n```\n\n##### ##### Python字符串\n字符串或串(String)是由数字、字母、下划线组成的一串字符。\n```python\ns = \"a1a2···an\"   # n>=0\n```\npython的字串列表有2种取值顺序:\n\n从左到右索引默认0开始的，最大范围是字符串长度少1\n从右到左索引默认-1开始的，最大范围是字符串开头\n![](/static/1/img/photo/20211221/249c4a439aa064790918f3a2e9_1).png)\n\n如果你要实现从字符串中获取一段子字符串的话，可以使用 [头下标:尾下标] 来截取相应的字符串，其中下标是从 0 开始算起，可以是正数或负数，下标可以为空表示取到头或尾。\n[头下标:尾下标] 获取的子字符串包含头下标的字符，但不包含尾下标的字符。\n```python\n>>> s = \'abcdef\'\n>>> s[1:5]\n\'bcde\'\n```\n也可以从后往前截取，不过是从-1开始\n![](/static/1/img/photo/20211221/1c9e704ca2895588090d75388e_aU.png)\n\n加号（+）是字符串连接运算符，星号（*）是重复操作\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\nstr = \'Hello World!\'\nprint str           # 输出完整字符串\nprint str[0]        # 输出字符串中的第一个字符\nprint str[2:5]      # 输出字符串中第三个至第六个之间的字符串\nprint str[2:]       # 输出从第三个字符开始的字符串\nprint str * 2       # 输出字符串两次\nprint str + \"TEST\"  # 输出连接的字符串\n```\n答应结果\n```\nHello World!\nH\nllo\nllo World!\nHello World!Hello World!\nHello World!TEST\n```\nletters的使用\n\nletters(begin,end,stemp)\nbegin: 第一个数\nend: 结尾数\nstemp: 步长 \n从begin开始，end结束(不包括end)，截取begin到end中begin下标+n*temp，包括begin不包括end的数据，且begin下表 + n * temp < end下表\n![](/static/1/img/photo/20211221/790728417abb73279b94fff27d__2.png)\n\n##### Python列表a(感觉就是平时见到的数组)\nList（列表） 是 Python 中使用最频繁的数据类型。\n列表可以完成大多数集合类的数据结构实现。它支持字符，数字，字符串甚至可以包含列表（即嵌套）。\n列表用 [ ] 标识，是 python 最通用的复合数据类型。\n列表中值的切割也可以用到变量 [头下标:尾下标] ，就可以截取相应的列表，从左到右索引默认 0 开始，从右到左索引默认 -1 开始，下标可以为空表示取到头或尾。\n![](/static/1/img/photo/20211221/c7df2c4d529462f51295d2ee39_w1.png)\n加号 + 是列表连接运算符，星号 * 是重复操作\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\nlist = [ \'runoob\', 786 , 2.23, \'john\', 70.2 ]\ntinylist = [123, \'john\']\nprint list               # 输出完整列表\nprint list[0]            # 输出列表的第一个元素\nprint list[1:3]          # 输出第二个至第三个元素 \nprint list[2:]           # 输出从第三个开始至列表末尾的所有元素\nprint tinylist * 2       # 输出列表两次\nprint list + tinylist    # 打印组合的列表\n```\n```\n打印\n[\'runoob\', 786, 2.23, \'john\', 70.2]\nrunoob\n[786, 2.23]\n[2.23, \'john\', 70.2]\n[123, \'john\', 123, \'john\']\n[\'runoob\', 786, 2.23, \'john\', 70.2, 123, \'john\']\n```\n\n##### Python 元组\n元组是另一个数据类型，类似于 List（列表）。\n元组用 () 标识。内部元素用逗号隔开。但是元组不能二次赋值，相当于只读列表。\n\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\ntuple = ( \'runoob\', 786 , 2.23, \'john\', 70.2 )\ntinytuple = (123, \'john\')\nonetuple = (123,)  ##这是单个元组\nonetuple2 = (123)  ##这是数组\nprint tuple               # 输出完整元组\nprint tuple[0]            # 输出元组的第一个元素\nprint tuple[1:3]          # 输出第二个至第四个（不包含）的元素 \nprint tuple[2:]           # 输出从第三个开始至列表末尾的所有元素\nprint tinytuple * 2       # 输出元组两次\nprint tuple + tinytuple   # 打印组合的元组\n```\n打印\n```\n(\'runoob\', 786, 2.23, \'john\', 70.2)\nrunoob\n(786, 2.23)\n(2.23, \'john\', 70.2)\n(123, \'john\', 123, \'john\')\n(\'runoob\', 786, 2.23, \'john\', 70.2, 123, \'john\')\n```\n\n##### 列表和元组的区别：\n属性上：\n	1.列表是动态数组，可以改变其长度和数据；\n	2.元组是静态数组，其长度和内容不可改变；\n	3.元组静态原因，使用元组不用每次都去访问内核去分配空间;\n应用上:\n	1.列表用于多个相互独立的对象数据集合；\n	2.元组用于一个不会改变的一个或多个属性的对象；\n\n对比：\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\ntuple = ( \'runoob\', 786 , 2.23, \'john\', 70.2 )\nlist = [ \'runoob\', 786 , 2.23, \'john\', 70.2 ]\ntuple[2] = 1000    # 元组中是非法应用\nlist[2] = 1000     # 列表中是合法应用\n```\n##### Python 字典\n字典(dictionary)是除列表以外python之中最灵活的内置数据结构类型。列表是有序的对象集合，字典是无序的对象集合。可以是java中的对象，可以是数字和字符\n两者之间的区别在于：字典当中的元素是通过键来存取的，而不是通过偏移存取。\n字典用\"{ }\"标识。字典由索引(key)和它对应的值value组成。\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\ndict = {}\ndict[\'one\'] = \"This is one\"\ndict[2] = \"This is two\"\ntinydict = {\'name\': \'runoob\',\'code\':6734, \'dept\': \'sales\'}\nprint dict[\'one\']          # 输出键为\'one\' 的值\nprint dict[2]              # 输出键为 2 的值\nprint tinydict             # 输出完整的字典\nprint tinydict.keys()      # 输出所有键\nprint tinydict.values()    # 输出所有值\n```\n打印\n```\nThis is one\nThis is two\n{\'dept\': \'sales\', \'code\': 6734, \'name\': \'runoob\'}\n[\'dept\', \'code\', \'name\']\n[\'sales\', 6734, \'runoob\']\n```\n转换\n将x转换为一个整数\nint(x [,base])\n\n将x转换为一个长整数\nlong(x [,base] )\n\n将x转换到一个浮点数\nfloat(x)\n\n创建一个复数\ncomplex(real [,imag])\n\n将对象 x 转换为字符串\nstr(x)\n\n将对象 x 转换为表达式字符串\nrepr(x)\n\n用来计算在字符串中的有效Python表达式,并返回一个对象\neval(str)\n\n将序列 s 转换为一个元组\ntuple(s)\n\n将序列 s 转换为一个列表\nlist(s)\n\n转换为可变集合\nset(s)\n\n创建一个字典。d 必须是一个序列 (key,value)元组。\ndict(d)\n\n转换为不可变集合\nfrozenset(s)\n\n将一个整数转换为一个字符\nchr(x)\n\n将一个整数转换为Unicode字符\nunichr(x)\n\n将一个字符转换为它的整数值\nord(x)\n\n将一个整数转换为一个十六进制字符串\nhex(x)\n\n将一个整数转换为一个八进制字符串\noct(x)\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', '#  python(2):变量\n1.赋值\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\n \ncounter = 100 # 赋值整型变量\nmiles = 1000.0 # 浮点型\nname = \"John\" # 字符串\n \nprint counter\nprint miles\nprint name\n```\n打印\n```\n100\n1000.0\nJohn\n```\n多变量赋值\n创建一个整型对象，值为1，三个变量被分配到相同的内存空间上。\n```python\na = b = c = 1\n```\n多个对象指定多个变量\n```python\na, b, c = 1, 2, \"john\"\n```\n### 标准数据类型\n在内存中存储的数据可以有多种类型。\n例如，一个人的年龄可以用数字来存储，他的名字可以用字符来存储。\nPython 定义了一些标准类型，用于存储各种类型的数据。\nPython有五个标准的数据类型：\n```\n    Numbers（数字）\n    String（字符串）\n    List（列表）\n    Tuple（元组）\n    Dictionary（字典）\n```\n##### python数字类型\n不可改变的数据类型，这意味着改变数字数据类型会分配一个新的对象。\n当你指定一个值时，Number 对象就会被创建：\n```python\nvar1 = 1\nvar2 = 10\n```\ndel语句删除单个或多个对象的引用\n```python\ndel var\ndel var_a, var_b\n\n```\n###### Python支持四种不同的数字类型：\n```python\nint（有符号整型）\nlong（长整型，也可以代表八进制和十六进制）\nfloat（浮点型）\ncomplex（复数）\n```\n![](/static/1/img/photo/20211219/ae68f347a3a4d3886a9ced0ff3_05.png)\n长整型也可以使用小写 l，但是还是建议您使用大写 L，避免与数字 1 混淆。Python使用 L 来显示长整型。\nPython 还支持复数，复数由实数部分和虚数部分构成，可以用 a + bj,或者 complex(a,b) 表示， 复数的实部 a 和虚部 b 都是浮点型。\n```\n\n注意：long 类型只存在于 Python2.X 版本中，在 2.2 以后的版本中，int 类型数据溢出后会自动转为long类型。在 Python3.X 版本中 long 类型被移除，使用 int 替代。\n\n```\n\n##### ##### Python字符串\n字符串或串(String)是由数字、字母、下划线组成的一串字符。\n```python\ns = \"a1a2···an\"   # n>=0\n```\npython的字串列表有2种取值顺序:\n\n从左到右索引默认0开始的，最大范围是字符串长度少1\n从右到左索引默认-1开始的，最大范围是字符串开头\n![](/static/1/img/photo/20211221/249c4a439aa064790918f3a2e9_1).png)\n\n如果你要实现从字符串中获取一段子字符串的话，可以使用 [头下标:尾下标] 来截取相应的字符串，其中下标是从 0 开始算起，可以是正数或负数，下标可以为空表示取到头或尾。\n[头下标:尾下标] 获取的子字符串包含头下标的字符，但不包含尾下标的字符。\n```python\n>>> s = \'abcdef\'\n>>> s[1:5]\n\'bcde\'\n```\n也可以从后往前截取，不过是从-1开始\n![](/static/1/img/photo/20211221/1c9e704ca2895588090d75388e_aU.png)\n\n加号（+）是字符串连接运算符，星号（*）是重复操作\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\nstr = \'Hello World!\'\nprint str           # 输出完整字符串\nprint str[0]        # 输出字符串中的第一个字符\nprint str[2:5]      # 输出字符串中第三个至第六个之间的字符串\nprint str[2:]       # 输出从第三个字符开始的字符串\nprint str * 2       # 输出字符串两次\nprint str + \"TEST\"  # 输出连接的字符串\n```\n答应结果\n```\nHello World!\nH\nllo\nllo World!\nHello World!Hello World!\nHello World!TEST\n```\nletters的使用\n\nletters(begin,end,stemp)\nbegin: 第一个数\nend: 结尾数\nstemp: 步长 \n从begin开始，end结束(不包括end)，截取begin到end中begin下标+n*temp，包括begin不包括end的数据，且begin下表 + n * temp < end下表\n![](/static/1/img/photo/20211221/790728417abb73279b94fff27d__2.png)\n\n##### Python列表a(感觉就是平时见到的数组)\nList（列表） 是 Python 中使用最频繁的数据类型。\n列表可以完成大多数集合类的数据结构实现。它支持字符，数字，字符串甚至可以包含列表（即嵌套）。\n列表用 [ ] 标识，是 python 最通用的复合数据类型。\n列表中值的切割也可以用到变量 [头下标:尾下标] ，就可以截取相应的列表，从左到右索引默认 0 开始，从右到左索引默认 -1 开始，下标可以为空表示取到头或尾。\n![](/static/1/img/photo/20211221/c7df2c4d529462f51295d2ee39_w1.png)\n加号 + 是列表连接运算符，星号 * 是重复操作\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\nlist = [ \'runoob\', 786 , 2.23, \'john\', 70.2 ]\ntinylist = [123, \'john\']\nprint list               # 输出完整列表\nprint list[0]            # 输出列表的第一个元素\nprint list[1:3]          # 输出第二个至第三个元素 \nprint list[2:]           # 输出从第三个开始至列表末尾的所有元素\nprint tinylist * 2       # 输出列表两次\nprint list + tinylist    # 打印组合的列表\n```\n```\n打印\n[\'runoob\', 786, 2.23, \'john\', 70.2]\nrunoob\n[786, 2.23]\n[2.23, \'john\', 70.2]\n[123, \'john\', 123, \'john\']\n[\'runoob\', 786, 2.23, \'john\', 70.2, 123, \'john\']\n```\n\n##### Python 元组\n元组是另一个数据类型，类似于 List（列表）。\n元组用 () 标识。内部元素用逗号隔开。但是元组不能二次赋值，相当于只读列表。\n\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\ntuple = ( \'runoob\', 786 , 2.23, \'john\', 70.2 )\ntinytuple = (123, \'john\')\nonetuple = (123,)  ##这是单个元组\nonetuple2 = (123)  ##这是数组\nprint tuple               # 输出完整元组\nprint tuple[0]            # 输出元组的第一个元素\nprint tuple[1:3]          # 输出第二个至第四个（不包含）的元素 \nprint tuple[2:]           # 输出从第三个开始至列表末尾的所有元素\nprint tinytuple * 2       # 输出元组两次\nprint tuple + tinytuple   # 打印组合的元组\n```\n打印\n```\n(\'runoob\', 786, 2.23, \'john\', 70.2)\nrunoob\n(786, 2.23)\n(2.23, \'john\', 70.2)\n(123, \'john\', 123, \'john\')\n(\'runoob\', 786, 2.23, \'john\', 70.2, 123, \'john\')\n```\n\n##### 列表和元组的区别：\n属性上：\n	1.列表是动态数组，可以改变其长度和数据；\n	2.元组是静态数组，其长度和内容不可改变；\n	3.元组静态原因，使用元组不用每次都去访问内核去分配空间;\n应用上:\n	1.列表用于多个相互独立的对象数据集合；\n	2.元组用于一个不会改变的一个或多个属性的对象；\n\n对比：\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\ntuple = ( \'runoob\', 786 , 2.23, \'john\', 70.2 )\nlist = [ \'runoob\', 786 , 2.23, \'john\', 70.2 ]\ntuple[2] = 1000    # 元组中是非法应用\nlist[2] = 1000     # 列表中是合法应用\n```\n##### Python 字典\n字典(dictionary)是除列表以外python之中最灵活的内置数据结构类型。列表是有序的对象集合，字典是无序的对象集合。可以是java中的对象，可以是数字和字符\n两者之间的区别在于：字典当中的元素是通过键来存取的，而不是通过偏移存取。\n字典用\"{ }\"标识。字典由索引(key)和它对应的值value组成。\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\ndict = {}\ndict[\'one\'] = \"This is one\"\ndict[2] = \"This is two\"\ntinydict = {\'name\': \'runoob\',\'code\':6734, \'dept\': \'sales\'}\nprint dict[\'one\']          # 输出键为\'one\' 的值\nprint dict[2]              # 输出键为 2 的值\nprint tinydict             # 输出完整的字典\nprint tinydict.keys()      # 输出所有键\nprint tinydict.values()    # 输出所有值\n```\n打印\n```\nThis is one\nThis is two\n{\'dept\': \'sales\', \'code\': 6734, \'name\': \'runoob\'}\n[\'dept\', \'code\', \'name\']\n[\'sales\', 6734, \'runoob\']\n```\n转换\n将x转换为一个整数\nint(x [,base])\n\n将x转换为一个长整数\nlong(x [,base] )\n\n将x转换到一个浮点数\nfloat(x)\n\n创建一个复数\ncomplex(real [,imag])\n\n将对象 x 转换为字符串\nstr(x)\n\n将对象 x 转换为表达式字符串\nrepr(x)\n\n用来计算在字符串中的有效Python表达式,并返回一个对象\neval(str)\n\n将序列 s 转换为一个元组\ntuple(s)\n\n将序列 s 转换为一个列表\nlist(s)\n\n转换为可变集合\nset(s)\n\n创建一个字典。d 必须是一个序列 (key,value)元组。\ndict(d)\n\n转换为不可变集合\nfrozenset(s)\n\n将一个整数转换为一个字符\nchr(x)\n\n将一个整数转换为Unicode字符\nunichr(x)\n\n将一个字符转换为它的整数值\nord(x)\n\n将一个整数转换为一个十六进制字符串\nhex(x)\n\n将一个整数转换为一个八进制字符串\noct(x)\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'python(2):变量', '1');
INSERT INTO `article` VALUES ('4', 'xdr', '2021-12-20 17:06:45', '0', null, null, 'xdr', null, '# nodejs运行命令 nodejs运行命令\n```c\n ###初始化文件（拉去淘宝镜像）\n npm install --registry=https://registry.npm.taobao.org\n  ###运行文件\n npm run dev\n```', null, 'nodejs运行命令 nodejs运行命令', '1');
INSERT INTO `article` VALUES ('5', 'xdr', '2021-12-21 15:36:35', '0', 'xdr', '2021-12-22 14:27:39', 'xdr', null, '# python(3) 运算符\n这一章何其他的编程差不多，简单过一下，特殊的重点提！！！！\n# 算数运算符\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\n \na = 21\nb = 10\nc = 0\n \nc = a + b\nprint \"1 - c 的值为：\", c\n \nc = a - b\nprint \"2 - c 的值为：\", c \n \nc = a * b\nprint \"3 - c 的值为：\", c \n \nc = a / b\nprint \"4 - c 的值为：\", c \n \nc = a % b\nprint \"5 - c 的值为：\", c\n \n# 修改变量 a 、b 、c\na = 2\nb = 3\nc = a**b \nprint \"6 - c 的值为：\", c\n \na = 9\nb = 2\nc = a//b \nprint \"7 - c 的值为：\", c\n```\n打印\n```\n1 - c 的值为： 31\n2 - c 的值为： 11\n3 - c 的值为： 210\n4 - c 的值为： 2\n5 - c 的值为： 1\n6 - c 的值为： 8\n7 - c 的值为： 4\n```\n这里提三个运算符：\n1.%：返回除法的余数，这里a=21,b=10。a除以b得2余1，所以返回1。故a%b=1\n2.**：返回x的y次幂，这里a=2,b=3。a**b就表示a的b次方，也就是2的3次方得8，故a**b=8\n3.//：返回商的整数部分（向下取整）。这里a=9,b=2。a//b就表示a/b得4.5,但是是取整数（4和5），去较小的一方得4，a=-9就取-5\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', '# python(3) 运算符\n这一章何其他的编程差不多，简单过一下，特殊的重点提！！！！\n# 算数运算符\n```python\n#!/usr/bin/python\n# -*- coding: UTF-8 -*-\n \na = 21\nb = 10\nc = 0\n \nc = a + b\nprint \"1 - c 的值为：\", c\n \nc = a - b\nprint \"2 - c 的值为：\", c \n \nc = a * b\nprint \"3 - c 的值为：\", c \n \nc = a / b\nprint \"4 - c 的值为：\", c \n \nc = a % b\nprint \"5 - c 的值为：\", c\n \n# 修改变量 a 、b 、c\na = 2\nb = 3\nc = a**b \nprint \"6 - c 的值为：\", c\n \na = 9\nb = 2\nc = a//b \nprint \"7 - c 的值为：\", c\n```\n打印\n```\n1 - c 的值为： 31\n2 - c 的值为： 11\n3 - c 的值为： 210\n4 - c 的值为： 2\n5 - c 的值为： 1\n6 - c 的值为： 8\n7 - c 的值为： 4\n```\n这里提三个运算符：\n1.%：返回除法的余数，这里a=21,b=10。a除以b得2余1，所以返回1。故a%b=1\n2.**：返回x的y次幂，这里a=2,b=3。a**b就表示a的b次方，也就是2的3次方得8，故a**b=8\n3.//：返回商的整数部分（向下取整）。这里a=9,b=2。a//b就表示a/b得4.5,但是是取整数（4和5），去较小的一方得4，a=-9就取-5\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n', 'python(3) 运算符', '1');
INSERT INTO `article` VALUES ('6', 'xdr', '2021-12-24 10:04:43', '0', null, null, 'xdr', null, ' #  虚拟机安装\n虚拟机下载：\n先注册再下载\n[注册页面](https://customerconnect.vmware.com/cn/account-registration \"注册页面\")\n[虚拟机地址](https://www.vmware.com/cn.html \"虚拟机下载地址\")\n\n![](/static/1/img/photo/20211224/7f36d54431ae61dec1ca0ad5e2_20.png)\n\n![](/static/1/img/photo/20211224/243af54abe9dcba737d52a3801_ee.png)\n![](/static/1/img/photo/20211224/dc3064436c836a998b3daaa3c0_83.png)\n\n![](/static/1/img/photo/20211224/079f774b9fa023b551a298e306_65.png)\n\n[百度云分享](https://pan.baidu.com/s/1vrnih3GbJKuxlmEaeluziw \"百度云分享\")\n提取码:***w2dv***\n\n下载以后\n一直下一步或者我接受下一步，直到自定义位置\n![](/static/1/img/photo/20211224/3e7d6b4c02a4a31ee22564f90c_20.png)\n安装个人喜好\n![](/static/1/img/photo/20211224/a8f2bd4c33b4b5ac8a19c185b1_82.png)\n然后点安装就行了', null, '虚拟机安装', '1');
INSERT INTO `article` VALUES ('7', 'xdr', '2021-12-24 10:08:38', '0', 'xdr', '2021-12-24 13:49:56', 'xdr', null, '# 虚拟机安装Ubuntu\n没装虚拟机可以看我上篇文章[安装虚拟机](http://localhost:9099/example/uedit/get/6 \"虚拟机的安装\")\n去这里下载：[Ubuntu地址](https://ubuntu.com/download/desktop \"Ubuntu地址\")\n![](/static/1/img/photo/20211224/3765a9406c8aa91787f0c7b4d0_12.png)\n下载好了我们开始安装\n![](/static/1/img/photo/20211224/e4357f4bc2996ec3279a70b967_48.png)\n选择高级，下一步\n![](/static/1/img/photo/20211224/6b724140649dfff0866ee9da66_35.png)\n下一步\n![](/static/1/img/photo/20211224/8b51ea4e6aba4ea6ff8d6dedad_52.png)![](/static/1/img/photo/20211224/6eb06342a5b70f22f123c47037_48.png)\n选择处理器配置，一般给4个就可以了\n![](/static/1/img/photo/20211224/6e4f57457ab108b7ff6977ae2b_00.png)\n选择内存，这台电脑运行内存是8G，所以给2G就行。电脑内存越大可以给的越大，给虚拟机内存太多会影响电脑的运行速度。自己衡量着给。上面划定的地方是，可以用左边的上下滑动选择内存，也可以在右上方输入对应的数字来确定内存\n![](/static/1/img/photo/20211224/e8ddce4f54a4804aeafe1b284a_14.png)\n![](/static/1/img/photo/20211224/0eba944df394249d6fe7788f9b_11.png)\n![](/static/1/img/photo/20211224/646dea4afd9c5300883fc04587_09.png)\n![](/static/1/img/photo/20211224/cae1164422be7f972b1562580e_06.png)\n![](/static/1/img/photo/20211224/9c8f7a4312a618a71d9e8fbf09_42.png)\n选择适当的磁盘大小，储存为单个，这样便于管理\n![](/static/1/img/photo/20211224/92237c44249985f3669a206da0_20.png)\n![](/static/1/img/photo/20211224/8916664a369064284d18c75b0f_07.png)\n这里我们选择自定义硬件，不要直接下一步\n![](/static/1/img/photo/20211224/521e3042f2adb04d08f725d2a2_15.png)\n选择cd-》手动选择映像文件（就是下载的Ubuntuw.iso文件）-》关闭\n![](/static/1/img/photo/20211224/79d1084d0f85a94a24bf3574c8_51.png)\n点完成后开启，然后等待窗口初始化\n![](/static/1/img/photo/20211224/a821dd4a2c96af1d599f4a37ad_37.png)\n![](/static/1/img/photo/20211224/129d414f60ad7c753f0349b920_31.png)\n选择English(US)\n![](/static/1/img/photo/20211224/e9a02a43638983737eaf6fcb5a_56.png)\n下面这两个选项第一个我们选择的是正常安装，这个安装完后就是会出现一些系统自带的软件，我们不用的可以卸载掉，第二个选项是最小安装他只会安装我们系统要用到的基本工具。下面两个都勾选上下面第一个是安装ubuntu时下载更新，第二个为图形或无线硬件以及其他的媒体格式安装第三方软件。我这里选择的是正常安装，如果想要一个纯净的系统就可以选择最小安装。\n![](/static/1/img/photo/20211224/72b60c4b49a486b44929794b55_15.png)\n这里是给磁盘分区，我们进行手动分区，选择最后一个“something else”\n![](/static/1/img/photo/20211224/92ed774804be46b600decd1945_45.png)\n分区,先分一个启动分区boot，一般启动分区的大小为200M就可以了。boot包含了操作系统的内核和在启动系统过程中所要用到的文件，我们要给他单独分出来。要是不分当我们磁盘文件写满了磁盘空间不足时或者根分区出现问题了，我们的系统就没法启动，所以我们给他单独分出来首先是boot。256mb够了\n![](/static/1/img/photo/20211224/23d0a84570b9126c68c305b587_12.png)\nboot分区完成后我们分一个交换分区“swap”，swap分区，在我们windows中就是虚拟内存，在liunx中如果没有swap分区，系统内存用完后，系统他会杀死一部分程序，这个在我们使用中是非常可怕的，你正在用电脑，电脑内存满了，你的程序莫名其妙被关闭了，这是绝对不能允许的，所以我们必须分一个swap分区。还有一点swap分区的大小，一般我们电脑的内存小于等于4G时，swap我们给他是电脑内存的1.5-2倍；大于4G时，电脑内存多少swap就给多少。比如我的电脑内存为4G，那么swap我们就给6G=6144M-8G=8192；我的电脑内存为8G,16G...我们的swap就给8G,16G...跟我们电脑内存一样大就可以了。在这里因为是虚拟机我给swap分了2G。\n![](/static/1/img/photo/20211224/644cd24c3dbb79137e10b8ecd3_52.png)\n下面我们给我们分最后一个分区“/”（根分区），根分区就是系统根目录所在的分区，一般根目录下面只有目录，不要直接有文件。Linux只有一个根目录，就是“/”，其它目录都是它的子目录。这里把剩下的磁盘空间都给“/”就可以了。（这里说一下\"/home\" 用户的home目录所在地，这个分区的大小取决于有多少用户。如果是多用户共同使用一台电脑的话，这个分区是完全有必要的，额外分割出/home有个最大的好处，当你重新安装系统时，你不需要特别去备份你的个人文件，只要在安装时，选择不要格式化这个分区，重新挂载为/home就不会丢失你的数据。因为我们是虚拟机，这台虚拟机是我们自己使用的，所以/home没有分出来，如果有需要可以自己分一个/home）\n![](/static/1/img/photo/20211224/6c358a4e63a2f09890f27f32a3_57.png)\n位置选择上海\n![](/static/1/img/photo/20211224/2e44714ce6984238d4c4af7248_19.png)\n填写对应详细下一步就可以去休息休息，然后继续\n![](/static/1/img/photo/20211224/2b259b40f4b432dc81e6ac0f7b_17.png)\n\n', '# 虚拟机安装Ubuntu\n没装虚拟机可以看我上篇文章[安装虚拟机](http://localhost:9099/example/uedit/get/6 \"虚拟机的安装\")\n去这里下载：[Ubuntu地址](https://ubuntu.com/download/desktop \"Ubuntu地址\")\n![](/static/1/img/photo/20211224/3765a9406c8aa91787f0c7b4d0_12.png)\n下载好了我们开始安装\n![](/static/1/img/photo/20211224/e4357f4bc2996ec3279a70b967_48.png)\n选择高级，下一步\n![](/static/1/img/photo/20211224/6b724140649dfff0866ee9da66_35.png)\n下一步\n![](/static/1/img/photo/20211224/8b51ea4e6aba4ea6ff8d6dedad_52.png)![](/static/1/img/photo/20211224/6eb06342a5b70f22f123c47037_48.png)\n选择处理器配置，一般给4个就可以了\n![](/static/1/img/photo/20211224/6e4f57457ab108b7ff6977ae2b_00.png)\n选择内存，这台电脑运行内存是8G，所以给2G就行。电脑内存越大可以给的越大，给虚拟机内存太多会影响电脑的运行速度。自己衡量着给。上面划定的地方是，可以用左边的上下滑动选择内存，也可以在右上方输入对应的数字来确定内存\n![](/static/1/img/photo/20211224/e8ddce4f54a4804aeafe1b284a_14.png)\n![](/static/1/img/photo/20211224/0eba944df394249d6fe7788f9b_11.png)\n![](/static/1/img/photo/20211224/646dea4afd9c5300883fc04587_09.png)\n![](/static/1/img/photo/20211224/cae1164422be7f972b1562580e_06.png)\n![](/static/1/img/photo/20211224/9c8f7a4312a618a71d9e8fbf09_42.png)\n选择适当的磁盘大小，储存为单个，这样便于管理\n![](/static/1/img/photo/20211224/92237c44249985f3669a206da0_20.png)\n![](/static/1/img/photo/20211224/8916664a369064284d18c75b0f_07.png)\n这里我们选择自定义硬件，不要直接下一步\n![](/static/1/img/photo/20211224/521e3042f2adb04d08f725d2a2_15.png)\n选择cd-》手动选择映像文件（就是下载的Ubuntuw.iso文件）-》关闭\n![](/static/1/img/photo/20211224/79d1084d0f85a94a24bf3574c8_51.png)\n点完成后开启，然后等待窗口初始化\n![](/static/1/img/photo/20211224/a821dd4a2c96af1d599f4a37ad_37.png)\n![](/static/1/img/photo/20211224/129d414f60ad7c753f0349b920_31.png)\n选择English(US)\n![](/static/1/img/photo/20211224/e9a02a43638983737eaf6fcb5a_56.png)\n下面这两个选项第一个我们选择的是正常安装，这个安装完后就是会出现一些系统自带的软件，我们不用的可以卸载掉，第二个选项是最小安装他只会安装我们系统要用到的基本工具。下面两个都勾选上下面第一个是安装ubuntu时下载更新，第二个为图形或无线硬件以及其他的媒体格式安装第三方软件。我这里选择的是正常安装，如果想要一个纯净的系统就可以选择最小安装。\n![](/static/1/img/photo/20211224/72b60c4b49a486b44929794b55_15.png)\n这里是给磁盘分区，我们进行手动分区，选择最后一个“something else”\n![](/static/1/img/photo/20211224/92ed774804be46b600decd1945_45.png)\n分区,先分一个启动分区boot，一般启动分区的大小为200M就可以了。boot包含了操作系统的内核和在启动系统过程中所要用到的文件，我们要给他单独分出来。要是不分当我们磁盘文件写满了磁盘空间不足时或者根分区出现问题了，我们的系统就没法启动，所以我们给他单独分出来首先是boot。256mb够了\n![](/static/1/img/photo/20211224/23d0a84570b9126c68c305b587_12.png)\nboot分区完成后我们分一个交换分区“swap”，swap分区，在我们windows中就是虚拟内存，在liunx中如果没有swap分区，系统内存用完后，系统他会杀死一部分程序，这个在我们使用中是非常可怕的，你正在用电脑，电脑内存满了，你的程序莫名其妙被关闭了，这是绝对不能允许的，所以我们必须分一个swap分区。还有一点swap分区的大小，一般我们电脑的内存小于等于4G时，swap我们给他是电脑内存的1.5-2倍；大于4G时，电脑内存多少swap就给多少。比如我的电脑内存为4G，那么swap我们就给6G=6144M-8G=8192；我的电脑内存为8G,16G...我们的swap就给8G,16G...跟我们电脑内存一样大就可以了。在这里因为是虚拟机我给swap分了2G。\n![](/static/1/img/photo/20211224/644cd24c3dbb79137e10b8ecd3_52.png)\n下面我们给我们分最后一个分区“/”（根分区），根分区就是系统根目录所在的分区，一般根目录下面只有目录，不要直接有文件。Linux只有一个根目录，就是“/”，其它目录都是它的子目录。这里把剩下的磁盘空间都给“/”就可以了。（这里说一下\"/home\" 用户的home目录所在地，这个分区的大小取决于有多少用户。如果是多用户共同使用一台电脑的话，这个分区是完全有必要的，额外分割出/home有个最大的好处，当你重新安装系统时，你不需要特别去备份你的个人文件，只要在安装时，选择不要格式化这个分区，重新挂载为/home就不会丢失你的数据。因为我们是虚拟机，这台虚拟机是我们自己使用的，所以/home没有分出来，如果有需要可以自己分一个/home）\n![](/static/1/img/photo/20211224/6c358a4e63a2f09890f27f32a3_57.png)\n位置选择上海\n![](/static/1/img/photo/20211224/2e44714ce6984238d4c4af7248_19.png)\n\n\n', '虚拟机安装Ubuntu', '1');
INSERT INTO `article` VALUES ('8', 'xdr', '2021-12-25 17:19:37', '0', 'xdr', '2022-01-06 16:19:10', 'xdr', null, ' #  常用的终端命令\nls 查看当下列表\ncd [param] 进入目录\nmkdir [param] 创建目录\nmkdir -p [param] 创建包含关系的目录，如：b/a/c\ntouch [param] 如果文件不存在就创建,存在就更改末次修改时间\n创建文件时注意，同一级下，文件和文件夹同名就不能被创建\npwd 查看当前所在位置\nrm [param] 删除文件或者文件夹，删除文件无法恢复\nrm -r [param] 强制删除文件或者文件夹\nrm -f [param] 删除文件夹及其子类（递归删除）\n最强命令 rm -rf /*\nclear 清屏\n##### 技巧\ntable 补全 多文件 两下会显示相关所有的列表\n上下会找敲过的命令\nctrl + c 不想执行\nls -a 查询所有文件夹和文件名称，包括隐藏文件\nls -l 纵向显示文件夹和文件详细信息列表\nls -h 人性化显示文件夹和文件详细信息列表\n可以组合使用 ls -hal 人性化显示文件夹和文件详细信息列表 （包括隐藏文件）\n* 代表任意0个或多个任意字符 组合： ls *.txt 查询所有以.txt结尾的文件 ls 1*.txt 查询以1开头，.txt结尾的文件\n？代表一个任意字符 组合：ls 1?1.txt 查询类似于111.txt 1y1.txt...文件\n[abc] 可以代表abc中一个字符 ls [123]23.txt 可以查询到123.txt 223.txt 323.txt\n[a-c] 可以代表a到c中一个字符 ls [1-3]23.txt 可以查询到123.txt 223.txt 323.txt\ncd 或者 cd ~ 回到home目录\ncd . 当前目录\ncd .. 上一级目录\ncd - 切换最近的一次目录\n. 代表当前目录\n.. 代表上级目录\ntree 显示目录文件树形图（入坑：如果没有tree 先切换到root(su或者su root或者su:输入密码（jOYCE)  输入命令:apt-get install tree 。或者修改密码sudo passwd root输入密码在进行一样的操作）\ntree -d 只显示目录\ncp 原来地址 目标地址\ncp 原来地址 ./文件名字\ncp 原来地址 .      直接把文件复制到当前目录中且文件名字一致\n如果文件存在，复制同名会出现警告是否重新加载\n安全用法 cp -i，会询问是否覆盖\n复制目录 cp -r [原来地址] [新目录名]\n移动文件 mv [原来地址] [新目录名]\n移动文件 mv [原来地址] .  直接把文件移动到到当前目录中且文件名字一致\n安全用法 mv -i，会询问是否覆盖\n一次性查看文件内容 cat [文件名称]  cat -b [文件名称]    后面加-b事对非空输出行进行编号;cat -b [文件名称]   -n事对所有的文件内容进行编号\n拓展：cat可以创建文件，追加文件内容，合并文件\n分屏查看文件内容 more [文件名称]  追加回车后滚动一行，b回滚一屏，f向前回滚一屏，空格向下回滚一屏幕，/word搜索关键字 q退出查看\n搜索文本字段 grep [关键字] [文件名称] 关键字若有空格，需要将关键字关入\"\"中\ngrep -n [关键字] [文件名称] 显示行号\ngrep -v [关键字] [文件名称] 显示不予关键字匹配的行\ngrep -i [关键字] [文件名称] 忽略关键字大小写\n^a 以首部为a开头\nb$ 以b结束\n文字内容echo ，文字内容会显示出来，一般与重定向使用 echo [文字] > [文件名称]\necho [文字] > [文件名称] 于 touch [文件名称]：都创建文件，但是前者可以创建并且写入内容.\n重定向 > ，文字写入文件中 [内容] > [文件名称]，会覆盖\n>> 文字追加文件中 [内容] > [文件名称]\n管道 | 允许将一个命令额输出可以通过管道作为另一个命令的输入，如 ls | more（分屏显示列表）\n现在重新启动 shutdown -r now\n现在立刻关机 shutdown now\n定时关机 showdown 20:34\n再过10分钟关机 showdown +10\n取消关机 shutdown -c\n', ' #  常用的终端命令\nls 查看当下列表\ncd [param] 进入目录\nmkdir [param] 创建目录\nmkdir -p [param] 创建包含关系的目录，如：b/a/c\ntouch [param] 如果文件不存在就创建,存在就更改末次修改时间\n创建文件时注意，同一级下，文件和文件夹同名就不能被创建\npwd 查看当前所在位置\nrm [param] 删除文件或者文件夹，删除文件无法恢复\nrm -r [param] 强制删除文件或者文件夹\nrm -f [param] 删除文件夹及其子类（递归删除）\n最强命令 rm -rf /*\nclear 清屏\n##### 技巧\ntable 补全 多文件 两下会显示相关所有的列表\n上下会找敲过的命令\nctrl + c 不想执行\nls -a 查询所有文件夹和文件名称，包括隐藏文件\nls -l 纵向显示文件夹和文件详细信息列表\nls -h 人性化显示文件夹和文件详细信息列表\n可以组合使用 ls -hal 人性化显示文件夹和文件详细信息列表 （包括隐藏文件）\n* 代表任意0个或多个任意字符 组合： ls *.txt 查询所有以.txt结尾的文件 ls 1*.txt 查询以1开头，.txt结尾的文件\n？代表一个任意字符 组合：ls 1?1.txt 查询类似于111.txt 1y1.txt...文件\n[abc] 可以代表abc中一个字符 ls [123]23.txt 可以查询到123.txt 223.txt 323.txt\n[a-c] 可以代表a到c中一个字符 ls [1-3]23.txt 可以查询到123.txt 223.txt 323.txt\ncd 或者 cd ~ 回到home目录\ncd . 当前目录\ncd .. 上一级目录\ncd - 切换最近的一次目录\n. 代表当前目录\n.. 代表上级目录\ntree 显示目录文件树形图（入坑：如果没有tree 先切换到root(su或者su root或者su:输入密码（jOYCE)  输入命令:apt-get install tree 。或者修改密码sudo passwd root输入密码在进行一样的操作）\ntree -d 只显示目录\ncp 原来地址 目标地址\ncp 原来地址 ./文件名字\ncp 原来地址 .      直接把文件复制到当前目录中且文件名字一致\n如果文件存在，复制同名会出现警告是否重新加载\n安全用法 cp -i，会询问是否覆盖\n复制目录 cp -r [原来地址] [新目录名]\n移动文件 mv [原来地址] [新目录名]\n移动文件 mv [原来地址] .  直接把文件移动到到当前目录中且文件名字一致\n安全用法 mv -i，会询问是否覆盖\n一次性查看文件内容 cat [文件名称]  cat -b [文件名称]    后面加-b事对非空输出行进行编号;cat -b [文件名称]   -n事对所有的文件内容进行编号\n拓展：cat可以创建文件，追加文件内容，合并文件\n分屏查看文件内容 more [文件名称]  追加回车后滚动一行，b回滚一屏，f向前回滚一屏，空格向下回滚一屏幕，/word搜索关键字 q退出查看\n搜索文本字段 grep [关键字] [文件名称] 关键字若有空格，需要将关键字关入\"\"中\ngrep -n [关键字] [文件名称] 显示行号\ngrep -v [关键字] [文件名称] 显示不予关键字匹配的行\ngrep -i [关键字] [文件名称] 忽略关键字大小写\n^a 以首部为a开头\nb$ 以b结束\n文字内容echo ，文字内容会显示出来，一般与重定向使用 echo [文字] > [文件名称]\necho [文字] > [文件名称] 于 touch [文件名称]：都创建文件，但是前者可以创建并且写入内容.\n重定向 > ，文字写入文件中 [内容] > [文件名称]，会覆盖\n>> 文字追加文件中 [内容] > [文件名称]\n管道 | 允许将一个命令额输出可以通过管道作为另一个命令的输入，如 ls | more', '常用的终端命令', '1');
INSERT INTO `article` VALUES ('9', 'xdr', '2021-12-25 17:23:27', '0', null, null, 'xdr', null, ' # 命令的格式 \n command [-options] [parameter]\n 选项可有可无\n 比如： rm -r parameter', null, '命令的格式 ', '1');
INSERT INTO `article` VALUES ('10', 'xdr', '2021-12-25 17:27:41', '0', 'xdr', '2021-12-25 17:36:21', 'xdr', null, '#   命令帮助\n  command --help\n  man command 比上一个多\n##### 常用命令  ：\n  ![](/static/1/img/photo/20211225/2a34de4c8ab31d1fdb8fc2326c_00.png)', '#   命令帮助\n  command --help\n  man command', '命令帮助', '1');
INSERT INTO `article` VALUES ('11', 'xdr', '2021-12-28 14:56:14', '0', null, null, 'xdr', null, '#  虚拟机出现无法获得权限，移除虚拟机解决方案，误删处理\n![](/static/1/img/photo/20211228/125d8d4596ac86d7aae5989097_36.png)\n将后缀为.lck的文件夹删除或者加上.backup\n误删出现的话，只要本地虚拟机没有将资源删除可以右键我的计算机点击扫描本地即可', null, '虚拟机的坑（1）', '1');
INSERT INTO `article` VALUES ('12', 'xdr', '2021-12-29 18:01:39', '0', null, null, 'xdr', null, '# 数据结构笔记\n博客:[左程云笔记](https://blog.csdn.net/qq_40813329/article/details/120835007 \"左程云笔记\")', null, '数据结构笔记', '1');
INSERT INTO `article` VALUES ('13', 'xdr', '2022-01-06 13:14:35', '0', null, null, 'xdr', null, '# 资料总结\n开源 Github 的 1000多本计算机电子书免费下载[传送门](https://github.com/itdevbooks/pdf \"传送门\")\n7701页《第3版：互联网大厂面试题》 [百度云盘](https://pan.baidu.com/s/1m0fifBTPYGhrH5QDFflhZA  \"百度云盘\")\n提取码：1234 \n视频资料汇总 [传送门](https://tech.souyunku.com/?p=49915 \"传送门\")\n JetBrains 全家桶，破解教程\n 1.IDEA 无限重置补丁教程 [传送门](https://tech.souyunku.com/?p=49115 \"传送门\")\n 2.IDEA 破解到2099教程 [传送门](https://tech.souyunku.com/?p=49896 \"传送门\")\n \n\n\n', null, '资料总结', '1');
