package com.bikram.appliedproject.service.specification;

import com.bikram.appliedproject.domain.category.Type;
import com.bikram.appliedproject.domain.category.Type_;
import com.bikram.appliedproject.domain.product.Product;
import com.bikram.appliedproject.domain.product.Product_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.Instant;

public class ProductSpecification {

    public static Specification hasName(String name){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate findName = criteriaBuilder.like(root.get(Product_.product_name), "%" + name + "%");
                Predicate findBrand = criteriaBuilder.like(root.get(Product_.brand), "%" + name + "%");

                return criteriaBuilder.or(findName, findBrand);
            }
        };
    }

    public static Specification hasProductDetail(String name){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate findName = criteriaBuilder.like(root.get(Product_.product_name), "%" + name + "%");
                Predicate findBrand = criteriaBuilder.like(root.get(Product_.brand), "%" + name + "%");
                Predicate findSKU = criteriaBuilder.like(root.get(Product_.SKU), "%" + name + "%");
                Predicate findDesc = criteriaBuilder.like(root.get(Product_.description), "%"+ name + "%");

                return criteriaBuilder.or(findName, findBrand, findSKU, findDesc);
            }
        };
    }

    public static Specification hasCategory(String category){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(Product_.category), "%" + category + "%");
            }
        };
    }

    public static Specification hasBrand( String brand){
        String[] brands = brand.split(",");
            return new Specification() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        return root.get(Product_.brand).in(brands);
                    }
                };
    }

    public static Specification hasPriceMin(int minPrice){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.price), minPrice);
            }
        };
    }

    public static Specification hasPriceMax(int maxPrice){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Product_.price), maxPrice);
            }
        };
    }

    public static Specification hasType(Integer typeId){
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Product, Type> joinProductManufacturer = root.join(Product_.type);
                return criteriaBuilder.equal(joinProductManufacturer.get(Type_.type_id), typeId);
            }
        };
    }

    public static Specification ProductByDate(){

        Instant instant = Instant.now();
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Product_.createdDate), instant);
            }
        };
    }

}
