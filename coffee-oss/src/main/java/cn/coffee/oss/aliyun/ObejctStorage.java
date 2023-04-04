package cn.coffee.oss.aliyun;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ObejctStorage {
    //文件名
    private String name;
    //key
    private String key;
    //url地址
    private String url;
    //大小
    private long size;
    //最后修改时间
    private Date lastModified;

    private List<ObejctStorage> subordinate;
}
