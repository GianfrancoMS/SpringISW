package com.gianfranco.trabajoparcial.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {
    public static void takeScreenshotAndWriteFile(ITestResult testResult, WebDriver driver) {
        try {
            String directory = "src/test/java/trabajoparcial/reports";

            String fileName = new SimpleDateFormat("ddMMyyyy-hhmmssSSS").format(new Date());

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            new File(directory).mkdirs();

            FileUtils.copyFile(screenshot, new File(directory + "\\" + fileName + ".jpg"));

            File fileMessageError = new File(directory + "\\" + fileName + ".txt");

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            Throwable cause = testResult.getThrowable();
            if (cause != null)
                cause.printStackTrace(pw);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileMessageError));
            bufferedWriter.write(sw.getBuffer().toString());
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
