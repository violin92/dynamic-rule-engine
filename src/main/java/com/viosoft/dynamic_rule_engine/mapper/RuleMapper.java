package com.viosoft.dynamic_rule_engine.mapper;

import com.viosoft.dynamic_rule_engine.dto.RuleDto;
import com.viosoft.dynamic_rule_engine.model.RuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RuleMapper {

    RuleMapper INSTANCE = Mappers.getMapper(RuleMapper.class);

    RuleDto toDto(RuleEntity rule);

    @Mapping(target = "id", ignore = true)
    RuleEntity toEntity(RuleDto dto);

    List<RuleDto> toDtoList(List<RuleEntity> rules);
}
