package de.nukulartechniker.badger.db.entity;

import de.nukulartechniker.badger.db.ReadOnlyRepository;

import java.util.List;

public interface UserRepository extends ReadOnlyRepository<User, Long> {
    List<User> findByName(String name);
}
