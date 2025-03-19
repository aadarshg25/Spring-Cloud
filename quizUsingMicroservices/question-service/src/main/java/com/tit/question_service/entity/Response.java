package com.tit.question_service.entity;

public class Response {
    private Integer id;
    private String response;

    public Response(Integer id, String response) {
        this.id = id;
        this.response = response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getresponse() {
        return response;
    }

    public void setresponse(String response) {
        this.response = response;
    }
}
