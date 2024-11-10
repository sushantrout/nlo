package com.nlo.repository;

import com.nlo.entity.News;

import java.util.List;

public interface NewsRepository extends BaseRepository<News> {
    List<News> findByHotTrue();
}
