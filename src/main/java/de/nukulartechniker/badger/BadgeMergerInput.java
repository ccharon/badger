package de.nukulartechniker.badger;

import de.nukulartechniker.badger.db.entity.Badge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BadgeMergerInput {
    private int badgeDimensionsxy = 64;
    private int elementsPerRow = 10;
    private List<Badge> badgesList = new ArrayList<>();
}
