package cn.coffee.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.google.common.collect.Lists;
import cn.coffee.common.utils.IdUtil;
import cn.coffee.common.validation.ValidationUtil;
import cn.coffee.common.exception.BizException;
import cn.coffee.common.spring.SpringContextUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class AliOssUtil {
    private static final Logger logger = LoggerFactory.getLogger(AliOssUtil.class);

    private AliOssSupport getAliOssSupport() {
        return SpringContextUtil.getApplicationContext().getBean(AliOssSupport.class);
    }

    public List list(String bucketName, String prefix) {
        List<OSSObjectSummary> tempList = this.getAliOssSupport().listObjects(bucketName, prefix);
        List<OSSObjectSummary> dirList = Lists.newArrayList();
        List<OSSObjectSummary> fileList = Lists.newArrayList();
        //文件夹放前面
        tempList.forEach(a -> {
            if (a.getKey().endsWith("/")) {
                dirList.add(a);
            } else {
                fileList.add(a);
            }
        });
        List<OSSObjectSummary> finalList = Lists.newArrayList();
        finalList.addAll(dirList);
        finalList.addAll(fileList);
        //下载前缀路径
        String preUrl = getAliOssSupport().getEndpoint();
        if (preUrl.contains("http://")) {
            preUrl = preUrl.replace("http://", "https://" + bucketName + ".");
        } else if (preUrl.contains("https://")) {
            preUrl = preUrl.replace("https://", "https://" + bucketName + ".");
        }
        ObejctStorage fileObj = new ObejctStorage();
        for (OSSObjectSummary objectSummary : finalList) {
            recursionBuildFileTree(preUrl, fileObj, objectSummary);
        }
        return fileObj.getSubordinate();
    }

    /**
     * 上传文件--自定义-bucketName-module
     *
     * @param multipartFile
     * @param bucketName
     * @param module
     * @return
     */
    public String upload(MultipartFile multipartFile, String bucketName, String module) {
        //校验储存地址目录
        isEmptyParam(bucketName, module);
        return uploadFile(multipartFile, bucketName, module);
    }

    /**
     * 上传文件--自定义-bucketName
     *
     * @param multipartFile
     * @return
     */
    public String upload(MultipartFile multipartFile, String module) {
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        //校验储存地址目录
        isEmptyParam(bucketName, module);
        return uploadFile(multipartFile, bucketName, module);
    }

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    public String upload(MultipartFile multipartFile) {
        //校验储存地址目录
        isEmptyParam(null, null);
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        return uploadFile(multipartFile, bucketName, null);
    }

    /**
     * 上传文件--自定义-bucketName
     *
     * @param multipartFile
     * @param bucketName
     * @return
     */
    public String uploadInBucket(MultipartFile multipartFile, String bucketName) {
        //校验储存地址目录
        isEmptyParam(bucketName, null);
        return uploadFile(multipartFile, bucketName, null);
    }

    /**
     * 批量上传文件--自定义-bucketName-module
     *
     * @param multipartFiles
     * @param bucketName
     * @param module
     * @return
     */
    public List<String> uploadMultiple(MultipartFile[] multipartFiles, String bucketName, String module) {
        //校验储存地址目录
        isEmptyParam(bucketName, module);
        //循环上传
        List<String> urlList = Lists.newArrayList();
        for (MultipartFile multipartFile : multipartFiles) {
            //上传
            urlList.add(uploadFile(multipartFile, bucketName, module));
        }
        return urlList;
    }

    /**
     * 批量上传文件--自定义-bucketName
     *
     * @param multipartFiles
     * @return
     */
    public List<String> uploadMultiple(MultipartFile[] multipartFiles, String module) {
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        //校验储存地址目录
        isEmptyParam(bucketName, module);
        //循环上传
        List<String> urlList = Lists.newArrayList();
        for (MultipartFile multipartFile : multipartFiles) {
            //上传
            urlList.add(uploadFile(multipartFile, bucketName, module));
        }
        return urlList;
    }

    /**
     * 批量上传文件--自定义-bucketName
     *
     * @param multipartFiles
     * @return
     */
    public List<String> uploadMultiple(MultipartFile[] multipartFiles) {
        //校验储存地址目录
        isEmptyParam(null, null);
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        //循环上传
        List<String> urlList = Lists.newArrayList();
        for (MultipartFile multipartFile : multipartFiles) {
            //上传
            urlList.add(uploadFile(multipartFile, bucketName, null));
        }
        return urlList;
    }

    /**
     * 批量上传文件--自定义-bucketName
     *
     * @param multipartFiles
     * @param bucketName
     * @return
     */
    public List<String> uploadMultipleInBucket(MultipartFile[] multipartFiles, String bucketName) {
        //校验储存地址目录
        isEmptyParam(bucketName, null);
        //循环上传
        List<String> urlList = Lists.newArrayList();
        for (MultipartFile multipartFile : multipartFiles) {
            //上传
            urlList.add(uploadFile(multipartFile, bucketName, null));
        }
        return urlList;
    }

    /**
     * 下载文件
     * @param module
     * @param fileName
     * @param response
     */
    public void download(String module,String fileName, String bucketName,HttpServletResponse response) throws IOException {
        //校验储存地址目录
        isEmptyParam(bucketName, module);
        String key = getKey(module,fileName);
        downloadByBucketKey(bucketName, key, response);
    }

    /**
     * 下载文件
     * @param module
     * @param fileName
     * @param response
     */
    public void download(String module,String fileName,HttpServletResponse response) throws IOException {
        //校验储存地址目录
        isEmptyParam(null, null);
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        String key = getKey(module,fileName);
        downloadByBucketKey(bucketName, key, response);
    }

    /**
     * 下载文件
     * @param fileName
     * @param response
     */
    public void download(String fileName,HttpServletResponse response) throws IOException {
        //校验储存地址目录
        isEmptyParam(null, null);
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        downloadByBucketKey(bucketName, fileName, response);
    }

    /**
     * 下载文件
     * @param key
     * @param response
     */
    public void downloadByKey(String key,String bucketName,HttpServletResponse response) throws IOException {
        //校验储存地址目录
        isEmptyParam(bucketName, null);
        downloadByBucketKey(bucketName, key, response);
    }

    /**
     * 删除文件
     *
     * @param module
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean remove(String module,String fileName, String bucketName) throws Exception {
        //校验储存地址目录
        isEmptyParam(bucketName, module );
        String key = getKey(module,fileName);
        deleteByBucketKey(bucketName, key);
        return true;
    }

    /**
     * 删除文件
     *
     * @param module
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean remove(String module,String fileName) throws Exception {
        //校验储存地址目录
        isEmptyParam(null, null);
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        String key = getKey(module,fileName);
        deleteByBucketKey(bucketName, key);
        return true;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean remove(String fileName) throws Exception {
        //校验储存地址目录
        isEmptyParam(null, null);
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        deleteByBucketKey(bucketName, fileName);
        return true;
    }
    /**
     * 删除文件
     *
     * @param key
     * @return
     * @throws Exception
     */
    public boolean removeByKey(String key,String bucketName) throws Exception {
        //校验储存地址目录
        isEmptyParam(bucketName, null);
        deleteByBucketKey(bucketName, key);
        return true;
    }

    /**
     * 导出文档
     * @param module
     * @param fileName
     * @return
     */
    public Workbook getExcelFile(String module, String fileName) {
        //文件名
        fileName = module + "/" + fileName;
        // 以流的形式下载文件
        InputStream fis = null;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(this.getAliOssSupport().getEndpoint(), this.getAliOssSupport().getAccessKeyId(), this.getAliOssSupport().getAccessKeySecret());
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, fileName);
        try {
            fis = ossObject.getObjectContent();
            Workbook workbook = WorkbookFactory.create(fis);
            return workbook;
        } catch (Exception e) {
            throw new BizException("导出文档失败，请联系管理员！");
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    /**
     * 保存文件到磁盘
     *
     * @param module      模块名称
     * @param fileName    文件名
     * @param inputStream 输入流
     * @throws Exception
     */
    public String writeToDisk(String module, String fileName, InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return null;
        }
        //指定地址为空-默认取第一个定义的地址
        String bucketName = Lists.newArrayList(this.getAliOssSupport().getBucketList().keySet()).get(0);
        String key = getKey(module,fileName);
        String objectUrl = writeToBucketKey(bucketName, key, inputStream);
        return objectUrl;
    }

    /**
     * 保存文件到磁盘
     *
     * @param module      模块名称
     * @param fileName    文件名
     * @param inputStream 输入流
     * @throws Exception
     */
    public String writeToDisk(String module, String fileName, String bucketName, InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return null;
        }
        String key = getKey(module,fileName);
        String objectUrl = writeToBucketKey(bucketName, key, inputStream);
        return objectUrl;
    }

    /**
     * 上传文件
     *
     * @param multipartFile
     * @param bucketName
     * @param module
     * @return
     */
    private String uploadFile(MultipartFile multipartFile, String bucketName, String module) {
        // 原文件名
        String originalFileName = multipartFile.getOriginalFilename();
        // 后缀
        String extension = FilenameUtils.getExtension(originalFileName);
        // 存入硬盘的文件名,文件名不需要修改原先的文件名
        String fileName2Disk = IdUtil.randomBase62(16) + "." + extension;
        String objectUrl;
        try {
            objectUrl = writeToDisk(module, fileName2Disk, bucketName, multipartFile.getInputStream());
        } catch (Exception e) {
            logger.error(e.toString());
            throw new BizException("上传文件失败！");
        }
        return objectUrl;
    }

    private void recursionBuildFileTree(String preUrl, ObejctStorage fileObj, OSSObjectSummary objectSummary) {
        if (ValidationUtil.isEmpty(fileObj.getSubordinate())) {
            fileObj.setSubordinate(Lists.newArrayList());
        }
        if (ValidationUtil.isNotEmpty(fileObj.getSubordinate())) {
            for (ObejctStorage obj : fileObj.getSubordinate()) {
                if (obj.getName().lastIndexOf("/") == obj.getName().length() - 1
                        && objectSummary.getKey().indexOf(obj.getKey()) == 0) {
                    //最后以"/"结尾，是文件夹
                    //并且跟本文件的前缀一致
                    recursionBuildFileTree(preUrl, obj, objectSummary);
                    return;
                }
            }
        }
        ObejctStorage newOne = new ObejctStorage();
        newOne.setKey(objectSummary.getKey());
        newOne.setSize(objectSummary.getSize());
        newOne.setLastModified(objectSummary.getLastModified());
        newOne.setUrl(preUrl + "/" + objectSummary.getKey());
        String name = objectSummary.getKey();
        if (StringUtils.isNotEmpty(fileObj.getKey())) {
            name = newOne.getKey().replace(fileObj.getKey(), "");
        }
        newOne.setName(name);
        //放进去
        fileObj.getSubordinate().add(newOne);
    }

    private String writeToBucketKey(String bucketName, String key, InputStream inputStream) throws Exception {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(key)) {
            return null;
        }
        //确定bucket
        logger.debug("==========准备进行上传动作==========");
        String objectUrl = uploadAliyun(bucketName, key, inputStream);
        logger.debug("==========上传动作结束=============");
        return objectUrl;
    }

    //上传文件到阿里云oss
    private String uploadAliyun(String bucketName, String key, InputStream inputStream) {
        OSS ossClient = null;
        try {
            // 1 获取上传需要的固定值
            String endpoint = this.getAliOssSupport().getEndpoint();      //你的站点
            String accessKeyId = this.getAliOssSupport().getAccessKeyId();  //你的acess_key_id
            String accessKeySecret = this.getAliOssSupport().getAccessKeySecret(); //你的acess_key_secret
            //2 创建OssClient对象
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 创建PutObjectRequest对象。
            // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream);
            //是否是图片
            //阿里云默认存储图片的请求头为"image/jpeg",此时打开图片地址就会直接下载
            //修改请求头为"image/jpg"，默认查看
            boolean isPic = false;
            String lowerCaseKey = key.toLowerCase();
            if (lowerCaseKey.endsWith(".jpg") || lowerCaseKey.endsWith(".jpeg")
                    || lowerCaseKey.endsWith(".png") || lowerCaseKey.endsWith(".gif")) {
                isPic = true;
            }
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            if (isPic) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType("image/jpg");
                putObjectRequest.setMetadata(metadata);
            }
            // 上传
            ossClient.putObject(putObjectRequest);
            // 关闭OSSClient。
            ossClient.shutdown();
            //返回上传之后地址，拼接地址
            String urlStr = this.getAliOssSupport().getEndpoint().replace("http://", "http://" + bucketName + ".");
            String uploadUrl = urlStr + "/" + key;
            return uploadUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    private void downloadByBucketKey(String bucketName, String key, HttpServletResponse response) throws IOException {
        String accessKeyId = this.getAliOssSupport().getAccessKeyId();
        String accessKeySecret = this.getAliOssSupport().getAccessKeySecret();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(this.getAliOssSupport().getEndpoint(), accessKeyId, accessKeySecret);
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        //BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        // 下载的文件的路径
        InputStream inputStream = ossObject.getObjectContent();
        int buffer = 1024 * 10;
        byte[] data = new byte[buffer];
        try {
            response.setContentType("application/octet-stream");
            // 文件名可以任意指定
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(key, "UTF-8"));//将文件名转为ASCLL编码
            int read;
            while ((read = inputStream.read(data)) != -1) {
                response.getOutputStream().write(data, 0, read);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            ossObject.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteByBucketKey(String bucketName, String key) throws IOException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(this.getAliOssSupport().getEndpoint(), this.getAliOssSupport().getAccessKeyId(), this.getAliOssSupport().getAccessKeySecret());
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, key);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 校验参数
     *
     * @param bucketName
     * @param module
     */
    private void isEmptyParam(String bucketName, String module) {
        if (this.getAliOssSupport().getBucketList().isEmpty()) {
            throw new BizException("未定义文件存储地址--bucketList:isEmpty！");
        }
        Map<String, BucketInfo> bucketList = this.getAliOssSupport().getBucketList();
        if (ValidationUtil.isNotEmpty(bucketName) && !bucketList.containsKey(bucketName)) {
            throw new BizException("未定义相关的储存地址，请联系管理员！");
        }
        if (ValidationUtil.isNotEmpty(module)) {
            BucketInfo bucketInfo = bucketList.get(bucketName);
            if (!bucketInfo.getModules().contains(module)) {
                throw new BizException("请确认文件夹！");
            }
        }
    }

    /**
     * 获取目录文件key
     * @param module
     * @param fileName
     * @return
     */
    private String getKey(String module,String fileName){
        String key=fileName;
        if(StringUtils.isNotEmpty(module)){
            module = module.endsWith("/") ? module : module + "/";
            key = module + fileName;
        }
        return key;
    }
}
