package com.hieu.javaweb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NGHANH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nganh {
    @Id
    private String maNganh;
    private String tenNganh;
}
