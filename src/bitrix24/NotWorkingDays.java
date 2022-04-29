/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitrix24;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author da
 */
public class NotWorkingDays {

	public List<LocalDateTime> notWorkingDays = new ArrayList<>();
	public List<LocalDateTime> addWorkingDays = new ArrayList<>();

	public NotWorkingDays() {
		notWorkingDays.add(LocalDateTime.of(2020, 12, 24, 0, 0, 0));
		notWorkingDays.add(LocalDateTime.of(2020, 12, 25, 0, 0, 0));
		notWorkingDays.add(LocalDateTime.of(2020, 12, 31, 0, 0, 0));
		notWorkingDays.add(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
	}

	public Boolean todayNotWorking(LocalDateTime time) {
		return notWorkingDays
				.stream()
				.anyMatch(e -> e.equals(LocalDate.parse(time.format(DateTimeFormatter.ISO_LOCAL_DATE)).atStartOfDay()));
	}

	public Boolean todayAddWorkDay(LocalDateTime time) {
		Function<LocalDateTime, LocalDateTime> func =
				t -> LocalDate.parse(t.format(DateTimeFormatter.ISO_LOCAL_DATE)).atStartOfDay();
		return addWorkingDays.contains(func.apply(time));
	}

	private LocalDateTime getStartOfDay(LocalDateTime date) {
		date = date.withHour(0);
		date = date.withMinute(0);
		date = date.withSecond(0);
		date = date.withNano(0);
		return date;
	}

}
