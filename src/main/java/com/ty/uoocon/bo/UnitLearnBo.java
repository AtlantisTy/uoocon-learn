package com.ty.uoocon.bo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: tianyuan
 * @Description:
 * @Date: 2023/3/3
 */
@Data
public class UnitLearnBo {

    private long id;
    private long course_id;
    private String title;
    private String content;
    private String uri;
    private String type;
    private String is_task;
    private int task_id;
    private int discuss_id;
    private String sort;
    private String parent;
    private String transcode_url;
    private int _level;
    private int pid;
    private long catalog_id;
    private CdnSumBo video_url;
    private List<CdnBo> video_play_list;
    private List<String> quiz;
    private String video_pos;
    private int finished;
    private int pay_status;

}
