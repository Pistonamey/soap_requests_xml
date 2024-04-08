import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Class definition for sending a SOAP request.
public class SimpleSOAPRequest {
    // Main method, entry point of the program.
    public static void main(String[] args) {
        try {
            // Define the URL of the SOAP web service. This should be replaced with the URL of the target web service.
            String url = "http://www.dneonline.com/calculator.asmx";
            
            // Create a URL object with the web service URL.
            URL obj = new URL(url);
            // Open a connection to the URL.
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            
            // Set the request method to POST, as required for SOAP requests.
            con.setRequestMethod("POST");
            // Set the Content-Type header to "text/xml; charset=utf-8", indicating this is a SOAP request.
            con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            // Set the SOAPAction header. This is often required by SOAP web services to determine the action to be performed.
            con.setRequestProperty("SOAPAction", "http://tempuri.org/Add");

            // Define the XML payload for the SOAP request. This example performs an addition operation.
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                       + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                       + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                       + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                       + "<soap:Body>"
                       + "<Add xmlns=\"http://tempuri.org/\">"
                       + "<intA>5</intA>"
                       + "<intB>3</intB>"
                       + "</Add>"
                       + "</soap:Body>"
                       + "</soap:Envelope>";
            
            // Enable output for the connection to send the SOAP message.
            con.setDoOutput(true);
            // Create a stream to write the XML payload to the connection.
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            // Write the XML payload as bytes to the request body.
            wr.writeBytes(xml);
            // Flush the stream to ensure all data is sent.
            wr.flush();
            // Close the stream.
            wr.close();

            // Get the response status message to check if the request was successful.
            String responseStatus = con.getResponseMessage();
            System.out.println("Response Status: " + responseStatus);
            
            // Create a BufferedReader to read the response from the web service.
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            // Use a StringBuffer to collect the response lines.
            StringBuffer response = new StringBuffer();
            
            // Read each line of the response until there are no more.
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            // Close the BufferedReader.
            in.close();
            
            // Print the entire response to the console.
            System.out.println("Response: " + response.toString());
        } catch (Exception e) {
            // Print any exceptions that occur during the process.
            System.out.println(e);
        }
    }
}
