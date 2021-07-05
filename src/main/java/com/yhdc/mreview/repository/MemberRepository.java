package com.yhdc.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yhdc.mreview.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
