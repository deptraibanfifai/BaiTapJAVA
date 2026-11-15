package com.library.management.system.service;

import com.library.management.system.dto.request.MemberRequest;
import com.library.management.system.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {
    List<MemberResponse> getAllMembers();
    MemberResponse getMemberById(Long id);
    MemberResponse createMember(MemberRequest request);
    MemberResponse updateMember(Long id, MemberRequest request);
    void deleteMember(Long id);
}