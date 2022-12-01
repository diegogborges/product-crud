package controller.v1.input;

import javax.validation.constraints.NotBlank;

import entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {

  @NotBlank
  private String description;

  public Product inputToProduct() {
    return Product.builder()
        .description(this.getDescription())
        .build();
  }
}
