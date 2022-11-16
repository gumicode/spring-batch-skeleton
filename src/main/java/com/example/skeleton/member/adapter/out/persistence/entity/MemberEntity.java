package com.example.skeleton.member.adapter.out.persistence.entity;

import com.example.skeleton.shared.adapter.out.persistence.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends BaseEntity {
	private String username;
	private String name;
}