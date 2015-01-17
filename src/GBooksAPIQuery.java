    import org.apache.http.client.methods.CloseableHttpResponse;
    import org.apache.http.client.methods.HttpUriRequest;
    import org.apache.http.client.methods.RequestBuilder;
    import org.apache.http.impl.client.CloseableHttpClient;
    import org.apache.http.impl.client.HttpClients;
    import org.apache.http.util.EntityUtils;
    import org.json.JSONArray;
    import org.json.JSONObject;

    import java.net.URI;

    /**
     * Created by Sean on 1/17/2015.
     */
    public class GBooksAPIQuery {

        public static void queryAPI(String query) {
            try {
                String url = "https://www.googleapis.com/books/v1/volumes";
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpUriRequest httpuri = RequestBuilder.get()
                        .setUri(new URI(url))
                        .addParameter("q", query)
                        .build();
                CloseableHttpResponse response = httpclient.execute(httpuri);
                parseAPIResult(EntityUtils.toString(response.getEntity()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void parseAPIResult(String strResponse) {
            JSONObject obj =new JSONObject(strResponse);
            JSONArray arr = obj.getJSONArray("items");
            int index = 0;

            JSONObject volumeInfo = arr.getJSONObject(index).getJSONObject("volumeInfo");
            String title = volumeInfo.getString("title");
            String subTitle = volumeInfo.getString("subtitle");

            JSONArray arrAuthors = volumeInfo.getJSONArray("authors");
            String authors[] = new String[arrAuthors.length()];
            for (int i=0; i<arrAuthors.length(); i++) {
                authors[i] = arrAuthors.getString(i);
            }
            printAPIResult(title, subTitle, authors);
        }

        public static void printAPIResult(String title, String subTitle, String authors[]) {
            System.out.printf("%s - %s by ", title, subTitle);
            for (int i=0; i<authors.length; i++) {
                System.out.printf("%s", authors[i]);
                if (i<authors.length-1)
                    System.out.print(", ");
                else
                    System.out.print(" ");
            }
        }
    }
