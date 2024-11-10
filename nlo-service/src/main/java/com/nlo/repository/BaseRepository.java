package com.nlo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository <E> extends JpaRepository<E, String> {
    Page<E> findByDeletedFalseOrDeletedIsNull(Pageable pageable);
}