package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

import entity.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
}
