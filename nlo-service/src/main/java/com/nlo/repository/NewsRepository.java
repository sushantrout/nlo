package com.nlo.repository;

import com.nlo.entity.Category;
import com.nlo.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsRepository extends BaseRepository<News> {
    List<News> findByHotTrue();
    Page<News> findByCategoryAndDeletedFalseOrDeletedIsNull(Category category, Pageable pageable);
}
