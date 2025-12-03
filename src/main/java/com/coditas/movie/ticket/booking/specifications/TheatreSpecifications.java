package com.coditas.movie.ticket.booking.specifications;


import com.coditas.movie.ticket.booking.entity.Theatre;
import org.springframework.data.jpa.domain.Specification;

    public class TheatreSpecifications {
        public static Specification<Theatre> hasName(String name) {
            return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        }

        public static Specification<Theatre> hasOwner_id(Long owner_id) {
            return (root, query, cb) -> owner_id == null ? null : cb.equal(root.get("ownerId"), owner_id);
        }



    }


