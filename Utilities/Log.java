package Utilities;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 * Makes a report for successful or unsuccessful attempts
 */
public class Log {
    public Logger logger;
    FileHandler fileHandler;

    public Log (String File_Name) throws SecurityException, IOException {
        File F = new File(File_Name);
        if(!F.exists()){
            F.createNewFile();
        }
        fileHandler = new FileHandler(File_Name, true);
        logger = Logger.getLogger("test");
        logger.addHandler(fileHandler);
        SimpleFormatter SF = new SimpleFormatter();
        fileHandler.setFormatter(SF);
    }
}