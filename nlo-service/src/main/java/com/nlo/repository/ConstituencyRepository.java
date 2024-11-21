package com.nlo.repository;

import com.nlo.entity.Constituency;

import java.util.List;

public interface ConstituencyRepository extends BaseRepository<Constituency> {
    List<Constituency> findByDeletedFalseOrDeletedIsNull();
}
