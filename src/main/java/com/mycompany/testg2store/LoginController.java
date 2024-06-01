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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textField.setVisible(false);  // Ensure textField is initially hidden

        // Set hình ảnh từ URL
        try {
            String imageUrl = "https://images.assetsdelivery.com/compings_v2/vectorv/vectorv1904/vectorv190412711.jpg";
            Image image = loadImageFromURL(imageUrl);
            imgLogin.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Lỗi khi tải ảnh", "Không thể tải ảnh từ URL.");
        }
    }

    private Image loadImageFromURL(String urlString) throws IOException {
        return new Image(urlString);
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
        App.setRoot("primary", 1400, 800,"G2Store App");
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
}
