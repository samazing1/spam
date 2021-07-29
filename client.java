import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//Client Process 1.


public class client {
    public static void main(String[] args) throws IOException {

        //define variables
        String localhost = args[0];
        int portNumber = 42210;
        Socket clientSocket;


        clientSocket = new Socket(localhost, portNumber);
        ServerConnection ServerConn = new ServerConnection(clientSocket);
        Scanner type = new Scanner(System.in);

        //start the thread listening for messages
       new Thread(ServerConn).start();


            try {

                while (true) {
                    //ask the user for their command
                    System.out.print("What is your command ? \n" +
                            ">");

                    //save the command
                    String msg = type.nextLine();


                    //create IO stream
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


                        //send message to the server
                        out.println(msg);
                        out.println(args[1]);
                    if(msg.equalsIgnoreCase("stop"))
                    {
                        break;
                    }
                    }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ServerRunnable value = new ServerRunnable(clientSocket);


               while (true){
                   //System.out.println(ServerConn.count);
                if (ServerConn.count >= 4)
                {

                    System.out.println(ServerConn.count + " processes have terminated");
                    System.exit(0);
                }

            }
    }

        }



