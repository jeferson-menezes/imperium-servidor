package com.zionflame.imperiumserver.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Pattern;

import org.apache.logging.log4j.util.Strings;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;

public interface DatetimeHelper {

	Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\{2}$");

	Pattern MONTH_PATTERN = Pattern.compile("^\\d{4}-\\d{2}$");

	Pattern YEAR_PATTERN = Pattern.compile("^\\d{4}$");

	default LocalDateTime sToDatetimeStart(String data) {
		if (Strings.isBlank(data) || !DATE_PATTERN.matcher(data).matches()) {
			throw new BadRequestException("Data inválida!");
		}
		return LocalDate.parse(data).atStartOfDay();
	}

	default LocalDateTime sToDatetimeEnd(String data) {
		if (Strings.isBlank(data) || !DATE_PATTERN.matcher(data).matches()) {
			throw new BadRequestException("Data inválida!");
		}
		return LocalDate.parse(data).atTime(LocalTime.MAX);
	}

	default LocalDateTime formatOfMonthStart(String anoMes) {
		if (Strings.isBlank(anoMes) || !MONTH_PATTERN.matcher(anoMes).matches()) {
			throw new BadRequestException("Data inválida!");
		}

		return LocalDate.parse(anoMes + "-01").atStartOfDay();
	}

	default LocalDateTime formatOfMonthEnd(String anoMes) {
		if (Strings.isBlank(anoMes) || !MONTH_PATTERN.matcher(anoMes).matches()) {
			throw new BadRequestException("Data inválida!");
		}

		LocalDate data = LocalDate.parse(anoMes + "-01");

		return data.withDayOfMonth(data.lengthOfMonth()).atTime(LocalTime.MAX);
	}
}
