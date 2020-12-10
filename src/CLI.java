import java.io.File;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Clasa Singleton care implementeaza metodele necesare comunicatiei dintre user si tool prin linia de comanda .
 * @author VertUnix
 * @version 0.2
 */

public class CLI {

    /* instanta unica a clasei Singleton*/
    private static CLI single_instance = null;

    /**
     *Constructor
     */
    private CLI(){

    }

    /**
     * Returneaza instnta privata unica a CLI-ului
     * @return single_instance
     */
    public static  CLI getInstance()
    {
        if(single_instance == null)
            single_instance = new CLI();

        return single_instance;
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
        if (args[0].equals("-c") || args[0].equals("crawl")) {
            Configuration _config = new Configuration(args[1]);
            //_config.printConfigurationSettings();

            Crawl _rootCrawl = new Crawl(_config.getSites_file(), _config);
            _rootCrawl.createFirstSourceCodeFile();
        }
        //crawler sitemap "D:\Path" wiki.mta.ro
        else if(args[0].equals("-s") || args[0].contains("sitemap")){
            GenerateSitemap _sitemap = new GenerateSitemap(args[1], args[2]);
            _sitemap.generateSitemap();
        }
        //crawler --help
        else if(args[0].contains("-help") || args[0].contains("?")){
            Path path = Paths.get("./src/data/help.txt");
            java.util.List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
            list.forEach(System.out::println);

           /* Scanner myReader = new Scanner(fisier);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }*/
        }
        //crawler list sitemaps extensie
        else if(args[0].equals("-l") || args[0].contains("list")){
            List extension = new List(args[1],args[2]);
            extension.print();
        }
        //crawler search sitemaps index
        else if(args[0].equals("-e") || args[0].contains("search")){
            Search search = new Search(args[1],args[2]);
            search.print();
        }
        //eroare comanda
        else {
            System.out.println("Comanda inexistenta. Verificati scrierea sau consultati meniul \"crawler --help\"");
            throw new IOException("Comanda incorecta!");
        }
    }



}
