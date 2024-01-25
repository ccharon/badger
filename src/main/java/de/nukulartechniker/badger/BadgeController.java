package de.nukulartechniker.badger;

import de.nukulartechniker.badger.db.BadgeRepository;
import de.nukulartechniker.badger.db.entity.Badge;
import de.nukulartechniker.badger.db.entity.User;
import de.nukulartechniker.badger.db.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class BadgeController {
    private final BadgeRepository badgesRepository;
    private final UserRepository userRepository;
    private final BadgeMerger badgeMerger;

    @GetMapping(value = "/badges/{id}/badges.png", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable("id") Long id) throws IOException {
        log.info("Loading badges for {}", id);

        Optional<Badge> optionalBadge = badgesRepository.findById(id);

        if (optionalBadge.isPresent()) {
            Badge badge = optionalBadge.get();
            log.info("Badge {}", badge);
        }

        InputStream in = getClass().getResourceAsStream("/static/test512.png");
        return IOUtils.toByteArray(in);
    }

    @Cacheable("badges")
    @GetMapping(value = "/users/{name}/{xyres}/{numrow}/badges.png", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getUserInfo(@PathVariable("name") String name, @PathVariable("xyres") int xyres, @PathVariable("numrow") int numrow) throws IOException {
        log.info("Loading badges for {}", name);

        List<User> users = userRepository.findByName(name);

        List<Badge> badges = new ArrayList<>();

        if (users.size() == 1) {
            User user = users.get(0);
            log.info("User {}", user);

            //Test Badge als Bytearray laden
            InputStream in = getClass().getResourceAsStream("/static/test512.png");
            byte[] bytes = IOUtils.toByteArray(in);

            for (int i=0; i<27 ; i++) {
                badges.add(new Badge(i, null, "", bytes));
            }
        } else {
            //Test Badge als Bytearray laden
            InputStream in = getClass().getResourceAsStream("/static/test512.png");
            byte[] bytes = IOUtils.toByteArray(in);

            badges.add(new Badge(0, null, "", bytes));
        }

        BadgeMergerInput input = new BadgeMergerInput(xyres, numrow, badges);
        return badgeMerger.mergeBadges(input);
    }
}
