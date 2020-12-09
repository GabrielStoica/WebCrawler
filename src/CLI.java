import java.io.File;
import java.util.Scanner;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Clasa implementeaza metodele necesare comunicatiei prin linia de comanda dintre user si tool.
 * @author VertUnix
 * @version 0.2
 */

public class CLI {

    /* Comentariu al unei variabile (camp)*/
    private int data1;

    /* Comentariu al unei variabile (camp)*/
    private int data2;


    /**
     *Constructor
     */
    public CLI(){

    }

    /**
     *Primeste input-ul utilizatorului din linia de comanda.
     * @param args  prin intermediul acestui arg se primeste comanda de la user
     * @throws Exception arunca dinverse tipuri derivate de exceptii (I/O, Configuration etc.)
     */
    public void readInput(String[] args) throws Exception
    {
        //afisare argumente
        for(int i=0; i<args.length; i++) {
            System.out.println("Argument " + i + ": " + args[i]);
        }

        //crawler crawl config.conf
        if (args[0].equals("crawl")) {
            Configuration _config = new Configuration(args[1]);
            //_config.printConfigurationSettings();

            Crawl _rootCrawl = new Crawl(_config.getSites_file(), _config);
            _rootCrawl.createFirstSourceCodeFile();
        }
        //crawler sitemap "D:\Path" wiki.mta.ro
        else if(args[0].equals("sitemap")){
            GenerateSitemap _sitemap = new GenerateSitemap(args[1], args[2]);
            _sitemap.generateSitemap();
        }
        //crawler --help
        else if(args[0].contains("-help") || args[0].contains("?")){
            File fisier = new File("./src/data/help.txt");
            Scanner myReader = new Scanner(fisier);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
        }
        //crawler list sitemaps extensie
        else if(args[0].equals("list")){
            List extension = new List(args[1],args[2]);
            extension.print();
        }
        //eroare comanda
        else {
            System.out.println("Comanda inexistenta. Verificati scrierea sau consultati meniul \"crawler --help\"");
            throw new IOException("Comanda incorecta!");
        }
    }

    /**
     *Functia parseaza comanda primita de la terminal.
     * @return Lista parsata cu optiunile specificate de user
     */
    private ArrayList<String> parseCommand()
    {
        ArrayList<String> rez = new ArrayList<String>();

        return  rez;
    }



}
