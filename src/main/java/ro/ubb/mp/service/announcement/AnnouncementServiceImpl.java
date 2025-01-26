package ro.ubb.mp.service.announcement;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ro.ubb.mp.controller.dto.mapper.InterestAreaMapper;
import ro.ubb.mp.controller.dto.request.AnnouncementRequestDTO;
import ro.ubb.mp.controller.dto.response.InterestAreaResponseDTO;
import ro.ubb.mp.controller.dto.response.announcement.AnnouncementResponseDTO;
import ro.ubb.mp.controller.dto.response.announcement.AnnouncementUserResponseDTO;
import ro.ubb.mp.dao.model.postgres.Announcement;
import ro.ubb.mp.dao.model.postgres.InterestArea;
import ro.ubb.mp.dao.model.postgres.User;
import ro.ubb.mp.dao.model.postgres.UserProfilePicture;
import ro.ubb.mp.dao.repository.postgres.AnnouncementRepository;
import ro.ubb.mp.dao.repository.postgres.UserProfilePictureRepository;
import ro.ubb.mp.service.interestArea.InterestAreaService;
import ro.ubb.mp.service.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Getter
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserService userService;
    private final InterestAreaService interestAreaService;
    private final UserProfilePictureRepository userProfilePictureRepository;
    private final InterestAreaMapper interestAreaMapper;
    @Override
    public Optional<Announcement> findById(Long id) {
        return announcementRepository.findById(id);
    }

    @Override
    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }

    @Override
    public List<Announcement> getAnnouncementsOrderedByDate() {
        return announcementRepository.findAllByOrderByPostingDateDesc();
    }

    @Override
    public Announcement saveAnnouncement(AnnouncementRequestDTO announcementDTO) {
        final User user = getUserService().getUserById(announcementDTO.getUserId()).
                orElseThrow(EntityNotFoundException::new);
        final InterestArea interestArea = getInterestAreaService().findById(announcementDTO.getInterestAreasId()).
                orElseThrow(EntityNotFoundException::new);

        final Announcement announcementToBeSaved = Announcement.builder()
                .description(announcementDTO.getDescription())
                .price(announcementDTO.getPrice())
                .title(announcementDTO.getTitle())
                .postingDate(Timestamp.from(Instant.now()))
                .user(user)
                .interestAreas(interestArea)
                .build();
        return announcementRepository.save(announcementToBeSaved);

    }

    @Override
    public Announcement updateAnnouncement(AnnouncementRequestDTO announcementRequestDTO, Long id) {
        Announcement announcement = findById(id).orElseThrow(EntityNotFoundException::new);

        final InterestArea interestArea = getInterestAreaService().findById(announcementRequestDTO.getInterestAreasId()).
                orElseThrow(EntityNotFoundException::new);

        announcement.setDescription(announcementRequestDTO.getDescription());
        announcement.setTitle(announcementRequestDTO.getTitle());
        announcement.setPrice(announcementRequestDTO.getPrice());
        announcement.setInterestAreas(interestArea);

        return announcementRepository.save(announcement);

    }

    @Override
    public void deleteAnnouncementById(Long id) {
        announcementRepository.deleteById(id);

    }

    @Override
    public List<Announcement> getAnnouncementFilteredByTitleOrDescription(String queryString) {
        if (!StringUtils.hasText(queryString)) {
            return announcementRepository.findAll();
        }
        return announcementRepository.findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(queryString, queryString);
    }

    @Override
    public List<AnnouncementResponseDTO> getAnnouncementsWithAvatars() {
        List<Announcement> announcements = getAnnouncementsOrderedByDate();
        return announcements.stream()
                .map(this::mapToDTOWithAvatar)
                .collect(Collectors.toList());
    }

    private AnnouncementResponseDTO mapToDTOWithAvatar(Announcement announcement) {
        InterestAreaResponseDTO interestAreaDTO  = getInterestAreaMapper().toDto(announcement.getInterestAreas());
        UserProfilePicture profilePicture = userProfilePictureRepository.findProfilePictureByUserId(announcement.getUser().getId())
                .orElse(null);

        String avatar = profilePicture != null ? Base64.getEncoder().encodeToString(profilePicture.getImageData()) : null;

        return AnnouncementResponseDTO.builder()
                .id(announcement.getId())
                .interestAreas(interestAreaDTO)
                .user(AnnouncementUserResponseDTO.builder()
                        .id(announcement.getUser().getId())
                        .fullName(announcement.getUser().getFullName())
//                        .profilePicture(avatar)
                        .build())
                .title(announcement.getTitle())
                .price(announcement.getPrice())
                .postingDate(announcement.getPostingDate())
                .description(announcement.getDescription())
                .build();
    }

}
