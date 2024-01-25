package de.nukulartechniker.badger;

import de.nukulartechniker.badger.db.entity.Badge;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class BadgeMerger {

    public byte[] mergeBadges(BadgeMergerInput bmi) {

        try {
            int width = bmi.getBadgesList().size() < bmi.getElementsPerRow() ? bmi.getBadgeDimensionsxy() * bmi.getBadgesList().size() : bmi.getBadgeDimensionsxy() * bmi.getElementsPerRow() ;
            int height = bmi.getBadgeDimensionsxy() * (1 + bmi.getBadgesList().size() / bmi.getElementsPerRow());
            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = result.getGraphics();

            for  (int i = 0; i < bmi.getBadgesList().size(); i++) {
                Badge badge = bmi.getBadgesList().get(i);

                int xoffset = bmi.getBadgeDimensionsxy() * (i % bmi.getElementsPerRow());
                int yoffset = bmi.getBadgeDimensionsxy() * (i / bmi.getElementsPerRow());

                ByteArrayInputStream bais = new ByteArrayInputStream(badge.getImage());
                BufferedImage bi = ImageIO.read(bais);

                Image resizedImage =  bi.getScaledInstance(bmi.getBadgeDimensionsxy(), bmi.getBadgeDimensionsxy(), Image.SCALE_SMOOTH);

                g.drawImage(resizedImage, xoffset,yoffset,null );
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(result,"png",baos);

            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
