# 共用的安卓打包签名

理论上是放在这里的，请勿提交到git，以下是签名和签名配置文件的样子

```text
xxx.jks
xxx.p12
signing.properties
```

以下是`signing.properties`文件的内容示例：

```text
STORE_FILE=../../doc/signing/xxx.jks
# STORE_FILE=xxx.jks
STORE_PASSWORD=1234
KEY_ALIAS=hello_alias
KEY_PASSWORD=4321
```
