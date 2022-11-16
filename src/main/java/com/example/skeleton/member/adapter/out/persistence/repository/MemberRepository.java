package com.example.skeleton.member.adapter.out.persistence.repository;

import com.example.skeleton.member.adapter.out.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
