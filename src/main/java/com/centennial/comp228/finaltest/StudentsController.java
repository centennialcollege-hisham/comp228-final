package com.centennial.comp228.finaltest;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentsController {
    @FXML
    private TextField studentId;
    @FXML
    private TextField studentName;
    @FXML
    private TextField studentTuitionFeeds;
    @FXML
    private TextField studentDiscountFees;
    @FXML
    private Label studentIdError;


    @FXML
    protected void onDisplayButtonClick() {
        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT * FROM Student WHERE StId=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(studentId.getText()));
                ResultSet resultSet = preparedStatement.executeQuery();
                boolean result = resultSet.next();
                if (result) {
                    studentIdError.setVisible(false);
                    int fees = resultSet.getInt(3);
                    studentName.setText(resultSet.getString(2));
                    studentTuitionFeeds.setText(String.valueOf(fees));
                    studentDiscountFees.setText(String.valueOf(fees - (fees * .3)));
                } else {
                    studentIdError.setVisible(true);
                    studentIdError.setText("User is not found");
                    studentIdError.setTextFill(Color.RED);
                }
            } catch (Exception e) {
                studentIdError.setVisible(true);
                studentIdError.setTextFill(Color.RED);
                studentIdError.setText("Please enter user id");
            }
        } catch (SQLException e) {

        }
    }

    @FXML
    protected void onResetButtonClick() {
        studentId.setText("");
        studentName.setText("");
        studentTuitionFeeds.setText("");
        studentDiscountFees.setText("");
        studentIdError.setVisible(false);
    }

    @FXML
    protected void onDeleteButtonClick() {
        if (checkIsUserExist()) {
            try (Connection connection = DBUtil.getConnection()) {
                String query = "DELETE FROM Student WHERE StId=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, Integer.parseInt(studentId.getText()));
                    preparedStatement.executeUpdate();
                    studentIdError.setText("Student deleted successfully");
                    studentIdError.setTextFill(Color.GREEN);
                    onResetButtonClick();
                    studentIdError.setVisible(true);
                } catch (Exception e) {
                    studentIdError.setVisible(true);
                    studentIdError.setTextFill(Color.RED);
                    studentIdError.setText("Failed to delete student");
                }
            } catch (SQLException e) {

            }
        }
    }

    @FXML
    protected void onUpdateButtonClick() {
        if (checkIsUserExist()) {
            try (Connection connection = DBUtil.getConnection()) {
                String query = "UPDATE Student SET StName=?, StFees=? WHERE StId=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, studentName.getText());
                    preparedStatement.setInt(2, Integer.parseInt(studentTuitionFeeds.getText()));
                    preparedStatement.setInt(3, Integer.parseInt(studentId.getText()));
                    preparedStatement.executeUpdate();
                    studentIdError.setTextFill(Color.GREEN);
                    studentIdError.setText("Student updated successfully");
                    studentIdError.setVisible(true);
                } catch (Exception e) {
                    studentIdError.setVisible(true);
                    studentIdError.setTextFill(Color.RED);
                    studentIdError.setText("Failed to update student");
                }
            } catch (SQLException e) {

            }
        }
    }

    @FXML
    protected void onQuietButtonClick() {
        try {
            DBUtil.closeConnection(DBUtil.getConnection());


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Goodbye");
            alert.setHeaderText("Thank you for using our application");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Platform.exit();
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIsUserExist() {
        try (Connection connection = DBUtil.getConnection()) {
            String query = "SELECT * FROM Student WHERE StId=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(studentId.getText()));
                ResultSet resultSet = preparedStatement.executeQuery();
                boolean result = resultSet.next();
                if (!result) {
                    studentIdError.setVisible(true);
                    studentIdError.setTextFill(Color.GREEN);
                    studentIdError.setText("Student not found");
                    return false;
                }
            } catch (Exception e) {
                studentIdError.setVisible(true);
                studentIdError.setTextFill(Color.RED);
                studentIdError.setText("Please enter student id");
                return false;
            }
        } catch (SQLException e) {

        }
        return true;
    }


}