import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class Scraber {
    Document doc;
    FileOutputStream fos;
    OutputStreamWriter osw;
    String path = "D:\\Abdonasef Work 2\\FCI-CU\\Java projects\\News Summerizer\\scrabedNews\\";
    String newsTitle = "";
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public void doScrabeSkyNews(String NewsLink) throws Exception{
        newsTitle = getTitle(NewsLink);
        fos = new FileOutputStream(path + newsTitle + ".txt");
        osw = new OutputStreamWriter(fos, "UTF-8");
        
        try{
            doc = Jsoup.connect(NewsLink).get();
            
            String data = " article-body";
            Elements productElements = doc.getElementsByClass(data);
            osw.write((String)productElements.text());
        }catch (IOException e) {
            System.out.println("Not Working!! ==>" + NewsLink);
        }
        osw.flush();
        osw.close();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public String getTitle(String NewsLink){
        String result = "";
        String title = "";
        
        title = NewsLink.substring(NewsLink.lastIndexOf("/") + 1);
        result = title.substring(title.indexOf('-'));
        
        return result.replace('-', ' ');
    }
    
}
