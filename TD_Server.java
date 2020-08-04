import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class Task_Distribution extends Thread {

    Task_Distribution(String ip, int client, String task) {

        try{
            System.out.println("\nWaiting for client " + client + "\n");
            //Initialize Sockets
            ServerSocket ssock = new ServerSocket(5000);
            Socket socket = ssock.accept();
            //The InetAddress specification
            InetAddress IA = InetAddress.getByName(ip);

            //Specify the file
            File file = new File(task);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            //Get socket's output stream
            OutputStream os = socket.getOutputStream();

            //Read File Contents into contents array
            byte[] contents;
            long fileLength = file.length();
            long current = 0;
            long start = System.nanoTime();
    
            while(current != fileLength)
            {
                int size = 10000;
                if(fileLength - current >= size)
                {
                    current += size;
                }
                else
                {
                    size = (int)(fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bis.read(contents, 0, size); 
                os.write(contents);
            }

            os.flush();
            System.out.println("File sent succesfully to Client " + client + " !");

            //File transfer done. Close the socket connection!
            socket.close();
            ssock.close();

/*
            // specifying directory which consists tasks
            String directory = task;
            File[] files = new File(directory).listFiles();
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeInt(files.length);
            for(File file : files)
            {
                long length = file.length();
                dos.writeLong(length);
    
                String name = file.getName();
                dos.writeUTF(name);
    
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
    
                int theByte = 0;
                while((theByte = bis.read()) != -1) bos.write(theByte);
                bis.close();
            }
            dos.close();
            System.out.println("\nFile sent succesfully to Client " + client + " !");
            //File transfer done. Close the socket connection!
            socket.close();
            ssock.close();
*/

            //Receiving the data from Client
            ServerSocket welcomeSocket = new ServerSocket(5000);
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("\nClient " + client + " Connected to receive the output data...");
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            String dataFromClient = inFromClient.readLine();
            System.out.println("Data received from client " + client + ": " + "\n");
            System.out.println(dataFromClient + "\n");
            connectionSocket.close();
            welcomeSocket.close();

/*
            ServerSocket welcomeSocket = new ServerSocket(5000);
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("\nClient" + client + " Connected to receive the output data...");
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            String dataFromClient = inFromClient.readLine();
            System.out.println("Data received from client" + client + " : \n");
            String[] arrOfStr = dataFromClient.split("EOL%");
            
            for(String a : arrOfStr){
                System.out.println(a);
            }
            connectionSocket.close();
*/
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}


public class TD_Server {

    public static void main(String args[]) {
    
        Scanner sc = new Scanner(System.in);

        System.out.println("enter the task to be sent for client1: ");
        String task1 = sc.nextLine();
        System.out.println("enter the task to be sent for client2: ");
        String task2 = sc.nextLine();

        Task_Distribution T1 = new Task_Distribution("10.1.39.89", 1, task1);

        Task_Distribution T2 = new Task_Distribution("10.0.72.56", 2, task2);

    }

}