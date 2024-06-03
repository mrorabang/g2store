package com.mycompany.testg2store;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private ImageView imgLogin;
    @FXML
    private TextField textUser;
    @FXML
    private PasswordField textPassword;
    @FXML
    private TextField textField;
    @FXML
    private CheckBox checkboxShowHidePass;
    @FXML
    private StackPane pageDN;
    @FXML
    private TextField textUser1;
    @FXML
    private PasswordField textPassword1;
    @FXML
    private TextField textField1;
    @FXML
    private CheckBox checkboxShowHidePass1;
    @FXML
    private PasswordField textPassword11;
    @FXML
    private StackPane pageDK;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pageDK.setVisible(false);
        textField.setVisible(false);  // Ensure textField is initially hidden
    }

    @FXML
    private void btnForgetPassword(MouseEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Quên mật khẩu");
        a.setContentText("Đang cập nhật...");
        a.setHeaderText(null);
        a.show();
    }

    @FXML
    private void btnLogin(MouseEvent event) throws IOException {
        Stage stage = (Stage) imgLogin.getScene().getWindow(); // Lấy đối tượng Stage từ ImageView
        stage.setFullScreen(true); // Đặt cửa sổ ứng dụng thành chế độ toàn màn hình
        App.setRoot("primary", 1400, 800, "G2Store App");
    }

    @FXML
    private void ShowHidePass(ActionEvent event) {
        if (checkboxShowHidePass.isSelected()) {
            // Show password
            textField.setText(textPassword.getText());
            textField.setVisible(true);
            textPassword.setVisible(false);
        } else {
            // Hide password
            textPassword.setText(textField.getText());
            textPassword.setVisible(true);
            textField.setVisible(false);
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    private void btnDangKy(MouseEvent event) {
        pageDN.setVisible(false);
        pageDK.setVisible(true);

    }

    @FXML
    private void btnDangNhap(MouseEvent event) {
        pageDN.setVisible(true);
        pageDK.setVisible(false);
    }
}
