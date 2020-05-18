package com.gfashion.api.ResultSet;

import com.gfashion.domain.UserGroup;

import java.util.List;

public class UserGroupList {
    private List<UserGroup> items;

    public List<UserGroup> getItems() {
        return items;
    }

    public void setItems(List<UserGroup> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
