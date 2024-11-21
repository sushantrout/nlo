package com.nlo.repository;

import com.nlo.entity.Reaction;
import com.nlo.repository.dbdto.ReactionDBDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReactionRepository extends BaseRepository<Reaction> {
    @Query("SELECT new com.nlo.repository.dbdto.ReactionDBDTO(r.id, r.userId, n.id, r.reactionType) " +
            "FROM News n JOIN n.reactions r " +
            "WHERE n.id IN :newsIds AND r.userId = :userId")
    List<ReactionDBDTO> findByUserIdAndNewsIds(@Param("userId") String userId, @Param("newsIds") List<String> newsIds);

    @Query("SELECT new com.nlo.repository.dbdto.ReactionDBDTO(r.id, r.userId, inf.id, r.reactionType) " +
            "FROM Infographics inf JOIN inf.reactions r " +
            "WHERE inf.id IN :infographicsIds AND r.userId = :userId")
    List<ReactionDBDTO> findByUserIdAndInfographicsIds(@Param("userId") String userId, @Param("infographicsIds") List<String> infographicsIds);


}

