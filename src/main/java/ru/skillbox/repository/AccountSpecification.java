package ru.skillbox.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.dto.AccountByFilterDto;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.entity.Account;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface AccountSpecification {

    /*
    {
      "accountSearchDto": {
        "ids": [
          "33b7b733-be17-4fd4-9d16-f650135f3821",
          "2adc4c6c-5a91-46a3-8262-9974242615b7",
          "3bf82c19-a821-4003-b8f6-2e67e331a915"
        ],
        "firstName": "name",
        "lastName": "name",
        "birthDateFrom": "1980-08-26T00:41:16.510Z",
        "birthDateTo": "1984-08-26T00:41:16.510Z",
        "city": "Moscow",
        "country": 'Russia',
        "blocked": false,
        "deleted": true,
        "ageFrom": "20",
        "ageTo": 40
      },
      "pageSize": 10,
      "pageNumber": 0
    }
    */

    static Specification<Account> withFilter(AccountSearchDto asd) {
        return Specification.where(byIds(asd.getIds()))
                .and(byAuthor(asd.getAuthor()))
                .and(byFirstName(asd.getFirstName()))
                .and(byLastName(asd.getLastName()))
                .and(byBirthDate(asd.getBirthDateFrom(), asd.getBirthDateTo()))
                .and(byCity(asd.getCity()))
                .and(byCountry(asd.getCountry()))
                .and(byBlocked(asd.isBlocked()))
                .and(byDeleted(asd.isDeleted()))
                .and(byAge(asd.getAgeFrom(), asd.getAgeTo()))
                ;
    }

    static Specification<Account> byIds(List<UUID> ids) {
        return (root, query, cb) -> {
            if (ids == null || ids.isEmpty()) {
                return null;
            }

            return root.get("id").in(ids);
        };
    }

    static Specification<Account> byAuthor(String author) {
        return (root, query, cb) -> {
            if (author == null || author.isEmpty()) {
                return null;
            }

            String[] parts = author.split("\\s+");
            if (parts.length == 2) {
                return cb.and(
                        cb.equal(root.get("firstName"), parts[0].trim()),
                        cb.equal(root.get("lastName"), parts[1].trim())
                );
            }

            return null;
        };
    }

    static Specification<Account> byFirstName(String firstName) {
        return (root, query, cb) -> {
            if (firstName == null || firstName.isEmpty()) {
                return null;
            }

            return cb.like(root.get("firstName"), "%" + firstName + "%");
        };
    }

    static Specification<Account> byLastName(String lastName) {
        return (root, query, cb) -> {
            if (lastName == null || lastName.isEmpty()) {
                return null;
            }

            return cb.like(root.get("lastName"), "%" + lastName + "%");
        };
    }


    static Specification<Account> byBirthDate(LocalDateTime birthDateFrom, LocalDateTime birthDateTo) {
        return (root, query, cb) -> {
            if (birthDateFrom == null && birthDateTo == null) {
                return null;
            }

            List<Predicate> predicates = new ArrayList<>();

            if (birthDateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("birthDate"), birthDateFrom));
            }

            if (birthDateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("birthDate"), birthDateTo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    static Specification<Account> byCity(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isEmpty()) {
                return null;
            }

            return cb.like(root.get("city"), "%" + city + "%");
        };
    }


    static Specification<Account> byCountry(String country) {
        return (root, query, cb) -> {
            if (country == null || country.isEmpty()) {
                return null;
            }

            return cb.like(root.get("country"), "%" + country + "%");
        };
    }

    static Specification<Account> byBlocked(Boolean isBlocked) {
        return (root, query, cb) -> {
            if (isBlocked == null) {
                return null;
            }

            return cb.equal(root.get("isBlocked"), isBlocked);
        };
    }

    static Specification<Account> byDeleted(Boolean isDeleted) {
        return (root, query, cb) -> {
            if (isDeleted == null) {
                return null;
            }

            return cb.equal(root.get("isDeleted"), isDeleted);
        };
    }

    static Specification<Account> byAge(Integer ageFrom, Integer ageTo) {
        return (root, query, cb) -> {
            if (ageFrom == null && ageTo == null) {
                return null;
            }

            LocalDate currentDate = LocalDate.now();
            LocalDate birthDateFrom = ageFrom != null ? currentDate.minusYears(ageFrom) : null;
            LocalDate birthDateTo = ageTo != null ? currentDate.minusYears(ageTo) : null;

            if (birthDateFrom != null && birthDateTo != null) {
                return cb.between(root.get("birthDate"),
                        Timestamp.valueOf(birthDateTo.atStartOfDay()),
                        Timestamp.valueOf(birthDateFrom.atStartOfDay()));
            }

            if (birthDateFrom != null) {
                return cb.lessThanOrEqualTo(root.get("birthDate"),
                        Timestamp.valueOf(birthDateFrom.atStartOfDay()));
            }

            return cb.greaterThanOrEqualTo(root.get("birthDate"),
                    Timestamp.valueOf(birthDateTo.atStartOfDay()));
        };
    }
}

