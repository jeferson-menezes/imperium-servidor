package com.zionflame.imperiumserver.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {

	public static LocalDate atingeDia(LocalDate data, int dia) {
		while (true) {
			data = data.plusDays(1);
			if (data.getDayOfMonth() == dia) {
				break;
			}
		}
		return data;
	}
	

	public static LocalDate lavaData(int year, int month, int day) {
		String data = String.valueOf(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(data, formatter);
	}
}
