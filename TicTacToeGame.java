import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGame extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean xTurn = true;
    private JLabel statusLabel;

    private String playerX;
    private String playerO;

    public TicTacToeGame() {
        // Get player names
        playerX = JOptionPane.showInputDialog(this, "Enter name for Player X:");
        if (playerX == null || playerX.trim().isEmpty()) playerX = "Player X";

        playerO = JOptionPane.showInputDialog(this, "Enter name for Player O:");
        if (playerO == null || playerO.trim().isEmpty()) playerO = "Player O";

        setTitle("Tic-Tac-Toe | 2 Player Game");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Game title
        JLabel title = new JLabel("Tic-Tac-Toe", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(50, 50, 150));
        add(title, BorderLayout.NORTH);

        // Grid Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        Font btnFont = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(btnFont);
                buttons[i][j].setFocusable(false);
                buttons[i][j].setBackground(new Color(240, 240, 240));
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }

        add(panel, BorderLayout.CENTER);

        // Status and Reset
        JPanel bottomPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel(playerX + "'s Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton resetBtn = new JButton("Reset Game");
        resetBtn.setFont(new Font("Arial", Font.BOLD, 18));
        resetBtn.setBackground(new Color(180, 220, 250));
        resetBtn.setFocusPainted(false);
        resetBtn.addActionListener(e -> resetGame());

        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(resetBtn, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (!btn.getText().equals("")) return;

        btn.setText(xTurn ? "X" : "O");
        btn.setForeground(xTurn ? Color.BLUE : Color.RED);
        if (checkWin()) {
            statusLabel.setText((xTurn ? playerX : playerO) + " Wins!");
            disableButtons();
        } else if (isBoardFull()) {
            statusLabel.setText("It's a Draw!");
        } else {
            xTurn = !xTurn;
            statusLabel.setText((xTurn ? playerX : playerO) + "'s Turn");
        }
    }

    private boolean checkWin() {
        String symbol = xTurn ? "X" : "O";

        for (int i = 0; i < 3; i++) {
            if (
                buttons[i][0].getText().equals(symbol) &&
                buttons[i][1].getText().equals(symbol) &&
                buttons[i][2].getText().equals(symbol)
            ) return true;

            if (
                buttons[0][i].getText().equals(symbol) &&
                buttons[1][i].getText().equals(symbol) &&
                buttons[2][i].getText().equals(symbol)
            ) return true;
        }

        if (
            buttons[0][0].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][2].getText().equals(symbol)
        ) return true;

        if (
            buttons[0][2].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][0].getText().equals(symbol)
        ) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                if (btn.getText().equals("")) return false;
        return true;
    }

    private void disableButtons() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                btn.setEnabled(false);
    }

    private void resetGame() {
        for (JButton[] row : buttons)
            for (JButton btn : row) {
                btn.setText("");
                btn.setEnabled(true);
                btn.setBackground(new Color(240, 240, 240));
            }
        xTurn = true;
        statusLabel.setText(playerX + "'s Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGame());
    }
}
