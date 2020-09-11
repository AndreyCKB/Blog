package com.example.rest.spring.blog.controller.pagination;

public interface Pagination {
    Page[] getPages(int currentPage, long quantityUnit, int quantityUnitOnPage);

    Page[] getPages(int currentPage, long quantityUnit);
}
