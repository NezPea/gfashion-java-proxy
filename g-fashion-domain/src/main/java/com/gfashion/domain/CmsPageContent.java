package com.gfashion.domain;

public class CmsPageContent {

    //文章ID
    private String id;

    //文章正文内容
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CmsPageContent{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
