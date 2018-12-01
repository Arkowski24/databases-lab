package pl.edu.agh.hibernate.example.shopapp.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.agh.hibernate.example.shopapp.model.order.OrderItem;

public class EditItemPresenter {
    private OrderItem orderItem;
    private boolean valid;

    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField unitsInStockTextField;
    @FXML
    private TextField orderedUnitsTextField;
    @FXML
    private Button saveButton;

    private Stage dialogStage;

    public void setData(OrderItem orderItem) {
        this.orderItem = orderItem;
        updateControls();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isValid() {
        return valid;
    }

    @FXML
    private void initialize() {
        orderedUnitsTextField.textProperty().addListener((obj, oldVal, newVal) -> saveButton.setDisable(!isInputValid(newVal)));
    }

    @FXML
    public void handleOkEvent(ActionEvent event) {
        String str = orderedUnitsTextField.getText();
        if (isInputValid(str)) {
            updateModel();
            valid = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleCancelEvent(ActionEvent event) {
        dialogStage.close();
    }

    private boolean isInputValid(String str) {
        Integer orderedUnits = convertToInteger(str);
        int unitsInStock = orderItem.getProduct().getUnitsInStock();
        if (orderedUnits == null || orderedUnits <= 0) {
            return false;
        }

        return orderedUnits <= unitsInStock;
    }

    private void updateControls() {
        this.productNameTextField.setText(orderItem.getProduct().getProductName());
        this.unitsInStockTextField.setText(Integer.toString(orderItem.getProduct().getUnitsInStock()));
        this.orderedUnitsTextField.setText(Integer.toString(orderItem.getOrderedUnits()));
    }

    private void updateModel() {
        orderItem.setOrderedUnits(convertToInteger(orderedUnitsTextField.getText()));
    }

    private Integer convertToInteger(String text) {
        Integer result;
        try {
            result = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            result = null;
        }
        return result;
    }
}
