package com.ty.uoocon.bo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: tianyuan
 * @Description:
 * @Date: 2023/3/3
 */
@Data
public class CdnBo {
    private String source;
    private int line;
    private String ratio;
    private String source_name;
    private List<SubtitleBo> subtitle;
}
