package com.ty.uoocon;

import com.alibaba.fastjson2.JSON;
import com.ty.uoocon.bo.*;
import com.ty.uoocon.util.HttpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
@Slf4j
class UooconApplicationTests {

    @Value("${uoocon.unitLearn.header.Cookie}")
    private String unitLearnCookie;
    @Value("${uoocon.unitLearn.header.Host}")
    private String unitLearnHost;
    @Value("${uoocon.unitLearn.header.Referer}")
    private String unitLearnReferer;

    @Value("${uoocon.mark.header.Content-Type}")
    private String markContentType;
    @Value("${uoocon.mark.header.Cookie}")
    private String markCookie;
    @Value("${uoocon.mark.header.Host}")
    private String markHost;
    @Value("${uoocon.mark.header.Origin}")
    private String markOrigin;
    @Value("${uoocon.mark.header.Referer}")
    private String markReferer;
    @Value("${uoocon.mark.header.User-Agent}")
    private String markUserAgent;

    /*
     * 总错误次数，超过10次就结束程序。免得人家后台排查到异常了
     */
    int exceptionIndex = 0;

    /*
     *cid，从网页上面复制
     * 每门课复制一次就行
     */
    String cid = "76936268";

    /*
     * 课程列表，从网页上面复制。找到 http://cce.org.uooconline.com/home/learn/getCatalogList?cid=76936268&hidemsg_=true&show= 的响应报文
     * 每门课复制一次就行
     */
    String catalogList = "{\n" +
            "  \"code\": 1,\n" +
            "  \"msg\": \"\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": 818876826,\n" +
            "      \"name\": \"第一章 礼仪概述\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"10000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"4\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 1,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 1,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 381466367,\n" +
            "          \"name\": \"礼仪的定义与内涵\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 818876826,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"10100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"1.1\",\n" +
            "          \"pid\": 818876826,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 2003455952,\n" +
            "          \"name\": \"礼仪的起源与演进\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 818876826,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"10200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"1.2\",\n" +
            "          \"pid\": 818876826,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1431823413,\n" +
            "          \"name\": \"礼仪的核心价值观\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 818876826,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"10300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"1.3\",\n" +
            "          \"pid\": 818876826,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 721730838,\n" +
            "          \"name\": \"礼仪的特征与作用\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 818876826,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"10400\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"1.4\",\n" +
            "          \"pid\": 818876826,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 166875723,\n" +
            "      \"name\": \"第二章 个人形象管理\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"20000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"3\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 2,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 1,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 1876945580,\n" +
            "          \"name\": \"色彩的奥秘\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 166875723,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"20100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"2.1\",\n" +
            "          \"pid\": 166875723,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1301054337,\n" +
            "          \"name\": \"个人色彩特征\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 166875723,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"20200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"2.2\",\n" +
            "          \"pid\": 166875723,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 578443490,\n" +
            "          \"name\": \"个人形象与风格\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 166875723,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"20300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"2.3\",\n" +
            "          \"pid\": 166875723,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2617799,\n" +
            "      \"name\": \"第三章 女士着装\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"30000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"3\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 3,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 1,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 1712625208,\n" +
            "          \"name\": \"女装分类\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 2617799,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"30100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"3.1\",\n" +
            "          \"pid\": 2617799,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1153576733,\n" +
            "          \"name\": \"丝巾系艺\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 2617799,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"30200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"3.2\",\n" +
            "          \"pid\": 2617799,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 447726718,\n" +
            "          \"name\": \"简易妆容\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 2617799,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"30300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"3.3\",\n" +
            "          \"pid\": 2617799,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2023529811,\n" +
            "      \"name\": \"第四章 男士着装\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"40000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"4\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 4,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 1,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 1498035636,\n" +
            "          \"name\": \"西装简介\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 2023529811,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"40100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"4.1\",\n" +
            "          \"pid\": 2023529811,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1060621033,\n" +
            "          \"name\": \"西装配饰\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 2023529811,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"40200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"4.2\",\n" +
            "          \"pid\": 2023529811,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 501572554,\n" +
            "          \"name\": \"衬衫与袖扣\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 2023529811,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"40300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"4.3\",\n" +
            "          \"pid\": 2023529811,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1943156783,\n" +
            "          \"name\": \"领带与系法\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 2023529811,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"40400\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"4.4\",\n" +
            "          \"pid\": 2023529811,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1371525376,\n" +
            "      \"name\": \"第五章 第一印象管理\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"50000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"3\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 5,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 1,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 913143397,\n" +
            "          \"name\": \"赢在第一印象\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1371525376,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"50100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"5.1\",\n" +
            "          \"pid\": 1371525376,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 341446470,\n" +
            "          \"name\": \"行礼有术\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1371525376,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"50200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"5.2\",\n" +
            "          \"pid\": 1371525376,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1778886587,\n" +
            "          \"name\": \"站坐蹲行要领\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1371525376,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"50300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"5.3\",\n" +
            "          \"pid\": 1371525376,\n" +
            "          \"finished\": 1,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1224047772,\n" +
            "      \"name\": \"第六章 沟通礼仪（上）\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"60000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"2\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 6,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 0,\n" +
            "      \"learning\": 1,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 782374385,\n" +
            "          \"name\": \"说话艺术与礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1224047772,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"60100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"6.1\",\n" +
            "          \"pid\": 1224047772,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 1,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 210741970,\n" +
            "          \"name\": \"日常生活谈话技巧\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1224047772,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"60200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"6.2\",\n" +
            "          \"pid\": 1224047772,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1836925751,\n" +
            "      \"name\": \"第七章 沟通礼仪（中）\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"70000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"3\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 7,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 0,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 1126832232,\n" +
            "          \"name\": \"谈话技巧1：如何陈述\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1836925751,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"70100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"7.1\",\n" +
            "          \"pid\": 1836925751,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 555200845,\n" +
            "          \"name\": \"谈话技巧2：协调与影响他人\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1836925751,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"70200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"7.2\",\n" +
            "          \"pid\": 1836925751,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 130369966,\n" +
            "          \"name\": \"谈话技巧3：劝说\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1836925751,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"70300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"7.3\",\n" +
            "          \"pid\": 1836925751,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1706225283,\n" +
            "      \"name\": \"第八章 沟通礼仪（下）\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"80000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"3\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 8,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 0,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 1000310756,\n" +
            "          \"name\": \"网络沟通礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1706225283,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"80100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"8.1\",\n" +
            "          \"pid\": 1706225283,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 407723225,\n" +
            "          \"name\": \"电话沟通礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1706225283,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"80200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"8.2\",\n" +
            "          \"pid\": 1706225283,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 2117793082,\n" +
            "          \"name\": \"文书礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1706225283,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"80300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"8.3\",\n" +
            "          \"pid\": 1706225283,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1558678047,\n" +
            "      \"name\": \"第九章 接待访客礼仪\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"90000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"3\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 9,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 0,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 852829040,\n" +
            "          \"name\": \"接待礼仪简介\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1558678047,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"90100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"9.1\",\n" +
            "          \"pid\": 1558678047,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 277018709,\n" +
            "          \"name\": \"送礼礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1558678047,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"90200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"9.2\",\n" +
            "          \"pid\": 1558678047,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1903136950,\n" +
            "          \"name\": \"会议礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1558678047,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"90300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"9.3\",\n" +
            "          \"pid\": 1558678047,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1465723371,\n" +
            "      \"name\": \"第十章 中餐礼仪\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"100000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"4\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 10,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 0,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 889900748,\n" +
            "          \"name\": \"座次安排\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1465723371,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"100100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"10.1\",\n" +
            "          \"pid\": 1465723371,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 200828705,\n" +
            "          \"name\": \"中餐点餐\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1465723371,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"100200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"10.2\",\n" +
            "          \"pid\": 1465723371,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1772436482,\n" +
            "          \"name\": \"行餐礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1465723371,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"100300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"10.3\",\n" +
            "          \"pid\": 1465723371,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1335022951,\n" +
            "          \"name\": \"中国传统菜系\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 1465723371,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"100400\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"10.4\",\n" +
            "          \"pid\": 1465723371,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 742419032,\n" +
            "      \"name\": \"第十一章 公共场所礼仪\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"110000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"3\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 11,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 0,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 36504253,\n" +
            "          \"name\": \"交通工具礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 742419032,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"110100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"11.1\",\n" +
            "          \"pid\": 742419032,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1612372894,\n" +
            "          \"name\": \"娱乐、休闲场所礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 742419032,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"110200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"11.2\",\n" +
            "          \"pid\": 742419032,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1187541235,\n" +
            "          \"name\": \"办公室礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 742419032,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"110300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"11.3\",\n" +
            "          \"pid\": 742419032,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 615844308,\n" +
            "      \"name\": \"第十二章 西餐礼仪\",\n" +
            "      \"desc\": \"\",\n" +
            "      \"parent\": 0,\n" +
            "      \"status\": \"2\",\n" +
            "      \"task_id\": 0,\n" +
            "      \"order_id\": \"120000\",\n" +
            "      \"learn_mode\": \"10\",\n" +
            "      \"learn_task_cnt\": \"4\",\n" +
            "      \"is_pay\": \"0\",\n" +
            "      \"number\": 12,\n" +
            "      \"pid\": 0,\n" +
            "      \"finished\": 0,\n" +
            "      \"learning\": 0,\n" +
            "      \"_level\": 1,\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"id\": 90353161,\n" +
            "          \"name\": \"西餐入座礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 615844308,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"120100\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"12.1\",\n" +
            "          \"pid\": 615844308,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 1531987818,\n" +
            "          \"name\": \"西餐菜式介绍\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 615844308,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"120200\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"12.2\",\n" +
            "          \"pid\": 615844308,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 960306255,\n" +
            "          \"name\": \"餐具介绍\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 615844308,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"120300\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"12.3\",\n" +
            "          \"pid\": 615844308,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": 535475360,\n" +
            "          \"name\": \"行餐礼仪\",\n" +
            "          \"desc\": \"\",\n" +
            "          \"parent\": 615844308,\n" +
            "          \"status\": \"2\",\n" +
            "          \"task_id\": 0,\n" +
            "          \"order_id\": \"120400\",\n" +
            "          \"learn_mode\": \"10\",\n" +
            "          \"learn_task_cnt\": \"1\",\n" +
            "          \"is_pay\": \"0\",\n" +
            "          \"number\": \"12.4\",\n" +
            "          \"pid\": 615844308,\n" +
            "          \"finished\": 0,\n" +
            "          \"learning\": 0,\n" +
            "          \"_level\": 2\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Autowired
    private HttpUtil httpUtil;

    @Test
    void contextLoads() {
        //开始解析章节 http://cce.org.uooconline.com/home/learn/getCatalogList?cid=76936268&hidemsg_=true&show=

        CataLogBo title = JSON.parseObject(catalogList,CataLogBo.class);

        try {
            //刷未完成的视频
            List<CataLogListBo> titleData = title.getData();
            for (CataLogListBo cataLogListBo:titleData){
                if (cataLogListBo.getFinished() == 1){
                    //完成的就跳过
                    log.info("{}已完成，开始下一个章节",cataLogListBo.getName());
                    continue;
                }else {
                    List<CataLogListBo> childrenList = cataLogListBo.getChildren();
                    //循环子章节
                    for (CataLogListBo children:childrenList){
                        if (children.getFinished() == 1){
                            log.info("{}->{}已完成，开始下一个子章节",cataLogListBo.getName(),children.getName());
                        }else {
                            //构造url
                            StringBuilder url = new StringBuilder("http://cce.org.uooconline.com/home/learn/getUnitLearn?");
                            url.append("catalog_id="+children.getId());
                            url.append("&");
                            url.append("chapter_id="+children.getParent());
                            url.append("&");
                            url.append("cid="+cid);
                            url.append("&");
                            url.append("hidemsg_=true");
                            url.append("&");
                            url.append("section_id="+children.getId());
                            url.append("&");
                            url.append("show=");

                            //构造header
                            LinkedHashMap<String,String> headers = new LinkedHashMap<>();
                            headers.put("Cookie",unitLearnCookie);
                            headers.put("Host",unitLearnHost);
                            headers.put("Referer",unitLearnReferer);

                            //发起请求获取章节详情
                            log.info("开始获取章节详情：{}->{}",cataLogListBo.getName(),children.getName());
                            String body = httpUtil.doGet(url.toString(), headers);
                            CataUnitBo cataUnitBo = JSON.parseObject(body, CataUnitBo.class);

                            List<UnitLearnBo> unitLearnBoList = cataUnitBo.getData();
                            for (UnitLearnBo unitLearnBo:unitLearnBoList){
                                if (unitLearnBo.getFinished() == 1){
                                    log.info("{}->{}->{}已完成，开始下一个子视频",cataLogListBo.getName(),children.getName(),unitLearnBo.getTitle());
                                    continue;
                                }else {
                                    //构造刷课url
                                    String markUrl = "http://cce.org.uooconline.com/home/learn/markVideoLearn";

                                    //构造header
                                    LinkedHashMap<String,String> markHeaders = new LinkedHashMap<>();
                                    markHeaders.put("Content-Type",markContentType);
                                    markHeaders.put("Cookie",markCookie);
                                    markHeaders.put("Host",markHost);
                                    markHeaders.put("Origin",markOrigin);
                                    markHeaders.put("Referer",markReferer);
                                    markHeaders.put("User-Agent",markUserAgent);

                                    //构造form
                                    LinkedHashMap<String,String> form = new LinkedHashMap<>();
                                    form.put("chapter_id",children.getParent()+"");
                                    form.put("cid",unitLearnBo.getCourse_id()+"");
                                    form.put("hidemsg_","true");
                                    form.put("network","1");
                                    form.put("resource_id",unitLearnBo.getId()+"");
                                    form.put("section_id",unitLearnBo.getCatalog_id()+"");
                                    form.put("source","1");
                                    form.put("subsection_id","0");
                                    form.put("video_length","11321.98");
                                    form.put("video_pos",unitLearnBo.getVideo_pos());

                                    while (true){
                                        Thread.sleep(35000);
                                        log.info("开始播放章节{}->{}->{}。当前视频进度{}",cataLogListBo.getName(),children.getName(),unitLearnBo.getTitle(),form.get("video_pos"));
                                        String markBody = httpUtil.doPost(markUrl, markHeaders, form);
                                        log.info("当前章节{}->{}->{}。返回结果{}",cataLogListBo.getName(),children.getName(),unitLearnBo.getTitle(),markBody);
                                        if ("error".equals(markBody)){
                                            log.info("刷课url请求出错...");
                                            continue;
                                        }else {
                                            try {
                                                if (markBody.contains("600")){
                                                    //可能是刷太快了，报视频进度不能拖拽的错误
                                                    continue;
                                                }else {
                                                    CataResultBo cataResultBo = JSON.parseObject(markBody, CataResultBo.class);
                                                    if (cataResultBo.getData().getFinished() == 1) {
                                                        log.info("当前章节{}->{}->{}播放完成", cataLogListBo.getName(), children.getName(), unitLearnBo.getTitle());
                                                        break;
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (!exceptionCheck(exceptionIndex)){
                                                    return;
                                                }
                                                log.info("刷课异常，忽略继续...");
                                                continue;
                                            }
                                        }
                                        //视频进度移动
                                        Double d = Double.parseDouble(form.get("video_pos"));
                                        d += genRandom();
                                        BigDecimal b = new BigDecimal(d);
                                        form.put("video_pos",b.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                                    }

                                }
                            }


                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        log.info("end----");
    }

    /**
     * 随机数生成器，每次视频进度调大35.00~40.99之间
     * @return
     */
    private double genRandom(){
        Random random = new Random();
        int indexI = random.nextInt(5)+35;
        double indexD = random.nextDouble();
        double re = indexI + indexD;
        return re;
    }

    /**
     * 异常次数检查器
     * @param exceptionIndex
     * @return 返回true可以继续执行，返回false就结束程序
     */
    private boolean exceptionCheck(int exceptionIndex){
        exceptionIndex++;
        if (exceptionIndex > 10){
            log.info("程序已经达到最大失败次数，放弃执行");
            return false;
        }
        return true;
    }

}
