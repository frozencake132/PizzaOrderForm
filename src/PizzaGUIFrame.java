import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private JTextArea orderTextArea;
    private JComboBox<String> sizeComboBox;
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JCheckBox topping1, topping2, topping3, topping4, topping5, topping6;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);

        // Create the panels for crust, size, and toppings
        JPanel crustPanel = createCrustPanel();
        JPanel sizePanel = createSizePanel();
        JPanel toppingsPanel = createToppingsPanel();

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add action listeners
        orderButton.addActionListener(new OrderButtonListener());
        clearButton.addActionListener(new ClearButtonListener());
        quitButton.addActionListener(new QuitButtonListener());

        // Layout setup
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(crustPanel);
        topPanel.add(sizePanel);
        topPanel.add(toppingsPanel);

        // Adding everything to the frame
        add(topPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createCrustPanel() {
        // Create radio buttons for crust type
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");

        // Group the buttons so only one can be selected at a time
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);

        // Panel for crust options
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        return crustPanel;
    }

    private JPanel createSizePanel() {
        // Create combo box for size selection
        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);

        // Panel for size selection
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Pizza Size"));
        sizePanel.add(sizeComboBox);

        return sizePanel;
    }

    private JPanel createToppingsPanel() {
        // Create checkboxes for toppings
        topping1 = new JCheckBox("Pepperoni");
        topping2 = new JCheckBox("Mushrooms");
        topping3 = new JCheckBox("Olives");
        topping4 = new JCheckBox("Pineapple");
        topping5 = new JCheckBox("Sausage");
        topping6 = new JCheckBox("Bacon");

        // Panel for toppings
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        toppingsPanel.add(topping1);
        toppingsPanel.add(topping2);
        toppingsPanel.add(topping3);
        toppingsPanel.add(topping4);
        toppingsPanel.add(topping5);
        toppingsPanel.add(topping6);

        return toppingsPanel;
    }

    // Action Listener for the Order button
    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double baseCost = 0;
            String crust = "";
            String size = (String) sizeComboBox.getSelectedItem();
            double sizeCost = 0;

            if (thinCrust.isSelected()) {
                crust = "Thin";
                sizeCost = 8.00;
            } else if (regularCrust.isSelected()) {
                crust = "Regular";
                sizeCost = 12.00;
            } else if (deepDishCrust.isSelected()) {
                crust = "Deep-dish";
                sizeCost = 16.00;
            }

            // Price based on size
            switch (size) {
                case "Small": sizeCost = 8.00; break;
                case "Medium": sizeCost = 12.00; break;
                case "Large": sizeCost = 16.00; break;
                case "Super": sizeCost = 20.00; break;
            }

            // Toppings cost
            double toppingsCost = 0;
            StringBuilder toppings = new StringBuilder();
            if (topping1.isSelected()) {
                toppings.append("Pepperoni, ");
                toppingsCost += 1.00;
            }
            if (topping2.isSelected()) {
                toppings.append("Mushrooms, ");
                toppingsCost += 1.00;
            }
            if (topping3.isSelected()) {
                toppings.append("Olives, ");
                toppingsCost += 1.00;
            }
            if (topping4.isSelected()) {
                toppings.append("Pineapple, ");
                toppingsCost += 1.00;
            }
            if (topping5.isSelected()) {
                toppings.append("Sausage, ");
                toppingsCost += 1.00;
            }
            if (topping6.isSelected()) {
                toppings.append("Bacon, ");
                toppingsCost += 1.00;
            }

            // Clean up trailing comma
            if (toppings.length() > 0) {
                toppings.setLength(toppings.length() - 2);
            }

            double subtotal = sizeCost + toppingsCost;
            double tax = subtotal * 0.07;
            double total = subtotal + tax;

            // Display order
            String orderSummary = String.format(
                    "=========================================\n" +
                            "Type of Crust: %s\nSize: %s\nPrice: $%.2f\n" +
                            "Ingredients: %s\nPrice: $%.2f\n" +
                            "-----------------------------------------\n" +
                            "Subtotal: $%.2f\nTax: $%.2f\n" +
                            "-----------------------------------------\n" +
                            "Total: $%.2f\n" +
                            "=========================================",
                    crust, size, sizeCost, toppings, toppingsCost, subtotal, tax, total
            );

            orderTextArea.setText(orderSummary);
        }
    }

    // Action Listener for the Clear button
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sizeComboBox.setSelectedIndex(0);
            thinCrust.setSelected(false);
            regularCrust.setSelected(false);
            deepDishCrust.setSelected(false);
            topping1.setSelected(false);
            topping2.setSelected(false);
            topping3.setSelected(false);
            topping4.setSelected(false);
            topping5.setSelected(false);
            topping6.setSelected(false);
            orderTextArea.setText("");
        }
    }

    // Action Listener for the Quit button
    private class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setVisible(true);
        });
    }
}
