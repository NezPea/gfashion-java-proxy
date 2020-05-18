package com.gfashion.api;

import com.gfashion.api.ResultSet.CmsPageList;
import com.gfashion.api.ResultSet.UserGroupList;
import com.gfashion.domain.CmsPageContent;
import com.gfashion.domain.CustomerEntity;
import com.gfashion.restclient.MagentoClientAdvanced;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path = "/gclub", produces = "application/json")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class Gclub {

    @Autowired
    private MagentoClientAdvanced magentoClientAdvanced;

    @Autowired
    private Gson gson;

    /**
     * 临时代码，将来框架的整体 token 管理方案出来之后，再整合
     * TODO:于项目全局 token 管理机制整合
     */
    private static final String QUERY_STRING = "?searchCriteria[filter_groups][0][filters][0][field]={field}&searchCriteria[filter_groups][0][filters][0][condition_type]={condition_type}&searchCriteria[filter_groups][0][filters][0][value]={value}&searchCriteria[pageSize]={pageSize}";

    //搜索条件。magento search api 必填项
    private final Map<String, String> searchCriteria = new HashMap<>();

    public Gclub() {
        super();

        //默认每页20条数据
        this.searchCriteria.put("pageSize", "20");
    }

    /**
     * gclub 首页：用户故事
     *
     * @param pageSize 拉取文章条数
     * @return 文章列表
     */
    @GetMapping("/userStories")
    public Object userStories(@RequestParam(defaultValue = "5") int pageSize) {
        this.searchCriteria.put("field", "title");
        this.searchCriteria.put("condition_type", "eq");
        this.searchCriteria.put("value", "gclub-stories");
        this.searchCriteria.put("pageSize", String.valueOf(pageSize));

        String body = this.magentoClientAdvanced.getForBody("rest/default/V1/cmsPage/search" + QUERY_STRING, searchCriteria);

        //通过 反序列化，去掉不需要返回的字段
        //TODO:文章列表应该有 缩略图 字段
        CmsPageList cmsPageList = this.gson.fromJson(body, CmsPageList.class);

        return cmsPageList.getItems();
    }

    /**
     * 会员主页：events list
     *
     * @param pageSize 拉取文章条数
     * @return 文章列表
     */
    @GetMapping("/events")
    public Object events(@RequestParam(defaultValue = "5") int pageSize) {
        this.searchCriteria.put("field", "title");
        this.searchCriteria.put("condition_type", "eq");
        this.searchCriteria.put("value", "gclub-events");
        this.searchCriteria.put("pageSize", String.valueOf(pageSize));

        String body = this.magentoClientAdvanced.getForBody("rest/default/V1/cmsPage/search" + QUERY_STRING, searchCriteria);

        //通过 反序列化，去掉不需要返回的字段
        CmsPageList cmsPageList = this.gson.fromJson(body, CmsPageList.class);

        return cmsPageList.getItems();
    }

    /**
     * 会员主页：favourites list
     *
     * @param pageSize 拉取文章条数
     * @return 文章列表
     */
    @GetMapping("/favourites")
    public Object favourites(@RequestParam(defaultValue = "5") int pageSize) {
        this.searchCriteria.put("field", "title");
        this.searchCriteria.put("condition_type", "eq");
        this.searchCriteria.put("value", "gclub-favourites");
        this.searchCriteria.put("pageSize", String.valueOf(pageSize));

        String body = this.magentoClientAdvanced.getForBody("rest/default/V1/cmsPage/search" + QUERY_STRING, searchCriteria);

        //通过 反序列化，去掉不需要返回的字段
        CmsPageList cmsPageList = this.gson.fromJson(body, CmsPageList.class);

        return cmsPageList.getItems();
    }

    /**
     * 加入会员：会员等级列表
     *
     * @return 会员等级列表
     */
    @GetMapping("/userLevels")
    public Object userLevels() {
        this.searchCriteria.put("field", "customer_group_code");
        this.searchCriteria.put("condition_type", "like");
        this.searchCriteria.put("value", "gclub-level%");

        String body = this.magentoClientAdvanced.getForBody("rest/default/V1/customerGroups/search" + QUERY_STRING, searchCriteria);

        //通过 反序列化，去掉不需要返回的字段
        UserGroupList userGroupList = this.gson.fromJson(body, UserGroupList.class);

        return userGroupList.getItems();
    }

    /**
     * 会员升级：获取会员当前等级
     *
     * @return 会员个人信息
     */
    @GetMapping("/me")
    public CustomerEntity me(@RequestHeader("Authorization") String token) {

        this.magentoClientAdvanced.setToken(token.substring(token.indexOf(" ") + 1));

        String body = this.magentoClientAdvanced.getForBody("rest/default/V1/customers/me");

        //通过 反序列化，去掉不需要返回的字段
        //TODO: 将 group_id 映射为 group_name 而不是直接返回
        return this.gson.fromJson(body, CustomerEntity.class);
    }

    /**
     * 获取文章详情
     *
     * @param pageId 文章ID
     * @return 文章内容
     */
    @GetMapping("/page")
    public CmsPageContent getPage(int pageId) {
        String body = this.magentoClientAdvanced.getForBody("rest/default/V1/cmsPage/" + pageId);

        //通过 反序列化，去掉不需要返回的字段
        return this.gson.fromJson(body, CmsPageContent.class);
    }
}
