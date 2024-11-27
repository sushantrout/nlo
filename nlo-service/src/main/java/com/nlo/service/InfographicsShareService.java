package com.nlo.service;

import com.nlo.entity.Infographics;
import com.nlo.entity.InfographicsShare;
import com.nlo.entity.User;
import com.nlo.model.UserDto;
import com.nlo.repository.InfographicsShareRepository;
import com.nlo.repository.Infographicsrepository;
import com.nlo.util.ShareCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InfographicsShareService {
    private final UserService userService;
    private final InfographicsShareRepository infographicsShareRepository;
    private final Infographicsrepository infographicsrepository;

    public String generateShareCode(String infographicsId) {
        Infographics infographics = infographicsrepository.findById(infographicsId).orElseThrow(() -> new ServiceException(""));

        try {
            UserDto currentUser = userService.getCurrentUser();
            User user = new User();
            user.setId(currentUser.getId());
            InfographicsShare is = new InfographicsShare();
            is.setInfographics(infographics);
            is.setUser(user);
            String shareCode = ShareCodeGenerator.generateShareCode();
            is.setShareCode(shareCode);
            infographicsShareRepository.save(is);
            return shareCode;
        } catch (Exception e) {
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    public void updateTheStatus(String shareId) {
        Optional<InfographicsShare> byShareCodeAndUsedIsNotTrue =
                infographicsShareRepository.findByShareCodeAndUsedFalseOrNull(shareId);

        byShareCodeAndUsedIsNotTrue.ifPresent(e -> {
            e.setUsed(true);
            infographicsShareRepository.save(e);
        });
    }

    public Number getShareCountByInfographics(String infographicsId) {
        return infographicsShareRepository.getShareCountByInfographics(infographicsId);
    }
}
