import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SEHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> input = new ArrayList<>();
    ArrayList<String> output = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("current list: %s", input);
        } else if (url.getPath().equals("/add")) {
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                input.add(parameters[1]);
                return String.format("%s added to the list", parameters[1]);
            }
            return "404 Not Found!";
        } else if (url.getPath().equals("/search")) {
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for (int i = 0; i < input.size(); i++) {
                    if (input.get(i).contains(parameters[1])) {
                        output.add(input.get(i));
                    }
                }
                return String.format("Result: %s", output);
            }
            return "404 Not Found!";
        } else {
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SEHandler());
    }
}
