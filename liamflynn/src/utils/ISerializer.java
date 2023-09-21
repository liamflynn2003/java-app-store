package utils;

//Implemented in DeveloperAPI and AppStoreAPI to facilitate save and load capabilities

public interface ISerializer {
    void save() throws Exception;

    void load() throws Exception;

    String fileName();
}