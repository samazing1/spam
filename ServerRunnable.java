import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerRunnable implements Runnable{

    //define variables and constructors
    protected Socket clientSocket = null;

    public ServerRunnable(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public Socket getSocket() {
        return this.clientSocket;
    }


    public  boolean flag = false;

    public void setFlag(boolean flag){
        this.flag = flag;
    }

    public boolean getFlag(){
        return flag;
    }

    @Override
    public void run() {

        try {
            int stopCounter = 0;
            while (true){

            //get message from client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //create avenue to send message to the client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            //save message gotten from client in the variable
            String val = in.readLine();
                String line[] = val.split(" ");


                        if (line[0].equalsIgnoreCase("send")) {

                            int numb = Integer.parseInt(in.readLine());

                            //get the unique process id
                            int pid = Integer.parseInt(line[1]);

                            //if 0 send to every process
                            if (pid == 0) {
                                sendToAll(SocketServer.processes, line[2], numb);
                            } else {
                                sendTo(SocketServer.processes, pid, line[2], numb);
                            }
                        }
                        else if (line[0].equalsIgnoreCase("stop"))
                        {
                            stopCounter++;
                            //System.out.println("The count now: " + stopCounter);
                            if (stopCounter >= 1)
                            {
                                //socketClose(SocketServer.processes);
                                setFlag(false);
                                SocketServer.pool.shutdown();
                                sendToAll(SocketServer.processes,"Quit",100);

                                //break;
                            }
                        }
            }
                } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean socketClose(ArrayList<ServerRunnable> temp) throws IOException {
       for (int i = 0; i < temp.size(); i++)
        temp.get(i).getSocket().close();


       return true;

    }


    public void sendTo(ArrayList<ServerRunnable> temp, int id, String msg, int pid) throws IOException {

        Socket s = temp.get(id-1).getSocket();
        //create avenue to send message to the client
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        //display message gotten from client
        System.out.println("Passed through server");

        //send message to the client
        out.println("From process " + pid +": "+ msg);
    }

    public void sendToAll(ArrayList<ServerRunnable> s, String msg, int pid) throws IOException {
        int size = SocketServer.processes.size();
        for(int i =1; i <= size; i++)
        {
            if(i == pid)
            {

            }
            else
                sendTo(s, i,msg, pid);

        }
    }
}

