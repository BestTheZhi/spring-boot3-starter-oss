package top.thezhi.oss.service;

import java.io.InputStream;

/**
 * @author ZHI LIU
 *
 * 文件上传下载接口
 */

public interface FileStorageService {

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


}
