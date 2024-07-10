package de.nukulartechniker.badger;

import de.nukulartechniker.badger.db.entity.Badge;
import de.nukulartechniker.badger.db.entity.User;
import de.nukulartechniker.badger.db.UserRepository;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class BadgeController {
    private final UserRepository userRepository;
    private final BadgeMerger badgeMerger;

    @Cacheable("badges")
    @GetMapping(value = "/badges/{name}/badges.png", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getUserInfo(
            @PathVariable("name") String name,
            @RequestParam(name = "xyres", defaultValue = "64") int xyres,
            @RequestParam(name = "numrow", defaultValue = "10") int numrow
    ) throws IOException {

        if (xyres < 16) xyres = 16;
        if (xyres > 512) xyres = 512;

        if (numrow < 1) numrow = 1;
        if (numrow > 100) numrow = 100;

        List<User> users = userRepository.findByName(name);
        List<Badge> badges = new ArrayList<>();


        if (users.size() == 1) {
            User user = users.get(0);
            log.info("User {}", user);
            badges = user.getBadges();

        } else {
            @Cleanup
            InputStream in = getClass().getResourceAsStream("/static/blank.png");
            byte[] bytes = IOUtils.toByteArray(in);

            badges.add(new Badge(0, null, "", bytes));
        }

        BadgeMergerInput bmi = new BadgeMergerInput(xyres, numrow, badges);
        return badgeMerger.mergeBadges(bmi);
    }
}
