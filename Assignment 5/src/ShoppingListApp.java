import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ShoppingListApp extends Application {
    private static final short WIDTH = 740;
    private static final short HEIGHT = 390;
    private static final byte padding = 10;


    private String[] labelText = {"Products", "Shopping Cart", "Contents"};
    private String[] buttonsText = {"Buy", "Return", "Checkout"};

    private Label label[] = new Label[labelText.length];
    private ListView listView[] = new ListView[labelText.length];
    private Button button[] = new Button[buttonsText.length];

    private ObservableList<Carryable> ProductsList = FXCollections.observableArrayList();
    private ObservableList<String> CartList = FXCollections.observableArrayList();

    private Shopper shoppingCart = new Shopper();
    private TextField totalPrice = new TextField(String.format("$%,1.2f", shoppingCart.computeTotalCost()));

    GroceryItem[] products = {
            new FreezerItem("Smart-Ones Frozen Entrees", 1.99f, 0.311f),
            new GroceryItem("SnackPack Pudding", 0.99f, 0.396f),
            new FreezerItem("Breyers Chocolate Icecream",2.99f,2.27f),
            new GroceryItem("Nabob Coffee", 3.99f, 0.326f),
            new GroceryItem("Gold Seal Salmon", 1.99f, 0.213f),
            new GroceryItem("Ocean Spray Cranberry Cocktail",2.99f,2.26f),
            new GroceryItem("Heinz Beans Original", 0.79f, 0.477f),
            new RefrigeratorItem("Lean Ground Beef", 4.94f, 0.75f),
            new FreezerItem("5-Alive Frozen Juice",0.75f,0.426f),
            new GroceryItem("Coca-Cola 12-pack", 3.49f, 5.112f),
            new GroceryItem("Toilet Paper - 48 pack", 40.96f, 10.89f),
            new RefrigeratorItem("2L Sealtest Milk",2.99f,2.06f),
            new RefrigeratorItem("Extra-Large Eggs",1.79f,0.77f),
            new RefrigeratorItem("Yoplait Yogurt 6-pack",4.74f,1.02f),
            new FreezerItem("Mega-Sized Chocolate Icecream",67.93f,15.03f)};


    public void update() {
        // Adds products into the 1st listView
        for (GroceryItem product : products) ProductsList.add(product);
        listView[0].setItems(ProductsList);

        listView[0].setOnMousePressed(new EventHandler<MouseEvent>() { // Products list view (1st listView)
            @Override
            public void handle(MouseEvent mouseEvent) {
                button[0].setDisable(false);
            }
        });
        listView[1].setOnMousePressed(new EventHandler<MouseEvent>() { // Shopping Cart list view (2nd listView)
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = listView[1].getSelectionModel().getSelectedIndex();

                if (listView[1].getSelectionModel().isSelected(index)) {
                    button[1].setDisable(false);
                    if (button[2].getText().equals("Restart Shopping")) {
                        button[1].setDisable(true); // Ensures 2nd button is disabled (if 3rd button is 'Restart Shopping')
                        button[2].setDisable(false); // Ensures 3rd button is enabled

                        Carryable selectedObject = shoppingCart.getCart()[index]; // Return the selected item.
                        if (selectedObject instanceof GroceryBag) // Shows contents of bag in Contents list view if item is a bag
                            listView[2].setItems(FXCollections.observableArrayList(selectedObject.getContents()));
                        else
                            listView[2].setItems(null); // Shows nothing if the selected item is a regular item
                    }
                } else
                    button[1].setDisable(true);
            }
        });
        button[0].setOnMousePressed(new EventHandler<MouseEvent>() { // If 'Buy' button is pressed
            @Override
            public void handle(MouseEvent mouseEvent) {
                shoppingCart.addItem((Carryable) listView[0].getSelectionModel().getSelectedItem());
                CartList.add(shoppingCart.getCart()[shoppingCart.getNumItems() - 1].getDescription());
                listView[1].setItems(CartList);
                totalPrice.setText(String.format("$%,1.2f", shoppingCart.computeTotalCost()));
                button[2].setDisable(false); // Enables Checkout button
            }
        });
        button[1].setOnMousePressed(new EventHandler<MouseEvent>() { // If 'Return' button is pressed
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = listView[1].getSelectionModel().getSelectedIndex();
                if (index > -1) {
                    shoppingCart.removeItemAtIndex(index); // Removes item from shopping cart
                    CartList.remove(index); // Removes item from cart list (2nd listView)
                    totalPrice.setText(String.format("$%,1.2f", shoppingCart.computeTotalCost()));
                }
                if (listView[1].getSelectionModel().isEmpty()) {
                    button[1].setDisable(true);
                    button[2].setDisable(true);
                }
            }
        });
        button[2].setOnMousePressed(new EventHandler<MouseEvent>() { // If third button is pressed
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < shoppingCart.getNumItems(); i++)
                    CartList.removeAll(shoppingCart.getCart()[i].getDescription()); // Remove all items from the 2nd listView
                if (button[2].getText().equals("Restart Shopping")) {
                    listView[0].setDisable(false); // Re-enables first listView

                    shoppingCart = new Shopper(); // New instance of shopping cart

                    // Disables all buttons
                    button[0].setDisable(true);
                    button[1].setDisable(true);
                    button[2].setDisable(true);

                    // Changes third button's text back to 'Checkout'
                    button[2].setText("Checkout");
                    totalPrice.setText(String.format("$%,1.2f", shoppingCart.computeTotalCost())); // Display total price again ($0.00)
                    listView[2].setItems(null); // Empty contents list (3rd listView)
                } else {
                    shoppingCart.packBags();

                    // Add all descriptions of each object (Carryable) to CarList(2nd listView)
                    for (int i = 0; i < shoppingCart.getNumItems(); i++)
                        CartList.addAll(shoppingCart.getCart()[i].getDescription());
                    listView[1].setItems(CartList);

                    button[0].setDisable(true);
                    button[1].setDisable(true);
                    listView[0].setDisable(true);

                    button[2].setText("Restart Shopping");

                    System.out.println("\n" + shoppingCart.printReceipt());
                }
            }
        });
    }

    public void start(Stage primaryStage) {
        GridPane aPane = new GridPane();
        aPane.setPadding(new Insets(padding, padding, padding, padding));

        for (int i = 0; i < labelText.length; i++) {
            // Adds each Label to its corresponding location.
            label[i] = new Label(labelText[i]);
            label[i].setPrefWidth(200);

            // Adds each ListView to its corresponding location.
            listView[i] = new ListView();
            listView[i].setPrefSize(200, 300);
            GridPane.setMargin(listView[i], new Insets(35, padding, padding, 0));

            // Adds each Button to its corresponding location.
            button[i] = new Button(buttonsText[i]);
            button[i].setPrefSize(200, 25);
            GridPane.setMargin(button[i], new Insets(0, padding, 0, 0));

            if (i == (labelText.length - 1)) {
                // Padding-right of last ListView changed to 0 instead of 10 pixels
                GridPane.setMargin(listView[i], new Insets(35, 0, padding, 0));
                button[i].setPrefSize(120, 25); // Changes size of Checkout button.
                label[i].setPrefWidth(120); // Changes preferred width of last Label to 120 to ensure layout is correct.
            }

            // Adds the entities on the screen.
            aPane.add(label[i], i, 0);
            aPane.add(button[i], i, 2);

            button[i].setDisable(true); // Disables all the buttons.
        }
        // Adds the ListViews on the screen.
        aPane.add(listView[0], 0, 1, 1, 1);
        aPane.add(listView[1], 1, 1,1, 1);
        aPane.add(listView[2], 2, 1, 3, 1);

        // Total Price Label added
        Label aLabel = new Label("Total Price:");
        aLabel.setPrefSize(65, 25);
        aLabel.setAlignment(Pos.TOP_LEFT); // Aligns label to top left of cell.

        GridPane.setMargin(aLabel, new Insets(0, 0, 0, 5));
        /* Padding-left = 5 since Padding-right for all buttons are 10, therefore padding
         * between Checkout button and Total Price = 15 (pixels) */
        aPane.add(aLabel, 3, 2);

        // Textfield of Total Price added
        totalPrice.setPrefSize(100, 25);
        totalPrice.setAlignment(Pos.CENTER_RIGHT);
        aPane.add(totalPrice, 4, 2);


        //aPane.setGridLinesVisible(true); // ----- FOR DEBUGGING ----- //

        primaryStage.setTitle("Grocery Store Application");
        primaryStage.setScene(new Scene(aPane, WIDTH, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene(); // Fixes weird padding glitch (has extra spacing on right)
        primaryStage.show();

        update();
    }

    public static void main(String[] args) { launch(args); }
}
