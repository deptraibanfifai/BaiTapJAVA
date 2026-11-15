package com.library.management.system.service.impl;

import com.library.management.system.dto.request.MemberRequest;
import com.library.management.system.dto.response.MemberResponse;
import com.library.management.system.entity.Member;
import com.library.management.system.mapper.MemberMapper;
import com.library.management.system.repository.MemberRepository;
import com.library.management.system.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MemberResponse getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy độc giả với ID: " + id));
        return memberMapper.toResponse(member);
    }

    @Override
    public MemberResponse createMember(MemberRequest request) {
        if (memberRepository.existsByMemberCode(request.getMemberCode())) {
            throw new RuntimeException("Mã độc giả '" + request.getMemberCode() + "' đã tồn tại!");
        }

        if (request.getRegistrationDate() == null) {
            request.setRegistrationDate(LocalDate.now());
        }

        Member member = memberMapper.toEntity(request);
        member = memberRepository.save(member);
        return memberMapper.toResponse(member);
    }

    @Override
    public MemberResponse updateMember(Long id, MemberRequest request) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy độc giả với ID: " + id));

        if (!existingMember.getMemberCode().equals(request.getMemberCode()) &&
                memberRepository.existsByMemberCode(request.getMemberCode())) {
            throw new RuntimeException("Mã độc giả '" + request.getMemberCode() + "' đã tồn tại!");
        }

        existingMember.setMemberCode(request.getMemberCode());
        existingMember.setFullName(request.getFullName());
        existingMember.setEmail(request.getEmail());
        existingMember.setPhone(request.getPhone());
        existingMember.setAddress(request.getAddress());
        existingMember.setRegistrationDate(request.getRegistrationDate());

        existingMember = memberRepository.save(existingMember);
        return memberMapper.toResponse(existingMember);
    }

    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy độc giả với ID: " + id);
        }
        memberRepository.deleteById(id);
    }
}