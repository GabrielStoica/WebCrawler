import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Creaza structura arborescenta plecand de la URL-urile din fisierul de intrare
 * Primeste ca parametru de initializare numele fisierului in care sunt scrise
 * pe linii separate URL-urile ce vor fi reprezenta root-ul crawler-ului
 *
 * Exemplu de fisier de intrare:
 * https://mta.ro
 * https://wiki.mta.ro/
 * https://stackoverflow.com/company/about-us
 *
 * @author Stoica Gabriel
 */

public class Crawl {

    private String _sitesFilename;
    Configuration _config;

    /**
     * Constructorul clasei primeste ca parametru numele
     * fisierului in care sunt stocate pe linii separate URL-urile
     *
     * @param sitesFilename Numele fisierului de intrare
     * @param config
     * @throws FileNotFoundException Eroare returnata atunci cand fisierul nu exista
     */
    public Crawl(String sitesFilename, Configuration config) throws FileNotFoundException {
        this._sitesFilename = sitesFilename;
        this._config = config;
    }

    /**
     * Creaza structura arborescenta, stocand paginile HTML
     * in directoarele corespunzatoare, in functie de link-urile URL din
     * fisierul de intrare
     *
     * @throws Exception Eroare returnata atunci cand fisierul de intrare este gol sau nu exista
     */
    public void createFirstSourceCodeFile() throws Exception {

        File sitesFile = new File(_sitesFilename);
        Scanner scan = new Scanner(sitesFile);
        String _siteURL;
        String fullDirectory;

        int numberOfURLs = 0;
        while (scan.hasNextLine()) {
            numberOfURLs ++;
            _siteURL = scan.nextLine();
            fullDirectory = _siteURL.split("//")[1];
            int occurenceOfSlash = 0;

            for (int i = 0; i < fullDirectory.length(); i++) {
                if (fullDirectory.charAt(i) == '/') {
                    occurenceOfSlash++;
                }
            }
            //daca e de forma wiki.mta.ro/ sau wiki.mta.ro
            //descarcam source code-ul in index.html
            String rootDirectory = fullDirectory;
            File directory = new File("sitemaps/" + rootDirectory);
            if ((occurenceOfSlash <= 1 && rootDirectory.indexOf('/') == rootDirectory.length() - 1) || occurenceOfSlash == 0) {
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                Document htmlDocument = Jsoup.connect(_siteURL).get();
                PrintWriter createHtmlDocument = new PrintWriter("sitemaps/" + rootDirectory + "/index.html");
                createHtmlDocument.println(htmlDocument);
            }
            //de forma wiki.mta.ro/company sau wiki.mta.ro/company/
            // sau wiki.mta.ro/company/pagina1 sau wiki.mta.ro/company/pagina1/
            else if (occurenceOfSlash >= 1 && rootDirectory.indexOf('/') != rootDirectory.length() - 1) {

                String[] splitPath = rootDirectory.split("/");
                String lastSourceCodeFilename = splitPath[splitPath.length - 1];

                rootDirectory = rootDirectory.substring(0, rootDirectory.lastIndexOf('/'));
                directory = new File("sitemaps/" + rootDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                //System.out.println(rootDirectory);
                Document htmlDocument = Jsoup.connect(_siteURL).get();
                PrintWriter createHtmlDocument = new PrintWriter("sitemaps/" + rootDirectory + "/" + lastSourceCodeFilename + ".html");
                createHtmlDocument.println(htmlDocument);
            }

        }

        if(numberOfURLs==0){
            throw new Exception("Fisierul de intrare cu URL-uri este gol!");
        }
    }

}
