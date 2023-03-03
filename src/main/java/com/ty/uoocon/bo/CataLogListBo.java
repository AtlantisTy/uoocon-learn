package com.ty.uoocon.bo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: tianyuan
 * @Description:
 * @Date: 2023/3/3
 */
@Data
public class CataLogListBo {
    private long id;
    private String name;
    private String desc;
    private long parent;
    private String status;
    private int task_id;
    private String order_id;
    private String learn_mode;
    private String learn_task_cnt;
    private String is_pay;
    private String number;
    private long pid;
    private int finished;
    private int learning;
    private int _level;
    private List<CataLogListBo> children;

}
