package cyra.trongame;

import javax.swing.*;
import java.awt.*;

public class ColorChoosing {


    private static int x;
    private Color color;
    private Color[] colors = new Color[] {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};

    public ColorChoosing(int playerNumber){
        String[] options = {"Blue", "Red", "Yellow", "Green", "Cyan", "Magenta"};
        x = JOptionPane.showOptionDialog(null, "Choose color for Player " + playerNumber,
                "Color",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        color = colors[x];
    }

    public Color getColorSelected(){
        return this.color;
    }
}
