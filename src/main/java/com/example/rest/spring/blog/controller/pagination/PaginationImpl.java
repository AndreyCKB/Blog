package com.example.rest.spring.blog.controller.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PaginationImpl implements Pagination{

    public static final Logger logger = LoggerFactory.getLogger(PaginationImpl.class);

    @Value("${com.example.rest.spring.blog.controller.pagination.quantityUnitOnPage}")
    private int quantityUnitOnPage = 5;
    private static final Page[] SINGLE_PAGE = new Page[]{Page.valueOf("current", 1)};

    public PaginationImpl() {
    }

    public Page[] getPages(int currentPage, long quantityUnit, int quantityUnitOnPage){
        logger.trace("getPages( int currentPage = \"{}\", long quantityUnit = \"{}\", quantityUnitOnPage = \"{}\" )", currentPage, quantityUnit, quantityUnitOnPage);
        int endPage =(int) (quantityUnit % quantityUnitOnPage == 0 ? quantityUnit / quantityUnitOnPage : quantityUnit / quantityUnitOnPage + 1);
        if (endPage == 1){
            logger.debug("SINGLE_PAGE = \"{}\" return client", Arrays.toString(SINGLE_PAGE));
            return SINGLE_PAGE;
        }else {
            Page[] pages = this.generationPages(currentPage, endPage);
            logger.debug("Pages[] = \"{}\" return client", pages);
            return pages;
        }
    }

    public Page[] getPages(int currentPage, long quantityUnit){
        return getPages(currentPage, quantityUnit, this.quantityUnitOnPage);
    }

    private Page[] generationPages(int currentPage, int endPage){
        Page[] result;
        if (currentPage == 1) {
            result = createPatternFirstPages(endPage);
            currentPage = 0;
        }else if (currentPage == endPage){
            result = createPatternLastPages(endPage);            
        }else {
            result = createPatternBasePages(currentPage, endPage);            
        }
        result[currentPage] = Page.valueOf("current", currentPage);
        return result;
    }  

    private Page[] createPatternBasePages(int currentPage, int endPage){
        Page[] result = new Page[endPage + 2];
        result[0] = Page.valueOf("Предыдущая " , currentPage - 1);
        result[endPage+1] = Page.valueOf(" Следующая" , currentPage + 1);
        this.addRangPages(result,1, endPage + 1, 1);
        return result;
    }

    private Page[] createPatternFirstPages(int endPage){
        Page[] result = new Page[endPage + 1];
        result[endPage] = Page.valueOf(" Следующая" , 2);
        this.addRangPages(result,0, endPage, 1);
        return result;
    }

    private Page[] createPatternLastPages(int endPage){
        Page[] result = new Page[endPage + 1];
        result[0] = Page.valueOf("Предыдущая " , endPage-1);
        this.addRangPages(result,1, endPage + 1, 1);
        return result;
    }

    private void addRangPages(Page[] pages,int startRang, int endRang, int index){
        for (int i = startRang; i < endRang; i++){
            pages[i] = Page.valueOf(index);
            index++;
        }
    }

}
