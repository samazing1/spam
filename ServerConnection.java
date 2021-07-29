import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.exit;


public class ServerConnection implements Runnable {
    public Socket server;
    BufferedReader in;
    PrintWriter out;
    public volatile int count = 0;


    public ServerConnection(Socket s) throws IOException {
        this.server = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        //create avenue to send message to the client
        out = new PrintWriter(s.getOutputStream(), true);
    }


    String val = "";

    @Override
    public void run() {


        ServerRunnable value = new ServerRunnable(server);

        //output the message gotten from the server
        try {
            while (true) {

                val = in.readLine();
                if (val == null)
                {
                    count=5;
                    System.exit(0);

                }

                String line[] =val.split(" ");
                if(line[3].equalsIgnoreCase("Quit"))
                {
                    count++;

                    if(count >=4)
                    {
                        break;
                    }

                }
                else {
                    System.out.println(val);
                }




            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

