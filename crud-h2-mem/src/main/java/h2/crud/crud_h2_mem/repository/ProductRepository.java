package h2.crud.crud_h2_mem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import h2.crud.crud_h2_mem.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
