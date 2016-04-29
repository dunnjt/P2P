//Author: John Madsen
package pkg490final;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class IOFunctions {

    public static void main(String[] args) {
        ArrayList<P2PFile> localFiles = readLocalFiles();
        for (P2PFile file : localFiles) {
            System.out.println(file);
        }

    }

    /**
     * Takes user input from the terminal and builds ArrayList of files in
     * directory specified.
     *
     * @return P2PFile ArrayList localFiles
     */
    public static ArrayList<P2PFile> readLocalFiles() {
        ArrayList<P2PFile> localFiles = new ArrayList();
        P2PFile tempFile;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the path of the files you want to share on the p2p network");

        File folder = new File(input.nextLine());

        File[] files = folder.listFiles();

        for (File file : files) {
            tempFile = new P2PFile(file.getName(), file.length(), PacketUtilities.getPublicIP(), getLocalHostName());
            tempFile.convertSpaces();
            localFiles.add(tempFile);
        }
        return localFiles;
    }

    /**
     * readLocalFiles method which takes in a path and builds an arraylist of
     * files from the directory specified.
     *
     * @param path path of directory
     * @return arraylist of files
     */
    public static ArrayList<P2PFile> readLocalFiles(String path) {
        ArrayList<P2PFile> localFiles = new ArrayList();
        P2PFile tempFile;
        File folder = new File(path);

        File[] files = folder.listFiles();

        for (File file : files) {
            tempFile = new P2PFile(file.getName(), file.length(), PacketUtilities.getPublicIP(), getLocalHostName());
            tempFile.convertSpaces();
            localFiles.add(tempFile);
        }
        return localFiles;

    }

    public static String getLocalHostName() {
        String hostName = null;
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostName = localHost.getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hostName;

    }

    /**
     * Converts spaces in file names to |. Needed to help delineate header lines
     * from packet body when sending and receiving packets.
     *
     * @param originalName
     * @return converted String
     */
    static String convertSpaces(String originalName) { // space is replace by "|" char
        return originalName.replace(" ", "|");
    }

    /**
     * Converts file names that have been sent as packets back to include spaces
     * for easy reading in GUI.
     *
     * @param originalName
     * @return converted String
     */
    static String convertBack(String originalName) { // space is replace by "|" char
        return originalName.replace("|", " ");
    }
}
