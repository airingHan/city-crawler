[TOC]

## 省市区

从国家统计局爬区划代码

https://github.com/ALawating-Rex/AreaAndBanks 别人的git

http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/ 区划和城乡划分代码

http://www.stats.gov.cn/tjsj/tjbz/200911/t20091125_8667.html 统计用区划代码和城乡划分代码编制规则

###省(省级)(sys_province)：

| 名称       | 变量名 | 类型   | 必填 | 说明       |
| ---------- | ------ | ------ | ---- | ---------- |
| 主键       | id     | int | 是   | 主键无意义 |
| 省行政代码 | code   | int | 是   |            |
| 省名称     | name   | string | 是   |            |
| 状态     | state | bit | 是   | 1 启用<br>0 未启用 |



###市(地级)(sys_city)：

| 名称       | 变量名 | 类型   | 必填 | 说明       |
| ---------- | ------ | ------ | ---- | ---------- |
| 主键 | id     | int    | 是   | 主键无意义 |
| 市行政代码 | code   | int    | 是   |            |
| 市名称     | name   | string | 是   |            |
| 省级行政代码 | province_code | int | 是   |            |
| 状态     | state | bit | 是   | 1 启用<br>0 未启用 |



###区(县级)(sys_county)：

| 名称       | 变量名 | 类型   | 必填 | 说明       |
| ---------- | ------ | ------ | ---- | ---------- |
| 主键 | id     | int    | 是   | 主键无意义 |
| 区行政代码 | code   | int    | 是   |            |
| 区名称     | name   | string | 是   |            |
| 地级行政代码 | city_code | int | 是   |            |
| 状态     | state | bit | 是   | 1 启用<br>0 未启用 |



###街道(乡级)(sys_town)：

| 名称       | 变量名 | 类型   | 必填 | 说明       |
| ---------- | ------ | ------ | ---- | ---------- |
| 主键 | id     | int    | 是   | 主键无意义 |
| 街道行政代码 | code   | int    | 是   |            |
| 街道名称   | name   | string | 是   |            |
| 县级行政代码 | county_code | int | 是   |            |
| 状态     | state | bit | 是   | 1 启用<br>0 未启用 |



###社区(村级)(sys_village)：

| 名称       | 变量名 | 类型   | 必填 | 说明       |
| ---------- | ------ | ------ | ---- | ---------- |
| 主键 | id     | int    | 是   | 主键无意义 |
| 社区行政代码 | code   | int    | 是   |            |
| 社区名称 | name   | string | 是   |            |
| 乡级行政代码 | town_code | int | 是   |            |
| 状态     | state | bit | 是   | 1 启用<br>0 未启用 |