package com.hieu.javaweb.config;

import com.hieu.javaweb.entity.Nganh;
import com.hieu.javaweb.entity.Truong;
import com.hieu.javaweb.repository.NganhRepository;
import com.hieu.javaweb.repository.TruongRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(TruongRepository truongRepo, NganhRepository nganhRepo) {
        return args -> {
            truongRepo.saveAll(Arrays.asList(
                new Truong("BK", "Đại học Bách Khoa"),
                new Truong("KHTN", "Đại học Khoa học Tự nhiên"),
                new Truong("SPKT", "Đại học Sư phạm Kỹ thuật")
            ));

            nganhRepo.saveAll(Arrays.asList(
                new Nganh("CNTT", "Công nghệ thông tin"),
                new Nganh("OTO", "Công nghệ kỹ thuật Ô tô"),
                new Nganh("DDT", "Điện - Điện tử")
            ));
        };
    }
}
