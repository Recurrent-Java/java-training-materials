package com.example.java_extra_spring.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.java_extra_spring.entity.Member;
import com.example.java_extra_spring.entity.UserServiceSetting;
import com.example.java_extra_spring.entity.id.ServiceId;
import com.example.java_extra_spring.form.MemberForm;
import com.example.java_extra_spring.repository.MemberRepository;
import com.example.java_extra_spring.repository.ServiceRepository;

@Service
public class MemberService {
  private final MemberRepository memberRepository;
  private final ServiceRepository serviceRepository;

  public MemberService(MemberRepository memberRepository, ServiceRepository serviceRepository) {
    this.memberRepository = memberRepository;
    this.serviceRepository = serviceRepository;
  }

  // 利用者情報登録処理
  @Transactional
  public void save(MemberForm memberForm) {
    Member member = new Member(
        null,
        memberForm.getName(),
        memberForm.getAge(),
        memberForm.getGender(),
        memberForm.getRole(),
        memberForm.getMailFlg());
    // 利用者情報の登録
    memberRepository.save(member);
    // 利用者IDの取得
    // JPAを利用すると登録時に引数として渡したEntityに
    // 採番されたIDを格納し、返却してくれる
    Long member_id = member.getMember_id();
    // Serviceテーブルへ登録
    for (String serviceCode : memberForm.getServices()) {
      ServiceId id = new ServiceId(member_id, serviceCode);
      UserServiceSetting service = new UserServiceSetting(id);
      serviceRepository.save(service);
    }
  }
}
