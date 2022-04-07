public class App 
{
    public static void main(String[] args) throws Exception 
    {
        Driver oDriver = new Driver(4);
        oDriver.runTagExperiments("data/bookStore/bInput.txt", true);
        oDriver.runTagExperiments("data/museums/mInput.txt", true);
    }
}