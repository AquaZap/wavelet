import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> strings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Strings added: %s", strings.toString());
        }
        else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                if(strings.size() != 100) {
                    strings.add(parameters[1]);
                    return String.format("Added %s to the list of strings!", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
        else {
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    ArrayList<String> results = new ArrayList<String>();

                    for (int i = 0; i < strings.size(); i++) {
                        if(strings.get(i).contains(parameters[1])) {
                            results.add(strings.get(i));
                        }
                    }

                    if (results.size() == 0) {
                        return "Word cannot be found in any of the Strings";
                    }
                    else {
                        return results.toString();
                    }
                }
            }
            return "404 Not Found!";
        }

    }
}

class SearchEngine {

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
