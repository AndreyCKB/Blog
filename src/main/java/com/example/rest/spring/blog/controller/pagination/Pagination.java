package com.example.rest.spring.blog.controller.pagination;

import java.util.Arrays;

public class Pagination {
    private static Pagination INSTANCE = new Pagination();

    private Pagination() {
    }

    public static Page[] getPages(int currentPage, long quantityUnit, int quantityUnitOnPage){
        int endPage =(int) (quantityUnit % quantityUnitOnPage == 0 ? (quantityUnit / quantityUnitOnPage) : (quantityUnit / quantityUnitOnPage + 1));
        if (endPage == 1){
            return new Page[]{Page.valueOf("current", 1)};
        }else {
            return INSTANCE.main(currentPage, endPage);
        }
    }

    private Page[] main(int currentPage, int endPage) {
        Page[] result = null;
        if (currentPage == 1) {
            result = createPatternFirstPages(endPage);
            result[0] = Page.valueOf("current", currentPage);
        }else if (currentPage == endPage){
            result = createPatternLastPages(endPage);
            result[currentPage] = Page.valueOf("current", currentPage);
        }else{
            result =createPatternBasePages(currentPage, endPage);
            result[currentPage] = Page.valueOf("current", currentPage);
        }
        return result;
    }

    private Page[] createPatternBasePages(int currentPage, int endPage){
        Page[] result = new Page[endPage + 2];
        result[0] = Page.valueOf("Предыдущая " , currentPage - 1);
        result[endPage+1] = Page.valueOf(" Следующая" , currentPage + 1);
        addRangPages(result,1, endPage + 1, 1);
        return result;
    }

    private Page[] createPatternFirstPages(int endPage){
        Page[] result = new Page[endPage + 1];
        result[endPage] = Page.valueOf(" Следующая" , 2);
        addRangPages(result,0, endPage, 1);
        return result;
    }

    private Page[] createPatternLastPages(int endPage){
        Page[] result = new Page[endPage + 1];
        result[0] = Page.valueOf("Предыдущая " , endPage-1);
        addRangPages(result,1, endPage + 1, 1);
        return result;
    }

    private void addRangPages(Page[] pages,int startRang, int endRang, int index){
        for (int i = startRang; i < endRang; i++){
            pages[i] = Page.valueOf(index);
            index++;
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getPages(2,4,3)));
    }
}
