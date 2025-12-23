package com.example.springlesson.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping; // Mappingをインポート

import com.example.springlesson.dto.ProductDTO;
import com.example.springlesson.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  /** 単体変換 */
  // 名前の不一致が不安な場合は、このように明示的に書きます
  @Mapping(source = "productId", target = "productId")
  @Mapping(source = "catId", target = "catId")
  ProductDTO toDto(Product entity);

  // DTO -> Entity
  @Mapping(source = "productId", target = "productId")
  @Mapping(source = "catId", target = "catId")
  Product toEntity(ProductDTO dto);

  /** List 変換 */
  List<ProductDTO> toDtoList(List<Product> entityList);

  List<Product> toEntityList(List<ProductDTO> dtoList);
}