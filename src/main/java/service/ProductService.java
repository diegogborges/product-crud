package service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import controller.v1.input.ProductInput;
import entity.Product;
import repository.ProductRepository;

@ApplicationScoped
public class ProductService {

  private final ProductRepository productRepository;

  @Inject
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }
  
  public Product getProductById(final long id) throws NotFoundException {
    return productRepository.findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("The Product doesn't exist"));
  }

  public List<Product> getAllProducts() {
    return productRepository.listAll();
  }

  @Transactional
  public Product updateProduct(final long id, final ProductInput productInput)
      throws NotFoundException {
    Product existingProduct = getProductById(id);
    existingProduct.setDescription(productInput.getDescription());
    productRepository.persist(existingProduct);
    return existingProduct;
  }

  @Transactional
  public Product saveProduct(final ProductInput productInput) {
    final Product product = productInput.inputToProduct();
    productRepository.persistAndFlush(product);
    return product;
  }

  @Transactional
  public void deleteProduct(final long id) throws NotFoundException {
    productRepository.delete(getProductById(id));
  }
}
