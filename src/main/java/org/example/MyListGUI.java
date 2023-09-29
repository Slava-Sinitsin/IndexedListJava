package org.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class MyListGUI extends Application {
    int N = 5;
    private MyList<Integer> integerList = new MyList<>(N);
    private MyList<Point2D> point2DList = new MyList<>(N);

    // Создаем группу RadioButton для выбора типа данных
    ToggleGroup dataTypeGroup = new ToggleGroup();
    RadioButton integerRadioButton = new RadioButton("Integer");
    RadioButton point2DRadioButton = new RadioButton("Point2D");

    TextField nTextField = new TextField();
    Text nText = new Text("N = ");

    // Создаем кнопку "Добавить в конец"
    Button addButton = new Button("Добавить в конец");
    // Создаем кнопку "Заполнить случайными числами"
    Button randomButton = new Button("Добавить 10 случайных элементов");
    // Создаем кнопку "Добавить по индексу"
    Button addAtIndexButton = new Button("Добавить по индексу");
    // Создаем кнопку "Удалить по индексу"
    Button deleteAtIndexButton = new Button("Удалить по индексу");
    // Создаем кнопку "Сортировать"
    Button sortButton = new Button("Сортировать");
    // Создаем кнопку "Сохранить в файл"
    Button saveToFileButton = new Button("Сохранить в файл");
    // Создаем кнопку "Загрузить из файла"
    Button loadFromFileButton = new Button("Загрузить из файла");
    // Создаем TextArea для вывода списка
    TextArea listTextArea = new TextArea();
    // Создаем TextArea для вывода вектора
    TextArea vectorTextArea = new TextArea();
    // Создаем кнопку для умножения элементов списка
    Button multiplyButton = new Button("Умножить все на число");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("Lab1");
        stage.setWidth(830);
        stage.setHeight(420);

        // Устанавливаем группу для RadioButton
        integerRadioButton.setToggleGroup(dataTypeGroup);
        point2DRadioButton.setToggleGroup(dataTypeGroup);
        integerRadioButton.setSelected(true); // Значение по умолчанию

        listTextArea.setLayoutX(300);
        listTextArea.setLayoutY(20);
        listTextArea.setEditable(false);
        listTextArea.setWrapText(true);
        listTextArea.setPrefSize(500, 270);

        vectorTextArea.setLayoutX(300);
        vectorTextArea.setLayoutY(300);
        vectorTextArea.setEditable(false);
        vectorTextArea.setWrapText(true);
        vectorTextArea.setPrefSize(500, 70);

        // Располагаем элементы в окне
        integerRadioButton.setLayoutX(20);
        integerRadioButton.setLayoutY(20);
        point2DRadioButton.setLayoutX(100);
        point2DRadioButton.setLayoutY(20);

        nText.setLayoutX(180);
        nText.setLayoutY(33);
        nTextField.setLayoutX(205);
        nTextField.setLayoutY(18);
        nTextField.setPrefSize(40, 20);
        nTextField.setText("5");

        randomButton.setLayoutX(20);
        randomButton.setLayoutY(60);

        addButton.setLayoutX(20);
        addButton.setLayoutY(100);

        addAtIndexButton.setLayoutX(20);
        addAtIndexButton.setLayoutY(140);

        multiplyButton.setLayoutX(20);
        multiplyButton.setLayoutY(180);

        deleteAtIndexButton.setLayoutX(20);
        deleteAtIndexButton.setLayoutY(220);

        sortButton.setLayoutX(20);
        sortButton.setLayoutY(260);

        saveToFileButton.setLayoutX(20);
        saveToFileButton.setLayoutY(300);

        loadFromFileButton.setLayoutX(20);
        loadFromFileButton.setLayoutY(340);

        nTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                // Если поле пустое или содержит только пробелы, то не выполняем действий
                return;
            }
            try {
                int newN = Integer.parseInt(newValue);
                if (newN > 0) {
                    N = newN;
                    integerList = new MyList<>(N);
                    point2DList = new MyList<>(N);
                } else {
                    // Обработка случая, когда введено отрицательное значение или ноль
                    showErrorAlert("Ошибка ввода");
                    nTextField.setText(Integer.toString(N)); // Восстанавливаем предыдущее значение
                }
            } catch (NumberFormatException e) {
                // Обработка случая, когда введено нечисловое значение
                showErrorAlert("Ошибка ввода");
                nTextField.setText(Integer.toString(N)); // Восстанавливаем предыдущее значение
            }
        });

        // Добавляем слушатель для RadioButton
        integerRadioButton.setOnAction(e -> {
            integerList = new MyList<>(N);
            listTextArea.clear();
            vectorTextArea.clear();
        });

        point2DRadioButton.setOnAction(e -> {
            point2DList = new MyList<>(N);
            listTextArea.clear();
            vectorTextArea.clear();
        });

        // Добавляем слушатель для кнопки "Добавить в конец"
        addButton.setOnAction(e -> {
            String selectedItem = ((RadioButton) dataTypeGroup.getSelectedToggle()).getText();

            // Создаем всплывающее окно для ввода элемента
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Добавить в конец");
            alert.setHeaderText(null);

            // Создаем поля для ввода данных
            TextField valueField1 = new TextField();
            TextField valueField2 = new TextField();
            valueField1.setMaxSize(50, 20);
            valueField2.setMaxSize(50, 20);
            // Создаем сетку для размещения полей
            GridPane grid = new GridPane();
            grid.addRow(0, new Text(selectedItem.equals("Integer") ? "           Значение:    " : "           X:"), valueField1);
            if (selectedItem.equals("Point2D")) {
                grid.addRow(1, new Text("           Y:"), valueField2);
            }
            grid.getRowConstraints().forEach(constraint -> {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setMinHeight(30);
                constraint.setPrefHeight(30);
            });

            alert.getDialogPane().setContent(grid);

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    String value1 = valueField1.getText();
                    String value2 = valueField2.getText();

                    if (selectedItem.equals("Integer")) {
                        if (!value1.isEmpty()) {
                            integerList.add(Integer.parseInt(value1));
                            updateListTextArea(listTextArea, integerList);
                        }
                    } else if (selectedItem.equals("Point2D")) {
                        if (!value1.isEmpty() && !value2.isEmpty()) {
                            double x = Double.parseDouble(value1);
                            double y = Double.parseDouble(value2);
                            point2DList.add(new Point2D(x, y));
                            updateListTextArea(listTextArea, point2DList);
                        }
                    }
                }
            });
        });

        // Добавляем слушатель для кнопки "Заполнить случайными числами"
        randomButton.setOnAction(e -> {
            String selectedItem = ((RadioButton) dataTypeGroup.getSelectedToggle()).getText();
            if (selectedItem.equals("Integer")) {
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    int randomValue = random.nextInt(100); // Генерируем случайное целое число от 0 до 99
                    integerList.add(randomValue);
                }
                updateListTextArea(listTextArea, integerList);
            } else if (selectedItem.equals("Point2D")) {
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    int x = random.nextInt(100); // Генерируем случайное число с плавающей точкой от 0 до 99
                    int y = random.nextInt(100);
                    point2DList.add(new Point2D(x, y));
                }
                updateListTextArea(listTextArea, point2DList);
            }
        });

        // Добавляем слушатель для кнопки "Добавить по индексу"
        addAtIndexButton.setOnAction(e -> {
            String selectedItem = ((RadioButton) dataTypeGroup.getSelectedToggle()).getText();
            if (selectedItem.equals("Integer")) {
                showAddAtIndexDialog("Введите Integer и индекс:", selectedItem);
            } else if (selectedItem.equals("Point2D")) {
                showAddAtIndexDialog("Введите x, y и индекс:", selectedItem);
            }
        });

        // Добавляем слушатель для кнопки "Удалить по индексу"
        deleteAtIndexButton.setOnAction(e -> {
            String selectedItem = ((RadioButton) dataTypeGroup.getSelectedToggle()).getText();
            if (selectedItem.equals("Integer")) {
                showDeleteAtIndexDialog(selectedItem);
            } else if (selectedItem.equals("Point2D")) {
                showDeleteAtIndexDialog(selectedItem);
            }
        });

        // Добавляем слушатель для кнопки "Умножить все элементы на число"
        multiplyButton.setOnAction(e -> {
            String selectedItem = ((RadioButton) dataTypeGroup.getSelectedToggle()).getText();
            if (selectedItem.equals("Integer") && integerList.getSize() != 0) {
                showMultiplyDialog(selectedItem);
            } else if (selectedItem.equals("Point2D") && point2DList.getSize() != 0) {
                showMultiplyDialog(selectedItem);
            } else {
                showErrorAlert("Список пуст");
            }
        });

        // Добавляем слушатель для кнопки "Сортировать"
        sortButton.setOnAction(e -> {
            String selectedItem = ((RadioButton) dataTypeGroup.getSelectedToggle()).getText();
            if (selectedItem.equals("Integer")) {
                integerList.mergeSort();
                updateListTextArea(listTextArea, integerList);
            } else if (selectedItem.equals("Point2D")) {
                point2DList.mergeSort();
                updateListTextArea(listTextArea, point2DList);
            }
        });

        // Добавляем слушатель для кнопки "Сохранить в файл"
        saveToFileButton.setOnAction(e -> {
            String selectedItem = ((RadioButton) dataTypeGroup.getSelectedToggle()).getText();
            String fileName = selectedItem + "List.json"; // Генерируем имя файла на основе выбранного типа данных

            if (selectedItem.equals("Integer")) {
                integerList.serializeToFile(fileName);
            } else if (selectedItem.equals("Point2D")) {
                point2DList.serializeToFile(fileName);
            }

            // Оповещаем пользователя об успешном сохранении
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Сохранение в файл");
            alert.setHeaderText(null);
            alert.setContentText("Данные успешно сохранены в файл " + fileName);
            alert.showAndWait();
        });

        // Добавляем слушатель для кнопки "Загрузить из файла"
        loadFromFileButton.setOnAction(e -> {
            String selectedItem = ((RadioButton) dataTypeGroup.getSelectedToggle()).getText();
            String fileName = selectedItem + "List.json"; // Генерируем имя файла на основе выбранного типа данных
            if (selectedItem.equals("Integer")) {
                MyList<Integer> tmpIntList = MyList.deserializeFromFile("IntegerList.json", Integer.class);
                N = tmpIntList.getN();
                nTextField.setText(String.valueOf(N));
                integerList = tmpIntList;
                updateListTextArea(listTextArea, integerList);
            } else if (selectedItem.equals("Point2D")) {
                MyList<Point2D> tmpPoint2DList = MyList.deserializeFromFile("Point2DList.json", Point2D.class);
                N = tmpPoint2DList.getN();
                nTextField.setText(String.valueOf(N));
                point2DList = tmpPoint2DList;
                updateListTextArea(listTextArea, point2DList);
            }

            // Оповещаем пользователя об успешной загрузке
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Загрузка из файла");
            alert.setHeaderText(null);
            alert.setContentText("Данные успешно загружены из файла " + fileName);
            alert.showAndWait();
        });

        group.getChildren().addAll(integerRadioButton, point2DRadioButton, nTextField, nText, addButton, randomButton, addAtIndexButton, multiplyButton, deleteAtIndexButton, sortButton, listTextArea, vectorTextArea, saveToFileButton, loadFromFileButton);

        stage.show();
    }

    private <T> void updateListTextArea(TextArea textArea, MyList<T> list) {
        textArea.setText(list.toString());
        vectorTextArea.setText(list.vectorToString());
    }

    @SuppressWarnings("DuplicatedCode")
    private void showAddAtIndexDialog(String labelText, String selectedItem) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Добавить по индексу");
        alert.setHeaderText(null);
        alert.setContentText(labelText);

        // Создаем поля для ввода данных и индекса
        TextField input1 = new TextField();
        TextField input2 = new TextField();
        TextField indexInput = new TextField();
        input1.setMaxSize(50, 20);
        input2.setMaxSize(50, 20);
        indexInput.setMaxSize(50, 20);
        // Создаем сетку для размещения полей
        GridPane grid = new GridPane();
        grid.addRow(0, new Text(selectedItem.equals("Integer") ? "           Значение:    " : "           X:"), input1);
        if (selectedItem.equals("Point2D")) {
            grid.addRow(1, new Text("           Y:"), input2);
        }
        grid.addRow(2, new Text("           Индекс:    "), indexInput);
        grid.getRowConstraints().forEach(constraint -> {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(30);
            constraint.setPrefHeight(30);
        });

        alert.getDialogPane().setContent(grid);

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                String value1 = input1.getText();
                String value2 = input2.getText();
                String indexValue = indexInput.getText();

                try {
                    int index = Integer.parseInt(indexValue);

                    if (selectedItem.equals("Integer")) {
                        if (!value1.isEmpty()) {
                            int intValue = Integer.parseInt(value1);
                            integerList.insert(index, intValue);
                            updateListTextArea(listTextArea, integerList);
                        }
                    } else if (selectedItem.equals("Point2D")) {
                        if (!value1.isEmpty() && !value2.isEmpty()) {
                            double x = Double.parseDouble(value1);
                            double y = Double.parseDouble(value2);
                            point2DList.insert(index, new Point2D(x, y));
                            updateListTextArea(listTextArea, point2DList);
                        }
                    }
                } catch (NumberFormatException e) {
                    showErrorAlert("Ошибка ввода");
                }
            }
        });
    }

    private void showDeleteAtIndexDialog(String selectedItem) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Удалить по индексу");
        alert.setHeaderText(null);
        alert.setContentText("Введите индекс для удаления:");

        // Создаем поле для ввода индекса
        TextField indexInput = new TextField();
        indexInput.setMaxSize(50, 20);

        GridPane grid = new GridPane();
        grid.addRow(0, new Text(""));
        grid.addRow(1, new Text("           Индекс:    "), indexInput);
        grid.getRowConstraints().forEach(constraint -> {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(30);
            constraint.setPrefHeight(30);
        });

        alert.getDialogPane().setContent(grid);

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                String indexValue = indexInput.getText();
                try {
                    int index = Integer.parseInt(indexValue);
                    if (selectedItem.equals("Integer")) {
                        integerList.remove(index);
                        updateListTextArea(listTextArea, integerList);
                    } else if (selectedItem.equals("Point2D")) {
                        point2DList.remove(index);
                        updateListTextArea(listTextArea, point2DList);
                    }
                } catch (NumberFormatException e) {
                    showErrorAlert("Ошибка ввода");
                }
            }
        });
    }

    private void showMultiplyDialog(String selectedItem) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Умножить все на число");
        alert.setHeaderText(null);
        alert.setContentText("Умножить на:");

        // Создаем поле для ввода индекса
        TextField valueInput = new TextField();
        valueInput.setMaxSize(50, 20);

        GridPane grid = new GridPane();
        grid.addRow(0, new Text(""));
        grid.addRow(1, new Text("           Умножить на:    "), valueInput);
        grid.getRowConstraints().forEach(constraint -> {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(30);
            constraint.setPrefHeight(30);
        });

        alert.getDialogPane().setContent(grid);

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                int value = Integer.parseInt(valueInput.getText());
                try {
                    if (selectedItem.equals("Integer")) {
                        integerList.forEach(v -> {
                            v = v * value;
                            return v;
                        });
                        updateListTextArea(listTextArea, integerList);
                    } else if (selectedItem.equals("Point2D")) {
                        point2DList.forEach(v -> {
                            v.setX(v.getX() * value);
                            v.setY(v.getY() * value);
                            return v;
                        });
                        updateListTextArea(listTextArea, point2DList);
                    }
                } catch (NumberFormatException e) {
                    showErrorAlert("Ошибка ввода");
                }
            }
        });
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
