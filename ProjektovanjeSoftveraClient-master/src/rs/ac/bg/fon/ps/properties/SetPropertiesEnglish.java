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
public class SetPropertiesEnglish {

    public static void main(String[] args) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config_english.properties");

            // set the properties value
            // Login form
            prop.setProperty("lblEmail", "Email:");
            prop.setProperty("lblPassword", "Password:");
            prop.setProperty("btnPrijava", "Login");
            prop.setProperty("cbSifra", "Show password");
            prop.setProperty("txtEmail", "Enter your email...");
            prop.setProperty("txtSifra", "Enter your password...");
            // Main form
            prop.setProperty("menuIzlagac", "Exhibitor");
            prop.setProperty("menuManifestacija", "Manifestation");
            prop.setProperty("menuDnevniRaspored", "Daily Schedule");
            prop.setProperty("miNoviIzlagac", "New Exhibitor");
            prop.setProperty("miPrikaziIzlagace", "Show all Exhibitors");
            prop.setProperty("miNovaManifestacija", "New Manifestation");
            prop.setProperty("miPrikaziManifestacije", "Show all Manifestation");
            prop.setProperty("miNoviDnevniRaspored", "New Daily schedule");
            prop.setProperty("miPrikaziDnevneRasporede", "Show all Daily schedules");
            prop.setProperty("menuONama", "About Us");
            prop.setProperty("btnLogOut", "Log out");
            // Exhibitor form
            prop.setProperty("titleExhibitor", "Exhibitor");
            prop.setProperty("lblIme", "Firstname:");
            prop.setProperty("lblPrezime", "Lastname:");
            prop.setProperty("lblEmail", "Email:");
            prop.setProperty("lblTelefon", "Telephone:");
            prop.setProperty("lblKompanija", "Company:");
            prop.setProperty("txtIme", "Enter your firstname...");
            prop.setProperty("txtPrezime", "Enter your lastname...");
            prop.setProperty("txtEmail", "Enter your email...");
            prop.setProperty("txtTelephone", "Enter your telephone...");
            // Exhibitor View All form
            prop.setProperty("titleAllExhibitors", "All Exhibitors");
            prop.setProperty("lblSearchExhibitors", "Enter search term for exhibitors:");
            prop.setProperty("tblIme", "Firstname");
            prop.setProperty("tblPrezime", "Lastname");
            prop.setProperty("tblEmail", "Email");
            prop.setProperty("tblTelefon", "Telephone");
            prop.setProperty("tblKompanija", "Company");
            // Manifestation form
            prop.setProperty("titleManifestation", "Manifestation");
            prop.setProperty("lblManifestationType", "Type:");
            prop.setProperty("lblNaziv", "Name:");
            prop.setProperty("txtNaziv", "Enter manifestation name...");
            prop.setProperty("lblDatumDo", "Date to:");
            prop.setProperty("lblDatumOd", "Date from:");
            // Manifestation View All form
            prop.setProperty("titleAllManifestations", "All Manifestations");
            prop.setProperty("lblSearchManifestations", "Enter search term for manifestations:");
            prop.setProperty("tblManifestationType", "Type");
            prop.setProperty("tblNaziv", "Name");
            prop.setProperty("tblDatumDo", "Date To");
            prop.setProperty("tblDatumOd", "Date From");
            // Daily Schedule
            prop.setProperty("titleDailySchedule", "Daily Schedule");
            prop.setProperty("lblManifestacija", "Manifestation:");
            prop.setProperty("lblTrajanje", "Duration:");
            prop.setProperty("lblDatum", "Date:");
            prop.setProperty("lblIzlagac", "Exhibitor:");
            prop.setProperty("lblHala", "Hall:");
            prop.setProperty("lblStand", "Stand:");
            prop.setProperty("btnDodajIzlagaca", "Add Exhibitor");
            prop.setProperty("btnObrisiIzlagaca", "Delete Exhibitor");
            prop.setProperty("btnIzmeniDnevniRaspored", "Edit daily schedule");
            prop.setProperty("btnObrisiDnevniRaspored", "Delete daily schedule");
            // Daily Schedule View All form
            prop.setProperty("titleAllDailySchedules", "All Daily schedules");
            prop.setProperty("lblSearchDailySchedules", "Enter search term for daily schedules:");
            // Common Buttons
            prop.setProperty("btnSave", "Save");
            prop.setProperty("btnEnableChanges", "Enable changes");
            prop.setProperty("btnDelete", "Delete");
            prop.setProperty("btnEdit", "Edit");
            prop.setProperty("btnCancel", "Cancel");
            prop.setProperty("btnDetails", "Details");
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
