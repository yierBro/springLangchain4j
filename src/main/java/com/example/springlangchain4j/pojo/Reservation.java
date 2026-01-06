package com.example.springlangchain4j.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//无参 全参构造方法
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private String gender;
    private String phone;
    private LocalDateTime communicationTime;
    private String province;
    private Integer estimatedScore;

    public Reservation(String name, String gender, String phone, LocalDateTime communicationTime, String province, Integer estimatedScore) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.communicationTime = communicationTime;
        this.province = province;
        this.estimatedScore = estimatedScore;
    }
}
