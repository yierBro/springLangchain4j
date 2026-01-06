package com.example.springlangchain4j.tools;

import com.example.springlangchain4j.ReservationService;
import com.example.springlangchain4j.pojo.Reservation;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("ReservationToolaa")
public class ReservationToolaa {
    @Autowired
    private ReservationService reservationService;

    //工具方法 添加预约信息
    @Tool("预约志愿填报服务")
    public void addReservation(
            @P("考生姓名") String name,
            @P("考生性别") String gender,
            @P("考生手机号") String phone,
            @P("预约沟通时间,格式为: yyyy-MM-dd'T'HH:mm") String communicationTime,
            @P("考生所在省份") String province,
            @P("考试预估分数") Integer estimatedScore){
        Reservation reservation=new Reservation(name,gender,phone, LocalDateTime.parse(communicationTime),province,estimatedScore);
        reservationService.insert(reservation);
    }



    //工具方法查看预约信息
    @Tool("根据考生手机号查询预约单")
    public Reservation findReservation(@P("考生手机号") String phone){
        return reservationService.findByPhone(phone);
    }
}
