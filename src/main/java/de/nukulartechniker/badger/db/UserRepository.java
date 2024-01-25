package de.nukulartechniker.badger.db;

import de.nukulartechniker.badger.db.ReadOnlyRepository;
import de.nukulartechniker.badger.db.entity.User;

import java.util.List;

public interface UserRepository extends ReadOnlyRepository<User, Long> {
    List<User> findByName(String name);
}
