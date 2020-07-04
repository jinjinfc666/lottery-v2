package com.jll.common.utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {

	@Test
	public void test() {
		String pw = "111111";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		pw = encoder.encode(pw);
		System.out.println("password::::::::" + pw);
	}

}
