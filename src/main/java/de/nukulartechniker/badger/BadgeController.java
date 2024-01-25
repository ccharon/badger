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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping(value = "/badges/{name}/{xyres}/{numrow}/badges.png", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getUserInfo(
            @PathVariable("name") String name,
            @PathVariable("xyres") int xyres,
            @PathVariable("numrow") int numrow) throws IOException {

        List<User> users = userRepository.findByName(name);
        List<Badge> badges = new ArrayList<>();

        if (users.size() == 1) {
            User user = users.get(0);
            log.info("User {}", user);
            badges = user.getBadges();

        } else {
            @Cleanup
            InputStream in = getClass().getResourceAsStream("/static/01.png");
            byte[] bytes = IOUtils.toByteArray(in);

            badges.add(new Badge(0, null, "", bytes));
        }

        BadgeMergerInput bmi = new BadgeMergerInput(xyres, numrow, badges);
        return badgeMerger.mergeBadges(bmi);
    }
}
