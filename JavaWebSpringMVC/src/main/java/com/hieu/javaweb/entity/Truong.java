package com.hieu.javaweb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRUONG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Truong {
    @Id
    private String maTruong;
    private String tenTruong;
}
