package cn.coffee.oss.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class AliOssSupport {

    @Getter
    private String endpoint;
    @Getter
    private String accessKeyId;
    @Getter
    private String accessKeySecret;
    @Getter
    private OSS ossClient;
    @Getter
    private String bucketDefault;
    @Getter
    private Map<String,BucketInfo> bucketList;

    public AliOssSupport(String endpoint, String accessKeyId, String accessKeySecret,String bucketDefault,String bucketListJson){
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketDefault = bucketDefault;
        this.bucketList = JSONObject.parseObject(bucketListJson,new TypeReference<Map<String, BucketInfo>>(){});
        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    public List<OSSObjectSummary> listObjects(String bucketName, String prefix) {
        ObjectListing objectListing = this.ossClient.listObjects(bucketName, prefix);
        List<OSSObjectSummary> list = objectListing.getObjectSummaries();
        return list;
    }
}
