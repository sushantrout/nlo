package com.nlo.service;

import com.nlo.constant.ShareType;
import com.nlo.entity.*;
import com.nlo.model.UserDto;
import com.nlo.repository.InfographicsShareRepository;
import com.nlo.repository.Infographicsrepository;
import com.nlo.repository.NewsRepository;
import com.nlo.repository.NewsShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShareService {
    private final NewsRepository newsRepository;
    private final Infographicsrepository infographicsrepository;
    private final UserService userService;
    private final InfographicsShareRepository infographicsShareRepository;
    private final NewsShareRepository newsShareRepository;

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
        InfographicsShare infographicsShare = new InfographicsShare();
        User curentuser = getCurentuser();
        if(Objects.nonNull(curentuser)) {
            infographicsShare.setInfographics(infographics);
            infographicsShare.setUser(curentuser);
            infographicsShareRepository.save(infographicsShare);
        }

    }

    private void increseNewsShare(String objectId) {
        News news = newsRepository.findById(objectId).orElseThrow();
        NewsShare newsShare = new NewsShare();
        User curentuser = getCurentuser();
        if(Objects.nonNull(curentuser)) {
            newsShare.setNews(news);
            newsShare.setUser(curentuser);
            newsShareRepository.save(newsShare);
        }
    }


    public User getCurentuser() {
        try {
            UserDto currentUser = userService.getCurrentUser();
            String currentUserId = currentUser.getId();
            return userService.findByUserId(currentUserId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        return null;
    }
}
