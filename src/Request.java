import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Request implements Serializable{
    private String address;
    private String headers;
    private String inHeaders;
    private boolean response;
    private RequestEnum method;
    private boolean followRedirect;
    private boolean save;
    private String output;
    private String data;
    private String status;
    private int statusCode;
    public String time;
    private Map<String, List<String>> outHeaders;
    String res = "";

    public Request(String address , String headers , boolean response , String method , String output , String data,boolean followRedirect)
    {
        this.address = address;
        this.followRedirect = followRedirect;
        this.response = response;
        this.headers = headers;
        this.inHeaders = "";
        inHeaders = headers.replace("\"","");
        data = data.replace("\"","");
        this.data = data;
        for(RequestEnum r : RequestEnum.values())
        {
            if(method.toUpperCase().equals(r.name()))
            {
                this.method = r;
                break;
            }
        }
        this.save=save;
        this.output = output;
    }

    public void sendRequest() {
        try {
            long time = System.currentTimeMillis();
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(followRedirect);
            connection.setRequestMethod(method.name());
            if(!inHeaders.equals(""))
            {
                for(String s : inHeaders.split(";"))
                {
                    connection.setRequestProperty(s.split(":")[0],s.split(":")[1]);
                }
            }
            if(!data.equals(""))
            {

                HashMap<String, String> fooBody = new HashMap<>();
                for(String d : data.split("&"))
                {
                    String key = d.split("=")[0];
                    String value = d.split("=")[1];
                    fooBody.put(key,value);
                }
                String boundary = System.currentTimeMillis() + "";
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
                bufferOutFormData(fooBody, boundary, request);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            }


            //Status
            statusCode = connection.getResponseCode();
            status = connection.getResponseMessage();
            if(statusCode != 200)
            {
                //Time
                long requestTime = System.currentTimeMillis() - time;
                this.time = "" + (requestTime/1000) +":"+(requestTime%1000)+"S ";
                outHeaders = connection.getHeaderFields();
                System.out.println(statusCode + " " + status + " Time: " + this.time);
                if(response)
                {
                    System.out.println("Response Headers: ");
                    outHeaders.forEach((k,v)-> System.out.println(k+" : "+v));
                    System.out.println("________________________");
                }
                return;
            }

            //Get Headers
            outHeaders = connection.getHeaderFields();
            //Response


            if(!output.equals(""))
            {
                File file = new File(output);
                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.close();
            }
            else
            {
                res = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while(true)
                {
                    String temp = bufferedReader.readLine();
                    if(temp == null) break;
                    res += temp;
                }
            }



            //Time
            long requestTime = System.currentTimeMillis() - time;
            this.time = "" + (requestTime/1000) +":"+(requestTime%1000)+"S ";

            //Print Statis
            System.out.println("_____________________Status_____________________");
            System.out.println(statusCode + " " + status + " Time: " + this.time);
            if(response)
            {
                System.out.println("________________Response Headers________________");
                outHeaders.forEach((k,v)-> System.out.println(k+" : "+v));
            }
            if(output.equals(""))
            {
                System.out.println("_____________________Output_____________________");
                System.out.println(res.replace(",",",\n"));
            }
            connection.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Map<String, List<String>> getOutHeaders() {
        return outHeaders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private void bufferOutFormData(HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    @Override
    public String toString() {
        String answer = method.name() + " | url: ";
        answer += address + " | ";
        if(!headers.equals(""))
        {
            answer += "Headers: " + headers + " | ";
        }
        if(!data.equals(""))
        {
            answer += "Data: " + data + " | ";
        }
        if(!output.equals(""))
        {
            answer += "Output file: " + output + " | ";
        }

        return answer;
    }
}
