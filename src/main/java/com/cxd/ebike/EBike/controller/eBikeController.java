package com.cxd.ebike.EBike.controller;

import com.cxd.ebike.EBike.entity.Bike;
import com.cxd.ebike.EBike.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/bike")
@Controller
public class eBikeController {

    @Autowired //在Spring容器中找到Service，注入到Controller里
    private BikeService bikeService;

    @RequestMapping("/add")
    @ResponseBody //以json形式返回
    public String addBike(@RequestBody Bike bike){
        //保存到MongoDB
        bikeService.save(bike);
        return "success ";
    }

    @RequestMapping("/find_near")
    @ResponseBody //以json形式返回
    public List<GeoResult<Bike>> findNear(double longitude,double latitude){
        //保存到MongoDB
        List<GeoResult<Bike>> bikes = bikeService.findNear(longitude,latitude);
        return bikes;
    }

}
