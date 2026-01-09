package com.example.java_extra_spring.entity.id;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceId implements Serializable {
  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "service_cd")
  private String serviceCd;
}
