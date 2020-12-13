import javax.naming.ConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Initializeaza variabilele de configurare necesare pe tot parcursul rularii utilitarului.
 * Primeste ca parametru fisierul de configurare de forma:
 *
 * n_threads=50
 * delay=100
 * root_dir=D:/Download
 * log_level=3
 * depth_level=3
 * max_size=500kB
 * files_type=all
 * sites_file=sites.txt
 *
 * @author Stoica Gabriel
 */

public class Configuration {

    private int n_threads;
    private int delay;
    private String root_dir;
    private int log_level;
    private int depth_level;
    private String max_size;
    private String files_type;
    private String sites_file;

    /**
     * Parcurge fisierul de configurare linie cu linie
     * si stocheaza in variabilele corespunzatoare fiecare
     * valoare a configuratiilor necesare rularii utilitarului
     *
     * @param configFilename Numele fisierului de intrare primit ca argument in Clasa Crawler
     * @throws FileNotFoundException Eroare returnata atunci cand fisierul de configurare nu exista
     * @throws ConfigurationException Eroare returnata atunci cand fisierul de configurare nu are structura corespunzatoare
     */

    public Configuration(String configFilename) throws FileNotFoundException, ConfigurationException {

        File configFile = new File(configFilename);
        Scanner scan = new Scanner(configFile);
        String line_buffer;

        int _linesCount = 0;

        //parcurgere fisier de intrare
        //si verificare linie cu linie pentru a putea
        //stoca valoarea variabilelor de configurare
        while (scan.hasNextLine()) {
            _linesCount++;
            line_buffer = scan.nextLine();
            if (line_buffer.contains("n_threads")) {
                this.n_threads = parseInt(line_buffer.split("=")[1]);
            } else if (line_buffer.contains("delay")) {
                this.delay = parseInt(line_buffer.split("=")[1]);
            } else if (line_buffer.contains("root_dir")) {
                this.root_dir = line_buffer.split("=")[1];
            } else if (line_buffer.contains("log_level")) {
                this.log_level = parseInt(line_buffer.split("=")[1]);
            } else if (line_buffer.contains("depth_level")) {
                this.depth_level = parseInt(line_buffer.split("=")[1]);
            } else if (line_buffer.contains("max_size")) {
                this.max_size = line_buffer.split("=")[1];
            } else if (line_buffer.contains("files_type")) {
                this.files_type = line_buffer.split("=")[1];
            } else if (line_buffer.contains("sites_file")) {
                this.sites_file = line_buffer.split("=")[1];
            }
        }
        //verificare daca fisierul de configurare
        //are exact 8 linii, reprezentand cele 8 variabile de interes
        //in caz contrar, se arunca exceptie
        if(_linesCount != 8){
            throw new ConfigurationException("Numar parametrii fisier de configurare inadecvat!");
        }
    }

    public void printConfigurationSettings(){
        System.out.println("n_threads="+this.n_threads);
        System.out.println("delay="+this.delay);
       // System.out.println(_line);
    }

    public int getN_threads() {
        return n_threads;
    }

    public int getDelay() {
        return delay;
    }

    public String getRoot_dir() {
        return root_dir;
    }

    public int getLog_level() {
        return log_level;
    }

    public int getDepth_level() {
        return depth_level;
    }

    public long getMax_size() {
        return Long.parseLong(max_size);
    }

    public String getFiles_type() {
        return files_type;
    }

    public String getSites_file() {
        return sites_file;
    }
}

