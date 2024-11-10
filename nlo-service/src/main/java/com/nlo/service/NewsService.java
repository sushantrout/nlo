package com.nlo.service;

import com.nlo.entity.Attachment;
import com.nlo.entity.Grievance;
import com.nlo.entity.News;
import com.nlo.mapper.NewsMapper;
import com.nlo.mapper.ReactionMapper;
import com.nlo.model.GrievanceDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    protected NewsService(NewsRepository repository, NewsMapper mapper, NewsValidation validation) {
        super(repository, mapper, validation);
    }

    public NewsDTO reaction(String newsId, ReactionDTO reactionDTO) {
        News news = repository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found"));
        news.getReactions().add(reactionMapper.toEntity(reactionDTO));
        NewsDTO newsDTO = mapper.toDto(repository.save(news));

        UserDto currentUser = userService.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            String currentUserId = currentUser.getId();
            reactionRepository.findByUserIdAndNewsIds(currentUserId, List.of(newsId)).stream().findFirst().ifPresent(reactionDBDTO -> {
                newsDTO.setCurrentUserReaction(reactionDBDTO.getReactionType());
            });
        }
        return newsDTO;
    }

    public List<NewsDTO> getAllWithReaction() {

        List<News> news = repository.findAll();
        List<String> newsIds = news.stream().map(News::getId).toList();
        List<NewsDTO> dtoList = mapper.toDtoList(news);

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
}