import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Solution  {

    public static void writeArrayListToCSV(CSVWriter csvWriter, ArrayList<String> arrayList){
        int size = arrayList.size();
        String [] array = new String[size];

        for(int i = 0; i<size ; i++){
            array[i] = arrayList.get(i);
        }
        // writing the row in the csv
        csvWriter.writeNext(array);
    }

    public static void main(String [] args){
        String output_csv_file = "./CBOE.csv";
        String url = "https://finance.yahoo.com/quote/%5EVIX/history?p=%5EVIX";

        try {
            // Initializing the csv writer
            Writer writer = Files.newBufferedWriter(Paths.get(output_csv_file));
            CSVWriter csvWriter = new CSVWriter(writer);

            // Extracting the html of the specified webpage as a document
            Document doc = Jsoup.connect(url).get();

            //Extracting the table
            Elements table = doc.getElementsByTag("tbody");

            // Extracting all the rows of the specified table
            Elements tableRows = table.first().getElementsByTag("tr");

            // Extracting the table headers
            Elements tableHeaders = doc.getElementsByTag("th");

            ArrayList<String> headersArray = new ArrayList<String>();

            for (Element tableHeader : tableHeaders){
                Elements headerElement = tableHeader.getElementsByTag("span");

                if(!headerElement.isEmpty()){
                    String header = headerElement.html();
                    headersArray.add(header);
                }
                else{
                    headersArray.add("");
                }
            }
            writeArrayListToCSV(csvWriter,headersArray);

            // For each row, we have extract each individual data
            for (Element row:tableRows){
                // Getting all the table data in a row
                // here tableData will have the <td></td> tag
                Elements tableData = row.getElementsByTag("td");
                if(tableData.isEmpty()){
                    continue;
                }

                ArrayList<String> dataArray = new ArrayList<String>();
                for (Element datum:tableData){
                    // from each tableData we have to extract the data-element
                    // data-element will have the <span></span> tag
                    Elements dataElement = datum.getElementsByTag("span");

                    if(!dataElement.isEmpty()){ // checking if empty
                        // extracting the value from <span> </span> tag
                        String data = dataElement.html();
                        dataArray.add(data);
                    }
                    else {
                        dataArray.add("-");
                    }
                }

                // writing the row in the csv
                writeArrayListToCSV(csvWriter,dataArray);
            }

            // closing the csv writer
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
