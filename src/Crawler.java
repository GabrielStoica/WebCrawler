public class Crawler {
    public static void main(String[] args) throws Exception {

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


    }
}
