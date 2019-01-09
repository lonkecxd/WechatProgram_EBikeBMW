package com.cxd.ebike.EBike.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
//Bike类映射到mongodb的bikes表
@Document(collection = "bikes")
public class Bike {
    @Id //主键、索引 -> Mongodb中的_id
    private String id;

    @Indexed //建立索引
    private Long bikeNo;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE) //[经度，纬度]
    private double[] location;

    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBikeNo() {
        return bikeNo;
    }

    public void setBikeNo(Long bikeNo) {
        this.bikeNo = bikeNo;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
