import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class TD_Client1
{
    public static void main(String[] args) throws Exception
    {
        //Initialize socket
        Socket socket = new Socket(InetAddress.getByName("localhost"), 5000);

        byte[] contents = new byte[10000];
        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream("p1.out");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = socket.getInputStream();

        //No of bytes read in one read() call
        int bytesRead = 0;

        while((bytesRead=is.read(contents))!=-1)
            bos.write(contents, 0, bytesRead);

        fos.close();
        //bos.flush();
        socket.close();

        System.out.println("File recieved successfully!");

        //Executing the recieved file
        File exfile = new File("p1.out");
        exfile.setExecutable(true);
        String run_command = "./p1.out";
        System.out.println("enter an integer to search: ");
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        Process p = Runtime.getRuntime().exec(run_command + " " + num);

        BufferedReader out = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line2 = null;
        line2 = out.readLine();
        //System.out.println(line2);


        //Sending the output data to Server
        Socket clientSocket = new Socket("localHost", 5000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        outToServer.writeBytes(line2 + '\n');
        System.out.println("Output is sent to the server... ");
        clientSocket.close();


/*
        String dirPath = "rec_tasks_clnt1";
        File f = new File(dirPath);
        f.mkdir();
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        DataInputStream dis = new DataInputStream(bis);
        int filesCount = dis.readInt();
        File[] files = new File[filesCount];
        for(int i = 0; i < filesCount; i++)
        {
            long fileLength = dis.readLong();
            String fileName = dis.readUTF();
            files[i] = new File(dirPath + "/" + fileName);
            FileOutputStream fos = new FileOutputStream(files[i]);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            for(int j = 0; j < fileLength; j++) bos.write(bis.read());
            bos.close();
        }
        dis.close();
        System.out.println("Files recieved successfully!\n");
        socket.close();
        String line2 = null;
        String line3 = "";
        File folder = new File(dirPath);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                file.setExecutable(true);
                String run_command = "./"+file;
                Process p2 = Runtime.getRuntime().exec(run_command);
                BufferedReader out = new BufferedReader(new InputStreamReader(p2.getInputStream()));
                line2 = out.readLine() + "\tEOL%";
                line3 = line3 + line2;
                System.out.println(line2);    
            }
        }
        //Sending the output data to Server
        Socket clientSocket = new Socket("localHost", 5000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        outToServer.writeBytes(line3 + '\n');
        clientSocket.close();
*/


    }
}