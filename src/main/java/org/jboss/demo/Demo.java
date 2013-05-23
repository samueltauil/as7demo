package org.jboss.demo;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: samueltauil
 * Date: 5/23/13
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Demo {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    buildGui();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });

    }

    private static void buildGui() throws IOException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jFrame = new JFrame("AS 7 Demo");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon red = new ImageIcon("images/red.png");
        ImageIcon green = new ImageIcon("images/green.png");

        JButton jButton1 = new JButton("Instance 1");
        jButton1.setPreferredSize(new Dimension(300, 300));
        if(checkInstanceStatus("server-one")){
            jButton1.setIcon(green);
        } else {
            jButton1.setIcon(red);
        }


        JButton jButton2 = new JButton("Instance 2");
        jButton2.setIcon(red);

        JButton jButton3 = new JButton("Instance 3");
        jButton3.setIcon(red);

        JButton jButton4 = new JButton("Instance 4");
        jButton4.setIcon(red);

        JButton jButton5 = new JButton("Instance 5");
        jButton5.setIcon(red);

        JButton jButton6 = new JButton("Instance 6");
        jButton6.setIcon(red);

        JPanel panel = new JPanel(new GridLayout(0, 3));

        panel.add(jButton1);
        panel.add(jButton2);
        panel.add(jButton3);
        panel.add(jButton4);
        panel.add(jButton5);
        panel.add(jButton6);

        panel.setBorder(BorderFactory.createEmptyBorder(
                30, //top
                30, //left
                30, //bottom
                30) //right
        );

        jFrame.getContentPane().add(panel,  BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static boolean checkInstanceStatus(String instanceName) throws IOException {

        ModelControllerClient client = ModelControllerClient.Factory.create(
                InetAddress.getByName("127.0.0.1"), 9999);
        ModelNode modelNode = new ModelNode();

        ModelNode op = new ModelNode();
        op.get("operation").set("read-attribute");

        ModelNode address = op.get("address");
        address.add("host", "master");
        address.add("server", instanceName);

        op.get("name").set("server-state");


        ModelNode returnVal = client.execute(op);
        String result = returnVal.get("result").toString();

        if(result.contains("running")) return true;

        return false;
    }

}
