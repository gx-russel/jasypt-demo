# SpringBoot集成Jasypt加密敏感信息

Jasypt官方使用文档：http://www.jasypt.org/

[jasypt-spring-boot](https://github.com/ulisesbocchio/jasypt-spring-boot)

### 项目集成Jasypt
- 引入依赖
    ```xml
    <dependency>
            <groupId>com.github.ulisesbocchio</groupId>
            <artifactId>jasypt-spring-boot-starter</artifactId>
            <version>3.0.4</version>
    </dependency>
    ```
- 配置文件中添加Jasypt配置信息
    ```yaml
    jasypt:
      encryptor:
        # 配置加密算法
        algorithm: PBEWithMD5AndDES
        iv-generator-classname: org.jasypt.iv.NoIvGenerator
        property:
          # 算法识别前缀(当算法发现配置文件中的值以这前缀开始，后缀结尾时，会使用指定算法解密)
          prefix: IT(
          # 算法识别后缀
          suffix: )
    ```  
  
### Jasypt加密有3种方式
- 使用代码生成加密串
    ```java
      import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
      import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
      
      public class JasyptUtil {
          public static void main(String[] args) {
              String username = encrypt("root");
              String password = encrypt("123456");
              System.out.println(username);
              System.out.println(password);
          }
      
          /**
           * 加密
           *
           * @param plaintext 明文密码     * @return
           */
          public static String encrypt(String plaintext) {
              //加密工具
              StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
              //加密配置
              EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
              // 算法类型
              config.setAlgorithm("PBEWithMD5AndDES");
              //生成秘钥的公钥
              config.setPassword("PEB123@321BEP");
              //应用配置
              encryptor.setConfig(config);
              //加密
              String ciphertext = encryptor.encrypt(plaintext);
              return ciphertext;
          }
      
          /**
           * 解密
           *
           * @param ciphertext 待解密秘钥
           * @return
           */
          public static String decrypt(String ciphertext) {
              //加密工具
              StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
              //加密配置
              EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
              config.setAlgorithm("PBEWithMD5AndDES");
              //生成秘钥的公钥
              config.setPassword("PEB123@321BEP");
              //应用配置
              encryptor.setConfig(config);
              //解密
              String pText = encryptor.decrypt(ciphertext);
              return pText;
          }
      }
    ```  
- 使用java cp命名对加密生成密文
    - org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI是jasypt提供的一个用于加密的实体类
    - org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI是jasypt提供的一个用于解密的实体类
    - input表示需要加密的字符串如：密码
    - password表示本次加密算法使用的秘钥
    - algorithm表示加密算法的名称。  
    ```shell
    # 加密命令
    java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input='root' password=abcdef algorithm=PBEWithMD5AndDES
    
    # 解密命令
    java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input='z4xP29fuY4wF2AJqp1NnoGJxj' password=abcdef algorithm=PBEWithMD5AndDES
    ```
- 使用jasypt-maven插件生成密文
    ```shell
    // 1、在Pom中添加maven插件依赖
    <plugin>
     <groupId>com.github.ulisesbocchio</groupId>
     <artifactId>jasypt-maven-plugin</artifactId>
     <version>3.0.4</version>
    </plugin>
    
    // 2、加密命令
    mvn jasypt:encrypt-value -Djasypt.encryptor.password="秘钥的值" -Djasypt.plugin.value="需要加密的敏感信息"
    
    // 解密命令
    mvn jasypt:decrypt-value -Djasypt.encryptor.password="秘钥的值" -Djasypt.plugin.value="需要解密的密文"
    ```
### 加解密秘钥如何存储及配置选下列其中一种方式
- 在yml中直接设置
    `jasypt:
      encryptor:
        password: PEm5vj23@3sKa2kiREP`
-  设置系统环境变量（推荐）
    - 直接设置 `jasypt.encryptor.password=PEm5vj23@3sKa2kiREP`
    - 设置环境变量 然后再配置文件中取
        1. `JASYPT_ENCRYPTOR_PASSWORD=PEm5vj23@3sKa2kiREP` 这个也可以再启动参数中设置
        2. `jasypt:
              encryptor:
                password: ${JASYPT_ENCRYPTOR_PASSWORD}`
- 设置启动参数（二选一）
    - 	`java -jar target/jasypt-spring-boot-demo-0.0.1-SNAPSHOT.jar --jasypt.encryptor.password=password`
    - 	`java -Djasypt.encryptor.password=password -jar target/jasypt-spring-boot-demo-0.0.1-SNAPSHOT.jar` 
    
    
### 使用中的一些坑    
1、使用jasypt3.0启动时报：Failed to bind properties under 'xxx.xxx.xxx' to java.lang.String

官方描述，3.0后默认支持的算法为PBEWITHHMACSHA512ANDAES_256 ，该种加密方式由sha512 加 AES 高级加密组成，需要JDK1.9以上支持或者添加JCE(Java Cryptography Extension无限强度权限策略文件)支持，否则运行会出现错误。
```
「解决方式：」
方式1、将加密算法替换成PBEWithMD5AndDES 算法，并配置iv-generator-classname: 为org.jasypt.iv.NoIvGenerator值
方式2、降低jasypt的版本 - 使用2.x的版本
```


### 【参考】[拒绝“裸奔“，SpringBoot集成Jasypt加密敏感信息](https://zhuanlan.zhihu.com/p/518439781)
 

