package cn.coffee.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Tomato
 * @description:
 * @date: 2023-03-29:09:34
 */
@Component("httpUtils")
public class HttpUtils {

    /**
     * =====================GET请求=====================
     */

    public static String get(String uri) {
        return HttpUtil.get(uri);
    }

    public static JSONObject getToJson(String uri) {
        return JSONObject.parseObject(HttpUtil.get(uri));
    }

    public static String get(String uri, int timeout) {
        return HttpUtil.get(uri, timeout);
    }

    public static JSONObject getToJson(String uri, int timeout) {
        return JSONObject.parseObject(HttpUtil.get(uri, timeout));
    }

    public static String get(String uri, Map<String, Object> map) {
        return HttpUtil.get(uri, map);
    }

    public static JSONObject getToJson(String uri, Map<String, Object> map) {
        return JSONObject.parseObject(HttpUtil.get(uri, map));
    }

    public static String get(String uri, Map<String, Object> map, int timeout) {
        return HttpUtil.get(uri, map, timeout);
    }

    public static JSONObject getToJson(String uri, Map<String, Object> map, int timeout) {
        return JSONObject.parseObject(HttpUtil.get(uri, map, timeout));
    }


    /**
     * =====================POST请求=====================
     */

    public static String post(String uri, Map<String, Object> map) {
        return HttpUtil.post(uri, map);
    }

    public static JSONObject postToJson(String uri, Map<String, Object> map) {
        return JSONObject.parseObject(HttpUtil.post(uri, map));
    }

    public static String post(String uri, Map<String, Object> map, int timeout) {
        return HttpUtil.post(uri, map, timeout);
    }

    public static JSONObject postToJson(String uri, Map<String, Object> map, int timeout) {
        return JSONObject.parseObject(HttpUtil.post(uri, map, timeout));
    }

    public static String post(String uri, String body) {
        return HttpUtil.post(uri, body);
    }

    public static JSONObject postToJson(String uri, String body) {
        return JSONObject.parseObject(HttpUtil.post(uri, body));
    }

    public static String post(String uri, String body, int timeout) {
        return HttpUtil.post(uri, body, timeout);
    }

    public static JSONObject postToJson(String uri, String body, int timeout) {
        return JSONObject.parseObject(HttpUtil.post(uri, body, timeout));
    }


}
