package br.com.dducl.collective_coffee_marketplace.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Pagination {

    private Integer page;

    private Integer pageSize;

    private Integer totalPages;

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private static final Integer DEFAULT_INITIAL_PAGE = 0;

    private static final Integer MAX_PAGE_SIZE = 1000;

    public Pagination(Integer page, Integer pageSize) {
        setPage(page);
        setPageSize(pageSize);
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else if (pageSize > MAX_PAGE_SIZE) {
            this.pageSize = MAX_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public void setPage(Integer page) {
        this.page = Objects.requireNonNullElse(page, DEFAULT_INITIAL_PAGE);
    }
}
