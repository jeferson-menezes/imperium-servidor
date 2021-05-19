package com.zionflame.imperiumserver.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
	private static String[] meses = {"",  "janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto",
			"setembro", "outubro", "novembro", "dezembro" };

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

	public static LocalDate data(String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(data, formatter);
	}

	public static LocalDate[] mes(String mesAno) {
		LocalDate data = DateHelper.data(mesAno + "-01");
		LocalDate[] periodo = new LocalDate[2];
		periodo[0] = data.withDayOfMonth(1);
		periodo[1] = data.withDayOfMonth(data.lengthOfMonth());
		return periodo;
	}

	public static String getMesExtenso(int i) {
		return meses[i];
	}
}
