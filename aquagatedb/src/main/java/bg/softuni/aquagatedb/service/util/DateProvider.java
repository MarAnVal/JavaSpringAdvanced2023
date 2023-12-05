package bg.softuni.aquagatedb.service.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class DateProvider {
    public LocalDate getDate(){
        return LocalDate.now(ZoneOffset.UTC);
    }
}
