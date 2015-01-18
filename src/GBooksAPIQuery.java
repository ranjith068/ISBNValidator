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
        private static String query;
        private static ISBN isbn;
        private static String queryType;

        public GBooksAPIQuery(ISBN isbn) {
            this.isbn = isbn;
            this.query = isbn.getISBN();
            this.queryType = "ISBN";
        }

        public void queryAPI() {
            try {
                String url = "https://www.googleapis.com/books/v1/volumes";
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpUriRequest httpuri = RequestBuilder.get()
                        .setUri(new URI(url))
                        .addParameter("q", this.query)
                        .build();
                CloseableHttpResponse response = httpclient.execute(httpuri);
                parseAPIResult(EntityUtils.toString(response.getEntity()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void parseAPIResult(String strResponse) {


            JSONObject obj =new JSONObject(strResponse);
            JSONArray arr = obj.getJSONArray("items");
            int index = 0;

            JSONObject volumeInfo = arr.getJSONObject(index).getJSONObject("volumeInfo");

            JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
            JSONObject ISBN13;
            JSONObject ISBN10;

            if (identifiers.getJSONObject(0).get("type").equals("ISBN_13")) { //if the first result is an ISBN-13
                ISBN13 = identifiers.getJSONObject(0);
                ISBN10 = identifiers.getJSONObject(1);
            }
            else { //otherwise the first is an ISBN-10
                ISBN10 = identifiers.getJSONObject(0);
                ISBN13 = identifiers.getJSONObject(1);
            }

            String strISBN10 = ISBN.format(ISBN10.getString("identifier"));
            String strISBN13 = ISBN.format(ISBN13.getString("identifier"));

            if (queryType.equals("ISBN")) { //if the query is by ISBN, then make sure the ISBNs match
                if (!(isbn.getLen() == 10 && ISBN10.get("identifier").equals(isbn.getISBN())) || (isbn.getLen() == 13 && ISBN13.get("identifier").equals(isbn.getISBN()))) {
                    System.out.println("No match found.");
                    return;
                }
            }

            //if expanded in the future, the code could be rearranged, but since right now it's ISBN only, I pull the ISBN first and compare to make sure I should continue.
            //If the ISBNs don't match, then just end (return) now.

            String title = volumeInfo.getString("title");
            String subtitle = volumeInfo.getString("subtitle");

            JSONArray arrAuthors = volumeInfo.getJSONArray("authors");
            String authors[] = new String[arrAuthors.length()];
            for (int i=0; i<arrAuthors.length(); i++) {
                authors[i] = arrAuthors.getString(i);
            }

            String description = volumeInfo.getString("description");
            int pageCount = volumeInfo.getInt("pageCount");
            double rating = volumeInfo.getDouble("averageRating");
            Book book = new Book(authors, title, subtitle, description, strISBN10, strISBN13, pageCount, rating);
            book.print();
        }
    }
