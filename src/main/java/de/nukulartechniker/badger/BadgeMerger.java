package de.nukulartechniker.badger;

import de.nukulartechniker.badger.db.entity.Badge;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * merges a list of badges into one big png file which is returned as byte[]
 */
@Component
public class BadgeMerger {
    public byte[] mergeBadges(BadgeMergerInput bmi) {

        try {
            // determin size of the resulting image
            int width = bmi.getBadgesList().size() < bmi.getElementsPerRow() ? bmi.getBadgeDimensionsxy() * bmi.getBadgesList().size() : bmi.getBadgeDimensionsxy() * bmi.getElementsPerRow();
            int height = bmi.getBadgeDimensionsxy() * (1 + bmi.getBadgesList().size() / bmi.getElementsPerRow());

            // create image with final dimensions
            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = result.getGraphics();

            // add every single image to to final image at the right spot
            for (int i = 0; i < bmi.getBadgesList().size(); i++) {
                Badge badge = bmi.getBadgesList().get(i);

                // position of next image
                int xOffset = bmi.getBadgeDimensionsxy() * (i % bmi.getElementsPerRow());
                int yOffset = bmi.getBadgeDimensionsxy() * (i / bmi.getElementsPerRow());

                // write image
                ByteArrayInputStream bais = new ByteArrayInputStream(badge.getImage());
                BufferedImage bi = ImageIO.read(bais);
                Image resizedImage = bi.getScaledInstance(bmi.getBadgeDimensionsxy(), bmi.getBadgeDimensionsxy(), Image.SCALE_SMOOTH);
                g.drawImage(resizedImage, xOffset, yOffset, null);
            }

            g.dispose();

            // prepare bytearray of created image
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(result, "png", baos);

            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
