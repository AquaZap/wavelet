import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    String[] strings = new String[100];

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Strings added: %s", strings.toString());
        }
        else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                int index = 0;
                    
                while (strings[index] != null) {
                    index += 1;
                }

                if (index > 99) {
                    return String.format("No more Strings can be added!");
                }
                else {
                    strings[index] = parameters[1];
                    return String.format("The String %s has been added", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
        else {
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String[] results = new String[100];
                    int index = 0;

                    while (strings[index] != null) {
                        if (strings[index].contains(parameters[1])) {
                            results[index] = parameters[1];
                        }
                        index += 1;
                    }

                    if (results.length == 0) {
                        return String.format("No Strings containing %s were found", parameters[1]);
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
