import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Main extends JFrame {

    private int move;
    private int maximumMove = (99*99999);

    private JTextField textField = new JTextField(4);
    private JMenuBar menuBar = new JMenuBar();
    private JMenu gameMenu = new JMenu("Игра");
    private JMenuItem newGameItem = new JMenuItem("Новая игра");
    private JMenuItem exitItem = new JMenuItem("Выход");
    private JMenu helpMenu = new JMenu("Помощь");
    private JPanel inputPanel = new JPanel();
    private JPanel textPanel = new JPanel();
    private JLabel inputSomethingLabel = new JLabel("Введите число:");
    private JPanel resultPanel = new JPanel();
    private JLabel resultLabel = new JLabel();
    private String stringValue;
    private JMenuItem rulesItem = new JMenuItem("Правила");
    private JMenuItem enterInConsoleTrueValueItem = new JMenuItem("Вывести ответ в консоль");
    private JMenuItem showTrueValueItem = new JMenuItem("Вывести ответ на экран");
    private JPanel areaPanel = new JPanel();
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private JLabel moveLabel = new JLabel("Ходы: " + move);
    private JMenuItem setMaximumMoveItem = new JMenuItem("Задать максимальное количество ходов");


    public Main() {
        super("Быки и коровы");
        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        gameMenu.add(newGameItem);
        gameMenu.add(setMaximumMoveItem);
        gameMenu.add(exitItem);
        helpMenu.add(rulesItem);
        helpMenu.add(enterInConsoleTrueValueItem);
        helpMenu.add(showTrueValueItem);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        exitItem.addActionListener(actionEvent -> System.exit(0));
        newGameItem.addActionListener(actionEvent -> setNumber());
        textField.addActionListener(actionEvent -> catchNumber());
        rulesItem.addActionListener(actionEvent -> JOptionPane.showMessageDialog(null, "В классическом варианте игра рассчитана на двух игроков. Каждый из игроков задумывает и записывает \nтайное 4-значное число с неповторяющимися цифрами. Игрок, который начинает игру по жребию, делает \nпервую попытку отгадать число. Попытка — это 4-значное число с неповторяющимися цифрами, сообщаемое \nпротивнику. Противник сообщает в ответ, сколько цифр угадано без совпадения с их позициями в тайном \nчисле (то есть количество коров) и сколько угадано вплоть до позиции в тайном числе (то есть \nколичество быков). Например: Задумано тайное число «3219». Попытка: «2310». Результат: две «коровы» (две цифры: «2» \nи «3» — угаданы на неверных позициях) и один «бык» (одна цифра «1» угадана вплоть до позиции). \nИгроки делают попытки угадать по очереди. Побеждает тот, кто угадает число первым, при условии, \nчто он не начинал игру. Если же отгадавший начинал игру — его противнику предоставляется последний \nшанс угадать последовательность. При игре против компьютера игрок вводит комбинации одну за \nдругой, пока не отгадает всю последовательность.", "Правила игры", JOptionPane.PLAIN_MESSAGE));
        enterInConsoleTrueValueItem.addActionListener(actionEvent -> System.out.println("Computer's Value = " + stringValue));
        showTrueValueItem.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null, stringValue, "Ответ", JOptionPane.INFORMATION_MESSAGE);
            setNumber();
        });
        setMaximumMoveItem.addActionListener(actionEvent -> {
            String  maximumMoveString;
            try {
                maximumMoveString = JOptionPane.showInputDialog(null, "Введите масимальное количество ходов", "", JOptionPane.QUESTION_MESSAGE);
                maximumMove = Integer.parseInt(maximumMoveString);
            } catch (NullPointerException e) {
                maximumMove = (99*99999);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Введите число!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.SOUTH);
        add(areaPanel, BorderLayout.CENTER);
        //add(textPanel, BorderLayout.NORTH);
        textPanel.add(inputSomethingLabel);
        inputPanel.add(textField);
        resultPanel.add(moveLabel);
        resultPanel.add(resultLabel);
        textField.setFont(new Font("Verdana", Font.PLAIN, 15));
        resultLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textArea.setFont(new Font("Comic MS Sans", Font.PLAIN, 15));
        areaPanel.add(scrollPane);
        textArea.setEditable(false);
        textArea.setColumns(40);
        textArea.setRows(15);
    }

    enum FullScreen { MAXIMUM_SIZE, NORMAL_SIZE }

    private void run(FullScreen fullScreen) {
        setResizable(false);
        if(fullScreen == FullScreen.MAXIMUM_SIZE) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else if (fullScreen == FullScreen.NORMAL_SIZE) {
            setBounds(300, 300, 640, 480);
        }
        setAlwaysOnTop(false);
        setNumber();
        setVisible(true);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textField.setColumns(3);
    }

    private void setNumber() {
        Random random = new Random();
        int intValue;
        do {
            intValue = 1234 + random.nextInt(9876 - 1234);
            stringValue = intValue + "";
            System.out.println("Computer's value = " + intValue);
        } while (stringValue.charAt(0) == stringValue.charAt(1) || stringValue.charAt(0) == stringValue.charAt(2)
                || stringValue.charAt(0) == stringValue.charAt(3) || stringValue.charAt(1) == stringValue.charAt(2)
                || stringValue.charAt(1) == stringValue.charAt(3) || stringValue.charAt(2) == stringValue.charAt(3));
        resultLabel.setText("Новое число загадано!");
        textArea.selectAll();
        textArea.replaceSelection(null);
        textField.selectAll();
        textField.replaceSelection(null);
        move = -1;
        move();
    }

    private int move() {
        //this.move = move;
        move++;
        moveLabel.setText("Ходы: " + move);
        return move;
    }

    private void catchNumber() {
        int userNumber;
        String userNumberString = textField.getText();
        try {
            userNumber = Integer.parseInt(userNumberString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Введите число!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Вы ничего не ввели", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ищем быков и коров
        int bulls = 0, cows = 0;
        try {
            for (int i = 0; i < 4; i++) {
                if (stringValue.charAt(i) == userNumberString.charAt(i)) {
                    bulls++;
                } else {
                    for (int j = 0; j < 4; j++) {
                        if (stringValue.charAt(i) == userNumberString.charAt(j)) {
                            if (i == j) {
                                bulls++;
                            } else {
                                cows++;
                            }
                            break;
                        }
                    }
                }
            }
            move();
            textField.selectAll();
            textField.replaceSelection(null);
        } catch (StringIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Введите 4 числа!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("User number = " + userNumber);
        System.out.println("Bulls = " + bulls + ", cows = " + cows);
        resultLabel.setText("Быки: " + bulls + " Коровы: " + cows);
        textArea.append("Ваше число: " + userNumberString + " | Быки: " + bulls + " | Коровы: " + cows + "\n");

        if (bulls == 4) {
            JOptionPane.showMessageDialog(null, "Вы победили!", "Победа", JOptionPane.PLAIN_MESSAGE);
            textArea.append("Вы победили!");
            setNumber();
        } else if(move > maximumMove) {
            JOptionPane.showMessageDialog(null, "Вы проиграли!", "Пройгрыш", JOptionPane.ERROR_MESSAGE);
            setNumber();
        }
    }

    public static void main(String[] args) {
        Main window = new Main();
        window.run(FullScreen.NORMAL_SIZE);
    }
}
