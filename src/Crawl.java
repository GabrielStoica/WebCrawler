import java.io.*;
import java.net.URL;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * Creeaza structura arborescenta plecand de la URL-urile din fisierul de intrare
 * Primeste ca parametru de initializare numele fisierului in care sunt scrise
 * pe linii separate URL-urile ce vor fi reprezenta root-ul crawler-ului
 * <p>
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
     * @param config Obiect de tip Configuration in care s-a facut initializarea variabilelor de configurare
     * @throws FileNotFoundException Eroare returnata atunci cand fisierul nu exista
     */
    public Crawl(String sitesFilename, Configuration config) throws FileNotFoundException {
        this._sitesFilename = sitesFilename;
        this._config = config;
    }

    /**
     * Creeaza structura arborescenta, stocand paginile HTML
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
        String resource = null;

        int numberOfURLs = 0;
        if(scan.hasNextLine())
            System.out.println("Downloading resources...");
        while (scan.hasNextLine()) {
            numberOfURLs++;
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
                resource = "sitemaps/" + rootDirectory + "/index.html";
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
                resource = "sitemaps/" + rootDirectory + "/" + lastSourceCodeFilename + ".html";

            }


            BufferedReader htmlDocument = downloadResource(_siteURL);
            if(SizeLimiter.isWithinSizeLimit(_config.getMax_size(), _siteURL, true)) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(resource));
                String line;
                while ((line = htmlDocument.readLine()) != null) {
                    writer.write(line);
                }
                //astept delay time pentru a descarca urmatoarea resursa
                sleep(_config.getDelay());
            }
        }

        if (numberOfURLs == 0) {
            Logger.getInstance().sendDataToLogger(2,"Input sites.txt file is empty! No resource will be downloaded!");
            throw new Exception("Fisierul de intrare cu URL-uri este gol!");
        }
    }

    /**
     * Functie responsabila cu descarcarea continutului de la adredsa urlString
     *
     * @param urlString URL-ul catre adresa resursei ce urmeaza a fi descarcata
     * @return returneaza un obiect de tipul BufferedReader, reprezentand continutul resursei
     * @throws IOException Exceptie atunci resursa respectiva nu exista la adresa urlString
     */
    
    public BufferedReader downloadResource(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            Logger.getInstance().sendDataToLogger(3,"Page: " + urlString + " not found (404)");
            e.printStackTrace();
        }
        Logger.getInstance().sendDataToLogger(1,"Resource downloading from: " + urlString);
        return reader;
    }
}


