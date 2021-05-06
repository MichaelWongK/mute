package com.micheal.mute.commons.page;

import com.micheal.mute.commons.utils.StringUtils;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/6 10:28
 * @Description 分页数据
 */
public class PageDomain {

    /** 当前记录索引 */
    private Integer pageNum;
    /** 每页显示记录数 */
    private Integer pageSize;
    /** 拍序列 */
    private String orderByColumn;
    /** 排序的方向 'desc' or 'asc' */
    private String isAsc;

    // defaultValue 0
    public Integer getPageNum() {
        if (StringUtils.isNull(pageNum) || pageNum < 1) {
            return 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    // defaultValue 10
    public Integer getPageSize() {
        if (StringUtils.isNull(pageSize)) {
            return 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        if (StringUtils.isNotEmpty(orderByColumn))
            return "";
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }
}
