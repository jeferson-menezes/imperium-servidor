package com.zionflame.imperiumserver;

import java.time.LocalDate;

public class TesteMain {

	public static void main(String[] args) {
		LocalDate data = LocalDate.of(2020, 9, 9);
		System.out.println(data.MIN);
		System.out.println(data.lengthOfMonth());
	}
}
