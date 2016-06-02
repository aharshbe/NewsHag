package badassapps.aaron.newshag;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aaron on 5/31/2016.
 */
public class GetCurrentDateTime {
    String df;

    GetCurrentDateTime(){
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        df = dateFormat.format(currentDate);
    }

    public String getDate(){
        return df;
    }
}
