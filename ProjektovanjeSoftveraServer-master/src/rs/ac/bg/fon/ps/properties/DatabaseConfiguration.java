/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Mirko
 */
public class DatabaseConfiguration {

    private static DatabaseConfiguration instance;
    private String url;
    private String password;
    private String username;

    public static DatabaseConfiguration getInstance() {
        if (instance == null) {
            instance = new DatabaseConfiguration();
        }
        return instance;
    }

    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config/dbconfig.properties");
            prop.load(input);
            url = prop.getProperty("url");
            username = prop.getProperty("dbusername");
            password = prop.getProperty("dbpassword");

        } catch (IOException ex) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void writeConfigProperties(String url, String username, String password) {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("config/dbconfig.properties");

            prop.setProperty("url", url);
            prop.setProperty("dbusername", username);
            prop.setProperty("dbpassword", password);
            prop.store(output, null);

        } catch (IOException ex) {
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
