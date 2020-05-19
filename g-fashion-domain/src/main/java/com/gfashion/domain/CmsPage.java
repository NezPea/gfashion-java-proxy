package com.gfashion.domain;

public class CmsPage {

    //文章ID
    private String id;

    //文章标题
    private String content_heading;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_heading() {
        return content_heading;
    }

    public void setContent_heading(String content_heading) {
        this.content_heading = content_heading;
    }

    @Override
    public String toString() {
        return "CmsPage{" +
                "id='" + id + '\'' +
                ", content_heading='" + content_heading + '\'' +
                '}';
    }
}
