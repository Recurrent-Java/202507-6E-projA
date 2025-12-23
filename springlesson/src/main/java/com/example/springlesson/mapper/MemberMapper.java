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

  /**
   * String から AllergenInfo への変換ルール
   * MapStructは List<String> を List<AllergenInfo> に変換する際、
   * このメソッドを自動的に繰り返し利用します。
   */
  default MemberDTO.AllergenInfo mapStringToAllergenInfo(String name) {
    if (name == null)
      return null;
    MemberDTO.AllergenInfo info = new MemberDTO.AllergenInfo();
    info.setName(name);
    // codeが必要な場合はここに追加ロジックを書くか、一端nullのままでもビルドは通ります
    return info;
  }

  /**
   * AllergenInfo から String への変換ルール（逆方向用）
   */
  default String mapAllergenInfoToString(MemberDTO.AllergenInfo info) {
    if (info == null)
      return null;
    return info.getName();
  }
}
