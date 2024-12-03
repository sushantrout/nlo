package com.nlo.repository;

import com.nlo.entity.ViewDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewDetailRepository extends BaseRepository<ViewDetail> {
    List<ViewDetail> findByNewsIdAndUserId(String newsId, String userId);
}
