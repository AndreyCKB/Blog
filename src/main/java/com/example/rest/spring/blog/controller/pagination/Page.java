package com.example.rest.spring.blog.controller.pagination;

import java.util.HashMap;
import java.util.Map;

public class Page {
    private static final Map<String, Page> CACHE_PAGES= initCashe();

    private static Map<String, Page> initCashe() {
        HashMap<String, Page> cache= new HashMap<>();
        for (int i = 1; i < 11; i++) {
            cache.put(String.valueOf(i), new Page(i +" ", i));
        }
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
            CACHE_PAGES.put(String.valueOf(page.getName()+numberPage), page);
        }
        return  page;
    }

    public static Page valueOf(int numberPage){
        Page page = CACHE_PAGES.get(String.valueOf(numberPage));
        if (page == null) {
            page = new Page(String.valueOf(numberPage), numberPage);
            CACHE_PAGES.put(page.getName(), page);
        }
        return page;
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

    public static void main(String[] args) {
        System.out.println(CACHE_PAGES);
    }
}
