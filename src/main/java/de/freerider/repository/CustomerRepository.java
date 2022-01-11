package de.freerider.repository;

import de.freerider.datamodel.Customer;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomerRepository implements CrudRepository<Customer,Long> {

    private HashMap<Long, Customer> data = new HashMap<Long, Customer>();
    Long count = 0L;

    public Long generateID() {
        long generatedLong = new Random().nextLong();
        if(data.get(generatedLong) != null) {
            generatedLong = generateID();
        }
        return generatedLong;
    }

    @Override
    public <S extends Customer> S save(S entity) {
        data.put(entity.getId(), entity);
        count++;
        return entity;
    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
        for (S entity : entities) {
            data.put(entity.getId(), entity);
            count++;
        }

        return entities;
    }

    @Override
    public boolean existsById(Long aLong) {
        Customer c = data.get(aLong);
        return c != null;
    }

    @Override
    public Optional<Customer> findById(Long aLong) {
        Customer c = data.get(aLong);
        if(c != null) {
            return Optional.of(c);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Customer> findAll() {
        return data.values();
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<Long> longs) {
        ArrayList<Customer> c_found = new ArrayList<Customer>();
        longs.forEach(key -> {
            Customer c = data.get(key);
            if(c != null) {
                c_found.add(c);
            }
        });
        return c_found;
    }

    @Override
    public long count() {
        return this.count;
    }

    @Override
    public void deleteById(Long aLong) {
        data.remove(aLong);
        count--;
    }

    @Override
    public void delete(Customer entity) {
        for (Map.Entry<Long, Customer> entry : data.entrySet()) {
            if (entry.getValue().equals(entity)) {
                data.remove(entry.getKey());
                break;
            }
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        longs.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        data.clear();
    }
}
