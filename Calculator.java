import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Stack;

public class Calculator extends JFrame implements ActionListener {
    JPanel jp;
    JTextField jtf;
    JButton jb1, jb2, jb3, jb4, jb5, jb6, jb7, jb8, jb9, jb0, jba, jbs, jbm, jbd, jbp, jbc, jbe;

    Calculator() {
        jp = CalculatorButtons();
        jtf = new JTextField();
        jbe = new JButton("=");
        add(jtf, BorderLayout.NORTH);
        add(jp, BorderLayout.CENTER);
        add(jbe, BorderLayout.SOUTH);
        jbe.addActionListener(this);
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (btn.getText().equals("=")) {
            String infixExpression = jtf.getText();
            String postfixExpression = infixToPostfix(infixExpression);
            jtf.setText(postfixExpression);

            try {
                double result = evaluatePostfixExpression(postfixExpression);
                jtf.setText(Double.toString(result));
            } catch (ArithmeticException ex) {
                jtf.setText("Error");
            }
        } else if (btn.getText().equals("C")) {
            jtf.setText("");
        } else {
            jtf.setText(jtf.getText() + btn.getText());
        }
    }

    public static String infixToPostfix(String infixExpression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (char c : infixExpression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                postfix.append(c);
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty() && precedence(c) <= precedence(operatorStack.peek())) {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(c);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    public static double evaluatePostfixExpression(String postfixExpression) {
        char[] tokens = postfixExpression.toCharArray();
        Stack<Double> numberStack = new Stack<>();

        for (char c : tokens) {
            if (Character.isDigit(c) || c == '.') {
                numberStack.push(Double.parseDouble(String.valueOf(c)));
            } else {
                double operand2 = numberStack.pop();
                double operand1 = numberStack.pop();
                switch (c) {
                    case '+':
                        numberStack.push(operand1 + operand2);
                        break;
                    case '-':
                        numberStack.push(operand1 - operand2);
                        break;
                    case '*':
                        numberStack.push(operand1 * operand2);
                        break;
                    case '/':
                        if (operand2 == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        numberStack.push(operand1 / operand2);
                        break;
                }
            }
        }

        return numberStack.pop();
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    JPanel CalculatorButtons() {
        jp = new JPanel();
        jb1 = new JButton("1");
        jb2 = new JButton("2");
        jb3 = new JButton("3");
        jb4 = new JButton("4");
        jb5 = new JButton("5");
        jb6 = new JButton("6");
        jb7 = new JButton("7");
        jb8 = new JButton("8");
        jb9 = new JButton("9");
        jb0 = new JButton("0");
        jba = new JButton("+");
        jbs = new JButton("-");
        jbm = new JButton("*");
        jbd = new JButton("/");
        jbp = new JButton(".");
        jbc = new JButton("C");

        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);
        jb5.addActionListener(this);
        jb6.addActionListener(this);
        jb7.addActionListener(this);
        jb8.addActionListener(this);
        jb9.addActionListener(this);
        jb0.addActionListener(this);
        jba.addActionListener(this);
        jbs.addActionListener(this);
        jbm.addActionListener(this);
        jbd.addActionListener(this);
        jbp.addActionListener(this);
        jbc.addActionListener(this);

        jp.setLayout(new GridLayout(4, 4, 4, 4));

        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);
        jp.add(jb4);
        jp.add(jb5);
        jp.add(jb6);
        jp.add(jb7);
        jp.add(jb8);
        jp.add(jb9);
        jp.add(jb0);
        jp.add(jba);
        jp.add(jbs);
        jp.add(jbm);
        jp.add(jbd);
        jp.add(jbp);
        jp.add(jbc);

        return jp;
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
