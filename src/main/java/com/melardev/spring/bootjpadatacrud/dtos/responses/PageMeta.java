package com.melardev.spring.bootjpadatacrud.dtos.responses;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PageMeta {
    boolean hasNext;
    boolean hasPrevPage;
    public int currentPage;
    long totalItemsCount; // total cartItems in total
    int pageSize; // max cartItems per page
    int currentItemsCount; // cartItems in this page
    int pageCount; // number of pages
    long offset;
    int nextPageNumber;
    int prevPageNumber;
    String nextPageUrl;
    String prevPageUrl;

    public PageMeta() {
    }


    public static PageMeta build(Page resourcePage, String basePath) {
        PageMeta pageMeta = new PageMeta();
        Pageable pageable = resourcePage.getPageable();

        pageMeta.setTotalItemsCount(resourcePage.getTotalElements());
        pageMeta.setOffset(pageable.getOffset());
        pageMeta.setPageSize(pageable.getPageSize());
        pageMeta.setCurrentItemsCount(resourcePage.getContent().size());
        pageMeta.setPageCount(resourcePage.getTotalPages());

        pageMeta.setCurrentPage(resourcePage.getNumber() + 1);

        pageMeta.setHasNextPage(resourcePage.hasNext());
        pageMeta.setHasPrevPage(resourcePage.hasPrevious());

        if (resourcePage.hasNext()) {
            pageMeta.setNextPageNumber(resourcePage.getPageable().next().getPageNumber() + 1);
            pageMeta.setNextPageUrl(String.format("%s?page_size=%d&page=%d",
                    basePath, pageMeta.getPageSize(), pageMeta.getNextPageNumber()));
        } else {
            pageMeta.setNextPageNumber(pageMeta.getPageCount());
            pageMeta.setNextPageUrl(String.format("%s?page_size=%d&page=%d",
                    basePath, pageMeta.getPageSize(), pageMeta.getNextPageNumber()));
        }
        if (resourcePage.hasPrevious()) {
            pageMeta.setPrevPageNumber(resourcePage.getPageable().previousOrFirst().getPageNumber() + 1);

            pageMeta.setPrevPageUrl(String.format("%s?page_size=%d&page=%d",
                    basePath, pageMeta.getPageSize(),
                    pageMeta.getPrevPageNumber()));
        } else {
            pageMeta.setPrevPageNumber(1);
            pageMeta.setPrevPageUrl(String.format("%s?page_size=%d&page=%d",
                    basePath, pageMeta.getPageSize(), pageMeta.getPrevPageNumber()));
        }

        return pageMeta;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public int getPrevPageNumber() {
        return prevPageNumber;
    }

    private void setPrevPageNumber(int prevPageNumber) {
        this.prevPageNumber = prevPageNumber;
    }

    private void setHasNextPage(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setCurrentItemsCount(int currentItemsCount) {
        this.currentItemsCount = currentItemsCount;
    }

    public int getCurrentItemsCount() {
        return currentItemsCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setNextPageNumber(int nextPageNumber) {
        this.nextPageNumber = nextPageNumber;
    }

    public int getNextPageNumber() {
        return nextPageNumber;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setHasPrevPage(boolean hasPrevPage) {
        this.hasPrevPage = hasPrevPage;
    }

    public boolean getHasPrevPage() {
        return hasPrevPage;
    }

    public void setTotalItemsCount(long totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    public long getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevPage() {
        return hasPrevPage;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }
}
