package support;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateConverter {

	public static LocalDateTime dateFromInt(Long date) {
		Instant epochTime = Instant.ofEpochMilli(date);

		LocalDateTime localDateTime = java.time.LocalDateTime.ofInstant(epochTime, java.time.ZoneId.of("UTC"));
		
		return  localDateTime;
	}

	public static Long dateToInt(LocalDateTime date) {
		Long miliTime = date.toInstant(ZoneOffset.UTC).toEpochMilli();
		
		return  miliTime;
	}
	
	public static Long dateToInt(LocalDate date) {
		Long miliTime = date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
		
		return  miliTime;
	}
	
}
