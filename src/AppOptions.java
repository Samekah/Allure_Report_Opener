public class AppOptions {
    private String operatingSystem;
    private String defaultOutputDirectory;
    String version = "1.0a";
    private boolean firstRun = false;

    //todo: check if this is the applications first run, if yes do the following:
    //  [] set operatingSystem variable
    //  [] Make sure default directory is outputted to the user
    //  [] Set output directory
    //  [] update first Run

    //todo: if no, do the following:
    // [] load OS and directory data


    public boolean firstRun(){
        //todo: runs when progam loads, checks if first run then either sets default values and outputs it to file or loads file data
        return firstRun;
    }

    /**
     *Sets a new value for the defaultDirectory
     * @param os String of the systems operating system
     */
    public void setOperatingSystem(String os){
        operatingSystem = os;
    }

    /**
     * returns the current value of defaultDirectory
     * @return String value of defaultDirectory
     */
    public String getOperatingSystem(){
        return operatingSystem;
    }

    /**
     *Sets a new value for the defaultDirectory
     * @param directory String of the directory to be save unzipped files
     */
    public void setDefaultDirectory(String directory){
        defaultOutputDirectory = directory;
    }

    /**
     * returns the current value of defaultDirectory
     * @return String value of defaultDirectory
     */
    public String getDefaultDirectory(){
        return defaultOutputDirectory;
    }

    public boolean loadProperties(){
        return true;
    }

    public boolean saveProperties(){
        return true;
    }
}
