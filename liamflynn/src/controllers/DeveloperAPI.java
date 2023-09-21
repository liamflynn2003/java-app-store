package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.ISerializer;
import utils.Utilities;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DeveloperAPI implements ISerializer {
    private List<Developer> developers = new ArrayList<>();

    //---------------------
    // Create methods
    //---------------------
    public boolean addDeveloper(Developer developer) {
        if (isValidDeveloper(developer.getDeveloperName())){
            return false;
        }
        return developers.add(developer);
    }

    //---------------------
    // Read methods
    //---------------------
    public Developer getDeveloperByIndex(int index){
        if (Utilities.isValidIndex(developers, index)){
            return developers.get(index);
        }
        else{
            return null;
        }
    }

    public Developer getDeveloperByName (String developerName){
        int index = retrieveDeveloperIndex(developerName);
        if (index != -1){
            return developers.get(index);
        }
        return null;
    }


    public String listDevelopers(){
        String listDevelopers = "";
        for (Developer developer : developers){
            listDevelopers += developers.indexOf(developer) + ": " + developer + "\n";
        }
        if (listDevelopers.equals("")){
            return "No developers";
        }
        else {
            return listDevelopers;
        }
    }

    //---------------------
    // Update methods
    //---------------------
    public boolean updateDeveloperWebsite(String developerName, String developerWebsite){
        if (isValidDeveloper(developerName)){
            Developer developerToUpdate = getDeveloperByName(developerName);
            developerToUpdate.setDeveloperWebsite(developerWebsite);
            return true;
        }
        return false;
    }

    //---------------------
    // Delete methods
    //---------------------
    public Developer removeDeveloper(String developerName){
        int index = retrieveDeveloperIndex(developerName);
        if (index != -1) {
            return developers.remove(index);
        }
        return null;
    }

    //---------------------
    // Validation Methods
    //---------------------
    public boolean isValidDeveloper(String developerName){
        for (Developer developer : developers){
            if (developer.getDeveloperName().equalsIgnoreCase(developerName)){
                return true;
            }
        }
        return false;
    }

    public int retrieveDeveloperIndex(String developerName){
        for (Developer developer : developers){
            if (developer.getDeveloperName().equalsIgnoreCase(developerName)){
                return developers.indexOf(developer);
            }
        }
        return -1;
    }

    //---------------------
    // Getters/Setters
    //---------------------
    public List<Developer> getDevelopers() {
        return developers;
    }

    //---------------------
    // Persistence Methods
    //---------------------
    /**
     * The load method uses the XStream component to read all the objects from the xml
     * file stored on the hard disk.  The read objects are loaded into the associated ArrayList
     *
     * @throws Exception An exception is thrown if an error occurred during the load e.g. a missing file.
     */
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{Developer.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        developers = (List<Developer>) in.readObject();
        in.close();
    }

    /**
     * The save method uses the XStream component to write all the objects in the ArrayList
     * to the xml file stored on the hard disk.
     *
     * @throws Exception An exception is thrown if an error occurred during the save e.g. drive is full.
     */
    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(developers);
        out.close();
    }

    public String fileName(){
        return "developers.xml";
    }


}