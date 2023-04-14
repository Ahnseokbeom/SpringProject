package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import net.skhu.dto.User;

@Mapper
public interface UserMapper {
	@Select("select * from user where loginName = #{loginName}")
	User findByLoginName(String loginName);
}
