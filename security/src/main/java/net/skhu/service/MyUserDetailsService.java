package net.skhu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.skhu.config.MyUserDetails;
import net.skhu.dto.User;
import net.skhu.mapper.UserMapper;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByLoginName(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return new MyUserDetails(user);
	}
}