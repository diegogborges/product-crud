package controller.v1;

import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import controller.v1.input.ProductInput;
import entity.Product;
import exceptionhandler.ExceptionHandler;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import service.ProductService;

@Path("/v1/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

  private final ProductService productService;

  @Inject
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GET
  @Operation(summary = "Gets Products", description = "Lists all available Products")
  @APIResponses(value = @APIResponse(responseCode = "200", description = "Success",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = Product.class))))
  public List<Product> getProducts() {
    return productService.getAllProducts();
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Gets a Product", description = "Retrieves a Product by id")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Success",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Product.class))),
      @APIResponse(responseCode = "404", description="Product not found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
  })
  public Product getProduct(@PathParam("id") int id) throws NotFoundException {
    return productService.getProductById(id);
  }

  @POST
  @Operation(summary = "Adds a Product",
      description = "Creates a Product and persists into database")
  @APIResponses(value = @APIResponse(responseCode = "200", description = "Success",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = Product.class))))
  public Product createProduct(@Valid ProductInput productInput) {
    return productService.saveProduct(productInput);
  }

  @PUT
  @Path("/{id}")
  @Operation(summary = "Updates a Product", description = "Updates an existing Product by id")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Success",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Product.class))),
      @APIResponse(responseCode = "404", description="Product not found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
  })
  public Product updateProduct(@PathParam("id") int id, @Valid ProductInput productInput)
      throws NotFoundException {
    return productService.updateProduct(id, productInput);
  }

  @DELETE
  @Path("/{id}")
  @Operation(summary = "Deletes a Product", description = "Deletes a Product by id")
  @APIResponses(value = {
      @APIResponse(responseCode = "204", description = "Success"),
      @APIResponse(responseCode = "404", description="Product not found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
  })
  public Response deleteProduct(@PathParam("id") int id) throws NotFoundException {
    productService.deleteProduct(id);
    return Response.status(Response.Status.NO_CONTENT).build();
  }
}
