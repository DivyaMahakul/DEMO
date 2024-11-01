import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jade.core.Runtime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class CalculatingAgent extends Agent {
    private final Map<String, List<String>> defUsePairs = new HashMap<>();

    protected void setup() {
        System.out.println("Hallo! Calculator-agent " + getAID().getName() + " is ready.");

        AID aid = getAID();
        addDefUsePair("aid", "setup", "defined");

        if (aid != null) {
            addDefUsePair("aid", "setup", "used in if condition");

            String name = aid.getName();
            addDefUsePair("name", "setup", "defined");
            addDefUsePair("aid", "setup", "used to get name");

            System.out.println("Hallo! Calculator-agent " + name + " is ready.");
        } else {
            addDefUsePair("aid", "setup", "used in else condition");

            System.out.println("CalculatingAgent.getAID() is null");
        }

        printDefUsePairs();

        CalculatorGui myGui = new CalculatorGui(this);
        myGui.showGui();
    }

    public void calculateSquare(final int n1) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                double answer = Math.pow(n1, 2);
                addDefUsePair("n1", "calculateSquare", "defined");
                addDefUsePair("answer", "calculateSquare", "defined");
                System.out.println("Square of " + n1 + " is " + answer);
                printDefUsePairs();
            }
        });
    }

    public void calculateCube(final int n2) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                double answer = Math.pow(n2, 3);
                addDefUsePair("n2", "calculateCube", "defined");
                addDefUsePair("answer", "calculateCube", "defined");
                System.out.println("Cube of " + n2 + " is " + answer);
                printDefUsePairs();
            }
        });
    }

    public void calculateLog(final double n2) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                double answer = Math.log10(n2);
                addDefUsePair("n2", "calculateLog", "defined");
                addDefUsePair("answer", "calculateLog", "defined");
                System.out.println("Log base 10 of " + n2 + " is " + answer);
                printDefUsePairs();
            }
        });
    }

    private void addDefUsePair(String variable, String defLocation, String useLocation) {
        defUsePairs.computeIfAbsent(variable, k -> new ArrayList<>()).add("Def in " + defLocation + " used in " + useLocation);
    }

    public void printDefUsePairs() {
        System.out.println("Def-Use Pairs:");
        for (Map.Entry<String, List<String>> entry : defUsePairs.entrySet()) {
            for (String pair : entry.getValue()) {
                System.out.println("Variable: " + entry.getKey() + ", " + pair);
            }
        }
    }

    public static void main(String[] args) {
        jade.core.Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        AgentContainer container = rt.createMainContainer(p);

        try {
            AgentController agent = container.createNewAgent("CalculatorAgent", CalculatingAgent.class.getName(), new Object[0]);
            agent.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}

class CalculatorGui extends JFrame {
    private final CalculatingAgent myAgent;
    private final JTextField numberField;

    CalculatorGui(CalculatingAgent a) {
        super(a.getLocalName());
        myAgent = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 1));
        p.add(new JLabel("Enter number:"));
        numberField = new JTextField(15);
        p.add(numberField);
        getContentPane().add(p, BorderLayout.CENTER);

        JButton addButton1 = new JButton("Square");
        JButton addButton2 = new JButton("Cube");
        JButton addButton3 = new JButton("Log(x)");
        p = new JPanel();
        p.add(addButton1);
        p.add(addButton2);
        p.add(addButton3);
        getContentPane().add(p, BorderLayout.SOUTH);

        addButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String number = numberField.getText().trim();
                    int n1 = Integer.parseInt(number);
                    myAgent.calculateSquare(n1);
                    numberField.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(CalculatorGui.this, "Invalid value. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String number = numberField.getText().trim();
                    int n2 = Integer.parseInt(number);
                    myAgent.calculateCube(n2);
                    numberField.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(CalculatorGui.this, "Invalid value. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String number = numberField.getText().trim();
                    double n2 = Double.parseDouble(number);
                    myAgent.calculateLog(n2);
                    numberField.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(CalculatorGui.this, "Invalid value. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pack();
    }

    public void showGui() {
        setVisible(true);
    }
}