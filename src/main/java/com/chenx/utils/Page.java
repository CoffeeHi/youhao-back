package com.chenx.utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 分页承载类，每次分页都会自动生成该类，用于页面展示使用
 */
public class Page<T> implements Serializable
{
    /**
     * 缺省每页的记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 15;

    /**
     * 获取任一页第一条数据的位置，每页条数使用默认值,此静态方法为了方便直接跳转至某一页时使用
     */
    public static int getStartOfPage(int pageNo)
    {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据的位置,startIndex从0开始
     */
    public static int getStartOfPage(int pageNo, int pageSize)
    {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 当前页中存放的数据集合
     */
    protected Object currentPageData;

    /**
     * 当前页第一条数据在总记录集的位置,从0开始
     */
    protected int currentPageStartIndex;

    /**
     * 每页的记录数
     */
    protected int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 整个总记录集拥有的总记录数
     */
    protected long totalSize;

    /**
     * 起始页码
     */
    protected int pageNo;

    /**
     * 构造方法，只构造空页
     */
    public Page()
    {
        this(0,DEFAULT_PAGE_SIZE, 0L,  new ArrayList());
    }

    /**
     * 默认构造方法
     *
     * @param currentPageStartIndex 本页数据在数据库中的起始位置
     * @param totalSize             数据库中总记录条数
     * @param pageSize              本页容量
     * @param currentData           本页包含的数据
     */
    @Deprecated
    public Page(int currentPageStartIndex, long totalSize, int pageSize,
                Object currentData)
    {
        this.currentPageStartIndex = currentPageStartIndex;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.currentPageData = currentData;
    }



    /**
     * 默认构造方法
     *
     * @param pageNo 本页数据在数据库中的起始位置
     * @param totalSize             数据库中总记录条数
     * @param pageSize              本页容量
     * @param currentData           本页包含的数据
     */
    public Page(int pageNo,  int pageSize,long totalSize,
                Object currentData)
    {
        this.pageNo= pageNo;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.currentPageData = currentData;

        this.currentPageStartIndex = Page.getStartOfPage(pageNo,pageSize);
    }

    /**
     * 当前页中的数据集合
     */
    public Object getCurrentPageData()
    {
        return this.currentPageData;
    }

    public void setCurrentPageData(Object currentPageData){
        this.currentPageData= currentPageData;
    }

    /**
     * 取当前页码,页码从1开始
     */
    public int getCurrentPageNo()
    {
        return (this.currentPageStartIndex / pageSize) + 1;
    }

    /**
     * 取每页的记录数
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * 取每页的记录数
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * 取总页数
     */
    public long getTotalPageSize()
    {
        if (this.totalSize == 0)
        {
            return 0;
        } else
        {
            long TotalPageSize = this.totalSize / pageSize;
            return this.totalSize % pageSize == 0 ? TotalPageSize : TotalPageSize + 1;
        }
    }

    /**
     * 取数据库中包含的总记录数
     */
    public long getTotalSize()
    {
        return this.totalSize;
    }

    public void setTotalSize(int totalSize){
        this.totalSize=totalSize;
    }

    public void setTotalSize(long totalSize){
        this.totalSize=totalSize;
    }

    /**
     * 是否有下一页
     */
    public boolean hasNextPage()
    {
        return (this.getCurrentPageNo() < this.getTotalPageSize());
    }

    /**
     * 是否有上一页
     */
    public boolean hasPreviousPage()
    {
        return (this.getCurrentPageNo() > 1);
    }

    /**
     * 取分页的页码
     */
    public int getPageNo()
    {
        return this.pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
}
