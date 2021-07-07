package com.yhdc.mreview.repositorytest;

import com.yhdc.mreview.model.Member;
import com.yhdc.mreview.repository.MemberRepository;
import com.yhdc.mreview.repository.ReviewRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberRepositoryTests {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
//	@Test
//	public void insertMembers() {
//		
//		IntStream.rangeClosed(1, 50).forEach(i -> {
//			Member member = Member.builder().email("r"+i+"@email.org").password("ppp"+i).nickname("reviewer"+i).build();
//			
//			memberRepository.save(member);
//		});
//	}
	
	@Commit
	@Transactional
	@Test
	public void testDeleteMember() {
		
		Long mid = 1L;
		
		Member member = Member.builder().mid(mid).build();
		
		reviewRepository.deleteByMember(member);
		memberRepository.deleteById(mid);
	}
	
}
