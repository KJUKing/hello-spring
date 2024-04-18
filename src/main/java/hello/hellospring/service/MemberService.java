package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Transactional
public class MemberService {

    private  final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } // alt insert로 constructor생성

    private static Map<Long, Member> store = new HashMap<>();
    /*
    회원가입
     */
    public Long join(Member member) {

            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
        // 같은 이름이 있는 중복회원은 x
        // 중복 회원 검증
        //extract Method = ctrl + alt + M

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(member1 -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /*
    전체회원조회
     */
    public List<Member> findMember() {

        return memberRepository.findAll();
    }
}
