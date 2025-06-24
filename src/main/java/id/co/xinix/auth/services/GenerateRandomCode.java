package id.co.xinix.auth.services;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service("authGenerateRandomCode")
public class GenerateRandomCode {

    private static final Integer CODE_LENGTH = 10;

    private static final String RANDOM_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generate(String prefix) {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.systemDefault());

        String prefixWithTimeStamp =
            prefix +
                dateTime.getYear() +
                String.format("%02d", dateTime.getMonthValue()) +
                String.format("%02d", dateTime.getDayOfMonth()) +
                String.format("%02d", dateTime.getHour()) +
                String.format("%02d", dateTime.getMinute()) +
                String.format("%02d", dateTime.getSecond());

        String randomString = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, RANDOM_STRING.toCharArray(), CODE_LENGTH);

        return String.format("%s_%s", prefixWithTimeStamp, randomString);
    }
}
