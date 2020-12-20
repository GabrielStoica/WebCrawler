import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecursiveScan {
    private ArrayList<String> UrlsFound;
    private String UrlSource;
    private String SourcePath = new String();
    private String fileContent=null;
    private String DirName;
    private int Depth;
    private String AbosultePath;
    private static String sitesFileName=null;
    private static ArrayList<String> sourceURLs;
    private static RecursiveScan obj=null;

    public static void init(String sitesFileName) throws IOException
    {
        if(RecursiveScan.sitesFileName==null)
        {
            RecursiveScan.sitesFileName=new String(sitesFileName);
            RecursiveScan.sourceURLs=new ArrayList<String>();
        }
        File sites = new File(sitesFileName);
        if(sites==null) {
            Logger.getInstance().sendDataToLogger(3, "Eroare la deschiderea fisierului initial cu linkuri!(fisier inexsitent)");
            throw new IOException();
        }
        Scanner reader=new Scanner(sites);
        while(reader.hasNext())
        {
            RecursiveScan.sourceURLs.add(reader.nextLine());
        }
    }

    public void start() throws Exception {
        if(this.SourcePath == null || this.Depth<=0) {
            Logger.getInstance().sendDataToLogger(3, "Wrong start parameters!");
            throw new Exception("Wrong start parameters!");
        }
                String FilePath=new String(SourcePath);
                String FileToDownload=RecursiveScan.StripLink(this.UrlSource);
                FileToDownload=RecursiveScan.GetFileName(FileToDownload);
                FileToDownload=RecursiveScan.AddType(FileToDownload);
                FilePath+="\\";
                FilePath+=FileToDownload;
                this.download_webPage(this.UrlSource, FilePath);
                FileParser fp=new FileParser(FilePath);
                fp.SetUrls(0);
                this.SetFileContent(fp.getFileContent());
                this.AddUrlFounded(fp.GetUrls());
                this.DownloadRecursively(fp.getFileContent());
    }

    public static ArrayList<String> getSourceURLs()
    {
        return sourceURLs;
    }

    public void download_webPage(String urlString, String Path) throws IOException {
        URL url = new URL(urlString);
        InputStream in = url.openStream();
        FileOutputStream fos = new FileOutputStream(new File(Path));
        int len = -1;
        byte[] buffer = new byte[1024];
        len = in.read(buffer);
        if (len == -1) {
            Logger.getInstance().sendDataToLogger(3, "Couldn`t download web page!");
            throw new IOException("Couldn't download specified!");
        } else
            fos.write(buffer, 0, len);
        while ((len = in.read(buffer)) > -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        in.close();
        this.AbosultePath=new String(Path);
        //System.out.println("Download complete!");
        Logger.getInstance().sendDataToLogger(1, "Downloading webpage: " + urlString);
    }

    public int CreateDirectory(String Path) {
        File f = new File( Path);
        if (f.exists() && f.isDirectory()) {
            this.SourcePath = f.getAbsolutePath();
            return 1;
        } else {
            boolean created = f.mkdirs();
            if (created) {
                this.SourcePath = f.getAbsolutePath();
                return 1;
            } else
                return -1;
        }
    }

    public void SetUrlSource(String UrlSource) {
        if (this.UrlSource == null) {
            this.UrlSource = new String();
            this.UrlSource = UrlSource;
        }
        this.UrlSource = UrlSource;
    }

    public String getUrlSource() {
        if (UrlSource != null)
            return this.UrlSource;
        else
            return "ISEMPTY";
    }

    public int GetDepth()
    {
        return this.Depth;
    }

    public RecursiveScan(String UrlSource, String SourcePath, int Depth) throws Exception {
        if(UrlSource!=null)
        {
            this.UrlSource=new String(UrlSource);
        }
        else
        {
            //System.out.println("Error in providing Source Url!");
            Logger.getInstance().sendDataToLogger(3, "Error in providing source URL");
            throw new Exception("Error in providing Source Url!");
        }
        if(SourcePath!=null)
        {
            this.SourcePath=new String(SourcePath);
        }
        else
        {
            System.out.println("Error in providing path to download!");
            System.exit(-1);
        }
        this.Depth=Depth;
        this.UrlsFound = new ArrayList<String>();
        this.DirName=new String(RecursiveScan.StripLink(this.UrlSource));
        this.DirName= RecursiveScan.RemoveSlash(this.DirName);
        String[] result=this.DirName.split("\\\\");
        int index=0;
        for(String line: result)
        {
            if((index= this.SourcePath.indexOf(line)) == -1)
                this.SourcePath+= "\\" + line;
        }
        File f = new File( this.SourcePath);
        if (f.exists() == false || f.isDirectory() == false) {
            boolean created = f.mkdirs();
        }
        this.SourcePath=new String(f.getAbsolutePath());
    }

    public void AddUrlFounded(ArrayList<String> NewUrls) {
        this.UrlsFound = NewUrls;
    }

    public String getDirName(String Url) {
        String var1, var2;
        var1 = Url.substring(0, 8);
        var2 = Url.substring(0, 7);
        if (var1.compareToIgnoreCase("https://") != 0 && var2.compareToIgnoreCase("http://") != 0) {
            return "NAN";
        }
        Scanner scn = new Scanner(Url);
        scn.useDelimiter("://");
        String DirName = scn.next();
        DirName = scn.next();
        return DirName;
    }

    public void DownloadRecursively(String FileContent) throws IOException {
        File file;
        int error=0;
        String FilePath=new String();
        for (String link : UrlsFound) {
            error=0;
            boolean result=LinkRepository.Verify(link);
            if(result == false)
                LinkRepository.AddLink(link);
            else
                continue;
            Pattern patern = Pattern.compile((UrlSource + "$"));
            Matcher matcher = patern.matcher(link);
            boolean checker = matcher.find();
            if (checker)
                continue;
            patern = Pattern.compile("^(https:|http:\\/|\\/[a-z]*)+");
            matcher = patern.matcher(link);
            checker = matcher.find();
            if (checker == false)
                continue;
            String FileName = link;
            patern=Pattern.compile("^(\\/[a-zA-Z0-9\\.]+)");
            matcher =patern.matcher(link);
            checker=matcher.find();
            if(checker == false)
                FileName = RecursiveScan.StripLink(FileName);
            else
            {
                link=this.UrlSource + link;
            }
            try {
                //System.out.println("opening connection");
                URL url = new URL(link);
                InputStream in = url.openStream();
                Logger.getInstance().sendDataToLogger(1, "Downloading web page: " + link);
                patern = Pattern.compile("([ro\\/])+$");
                matcher = patern.matcher(FileName);
                checker = matcher.find();
                if (checker) {
                    patern=Pattern.compile("\\/+$");
                    matcher = patern.matcher(FileName);
                    checker = matcher.find();
                    if(checker)
                    {
                        FileName = FileName.substring(0, FileName.length() - 3);
                        FileName += "html";
                    }
                    else
                    {
                        FileName = FileName.substring(0, FileName.length() - 2);
                        FileName += "html";
                    }

                }
                patern = Pattern.compile("\\/[a-z-+!@#$%^&*()A-Z0-9]+$");
                matcher = patern.matcher(FileName);
                checker = matcher.find();
                if (checker) {
                    FileName += ".html";
                }
                FilePath = new String(this.SourcePath);
                //File f = new File(FilePath);
                String[] dirs=FileName.split("/");
                for(String dir: dirs)
                {
                    if(FilePath.indexOf(dir) == -1)
                        FilePath+= "\\" + dir;
                }
                //FilePath += dir;
                if(this.GetDepth()>0) {
                    RecursiveScan recursiveScan=new RecursiveScan(link, this.GetSourcePath(), Depth-1);
                    dirs=FilePath.split("\\\\");
                    FilePath=recursiveScan.GetSourcePath();
                    FilePath+= "\\" + dirs[dirs.length-1];
                    recursiveScan.download_webPage(link, FilePath);
                    FileParser fp=new FileParser(FilePath);
                    fp.SetUrls(0);
                    recursiveScan.AddUrlFounded(fp.GetUrls());
                    recursiveScan.SetFileContent(fp.getFileContent());
                    recursiveScan.DownloadRecursively(fp.getFileContent());
                }
                else {
                    dirs=FilePath.split("\\\\");
                    FilePath=this.GetSourcePath();
                    FilePath+= "\\" + dirs[dirs.length-1];
                    FileOutputStream fos = new FileOutputStream(new File(FilePath));
                    //System.out.println("reading from resource and writing to file...");
                    int length = -1;
                    byte[] buffer = new byte[1024];// buffer for portion of data from connection
                    while ((length = in.read(buffer)) > -1) {
                        fos.write(buffer, 0, length);
                    }
                    fos.close();
                    in.close();
                    if(FilePath.indexOf(".html") != -1)
                        this.ReplacePaths( FilePath, link, 0);
                    //System.out.println("File downloaded " + link);
                    error=1;
                }
            } catch (IOException e) {
                //System.out.println("Error in downloading " + link + " coz " + e);
                Logger.getInstance().sendDataToLogger(3, e.getMessage());
                error=1;
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            finally {
                if(error==1)
                    continue;
                else
                {
                    if(FilePath.indexOf(".html") != -1)
                        this.ReplacePaths( FilePath, link, 0);
                }
            }

        }
        if(this.AbosultePath.indexOf(".html")!= -1)
            this.ReplacePaths(this.AbosultePath, null, 1);
    }

    public void ReplacePaths(String PathtoFile, String link, int Option) {
        if(Option==0) {
            this.fileContent = this.fileContent.replace(link, PathtoFile);
        }
        else
        {
            File f=new File(PathtoFile);
            try {
                FileWriter write = new FileWriter(f);
                write.write(this.fileContent);
                write.close();
            }
            catch(IOException e)
            {
                System.out.println("Eroare la scrierea in fis " + e);
            }
        }

    }

    public String GetSourcePath() {
        if (SourcePath != null)
            return SourcePath;
        else
            return null;
    }

    public static String StripLink(String Url) {
        Pattern patern = Pattern.compile("^(https)+");
        Matcher matcher = patern.matcher(Url);
        boolean checker = matcher.find();
        if (checker) {
            String newLink = Url.substring(8);
            patern=Pattern.compile("(www)");
            matcher= patern.matcher(newLink);
            checker= matcher.find();
            if(checker)
            {
                return newLink.substring(4);
            }
            return newLink;
        }
        patern = Pattern.compile("^(http)+");
        matcher = patern.matcher(Url);
        checker = matcher.find();
        if (checker) {
            String newLink = Url.substring(7);
            patern=Pattern.compile("(www)");
            matcher= patern.matcher(newLink);
            checker= matcher.find();
            if(checker)
            {
                return newLink.substring(4);
            }
            return newLink;
        }
        patern = Pattern.compile("^\\\\[a-zA-Z0-9]+");
        matcher = patern.matcher(Url);
        checker = matcher.find();
        if (checker) {
            String newLink = Url.substring(1);
            patern=Pattern.compile("(www)");
            matcher= patern.matcher(newLink);
            checker= matcher.find();
            if(checker)
            {
                return newLink.substring(4);
            }
            return newLink;
        }
        patern = Pattern.compile("^([a-zA-Z])+");
        matcher = patern.matcher(Url);
        checker = matcher.find();
        if (checker) {
            String newLink = Url.substring(0);
            patern=Pattern.compile("(www)");
            matcher= patern.matcher(newLink);
            checker= matcher.find();
            if(checker)
            {
                return newLink.substring(4);
            }
            return Url;
        }
        patern = Pattern.compile("^/[a-zA-Z0-9]+");
        matcher = patern.matcher(Url);
        checker = matcher.find();
        if (checker) {
            String newLink = Url.substring(1);
            patern=Pattern.compile("(www)");
            matcher= patern.matcher(newLink);
            checker= matcher.find();
            if(checker)
            {
                return newLink.substring(4);
            }
            return newLink;
        }
        return "NAN";
    }

    public void SetSourcePath(String SourcePath) {
        this.SourcePath = SourcePath;
    }

    public static String AddType(String Link)
    {
        Pattern patern = Pattern.compile(("(\\.ro)$"));
        Matcher matcher = patern.matcher(Link);
        boolean checker = matcher.find();
        if (checker)
        {
            String ModifiedLink=Link.substring(0, Link.length()-2);
            ModifiedLink+="html";
            return ModifiedLink;
        }
        else
        {
            patern = Pattern.compile(("(\\.html)$"));
            matcher = patern.matcher(Link);
            checker = matcher.find();
            if(checker)
                return Link;
            else{
                String ModifiedLink= new String(Link + ".html");
                return ModifiedLink;
            }

        }

    }

    public static String GetFileName(String StrippedLink)
    {
        int lastIndex=0;
        if((lastIndex=StrippedLink.lastIndexOf("/")) != -1 && (lastIndex=StrippedLink.lastIndexOf("/"))!= StrippedLink.length()-1)
        {
            StrippedLink=StrippedLink.substring(lastIndex);
            return StrippedLink;
        }
        else
        {
            if((lastIndex = StrippedLink.lastIndexOf("/"))== StrippedLink.length()-1)
            {
                StrippedLink=StrippedLink.substring(0, lastIndex);
                return StrippedLink;
            }
            else
                return StrippedLink;
        }
    }

    public static String RemoveSlash(String Source)
    {
        if(Source.indexOf("/")!= -1)
        {
            Source=Source.replace("/", "\\");
            return Source;
        }
        else
            return Source;
    }

    public void SetFileContent(String Content)
    {
        this.fileContent=new String(Content);
    }

}
