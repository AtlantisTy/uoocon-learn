package com.ty.uoocon.bo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: tianyuan
 * @Description:
 * @Date: 2023/3/3
 */
@Data
public class CataUnitBo {

    private int code;
    private String msg;
    private List<UnitLearnBo> data;
}
