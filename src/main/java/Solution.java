import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Solution  {

    public static void main(String [] args){
        try {
            // Extracting the html of the specified webpage as a document
            Document doc = Jsoup.connect("https://finance.yahoo.com/quote/%5EVIX/history?p=%5EVIX").get();

            // Extracting all the rows of the specified table
            Elements tableRows = doc.getElementsByTag("tr");

            // Extracting the table headers
            Elements tableHeaders = tableRows.first().getElementsByTag("th");
            System.out.println("Table Headers");
            for (Element tableHeader : tableHeaders){
                Elements headerElement = tableHeader.getElementsByTag("span");

                if(!headerElement.isEmpty()){
                    String header = headerElement.html();
                    System.out.println(header);
                }
                else{
                    System.out.println("null");
                }
            }

            // For each row, we have extract each individual data
            int i = 0;  // for indexing the rows
            for (Element row:tableRows){
                // Getting all the table data in a row
                // here tableData will have the <td></td> tag
                Elements tableData = row.getElementsByTag("td");
                if(tableData.isEmpty()){
                    continue;
                }

                System.out.println("row"+i);

                for (Element datum:tableData){
                    // from each tableData we have to extract the data-element
                    // data-element will have the <span></span> tag
                    Elements dataElement = datum.getElementsByTag("span");

                    if(!dataElement.isEmpty()){ // checking if empty
                        // extracting the value from <span> </span> tag
                        String data = dataElement.html();
                        System.out.println(data);
                    }
                    else {
                        System.out.println("null");
                    }
                }
                i+=1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
