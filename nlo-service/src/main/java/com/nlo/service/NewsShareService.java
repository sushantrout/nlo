package com.nlo.service;

import com.nlo.entity.News;
import com.nlo.entity.NewsShare;
import com.nlo.entity.User;
import com.nlo.model.UserDto;
import com.nlo.repository.NewsRepository;
import com.nlo.repository.NewsShareRepository;
import com.nlo.util.ShareCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsShareService {
    private final NewsShareRepository newsShareRepository;
    private final NewsRepository newsRepository;
    private final UserService userService;

    public String generateShareCode(String newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found"));
        try {
            UserDto currentUser = userService.getCurrentUser();
            User user = new User();
            user.setId(currentUser.getId());
            NewsShare newsShare = new NewsShare();
            newsShare.setNews(news);
            newsShare.setUser(user);
            String shareCode = ShareCodeGenerator.generateShareCode();
            newsShare.setShareCode(shareCode);
            newsShareRepository.save(newsShare);
            return shareCode;
        } catch (Exception e) {
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    public void updateTheStatus(String shareId) {
        Optional<NewsShare> byShareCodeAndUsedIsNotTrue =
                newsShareRepository.findByShareCodeAndUsedFalseOrNull(shareId);

        byShareCodeAndUsedIsNotTrue.ifPresent(e -> {
            e.setUsed(true);
            newsShareRepository.save(e);
        });
    }

    public Number getShareCountByNewsId(News news) {
        return newsShareRepository.countUsedNewsShares(news);
    }
}
