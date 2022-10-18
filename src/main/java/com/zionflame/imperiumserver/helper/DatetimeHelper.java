package com.zionflame.imperiumserver.helper;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.apache.logging.log4j.util.Strings;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;

public interface DatetimeHelper {

	Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\{2}$");

	Pattern MONTH_PATTERN = Pattern.compile("^\\d{4}-\\d{2}$");

	Pattern YEAR_PATTERN = Pattern.compile("^\\d{4}$");

	default LocalDate sToDatetimeStart(String data) {
		if (Strings.isBlank(data) || !DATE_PATTERN.matcher(data).matches()) {
			throw new BadRequestException("Data inválida!");
		}
		return LocalDate.parse(data);
	}

	default LocalDate sToDatetimeEnd(String data) {
		if (Strings.isBlank(data) || !DATE_PATTERN.matcher(data).matches()) {
			throw new BadRequestException("Data inválida!");
		}
		return LocalDate.parse(data);
	}

	default LocalDate formatOfMonthStart(String anoMes) {
		if (Strings.isBlank(anoMes) || !MONTH_PATTERN.matcher(anoMes).matches()) {
			throw new BadRequestException("Data inválida!");
		}

		return LocalDate.parse(anoMes + "-01");
	}

	default LocalDate formatOfMonthEnd(String anoMes) {
		if (Strings.isBlank(anoMes) || !MONTH_PATTERN.matcher(anoMes).matches()) {
			throw new BadRequestException("Data inválida!");
		}

		LocalDate data = LocalDate.parse(anoMes + "-01");

		return data.withDayOfMonth(data.lengthOfMonth());
	}
}
