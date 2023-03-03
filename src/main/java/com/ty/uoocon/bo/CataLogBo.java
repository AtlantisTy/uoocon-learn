package com.ty.uoocon.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Auther: tianyuan
 * @Description:
 * @Date: 2023/3/3
 */
@Data
public class CataLogBo {

    private int code;
    private String msg;
    private List<CataLogListBo> data;
}
