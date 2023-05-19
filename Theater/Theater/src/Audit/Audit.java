package Audit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Audit {
    private static Audit singleInstance = null;
    private static final String CVS_FILE_PATH = "./theater.cvs";
    private Audit() {}

    public static synchronized Audit getInstance()
    {
        if (singleInstance == null)
            singleInstance = new Audit();
        return singleInstance;
    }

    public void writeToFile(String data) {
        File file = new File(CVS_FILE_PATH);

        try
        {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date date = new Date();

            bufferedWriter.write(data + " \uF0B2 " + dateFormat.format(date));
            bufferedWriter.newLine();
            bufferedWriter.close();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
