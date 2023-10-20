package br.com.dducl.bffmarketplaceapp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    private Integer page;

    private Integer pageSize;

    private Integer totalPages;

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private static final Integer DEFAULT_INITIAL_PAGE = 0;

    public Pagination(Integer page, Integer pageSize) {
        setPage(page);
        setPageSize(pageSize);
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            return DEFAULT_PAGE_SIZE;
        }

        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_INITIAL_PAGE;
        }

        return page;
    }

    public void setPage(Integer page) {
        if (page == null) {
            this.page = DEFAULT_INITIAL_PAGE;
        } else {
            this.page = page;
        }
    }
}
