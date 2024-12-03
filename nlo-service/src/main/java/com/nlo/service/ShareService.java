package com.nlo.service;

import com.nlo.constant.ShareType;
import com.nlo.entity.Infographics;
import com.nlo.entity.News;
import com.nlo.entity.User;
import com.nlo.model.UserDto;
import com.nlo.repository.Infographicsrepository;
import com.nlo.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShareService {
    private final NewsRepository newsRepository;
    private final Infographicsrepository infographicsrepository;
    private final UserService userService;

    public void increseShare(ShareType type, String objectId) {
        switch (type) {
            case ShareType.NEWS:
                increseNewsShare(objectId);
                break;
            case ShareType.INFOGRAPHICS:
                increseInfographicsShare(objectId);
                break;
        }
    }

    private void increseInfographicsShare(String objectId) {
        Infographics infographics = infographicsrepository.findById(objectId).orElseThrow();
        infographics.setTotalShare(Optional.ofNullable(infographics.getTotalShare()).orElse(0L) + 1);
        infographicsrepository.save(infographics);
        increseUserShareCount();
    }

    private void increseNewsShare(String objectId) {
        News news = newsRepository.findById(objectId).orElseThrow();
        news.setTotalShare(Optional.ofNullable(news.getTotalShare()).orElse(0L) + 1);
        newsRepository.save(news);
        increseUserShareCount();
    }


    public void increseUserShareCount() {
        try {
            UserDto currentUser = userService.getCurrentUser();
            String currentUserId = currentUser.getId();
            User user = userService.findByUserId(currentUserId);
            user.setTotalShare(Optional.ofNullable(user.getTotalShare()).orElse(0L) + 1);
            userService.saveEntity(user);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
