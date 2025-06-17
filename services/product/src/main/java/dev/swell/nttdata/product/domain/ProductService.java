package dev.swell.nttdata.product.domain;

import dev.swell.nttdata.product.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Long createProduct(ProductRequest request) {
        var product = productRepository.save(productMapper.toProduct(request));
        return product.getId();
    }

    public void updateProduct(Long productId, ProductRequest request) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        format("Produto não foi atualizado: Nenhum produto com o ID: %d foi encontrado.", productId)
                ));
        mergerProduct(product, request);
        productRepository.save(product);
    }

    private void mergerProduct(Product product, ProductRequest request) {
        product.setName(StringUtils.isNotBlank(request.name()) ? request.name() : product.getName());
        product.setDescription(StringUtils.isNotBlank(request.description()) ? request.description() : product.getDescription());
        product.setPrice(request.price() != null ? request.price() : product.getPrice());
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(
                        format("Nenhum produto com o ID: %d foi encontrado.", productId)
                ));
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
