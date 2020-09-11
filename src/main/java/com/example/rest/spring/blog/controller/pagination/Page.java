package com.example.rest.spring.blog.controller.pagination;

import com.example.rest.spring.blog.controller.PostController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Page {

    public static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private static final Map<String, Page> CACHE_PAGES= initCashe();

    private static Map<String, Page> initCashe() {
        Map<String, Page> cache= new ConcurrentHashMap<>();
        for (int i = 1; i < 11; i++) {
            cache.put(String.valueOf(i), new Page(i + " ", i));
        }
        logger.debug("Cash for Pages initialized. CACHE_PAGES = \"{}\"", cache);
        return cache;
    }

    private final String name;
    private final int numberPage;

    private Page(String name, int numberPage) {
        this.name = name;
        this.numberPage = numberPage;
    }

    public static Page valueOf(String name, int numberPage){
        Page page = CACHE_PAGES.get(name + numberPage);
        if (page == null) {
            page = new Page(name, numberPage);
            CACHE_PAGES.put(page.getName() + numberPage, page);
            logger.debug("Page = \"{}\" added to cash", page);
        }
        logger.debug("Page = \"{}\" return to client", page);
        return  page;
    }

    public static Page valueOf(int numberPage){
        return valueOf("", numberPage);
    }


    public String getName() {
        return name;
    }

    public int getNumberPage() {
        return numberPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "name='" + name + '\'' +
                ", numberPage=" + numberPage +
                '}';
    }

}
