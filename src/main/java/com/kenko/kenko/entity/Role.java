package com.kenko.kenko.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("roles")
public class Role {

    @Id
    private String id;

    private ERole name;

}
