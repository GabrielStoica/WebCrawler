import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
    private ArrayList<String> FileLines;
    private String FileContent;
    private ArrayList<String> URLs;
    private int NumOfUrls;
    public FileParser(String Path) throws IOException {
        FileLines=new ArrayList<String>();
        FileContent=new String();
        URLs=new ArrayList<String>();
        File file=new File(Path);
        Scanner reader=new Scanner(file);
        String line=null;
        while(reader.hasNext())
        {
            line=reader.nextLine();
            FileLines.add(line);
            FileContent+=line;
        }
    }
    public void SetUrls(int restrict)
    {
        if(restrict==0)
            restrict= Integer.MAX_VALUE;
        int st_index, last_index=0;
        for(String line: FileLines)
        {
            while(true) {
                Pattern patern = Pattern.compile("(<a)+[a-zA-Z0-9 =!@#$%^&*()_\\-=+:;\\.\"\\/>]*(href=)+");
                Matcher matcher = patern.matcher(line);
                boolean checker = matcher.find();
                if (checker) {
                    st_index = line.indexOf("<a");
                    if (st_index == -1)
                        break;
                    line = line.substring(st_index);
                    st_index = line.indexOf("href=");
                    if(st_index == -1)
                        break;
                    st_index+=6;
                    line=line.substring(st_index);
                    last_index = line.indexOf("\"");
                    if (last_index == -1)
                        break;
                    URLs.add(line.substring(0, last_index));
                    NumOfUrls++;
                    if (NumOfUrls >= restrict) {
                        return;
                    }
                } else
                    break;
            }
        }
    }
    public ArrayList<String> GetUrls()
    {
        return URLs;
    }

    public void showUrls()
    {
        for(String url: URLs)
        {
            System.out.println(url);
        }
    }

    public String getFileContent()
    {
        return FileContent;
    }

}
