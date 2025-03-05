import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {

    // Components for the pizza options
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> pizzaSizeComboBox;
    private JCheckBox topping1, topping2, topping3, topping4, topping5, topping6;
    private JTextArea orderTextArea;
    private JButton orderButton, clearButton, quitButton;

    // Prices and constants
    private static final double[] SIZE_COSTS = {8.00, 12.00, 16.00, 20.00}; // Small, Medium, Large, Super
    private static final double TOPPING_COST = 1.00;
    private static final double TAX_RATE = 0.07;

    public PizzaGUIFrame() {
        // Setting up the frame
        setTitle("Pizza Order Form");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panels for grouping options
        JPanel crustPanel = createCrustPanel();
        JPanel sizePanel = createSizePanel();
        JPanel toppingPanel = createToppingPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel orderPanel = createOrderPanel();

        // Adding panels to the frame
        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingPanel, BorderLayout.CENTER);
        add(orderPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    private JPanel createCrustPanel() {
        // Crust Options
        thinCrust = new JRadioButton("Thin Crust");
        regularCrust = new JRadioButton("Regular Crust");
        deepDishCrust = new JRadioButton("Deep-dish Crust");

        // Grouping radio buttons
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);

        // Default selection
        regularCrust.setSelected(true);

        // Panel setup
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
        crustPanel.setLayout(new GridLayout(3, 1));
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        return crustPanel;
    }

    private JPanel createSizePanel() {
        // Pizza Size ComboBox
        String[] sizes = {"Small", "Medium", "Large", "Super"};
        pizzaSizeComboBox = new JComboBox<>(sizes);
        pizzaSizeComboBox.setSelectedIndex(1); // Default to Medium

        // Panel setup
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Pizza Size"));
        sizePanel.add(pizzaSizeComboBox);

        return sizePanel;
    }

    private JPanel createToppingPanel() {
        // Topping Options
        topping1 = new JCheckBox("Cheese");
        topping2 = new JCheckBox("Pepperoni");
        topping3 = new JCheckBox("Mushrooms");
        topping4 = new JCheckBox("Olives");
        topping5 = new JCheckBox("Pineapple");
        topping6 = new JCheckBox("Anchovies");

        // Panel setup
        JPanel toppingPanel = new JPanel();
        toppingPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        toppingPanel.setLayout(new GridLayout(3, 2));
        toppingPanel.add(topping1);
        toppingPanel.add(topping2);
        toppingPanel.add(topping3);
        toppingPanel.add(topping4);
        toppingPanel.add(topping5);
        toppingPanel.add(topping6);

        return toppingPanel;
    }

    private JPanel createOrderPanel() {
        // Order Display Area
        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);

        JPanel orderPanel = new JPanel();
        orderPanel.add(scrollPane);

        return orderPanel;
    }

    private JPanel createButtonPanel() {
        // Buttons
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        // Button Actions
        orderButton.addActionListener(new OrderButtonListener());
        clearButton.addActionListener(new ClearButtonListener());
        quitButton.addActionListener(new QuitButtonListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        return buttonPanel;
    }

    // Listener for Order button
    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get selected crust
            String crust = getSelectedCrust();

            // Get selected size and price
            String size = (String) pizzaSizeComboBox.getSelectedItem();
            double sizeCost = SIZE_COSTS[pizzaSizeComboBox.getSelectedIndex()];

            // Get selected toppings
            StringBuilder toppings = new StringBuilder();
            double toppingCost = 0;
            if (topping1.isSelected()) {
                toppings.append("Cheese\n");
                toppingCost += TOPPING_COST;
            }
            if (topping2.isSelected()) {
                toppings.append("Pepperoni\n");
                toppingCost += TOPPING_COST;
            }
            if (topping3.isSelected()) {
                toppings.append("Mushrooms\n");
                toppingCost += TOPPING_COST;
            }
            if (topping4.isSelected()) {
                toppings.append("Olives\n");
                toppingCost += TOPPING_COST;
            }
            if (topping5.isSelected()) {
                toppings.append("Pineapple\n");
                toppingCost += TOPPING_COST;
            }
            if (topping6.isSelected()) {
                toppings.append("Anchovies\n");
                toppingCost += TOPPING_COST;
            }

            // Calculate total costs
            double subTotal = sizeCost + toppingCost;
            double tax = subTotal * TAX_RATE;
            double total = subTotal + tax;

            // Generate order details
            String orderDetails = String.format(
                    "=========================================\n" +
                            "Type of Crust: %s\n" +
                            "Size: %s $%.2f\n" +
                            "Ingredient Price:\n%s" +
                            "Sub-total: $%.2f\n" +
                            "Tax: $%.2f\n" +
                            "-----------------------------------------\n" +
                            "Total: $%.2f\n" +
                            "=========================================",
                    crust, size, sizeCost, toppings.toString(), subTotal, tax, total
            );

            orderTextArea.setText(orderDetails);
        }
    }

    // Listener for Clear button
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            thinCrust.setSelected(false);
            regularCrust.setSelected(true);
            deepDishCrust.setSelected(false);
            pizzaSizeComboBox.setSelectedIndex(1);
            topping1.setSelected(false);
            topping2.setSelected(false);
            topping3.setSelected(false);
            topping4.setSelected(false);
            topping5.setSelected(false);
            topping6.setSelected(false);
            orderTextArea.setText("");
        }
    }

    // Listener for Quit button
    private class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(
                    null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION
            );
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    // Helper method to get selected crust type
    private String getSelectedCrust() {
        if (thinCrust.isSelected()) {
            return "Thin Crust";
        } else if (regularCrust.isSelected()) {
            return "Regular Crust";
        } else {
            return "Deep-dish Crust";
        }
    }
}
