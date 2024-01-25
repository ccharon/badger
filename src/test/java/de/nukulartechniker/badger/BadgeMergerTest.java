package de.nukulartechniker.badger;

import de.nukulartechniker.badger.db.entity.Badge;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BadgeMergerTest {

    @Test
    public void testMerge() throws IOException {

        //Test Badge als Bytearray laden
        InputStream in = getClass().getResourceAsStream("/static/test512.png");
        byte[] bytes = IOUtils.toByteArray(in);

        List<Badge> badges = new ArrayList<>();
        for (int i=0; i<25 ; i++) {
            badges.add(new Badge(i, null, "", bytes));
        }

        BadgeMerger merger = new BadgeMerger();
        BadgeMergerInput input = new BadgeMergerInput(64, 9, badges);
        byte[] merged = merger.mergeBadges(input);

        File file = new File("output.png");

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(merged);
        }
    }
}
