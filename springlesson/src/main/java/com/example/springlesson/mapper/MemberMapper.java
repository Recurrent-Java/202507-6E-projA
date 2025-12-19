package com.example.springlesson.mapper;

import org.mapstruct.Mapper;

import com.example.springlesson.dto.MemberDTO;
import com.example.springlesson.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  /** 単体変換 */
  // Entity -> DTO
  MemberDTO toDto(Member entity);

  // DTO -> Entity
  Member toEntity(MemberDTO dto);
}