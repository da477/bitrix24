/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitrix24;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author da
 */
public class NotWorkingDays {

    public List<LocalDateTime> notWorkingDays = new ArrayList<>();
    public List<LocalDateTime> addWorkingDays = new ArrayList<>();

    public NotWorkingDays() {

        notWorkingDays.add(LocalDateTime.of(2020, 12, 24, 0, 0, 0));
        notWorkingDays.add(LocalDateTime.of(2020, 12, 25, 0, 0, 0));
        notWorkingDays.add(LocalDateTime.of(2020, 12, 31, 0, 0, 0));
        notWorkingDays.add(LocalDateTime.of(2021, 1, 01, 0, 0, 0));

    }

    public Boolean todayNotWorking(LocalDateTime time) {

        Boolean todayNotWorking = false;
        LocalDateTime startDay = getStartOfDay(time);
        for (LocalDateTime elarr : notWorkingDays) {
            if (elarr.equals(startDay)) {
                todayNotWorking = true;
                break;
            }
        }

        return todayNotWorking;

    }

    public Boolean todayAddWorkDay(LocalDateTime time) {

        Boolean todayAddWorkDay = false;
        LocalDateTime startDay = getStartOfDay(time);
        for (LocalDateTime elarr : addWorkingDays) {
            if (elarr.equals(startDay)) {
                todayAddWorkDay = true;
                break;
            }
        }

        return todayAddWorkDay;

    }

    private LocalDateTime getStartOfDay(LocalDateTime date) {

        date = date.withHour(0);
        date = date.withMinute(0);
        date = date.withSecond(0);
        date = date.withNano(0);
        return date;

    }

}
