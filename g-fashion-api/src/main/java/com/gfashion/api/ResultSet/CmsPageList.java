package com.gfashion.api.ResultSet;

import com.gfashion.api.bean.CmsPage;

import java.util.List;

public class CmsPageList<T> {
    private List<CmsPage> items;

    public List<CmsPage> getItems() {
        return items;
    }

    public void setItems(List<CmsPage> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
