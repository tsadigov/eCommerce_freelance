package com.project.ecommerce.controller;

import com.project.ecommerce.domain.Product;
import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.project.ecommerce.bootstrap.Constants.*;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    ProductDTO getOne(@PathVariable Long id){
        return productService.getOne(id);
    }

    @GetMapping
    List<ProductDTO> getAll(){

        List<ProductDTO> products = productService.getAll();
        return products;

    }

    @GetMapping("/store/{id}")
    List<ProductDTO> getAllByStoreId(@PathVariable Long id){

        List<ProductDTO> products = productService.getAllByStoreId(id);
        return products;

    }

    @GetMapping("/search/{searchString}")
    List<ProductDTO> getAllByNameAndDetails(@PathVariable String searchString){

        List<ProductDTO> products = productService.getAllByNameAndDetails(searchString);
        return products;

    }

    @PostMapping
    ResponseEntity<ResponseDTO> create(@RequestBody ProductDTO productDTO){
        ResponseDTO responseDTO = productService.create(productDTO);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id){
        ResponseDTO responseDTO = productService.delete(id);
        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @GetMapping(
            value = "/get-product-picture/{pictureName}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody
    byte[] getProductPicture(@PathVariable String pictureName) throws IOException {
        String imageDir = Paths.get("").toAbsolutePath() + "/uploads/product/" + pictureName;
        imageDir.replace("\\", "/");
        File fi = new File(imageDir);
        byte[] fileContent;

        if (fi.exists()) {
            fileContent = Files.readAllBytes(fi.toPath());
        } else {
            InputStream in = this.getClass().getResourceAsStream("/static/product-icon.png");
            fileContent = in.readAllBytes();
        }
        return fileContent;
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateProduct(@RequestPart("file") MultipartFile file,
                                                     @RequestPart("product-id") String id,
                                                     @RequestPart("product-name") String name,
                                                     @RequestPart("cost") String cost,
                                                     @RequestPart("amount") String amount,
                                                     @RequestPart("storeId") String storeId,
                                                     @RequestPart("subcategory") String subCategoryId,
                                                     @RequestPart("details") String details) throws IOException {

        ProductDTO productDTO = ProductDTO.builder()
                .id(Long.parseLong(id))
                .name(name)
                .cost(Float.parseFloat(cost))
                .amount(Long.parseLong(amount))
                .storeId(Long.parseLong(storeId))
                .subcategoryId(Long.parseLong(subCategoryId))
                .details(details)
                .build();

        ResponseDTO responseDTO = productService.update(file, productDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createProduct(@RequestPart("file") MultipartFile file,
                                                @RequestPart("product-name") String name,
                                                @RequestPart("cost") String cost,
                                                @RequestPart("amount") String amount,
                                                @RequestPart("storeId") String storeId,
                                                @RequestPart("subcategory") String subCategoryId,
                                                @RequestPart("details") String details) throws IOException {

        ProductDTO productDTO = ProductDTO.builder()
                .name(name)
                .cost(Float.parseFloat(cost))
                .amount(Long.parseLong(amount))
                .storeId(Long.parseLong(storeId))
                .subcategoryId(Long.parseLong(subCategoryId))
                .details(details)
                .build();

        ResponseDTO responseDTO = productService.createProduct(file, productDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

}
