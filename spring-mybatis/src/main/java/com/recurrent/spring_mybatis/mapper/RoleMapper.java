package com.recurrent.spring_mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.recurrent.spring_mybatis.entity.Role;

@Mapper  // MyBatisのMapperインターフェースであることを示すアノテーション
public interface RoleMapper {
  // ロール名でロールを取得
  public Role findByName(String name);
}
