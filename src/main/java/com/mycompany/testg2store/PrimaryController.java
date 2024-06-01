package com.mycompany.testg2store;

import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.commons.lang3.StringUtils;

public class PrimaryController {

    @FXML
    private TableColumn<Order, Integer> stt;
    @FXML
    private TableColumn<Order, String> orderId;
    @FXML
    private TableColumn<Order, String> customerPhone;
    @FXML
    private TableColumn<Order, String> employeeId;
    @FXML
    private TableColumn<Order, String> time;
    @FXML
    private TableColumn<Order, Float> total;
    @FXML
    private TableView<Order> tableView;

    @FXML
    private Label btnTrangChu;
    @FXML
    private Label btnBanHang;
    @FXML
    private Label btnNhapHang;
    @FXML
    private Label btnKhachHang;
    @FXML
    private Label btnNhanVien;
    @FXML
    private Label btnTaiKhoan;
    @FXML
    private Label btnBaoCao;
    @FXML
    private Label btnPhanQuyen;

    @FXML
    private ImageView iconAdd;
    @FXML
    private ImageView iconDetail;
    @FXML
    private ComboBox<String> comboBoxSearch;
    @FXML
    private TextField textSearch;
    private ObservableList<Order> orderList;
    private Connection con;
    private Statement st;
    private ResultSet rs;

    @FXML
    private ComboBox<String> comboBoxCustomer;
    @FXML
    private ComboBox<String> comboBoxEmployee;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField totalFrom;
    @FXML
    private TextField totalTo;
    @FXML
    private VBox btnExportExcel;
    @FXML
    private VBox btnExportPDF;
    @FXML
    private VBox btnExportPDF1;

    public void initialize() {

        //Set giá trị cho comboBox
        ObservableList<String> searchCriteria = FXCollections.observableArrayList(
                "Tất cả",
                "Mã phiếu xuất",
                "Số máy khách hàng",
                "Mã nhân viên"
        );
        comboBoxSearch.setItems(searchCriteria);

        textSearch.setOnKeyReleased(event -> {
            String newValue = textSearch.getText().trim();
            String selectedCriteria = comboBoxSearch.getValue();

            // Tùy thuộc vào tiêu chí tìm kiếm, thực hiện xử lý tìm kiếm tương ứng
            if (selectedCriteria != null) {
                switch (selectedCriteria) {
                    case "Mã phiếu xuất":
                        searchByOrderId(newValue);
                        break;
                    case "Số máy khách hàng":
                        searchByCustomerPhone(newValue);
                        break;
                    case "Mã nhân viên":
                        searchByEmployeeId(newValue);
                        break;
                    default:
                        break;
                }
            }
        });
        //Kết nối DB hiển thị all
        try {
            // Lấy kết nối
            ConnectDB c = new ConnectDB();
            con = c.getConnect();
            String sql = "SELECT * FROM orders";
            // Tạo statement
            st = con.createStatement();

            // Truy vấn 
            rs = st.executeQuery(sql);

            // Tạo ObservableList để lưu dữ liệu từ ResultSet
            orderList = FXCollections.observableArrayList();

            orderList.clear(); // Xóa dữ liệu cũ trong orderList trước khi thêm dữ liệu mới
            int index = 1; // Biến để lưu số thứ tự
            while (rs.next()) {
                String orderIdStr = rs.getString("orderid");
                String customerPhone = rs.getString("customerphone");
                String employeeId = rs.getString("empid");
                String time = rs.getString("time");
                float total = rs.getFloat("total");
                orderList.add(new Order(orderIdStr, customerPhone, employeeId, time, total));
                index++; // Tăng số thứ tự sau mỗi dòng
            }

            tableView.setItems(orderList);

            // Thiết lập giá trị cho các cột
            stt.setCellValueFactory(data -> {
                Order order = data.getValue(); // Lấy đối tượng Order từ dữ liệu cột
                int rowIndex = tableView.getItems().indexOf(order) + 1; // Lấy chỉ số dòng trong TableView
                return new SimpleIntegerProperty(rowIndex).asObject(); // Trả về chỉ số dòng dưới dạng Integer
            });

            orderId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrderId()));
            customerPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerPhone()));
            employeeId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmployeeId()));
            time.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTime()));
            total.setCellValueFactory(data -> new SimpleFloatProperty(data.getValue().getTotal()).asObject());

            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Handle row selection, for example, show detail information
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //truy vấn db hiển thị comboBoxCustomerList
        try {
            // Lấy kết nối
//            ConnectDB c = new ConnectDB();
//            con = c.getConnect();
            String sql = "SELECT DISTINCT customerphone FROM orders"; // Lấy các customerphone duy nhất từ bảng orders
            st = con.createStatement();
            rs = st.executeQuery(sql);

            // Tạo danh sách các CustomerPhone từ kết quả truy vấn
            ObservableList<String> customerPhoneList = FXCollections.observableArrayList();
            customerPhoneList.add("Tất cả");
            while (rs.next()) {
                String customerPhone = rs.getString("customerphone");
                customerPhoneList.add(customerPhone);
            }

            // Đặt danh sách CustomerPhone vào comboBoxCustomer
            comboBoxCustomer.setItems(customerPhoneList);
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //truy vấn db để hiển thị comboBoxEmployee
        try {
            // Lấy kết nối
//            ConnectDB c = new ConnectDB();
//            con = c.getConnect();
            String sql = "SELECT DISTINCT empid FROM orders"; // Lấy các empid duy nhất từ bảng orders
            st = con.createStatement();
            rs = st.executeQuery(sql);

            // Tạo danh sách các EmployeeId từ kết quả truy vấn
            ObservableList<String> employeeIdList = FXCollections.observableArrayList();
            employeeIdList.add("Tất cả");

            while (rs.next()) {
                String employeeId = rs.getString("empid");
                employeeIdList.add(employeeId);
            }

            // Đặt danh sách EmployeeId vào comboBoxEmployee
            comboBoxEmployee.setItems(employeeIdList);
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Bắt sự kiện từ textSearch
        textSearch.setOnKeyReleased(event -> filterData());
        // Bắt sự kiện từ totalFrom và totalTo
        totalFrom.setOnKeyReleased(event -> filterData());
        totalTo.setOnKeyReleased(event -> filterData());
        // Bắt sự kiện từ comboBoxCustomer và comboBoxEmployee
        comboBoxCustomer.setOnAction(event -> filterData());
        comboBoxEmployee.setOnAction(event -> filterData());

        //chỉ cho totalFrom và totalTo nhập số
        totalFrom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                totalFrom.setText(newValue.replaceAll("[^\\d]", ""));
            }
            filterData(); // Gọi phương thức filterData() sau khi người dùng nhập
        });

        totalTo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                totalTo.setText(newValue.replaceAll("[^\\d]", ""));
            }
            filterData(); // Gọi phương thức filterData() sau khi người dùng nhập
        });
    }

    private void filterData() {
        String selectedCustomer = comboBoxCustomer.getValue();
        String selectedEmployee = comboBoxEmployee.getValue();
        LocalDate fromDateValue = dateFrom.getValue();
        LocalDate toDateValue = dateTo.getValue();
        float totalFromValue = 0;
        float totalToValue = 0;

        totalFromValue = Float.parseFloat(totalFrom.getText());
        totalToValue = Float.parseFloat(totalTo.getText());

        try {
            ConnectDB c = new ConnectDB();
            con = c.getConnect();

            String sql = "SELECT * FROM orders WHERE 1=1";

            if (selectedCustomer != null && !selectedCustomer.isEmpty() && !selectedCustomer.equals("Tất cả")) {
                sql += " AND customerphone = '" + selectedCustomer + "'";
            }

            if (selectedEmployee != null && !selectedEmployee.isEmpty() && !selectedEmployee.equals("Tất cả")) {
                sql += " AND empid = '" + selectedEmployee + "'";
            }

            if (fromDateValue != null && toDateValue != null) {
                sql += " AND time BETWEEN '" + fromDateValue.toString() + "' AND '" + toDateValue.toString() + "'";
            }

            if (totalFromValue != 0) {
                sql += " AND total >= " + totalFromValue;
            }

            // Chỉ thêm điều kiện vào câu truy vấn nếu giá trị của totalTo khác Float.MAX_VALUE
            if (totalToValue != Float.MAX_VALUE) {
                sql += " AND total <= " + totalToValue;
            }

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Xóa tất cả các phần tử trong orderList
            orderList.clear();

            // Thêm các đối tượng mới phù hợp với các điều kiện lọc vào orderList
            while (rs.next()) {
                String orderIdStr = rs.getString("orderid");
                String customerPhone = rs.getString("customerphone");
                String employeeId = rs.getString("empid");
                String time = rs.getString("time");
                float total = rs.getFloat("total");
                orderList.add(new Order(orderIdStr, customerPhone, employeeId, time, total));
            }
            tableView.setItems(orderList);

            stt.setCellValueFactory(data -> {
                Order order = data.getValue(); // Lấy đối tượng Order từ dữ liệu cột
                int rowIndex = tableView.getItems().indexOf(order) + 1; // Lấy chỉ số dòng trong TableView
                return new SimpleIntegerProperty(rowIndex).asObject(); // Trả về chỉ số dòng dưới dạng Integer
            });
            orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            employeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            time.setCellValueFactory(new PropertyValueFactory<>("time"));
            total.setCellValueFactory(new PropertyValueFactory<>("total"));
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //filter lịch sử bán theo ngày 
    private void filterByDate(ActionEvent event) {
        LocalDate fromDateValue = dateFrom.getValue();
        LocalDate toDateValue = dateTo.getValue();

        // Nếu cả hai ngày đều được chọn
        if (fromDateValue != null && toDateValue != null) {
            try {
                // Lấy kết nối
                ConnectDB c = new ConnectDB();
                con = c.getConnect();
                String sql = "SELECT * FROM orders WHERE time BETWEEN ? AND ?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, fromDateValue.toString());
                preparedStatement.setString(2, toDateValue.toString());
                rs = preparedStatement.executeQuery();

                // Xử lý dữ liệu trả về và hiển thị lên TableView
                // Code xử lý dữ liệu ở đây
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Hiển thị cảnh báo nếu người dùng chưa chọn đủ hai ngày
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Chọn ngày");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Vui lòng chọn cả ngày bắt đầu và ngày kết thúc!");
            warningAlert.showAndWait();
        }
    }

    //filter lịch sử bán theo tổng tiền
    private void filterByTotal(ActionEvent event) {
        try {
            float totalFromValue = Float.parseFloat(totalFrom.getText());
            float totalToValue = Float.parseFloat(totalTo.getText());

            // Lấy kết nối
            ConnectDB c = new ConnectDB();
            con = c.getConnect();
            String sql = "SELECT * FROM orders WHERE total BETWEEN ? AND ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setFloat(1, totalFromValue);
            preparedStatement.setFloat(2, totalToValue);
            rs = preparedStatement.executeQuery();

            // Xử lý dữ liệu trả về và hiển thị lên TableView
            // Code xử lý dữ liệu ở đây
        } catch (NumberFormatException | SQLException ex) {
            // Xử lý ngoại lệ nếu người dùng nhập không hợp lệ hoặc lỗi SQL
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Lỗi");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Vui lòng nhập số tiền hợp lệ!");
            errorAlert.showAndWait();
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnDetail(MouseEvent event) {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            // Tạo cửa sổ Alert để hiển thị thông tin chi tiết của đơn hàng
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chi tiết đơn hàng");
            alert.setHeaderText(null);
            String content = String.format("Mã đơn hàng: %s\nSố điện thoại khách hàng: %s\nMã nhân viên: %s\nThời gian: %s\nTổng giá trị: %.2f",
                    selectedOrder.getOrderId(), selectedOrder.getCustomerPhone(), selectedOrder.getEmployeeId(), selectedOrder.getTime(), selectedOrder.getTotal());
            alert.setContentText(content);
            alert.getDialogPane().setMinWidth(400);
            alert.getDialogPane().setMinHeight(200);
            alert.showAndWait();
        }
    }

    @FXML
    private void btnDeleteBill(MouseEvent event) {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            // Hiển thị cảnh báo xác nhận trước khi xóa đơn hàng
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Xác nhận xóa đơn hàng");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Bạn có chắc chắn muốn xóa đơn hàng này?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Xóa dòng dữ liệu từ cơ sở dữ liệu
                    String sql = "DELETE FROM orders WHERE orderid = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, selectedOrder.getOrderId());
                    preparedStatement.executeUpdate();

                    // Xóa dòng dữ liệu khỏi TableView
                    tableView.getItems().remove(selectedOrder);

                    // Hiển thị thông báo khi xóa thành công
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Xóa đơn hàng");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Đã xóa đơn hàng thành công!");
                    successAlert.showAndWait();
                } catch (SQLException ex) {
                    // Hiển thị thông báo khi xóa thất bại
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Lỗi khi xóa đơn hàng");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Đã xảy ra lỗi khi xóa đơn hàng!");
                    errorAlert.showAndWait();
                    Logger
                            .getLogger(PrimaryController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            // Hiển thị thông báo nếu không có dòng nào được chọn
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Không có đơn hàng được chọn");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Vui lòng chọn một đơn hàng để xóa!");
            warningAlert.showAndWait();
        }
    }

    @FXML
    private void btnExportExcel(MouseEvent event) {
        // Hiển thị cảnh báo xác nhận trước khi xuất Excel
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận xuất Excel");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Bạn có chắc chắn muốn xuất dữ liệu sang Excel?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Tạo workbook mới
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Orders");

                // Tạo hàng tiêu đề
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < tableView.getColumns().size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(tableView.getColumns().get(i).getText());
                }

                // Đổ dữ liệu từ TableView vào Excel
                for (int i = 0; i < tableView.getItems().size(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < tableView.getColumns().size(); j++) {
                        Cell cell = row.createCell(j);
                        Object cellData = tableView.getColumns().get(j).getCellData(i);
                        if (cellData != null) {
                            cell.setCellValue(cellData.toString());
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }

                // Hiển thị hộp thoại FileChooser để người dùng chọn vị trí và tên file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Lưu File Excel");
                fileChooser.setInitialFileName("orders.xlsx");
                File file = fileChooser.showSaveDialog(new Stage());

                // Nếu người dùng chọn đúng và không null
                if (file != null) {
                    FileOutputStream fileOut = new FileOutputStream(file);
                    workbook.write(fileOut);
                    fileOut.close();

                    // Hiển thị thông báo khi xuất Excel thành công
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Xuất Excel");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Đã xuất dữ liệu sang Excel thành công!");
                    successAlert.showAndWait();
                }
            } catch (IOException ex) {
                // Hiển thị thông báo khi xuất Excel thất bại
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Lỗi khi xuất Excel");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Đã xảy ra lỗi khi xuất dữ liệu sang Excel!");
                errorAlert.showAndWait();
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void btnExportPDF(javafx.scene.input.MouseEvent event) {
        // Hiển thị cảnh báo xác nhận trước khi xuất PDF
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận xuất PDF");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Bạn có chắc chắn muốn xuất dữ liệu sang PDF?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Tạo một FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Lưu File PDF");
            fileChooser.setInitialFileName("orders.pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            // Hiển thị hộp thoại chọn vị trí và tên tệp và lấy đường dẫn tệp được chọn
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                try {
                    // Tạo một tài liệu mới
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage();
                    document.addPage(page);

                    // Tạo nội dung cho trang
                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
                    contentStream.newLineAtOffset(100, 700);
                    contentStream.setLeading(14.5f);

                    // Tạo tiêu đề cho bảng
                    String[] headers = {"ma hoa don", "sdt khach", "ma nhan vien", "thoi gian", "tong gia tri"};
                    contentStream.showText(StringUtils.center("BANG THONG TIN DON HANG", 100));
                    contentStream.newLine();
                    contentStream.newLine();

                    // In tiêu đề bảng
                    for (String header : headers) {
                        contentStream.showText(StringUtils.rightPad(header, 25));
                    }
                    contentStream.newLine();

                    // Duyệt qua danh sách orderList và ghi thông tin vào tài liệu PDF
                    for (Order order : orderList) {
                        contentStream.showText(StringUtils.rightPad(StringUtils.stripAccents(order.getOrderId()), 25));
                        contentStream.showText(StringUtils.rightPad(StringUtils.stripAccents(order.getCustomerPhone()), 25));
                        contentStream.showText(StringUtils.rightPad(StringUtils.stripAccents(order.getEmployeeId()), 25));
                        contentStream.showText(StringUtils.rightPad(StringUtils.stripAccents(order.getTime()), 25));
                        contentStream.showText(StringUtils.rightPad(String.format("%.1f", order.getTotal()), 30));
                        contentStream.newLine();
                    }

                    contentStream.endText();
                    contentStream.close();

                    // Lưu tài liệu vào tệp đã chọn
                    document.save(file);
                    // Đóng tài liệu
                    document.close();

                    // Hiển thị thông báo khi xuất PDF thành công
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Xuất PDF");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Đã xuất dữ liệu sang PDF thành công!");
                    successAlert.showAndWait();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void btnRefresh(MouseEvent event) {
        // Reset các trường ComboBox và DatePicker
        comboBoxSearch.setValue("Tất cả");
        comboBoxCustomer.setValue("Tất cả");
        comboBoxEmployee.setValue("Tất cả");
        dateFrom.setValue(null);
        dateTo.setValue(null);

        // Reset các trường TextField
        textSearch.clear();
        totalFrom.clear();
        totalTo.clear();

        // Gọi lại phương thức initialize để cập nhật dữ liệu mới
        initialize();

        // Hiển thị thông báo khi làm mới thành công
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Refresh thành công");
        alert.show();
    }

    private void searchByOrderId(String orderId) {
        // Tạo một danh sách mới để lưu kết quả tìm kiếm
        ObservableList<Order> searchResult = FXCollections.observableArrayList();

        // Lặp qua danh sách dữ liệu gốc và thêm các đối tượng có mã phiếu xuất trùng khớp vào danh sách kết quả
        for (Order order : orderList) {
            if (order.getOrderId().toLowerCase().contains(orderId.toLowerCase())) {
                searchResult.add(order);
            }
        }
        // Hiển thị kết quả tìm kiếm trên TableView
        tableView.setItems(searchResult);
    }

    // Phương thức tìm kiếm theo số máy khách hàng
    private void searchByCustomerPhone(String customerPhone) {
        // Tạo một danh sách mới để lưu kết quả tìm kiếm
        ObservableList<Order> searchResult = FXCollections.observableArrayList();

        // Lặp qua danh sách dữ liệu gốc và thêm các đối tượng có số máy khách hàng trùng khớp vào danh sách kết quả
        for (Order order : orderList) {
            if (order.getCustomerPhone().toLowerCase().contains(customerPhone.toLowerCase())) {
                searchResult.add(order);
            }
        }
        // Hiển thị kết quả tìm kiếm trên TableView
        tableView.setItems(searchResult);
    }

    // Phương thức tìm kiếm theo mã nhân viên
    private void searchByEmployeeId(String employeeId) {
        // Tạo một danh sách mới để lưu kết quả tìm kiếm
        ObservableList<Order> searchResult = FXCollections.observableArrayList();

        // Lặp qua danh sách dữ liệu gốc và thêm các đối tượng có mã nhân viên trùng khớp vào danh sách kết quả
        for (Order order : orderList) {
            if (order.getEmployeeId().toLowerCase().contains(employeeId.toLowerCase())) {
                searchResult.add(order);
            }
        }
        // Hiển thị kết quả tìm kiếm trên TableView
        tableView.setItems(searchResult);
    }

    @FXML
    private void btnLogOut(MouseEvent event) {
        // Tạo cửa sổ confirm
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận đăng xuất");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

        // Lấy Stage của cửa sổ hiện tại
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Đặt cửa sổ confirm là modal
        confirmAlert.initModality(Modality.APPLICATION_MODAL);

        // Đặt cửa sổ cha là cửa sổ hiện tại
        confirmAlert.initOwner(currentStage);

        // Lấy kích thước của màn hình
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Tính toán vị trí mới của cửa sổ
        double newX = (screenBounds.getWidth() - currentStage.getWidth()) / 2;
        double newY = (screenBounds.getHeight() - currentStage.getHeight()) / 2;

        // Đặt vị trí mới cho cửa sổ
        currentStage.setX(newX);
        currentStage.setY(newY);

        // Hiển thị cửa sổ confirm và xử lý kết quả
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    App.setRoot("Login", 800, 400,"Đăng nhập");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
