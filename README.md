## spring-boot-starter-oss
阿里云文件上传下载

(使用了spring-boot-configuration-processor 依赖生产了 matadata.json
使用了maven-source-plugin 和 maven-javadoc-plugin插件生产相关文档信息)

### 使用
1. Clone项目, 然后clean  install到本地仓库
2. 引入依赖

```xml
<dependency>
    <groupId>top.thezhi.oss</groupId>
    <artifactId>spring-boot-starter-oss</artifactId>
    <version>1.0</version>
</dependency>
```

3. 装配FileStorageService,调用对应方法
```java
@SpringBootTest
public class Test {

    @Autowired
    FileStorageService fileStorageService;

    @org.junit.jupiter.api.Test
    public void test1() throws FileNotFoundException {
        InputStream is = new FileInputStream("D:\\Code\\Roco\\img\\傲凌冰龙王.png");
        String url = fileStorageService.upload("test", "傲凌冰龙王.png", is);
        System.out.println(url);
    }

    @org.junit.jupiter.api.Test
    public void test2(){
        InputStream inputStream = fileStorageService.download("test/傲凌冰龙王.png");
        System.out.println(inputStream);
    }


    @org.junit.jupiter.api.Test
    public void test3(){
        boolean success = fileStorageService.delete("test/傲凌冰龙王.png");
        System.out.println(success);
    }
}

```

```java
    /**
     * 简单文件上传
     * @param prefix  文件目录
     * @param filename  文件名称
     * @param inputStream  输入流
     * @return   对象的完整路径 prefix\filename
     */
    String upload(String prefix, String filename, InputStream inputStream);


    /**
     * 下载文件
     * @param objectName Object完整路径，例如exampleDir/exampleObject.txt
     * @return  文件输入流
     */
    InputStream download(String objectName);

    /**
     * 删除文件
     * @param objectName Object完整路径，例如exampleDir/example)bject.txt
     * @return success
     */
    boolean delete(String objectName);
```
