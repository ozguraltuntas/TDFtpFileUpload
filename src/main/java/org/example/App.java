package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger logger = LogManager.getLogger(App.class);
    public static void main( String[] args )
    {
        logger.info("      \n Application Process started ...    \n");
        Timer timer = new Timer();
        TimerTask startProcess = new ProcessStartPoint();
        timer.schedule(startProcess, 1000, 3600000);
    }
}
