package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * @Auther: xuyang
 * @Date: 2020/1/21 19:33
 * @Description:
 */
public class SysBlog extends BaseEntity {

    private static final long serialVersionUID = 7796053323245910719L;

    private String blogId;

    private String blogTitle;

    private String blogRemark;

    private String blogContent;

    private String blogType;

    private String blogUrl;

    private String userId;

    private String ids;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogRemark() {
        return blogRemark;
    }

    public void setBlogRemark(String blogRemark) {
        this.blogRemark = blogRemark;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }
}
