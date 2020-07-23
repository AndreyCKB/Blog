package com.example.rest.spring.blog.service.post;

import org.springframework.data.domain.Sort;

public enum ParameterSort {
    TITLE("По названию"){
        @Override
        public Sort getSort(){
            return  Sort.by(Sort.Order.asc("title"));
        }
    },
    ANONS("По анонсу"){
        @Override
        public Sort getSort(){
            return Sort.by(Sort.Order.asc("anons"));
        }
    },
    VIEWS("По количиству просмотров"){
        @Override
        public Sort getSort(){
            return Sort.by(Sort.Order.desc("views"));
        }
    },
    CREATED_DATE("По дате создания"){
        @Override
        public Sort getSort(){
            return Sort.by(Sort.Order.desc("createdPostDate"));
        }
    },
    CHANGED_DATE("По дате изменения"){
        @Override
        public Sort getSort(){
            return Sort.by(Sort.Order.desc("changedPostDate"));
        }
    };



    private String parameterSort;
    ParameterSort(String st) {
        this.parameterSort = st;
    }

    public String getParameterSort() {
        return parameterSort;
    }

    public Sort getSort(){
        return this.getSort();
    }

}
