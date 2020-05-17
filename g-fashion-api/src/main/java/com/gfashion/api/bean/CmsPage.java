package com.gfashion.api.bean;

public class CmsPage {

    private String id;

    //不需要这个链接，而是通过前端 id 获取直接获取文章内容
    //private String identifier;

    //仅做分类用，不需要返回
    //private String title;

    //真正的页面标题
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
