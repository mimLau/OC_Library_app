package com.mimi.mlibrary.mapper.account;

import com.mimi.mlibrary.model.dto.account.MemberDto;
import com.mimi.mlibrary.model.entity.account.Member;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;

@MapperConfig
public interface MemberMapperConfig extends AccountMapperConfig {

    @InheritConfiguration( name = "mapAccount")
    void mapMember(Member member, @MappingTarget MemberDto memberDto );
}
