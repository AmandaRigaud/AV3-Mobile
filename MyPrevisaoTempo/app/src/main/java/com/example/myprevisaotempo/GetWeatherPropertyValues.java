package com.example.myprevisaotempo;

import android.os.Environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetWeatherPropertyValues {
    String result;
    InputStream inputStream;

    public String getPropValues(String code) throws IOException{

        FileInputStream fis = null;
        Properties prop = null;
        try {
            //Não consigo acessar o arquivo de jeito nenhum
            fis = new FileInputStream("weatherI.properties");
            prop = new Properties();

            prop.load(fis);

            System.out.println("Prop: " + prop);
            System.out.println("Code: " + code);

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();

        } catch (Exception e){
            System.out.println("Exception: " + e);

        } finally {
            fis.close();
        }

        System.out.println("Prop: " + prop);
        System.out.println("Code: " + code);

        return result = prop.getProperty(code);

    }
}
