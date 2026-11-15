package com.library.management.system.mapper;

import com.library.management.system.dto.request.MemberRequest;
import com.library.management.system.dto.response.MemberResponse;
import com.library.management.system.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberRequest request) {
        if (request == null) return null;

        Member member = Member.builder()
                .memberCode(request.getMemberCode())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .registrationDate(request.getRegistrationDate())
                .build();

        if (request.getId() != null) {
            member.setId(request.getId());
        }

        return member;
    }

    public MemberResponse toResponse(Member entity) {
        if (entity == null) return null;

        MemberResponse response = new MemberResponse();
        response.setId(entity.getId());
        response.setMemberCode(entity.getMemberCode());
        response.setFullName(entity.getFullName());
        response.setEmail(entity.getEmail());
        response.setPhone(entity.getPhone());
        response.setAddress(entity.getAddress());
        response.setRegistrationDate(entity.getRegistrationDate());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}