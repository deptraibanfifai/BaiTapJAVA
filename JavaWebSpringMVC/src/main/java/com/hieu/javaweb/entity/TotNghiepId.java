package com.hieu.javaweb.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class TotNghiepId implements Serializable {
    private String soCMND;
    private String maTruong;
    private String maNganh;
}
