package com.course.model;

import lombok.Data;

@Data
public class GetUserInfoCase {
    private String id;
    private String userId;
    private String expected;
}
