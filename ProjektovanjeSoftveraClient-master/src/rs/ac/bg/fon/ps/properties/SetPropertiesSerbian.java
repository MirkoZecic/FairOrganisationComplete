/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Mirko
 */
public class SetPropertiesSerbian {

    public static void main(String[] args) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config_serbian.properties");

            // set the properties value
            prop.setProperty("driver", "com.mysql.jdbc.Driver");
            prop.setProperty("url", "jdbc:mysql://127.0.0.1:3306/projsoftprojekat");
            prop.setProperty("dbusername", "root");
            prop.setProperty("dbpassword", "mysql");

            // set the properties value
            //Login form
            prop.setProperty("lblEmail", "Mejl:");
            prop.setProperty("lblPassword", "Sifra:");
            prop.setProperty("btnPrijava", "Prijavi se");
            prop.setProperty("cbSifra", "Prikazi sifru");
            prop.setProperty("txtEmail", "Unesite email...");
            prop.setProperty("txtSifra", "Unesite sifru...");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {

        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {

                }
            }

        }
    }
}
