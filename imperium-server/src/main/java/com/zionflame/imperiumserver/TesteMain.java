package com.zionflame.imperiumserver;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteMain {

	public static void main(String[] args) {

		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
}
