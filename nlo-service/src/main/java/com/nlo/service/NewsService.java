package com.nlo.service;

import com.nlo.constant.ReactionType;
import com.nlo.entity.Attachment;
import com.nlo.entity.Category;
import com.nlo.entity.News;
import com.nlo.mapper.NewsMapper;
import com.nlo.mapper.ReactionMapper;
import com.nlo.model.NewsDTO;
import com.nlo.model.ReactionDTO;
import com.nlo.model.UserDto;
import com.nlo.repository.NewsRepository;
import com.nlo.repository.ReactionRepository;
import com.nlo.repository.dbdto.ReactionDBDTO;
import com.nlo.security.JwtService;
import com.nlo.validation.NewsValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsService extends BaseServiceImpl<News, NewsDTO, NewsMapper, NewsValidation, NewsRepository> {

    @Autowired
    @Lazy
    private JwtService jwtService;

    @Autowired
    private ReactionMapper reactionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Lazy
    @Autowired
    private NewsShareService newsShareService;

    protected NewsService(NewsRepository repository, NewsMapper mapper, NewsValidation validation) {
        super(repository, mapper, validation);
    }

    public Optional<NewsDTO> getById(String id, String shareId) {
        Optional<News> dataOpt = repository.findById(id);
        Optional<NewsDTO> newsDTO = dataOpt.map(mapper::toDto);
        if(newsDTO.isPresent()) {
            ArrayList<NewsDTO> dtoList = new ArrayList<>();
            dtoList.add(newsDTO.get());
            getAllWithReaction(dtoList);
        }
        activeShareCount(shareId);
        if(newsDTO.isPresent()) {
            newsDTO.get().setTotalShare(newsShareService.getShareCountByNewsId(dataOpt.get()));
        }
        return newsDTO;
    }

    private void activeShareCount(String shareId) {
        newsShareService.updateTheStatus(shareId);
    }

    public NewsDTO reaction(String newsId, ReactionDTO reactionDTO) {
        News news = repository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found"));

        UserDto currentUser = userService.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            String currentUserId = currentUser.getId();
            Optional<ReactionDBDTO> first = reactionRepository.findByUserIdAndNewsIds(currentUserId, List.of(newsId)).stream().findFirst();
            if(Objects.nonNull(reactionDTO) && reactionDTO.getReactionType().equals(ReactionType.NONE) && first.isPresent()) {
                reactionRepository.deleteById(first.get().getReactionId());
            } else {
                if(first.isPresent()) {
                    String reactionId = first.get().getReactionId();
                    reactionRepository.findById(reactionId).ifPresent(re -> {
                        re.setReactionType(reactionDTO.getReactionType());
                        reactionRepository.save(re);
                    });
                } else {
                    news.getReactions().add(reactionMapper.toEntity(reactionDTO));
                    repository.save(news);
                }
            }

        }
        return mapper.toDto(repository.findById(newsId).get());
    }

    @Override
    public Page<NewsDTO> getAll(Pageable pageable) {
        Page<News> dataPage = repository.findByDeletedFalseOrDeletedIsNull(pageable);
        Page<NewsDTO> newsDTOS = dataPage.map(mapper::toDto);
        getAllWithReaction(newsDTOS.getContent());
        return newsDTOS;
    }

    public List<NewsDTO> getAllWithReaction(List<NewsDTO> dtoList) {
        List<String> newsIds = dtoList.stream().map(NewsDTO::getId).toList();

        UserDto currentUser = userService.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            String currentUserId = currentUser.getId();
            List<ReactionDBDTO> currentUserReactions = reactionRepository.findByUserIdAndNewsIds(currentUserId, newsIds);

            Map<String, ReactionDBDTO> reactionMap = currentUserReactions.stream()
                    .collect(Collectors.toMap(ReactionDBDTO::getNewsId, reaction -> reaction, (a, b) -> a));

            // Update the news DTOs with the current user's reaction if present
            dtoList.forEach(newsDTO -> {
                ReactionDBDTO reaction = reactionMap.get(newsDTO.getId());
                if (reaction != null && reaction.getCurrentUser().equals(currentUserId)) {
                    newsDTO.setCurrentUserReaction(reaction.getReactionType());
                }
            });
        }
        return dtoList;
    }
    public List<NewsDTO> getAllWithReaction() {
        List<News> news = repository.findAll();
        List<NewsDTO> dtoList = mapper.toDtoList(news);
        return getAllWithReaction(dtoList);
    }

    public List<NewsDTO> getAllHotNews() {
        return mapper.toDtoList(repository.findByHotTrue());
    }

    public NewsDTO saveWithAttachment(NewsDTO newsDTO, List<MultipartFile> files) {
        News entity = mapper.toEntity(newsDTO);
        entity = repository.save(entity);
        List<Attachment> attachments = attachmentService.saveAll(files, entity.getId());
        entity = repository.findById(entity.getId()).orElseThrow();
        entity.getAttachments().addAll(attachments);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public NewsDTO makeHot(String newsId, Boolean value) {
        News news = repository.findById(newsId).orElseThrow();
        news.setHot(value);
        repository.save(news);
        return mapper.toDto(news);
    }

    public Page<NewsDTO> getAllNewsByType(String categoryId, Pageable pageable) {
        Category category = new Category();
        category.setId(categoryId);
        Page<NewsDTO> newsDTOS = repository.findByCategoryAndDeletedFalseOrDeletedIsNull(category, pageable).map(mapper::toDto);
        getAllWithReaction(newsDTOS.getContent());
        return newsDTOS;
    }
}