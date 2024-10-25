package avduhova.lubov.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Url {
    private String longUrl;
    private Date expirationDate;
    private int referralLimit;
    private boolean block;

    {
        Date currentDate = new Date();
        long currentTimeInMillis = currentDate.getTime();
        long timePlusNextDay = currentTimeInMillis + 86400000L;
        expirationDate = new Date(timePlusNextDay);
    }

    public Url(String longUrl, int referralLimit) {
        this(longUrl);
        this.referralLimit = referralLimit;

    }

    public Url(String longUrl) {
        this.longUrl = longUrl;
        referralLimit = 3;
    }
}
