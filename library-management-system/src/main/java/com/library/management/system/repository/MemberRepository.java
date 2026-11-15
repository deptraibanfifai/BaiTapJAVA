package com.library.management.system.repository;

import com.library.management.system.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberCode(String memberCode);
    boolean existsByMemberCode(String memberCode);
    Optional<Member> findByEmail(String email);
}