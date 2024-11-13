package com.nlo.mapper;

import com.nlo.entity.Category;
import com.nlo.entity.News;
import com.nlo.model.CategoryDTO;
import com.nlo.model.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional
@RequiredArgsConstructor
public class NewsMapper implements BaseMapper<NewsDTO, News> {
    private final ReactionMapper reactionMapper;
    private final AttachmentMapper attachmentMapper;

    @Override
    public NewsDTO toDto(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setReactions(reactionMapper.toDtoList(news.getReactions()));
        newsDTO.setAttachments(attachmentMapper.toDtoList(news.getAttachments()));
        newsDTO.setContent(news.getContent());
        newsDTO.setPublishedDate(news.getPublishedDate());
        newsDTO.setStaticUrl(news.getStaticUrl());
        newsDTO.setHot(news.getHot());
        newsDTO.setCreatedOn(news.getCreatedOn());
        newsDTO.setUpdatedOn(news.getUpdatedOn());

        if(Objects.nonNull(news.getCategory())) {
            Category category = news.getCategory();
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setTitle(category.getTitle());
            newsDTO.setCategory(categoryDTO);
        }
        return newsDTO;
    }

    @Override
    public News toEntity(NewsDTO newsDTO) {
        News news = new News();
        news.setId(newsDTO.getId());
        news.setTitle(newsDTO.getTitle());
        news.setReactions(reactionMapper.toEntityList(newsDTO.getReactions()));
        news.setAttachments(attachmentMapper.toEntityList(newsDTO.getAttachments()));
        news.setContent(newsDTO.getContent());
        news.setPublishedDate(newsDTO.getPublishedDate());
        news.setStaticUrl(newsDTO.getStaticUrl());
        news.setHot(newsDTO.getHot());
        news.setCreatedOn(newsDTO.getCreatedOn());
        news.setUpdatedOn(newsDTO.getUpdatedOn());

        if(Objects.nonNull(newsDTO.getCategory())) {
            CategoryDTO categoryDTO = newsDTO.getCategory();
            Category category = new Category();
            category.setId(categoryDTO.getId());
            news.setCategory(category);
        }

        return news;
    }
}