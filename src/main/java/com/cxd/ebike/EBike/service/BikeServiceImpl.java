package com.cxd.ebike.EBike.service;

import com.cxd.ebike.EBike.entity.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Bike bike) {
//        mongoTemplate.insert(bike,"bikes");
        mongoTemplate.insert(bike);
    }

    @Override
    public List<GeoResult<Bike>> findNear(double longitude, double latitude) {
        //查找所有的单车
//        return mongoTemplate.findAll(Bike.class);
        //查找附近的单车
        NearQuery near = NearQuery.near(longitude,latitude);
        //查找的范围和距离单位
        near.maxDistance(1.2, Metrics.KILOMETERS);
        GeoResults<Bike> bikes = mongoTemplate.geoNear(near.query(new Query(Criteria.where("status").is(0)).limit(20)), Bike.class);
        return bikes.getContent();
    }


}
