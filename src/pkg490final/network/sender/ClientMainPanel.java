package pkg490final.network.sender;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import pkg490final.IOFunctions;
import pkg490final.P2PFile;
import pkg490final.PacketUtilities;
import pkg490final.Packets.Packet;
import pkg490final.Packets.Request.*;
import pkg490final.Packets.RequestLine;
import pkg490final.Packets.Response.ResponseMethod;
import pkg490final.Packets.Response.ResponsePacketSet;
import pkg490final.TCP.P2PClient;
import pkg490final.TCP.P2PHost;
import pkg490final.network.receiver.ClientReceiver;
import pkg490final.network.receiver.ClientReceiverDown;

/**
 *
 * @author John
 */
public class ClientMainPanel extends javax.swing.JPanel {

    ArrayList<P2PFile> p2pFiles;
    String ip;
    int ackPort;
    private static Map<String, Thread> threads = null;
    private int threadCounter = 0;
    P2PClient tcpClient = null;

    /**
     * Creates new form ClientMainPanel
     */
    public ClientMainPanel() {

        ip = PacketUtilities.getLocalIP();
        threads = new HashMap<>();
//        ip = PacketUtilities.getPublicIP();
        initComponents();
    }

    public void updateJTable() {
        DefaultTableModel model = (DefaultTableModel) fileTable.getModel();
        model.setRowCount(0);
        Object rowData[] = new Object[4];
        for (int i = 0; i < p2pFiles.size(); i++) {
            rowData[0] = p2pFiles.get(i).getName();
            rowData[1] = p2pFiles.get(i).getSize();
            rowData[2] = p2pFiles.get(i).getIp();
            rowData[3] = p2pFiles.get(i).getHostName();
            model.addRow(rowData);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        fileTable = new javax.swing.JTable();
        queryButton = new javax.swing.JButton();
        downloadButton = new javax.swing.JButton();
        informButton = new javax.swing.JButton();
        queryTextBox = new javax.swing.JTextField();
        folderTextBox = new javax.swing.JTextField();
        sourcePortTextBox = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        destPortTextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        serverIPTextBox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        disconnectButton = new javax.swing.JButton();
        responseLabel = new javax.swing.JLabel();

        fileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "File Name", "File Size (Bytes)", "Host IP", "Host Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fileTable.setColumnSelectionAllowed(true);
        fileTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(fileTable);
        fileTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (fileTable.getColumnModel().getColumnCount() > 0) {
            fileTable.getColumnModel().getColumn(0).setPreferredWidth(250);
            fileTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            fileTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            fileTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        }

        queryButton.setText("Query For Results");
        queryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                queryButtonMouseClicked(evt);
            }
        });

        downloadButton.setText("Download Selected File");
        downloadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadButtonMouseClicked(evt);
            }
        });

        informButton.setText("Inform And Update Server");
        informButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                informButtonMouseClicked(evt);
            }
        });

        queryTextBox.setText("Query");

        folderTextBox.setText("d:\\p2p");

        sourcePortTextBox.setText("2014");

        jLabel1.setText("Source Port:");

        jLabel2.setText("Destination Port:");

        destPortTextBox.setText("4014");
        destPortTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destPortTextBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("Directory Server IP:");

        serverIPTextBox.setText("192.168.1.111");

        jLabel4.setText("Search:");

        jLabel5.setText("Location of Files:");

        disconnectButton.setText("Disconnect ");
        disconnectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disconnectButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(informButton, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(queryButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(downloadButton)
                            .addComponent(disconnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(queryTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(folderTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(destPortTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(sourcePortTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(serverIPTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(responseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 16, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(responseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(serverIPTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(destPortTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sourcePortTextBox))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(downloadButton)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(queryButton)
                            .addComponent(queryTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(informButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(disconnectButton)
                            .addComponent(folderTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(16, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * When the download button is clicked and file is highlighted: file is sent
     * as a request to the receiver holding the file(ip found from P2P file
     * info. A new TcP client is spawned in order to listen for a tcp connection
     * on port 7014.
     *
     * @param evt
     */
    private void downloadButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadButtonMouseClicked
        if (fileTable.getSelectedRowCount() == 1) {
            int i = fileTable.getSelectedRow();
            ArrayList<P2PFile> files = new ArrayList<>();
            files.add(p2pFiles.get(i));
            DOWNRequestPacketSet downPacketSet = new DOWNRequestPacketSet(files, 7014, ip);
            send(downPacketSet, 3014, p2pFiles.get(i).getIp());
            //this may need to go below after the OK is received from the other peer
            if (tcpClient == null) {
                tcpClient = new P2PClient(Integer.toString(threadCounter++), 7014, p2pFiles.get(i).getName());
                tcpClient.setFileName(p2pFiles.get(i).getName());
                tcpClient.startListening();
                tcpClient.start();

            } else {
                tcpClient.setFileName(p2pFiles.get(i).getName());
            }
        }
    }//GEN-LAST:event_downloadButtonMouseClicked
    /**
     * When the query button is pressed a query is sent to the server to ask
     * which file matches the query. a blank query returns all the files on the
     * server.
     *
     * @param evt
     */
    private void queryButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_queryButtonMouseClicked
        String query = queryTextBox.getText();
        QRYRequestPacketSet qryPacketSet = new QRYRequestPacketSet(query, Integer.parseInt(sourcePortTextBox.getText()), ip);
        send(qryPacketSet);
    }//GEN-LAST:event_queryButtonMouseClicked

    private void informButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_informButtonMouseClicked
        ArrayList<P2PFile> localFiles = IOFunctions.readLocalFiles(folderTextBox.getText());
        INFRequestPacketSet infPacketSet = new INFRequestPacketSet(localFiles, Integer.parseInt(sourcePortTextBox.getText()), ip);
        send(infPacketSet);

        peerReceiver();
        setFileLocationTCP();
    }//GEN-LAST:event_informButtonMouseClicked

    private void disconnectButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disconnectButtonMouseClicked
        EXTRequestPacketSet extPacketSet = new EXTRequestPacketSet(Integer.parseInt(sourcePortTextBox.getText()), ip);
        send(extPacketSet);
    }//GEN-LAST:event_disconnectButtonMouseClicked

    private void destPortTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destPortTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_destPortTextBoxActionPerformed

    /**
     * send method overload for just down request packets.
     *
     * @param packetSet packetSet to be sent (only down request packet passed
     * here)
     * @param destPort port to send packet to, hard-coded to 3014
     * @param destIP IP to send packet to (client)
     */
    private void send(RequestPacketSet packetSet, int destPort, String destIP) {

        RDT30Sender client = new RDT30Sender();

        try {
            client.startSender(destIP, destPort, 7014);
            client.initializeSend(packetSet.getPackets());
        } catch (Exception ex) {
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
//        receive(packetSet, destPort, destIP);
    }

    /**
     * send method for INF, EXIT and QRY request packets.
     *
     * @param packetSet packetSet to be sent.
     */
    private void send(RequestPacketSet ReqPacketSet) {

        RDT30Sender client = new RDT30Sender();

        try {
            client.startSender(serverIPTextBox.getText(), Integer.parseInt(destPortTextBox.getText()), Integer.parseInt(sourcePortTextBox.getText()));
            ReqPacketSet.createPackets();
            client.initializeSend(ReqPacketSet.getPackets());
        } catch (Exception ex) {
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        receive(ReqPacketSet, 5014, serverIPTextBox.getText());
    }

    /**
     * takes in a request packet set with a destination port and destination ip
     * This method listens for a response packet and passes it onto the client
     * response in order to
     *
     * @param ReqPacketSet
     * @param destPort
     * @param destIP
     */
    private void receive(RequestPacketSet ReqPacketSet, int destPort, String destIP) {
        ResponsePacketSet responsePacketSet = null;

        try {
            ClientReceiver rcvr = new ClientReceiver(ReqPacketSet.responseExpected().name(), Integer.parseInt(sourcePortTextBox.getText()), destPort, destIP);
            rcvr.run();
            System.out.println("CLIENT RECEIVER STARTED, EXPECTED RESPONSE: " + ReqPacketSet.responseExpected().name());

            responsePacketSet = (ResponsePacketSet) rcvr.getPacketSet();
            rcvr.stopListening();
            //System.out.println("------------------------------------------RESPONSE METHOD RCVD = " + responsePacketSet.getResponseMethod());

        } catch (SocketException ex) {
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestLine reqLine = (RequestLine) ReqPacketSet.getLine();
        clientResponse(reqLine, responsePacketSet);
    }

    private void clientResponse(RequestLine reqLine, ResponsePacketSet responsePacketSet) {
        responseLabel.setForeground(Color.green);

        if (reqLine.getMethod() == RequestMethod.INF && responsePacketSet.getResponseMethod() == ResponseMethod.OK) {
            String ackStringPort = responsePacketSet.getPacketBody();
            ackPort = Integer.parseInt(ackStringPort);

            responseLabel.setText("Inform and Update Successful " + ackPort);
        } else if (reqLine.getMethod() == RequestMethod.QRY && responsePacketSet.getResponseMethod() == ResponseMethod.LIST) {
            p2pFiles = responsePacketSet.convertToP2PFiles();
            updateJTable();
            responseLabel.setText("Query Successful, choose a file to download");

        } else if (reqLine.getMethod() == RequestMethod.DOWN && responsePacketSet.getResponseMethod() == ResponseMethod.OK) {
            responseLabel.setText("Download Request Successful");
        } else if (reqLine.getMethod() == RequestMethod.EXT && responsePacketSet.getResponseMethod() == ResponseMethod.OK) {
            responseLabel.setText("Successfully Disconnected From Server");
        } else {
            responseLabel.setForeground(Color.red);
            responseLabel.setText("Error Occured, please try again");
        }
    }

    private void peerReceiver() {
        try {
            ClientReceiverDown rcvr = new ClientReceiverDown("peer2", 3014);
            rcvr.start();
        } catch (SocketException se) {

        }

    }

    public void setFileLocationTCP() {
        P2PClient.setFileLocation(folderTextBox.getText());
        P2PHost.setFileLocation(folderTextBox.getText());
    }

//    private void peerReceiver() {
//
//        byte[] receive = new byte[128];
//        try {
//            DatagramSocket socket = new DatagramSocket(6014);
//            while (true) {
//                ClientReceiverDown receiver;
//                DatagramPacket inboundPacket = new DatagramPacket(receive, receive.length);
//                socket.receive(inboundPacket);
//
//                byte[] packetData = Arrays.copyOf(inboundPacket.getData(), inboundPacket.getLength());
//                String temp = new String(packetData);
//                System.out.println(temp);
//                Packet reconstructedPacket = new Packet(temp);
//
//                RequestLine reqLine = (RequestLine) reconstructedPacket.getLine();
//
//                if (runningThread(reqLine.getHostName())) {
//                    receiver = (ClientReceiverDown) threads.get(reqLine.getHostName());
//                } else {
//                    receiver = new ClientReceiverDown(reqLine.getHostName(), reqLine.getSourcePort());
//                    threads.put(reqLine.getHostName(), receiver);
//                    //threadNames.add(reqLine.getHostName());
//                    receiver.start();
//                }
//                receiver.incoming(reconstructedPacket);
//            }
//        } catch (SocketException e) {
//            System.out.println("CAUGHT EXCEPTION ON ClIENT LISTENER");
//        } catch (IOException ex) {
//            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Peer-to-Peer File Sharing Application");
        frame.add(new ClientMainPanel(), BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField destPortTextBox;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JButton downloadButton;
    private javax.swing.JTable fileTable;
    private javax.swing.JTextField folderTextBox;
    private javax.swing.JButton informButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton queryButton;
    private javax.swing.JTextField queryTextBox;
    private javax.swing.JLabel responseLabel;
    private javax.swing.JTextField serverIPTextBox;
    private javax.swing.JTextField sourcePortTextBox;
    // End of variables declaration//GEN-END:variables
}
