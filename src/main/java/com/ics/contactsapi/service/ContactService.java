package com.ics.contactsapi.service;

import com.ics.contactsapi.model.Contact;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContactService {
    private final Map<Long, Contact> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    public ContactService() {
        // seed
        create(new Contact(null, "Ada Lovelace", "+44 1234", "ada@example.com"));
        create(new Contact(null, "Alan Turing",  "+44 5678", "alan@example.com"));
    }

    public List<Contact> list(String q) {
        ArrayList<Contact> all = new ArrayList<>(store.values());
        all.sort(Comparator.comparing(Contact::getId));
        if (q == null || q.isBlank()) return all;
        String qq = q.toLowerCase();
        all.removeIf(c ->
            (c.getName()  == null || !c.getName().toLowerCase().contains(qq)) &&
            (c.getPhone() == null || !c.getPhone().toLowerCase().contains(qq)) &&
            (c.getEmail() == null || !c.getEmail().toLowerCase().contains(qq))
        );
        return all;
    }

    public Contact create(Contact c) {
        long id = seq.incrementAndGet();
        c.setId(id);
        store.put(id, c);
        return c;
    }

    public Optional<Contact> get(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Contact> update(Long id, Contact patch) {
        Contact existing = store.get(id);
        if (existing == null) return Optional.empty();
        if (patch.getName()  != null) existing.setName(patch.getName());
        if (patch.getPhone() != null) existing.setPhone(patch.getPhone());
        if (patch.getEmail() != null) existing.setEmail(patch.getEmail());
        return Optional.of(existing);
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}
