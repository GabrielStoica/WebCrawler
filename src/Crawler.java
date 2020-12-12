import javax.naming.ConfigurationException;
import java.io.IOException;

/**
 * Clasa ce contine metoda main() si care determina workflow-ul intregii aplicatii prin
 * apeluri ale metodelor din celelalte clasele.
 * @author Stoica Gabriel
 * @author VertUnix
 * @version v0.5
 *
 */
public class Crawler {
    /*You only throw an exception if you want it to be handled by a "higher" function.
     * => am eliminat throw-ul din main pt ca o sa tratam orice exceptie folosind try-catch*/

    /**
     * Metoda main() in care apelez principalele metode ale programului.
     *
     * @param args contine comanda primita de la utilizator ce urmeaza a fi interpretata
     */
    public static void main(String[] args) {

        try {
            CLI cli = CLI.getInstance();
            cli.readInput(args);

            //Test a = new Test();
            //a.downloadSite();
        } catch (IOException e)
        {
            System.out.println("Eroare de input/ output. " + e.getMessage());
            Logger.getInstance().sendDataToLogger(3,"Eroare de input/ output. " + e.getMessage());
            System.exit(-1);
        } catch (ConfigurationException e)
        {
                System.out.println("Eroare la configurare. " + e.getMessage());
                Logger.getInstance().sendDataToLogger(3,"Eroare la configurare. " + e.getMessage());
                System.exit(-1);
        } catch (Exception e)
        {
            //prinde o exceptie generica
            System.out.println("Eroare generica. " + e.getMessage());
            Logger.getInstance().sendDataToLogger(3,"Eroare generica. " + e.getMessage());
            System.exit(-1);
        }


    }
}