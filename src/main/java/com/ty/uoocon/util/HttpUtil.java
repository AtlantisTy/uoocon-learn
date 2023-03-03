package com.ty.uoocon.util;

import com.alibaba.fastjson2.JSON;
import com.ty.uoocon.bo.CataUnitBo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: tianyuan
 * @Description:
 * @Date: 2023/3/3
 */
@Component
@Slf4j
public class HttpUtil {

    private OkHttpClient httpClient = new OkHttpClient();

    public String doGet(String url, LinkedHashMap<String,String> headers){
        String result = "";
        try {
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .get();
            //设置消息头
            for (Map.Entry<String,String> entry:headers.entrySet()){
                builder.addHeader(entry.getKey(),entry.getValue());
            }
            Request request = builder.build();

            Response response = httpClient
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            } else {
                log.info(response.body().string());
                result = "error";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public String doPost(String url, LinkedHashMap<String, String> headers, LinkedHashMap<String,String> form){
        String result = "";
        try {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            //生成form表单
            for (Map.Entry<String,String> entry:form.entrySet()){
                bodyBuilder.add(entry.getKey(),entry.getValue());
            }
            FormBody formBody = bodyBuilder.build();

            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .post(formBody);
            //设置消息头
            for (Map.Entry<String,String> entry:headers.entrySet()){
                builder.addHeader(entry.getKey(),entry.getValue());
            }
            Request request = builder.build();

            Response response = httpClient
                    .newCall(request)
                    .execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            } else {
                log.info(response.body().string());
                result = "error";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public static void main(String[] args) {
        //构造刷课url
        String markUrl = "http://cce.org.uooconline.com/home/learn/markVideoLearn";

        //构造header
        LinkedHashMap<String,String> markHeaders = new LinkedHashMap<>();
        markHeaders.put("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        markHeaders.put("Cookie","account=13177181684; examRemindNum_614428350=1; cerRemindNum_614428350=1; cerRemindNum_1233459898=1; examRemindNum_1233459898=1; JSESSID=6ob72kggcla9neagsdumq7dg37; Hm_lvt_7c307a902207c45c0eaf86510e2c24a1=1677747604; pwd_acount=; uooc_auth=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyZW1lbWJlciI6ZmFsc2UsImxvZ2luX2hvc3QiOiJjY2Uub3JnLnVvb2NvbmxpbmUuY29tIiwic3ViIjoiODc4MjE0IiwiZXhwIjoxNjgwMzM5NjMyfQ.FoPk0KbGrt6hYUyWBO3fsXavFqidcQbvBkyTSe5QOKI; password_set=true; user_ad_view_183.9.102.136=1; formpath=/home/learn/index; Hm_lpvt_7c307a902207c45c0eaf86510e2c24a1=1677747660; formhash=/614428350/1458517007/1802975910");
        markHeaders.put("Host","cce.org.uooconline.com");
        markHeaders.put("Origin","http://cce.org.uooconline.com");
        markHeaders.put("Referer","http://cce.org.uooconline.com/home/learn/index");
        markHeaders.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36 Edg/110.0.1587.57");

        //构造form
        LinkedHashMap<String,String> form = new LinkedHashMap<>();
        form.put("chapter_id","1224047772");
        form.put("cid","76936268");
        form.put("hidemsg_","true");
        form.put("network","1");
        form.put("resource_id","1846177434");
        form.put("section_id","782374385");
        form.put("source","1");
        form.put("subsection_id","0");
        form.put("video_length","3167.98");
        form.put("video_pos","4.09");

        HttpUtil util = new HttpUtil();
        String body = util.doPost(markUrl, markHeaders, form);

        log.info("end");
    }

}
