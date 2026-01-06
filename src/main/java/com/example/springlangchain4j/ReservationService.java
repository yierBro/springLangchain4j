package com.example.springlangchain4j;

import com.example.springlangchain4j.mapper.ReservationMapper;
import com.example.springlangchain4j.pojo.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;
    //1.添加预约信息的方法
    public void insert(Reservation reservation){
        reservationMapper.insert(reservation);
    };

    //2.查询预约信息的方法
    public Reservation findByPhone(String phone){
        return reservationMapper.findByPhone(phone);
    }
}
