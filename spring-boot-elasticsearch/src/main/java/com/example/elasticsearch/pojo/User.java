package com.example.elasticsearch.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * es实体类
 * @author wangpeil
 */

@Document(indexName = "qsoc")
@Data
public class User implements Serializable {
    /**
     * Id标注主键
     */
    @Id
    private Long id;
    @Field(type = FieldType.Auto)
    private int age;
    @Field(type =  FieldType.Auto)
    private String gender;
}
