import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// parent class: eyeball outline
abstract class Eyeball extends JPanel {
    private BufferedImage outlineImage;
    protected String description;

    public Eyeball() {
        description = "Constructing Link: ";
        loadImage();
    }

    protected abstract void loadImage();

    protected void setOutlineImage(String filePath) {
        try {
            outlineImage = ImageIO.read(new File(filePath));
            description += "Base outline. ";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected BufferedImage getOutlineImage() {
        return outlineImage;
    }

    public String getDescription() {
        return description;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (outlineImage != null) {
            g.drawImage(outlineImage, 0, 0, this);
        }
    }
}

// child class: adds head
class EyeballWithHead extends Eyeball {
    private BufferedImage headImage;

    public EyeballWithHead() {
        super();
    }

    @Override
    protected void loadImage() {
        setOutlineImage("outline-eye.png");
        try {
            headImage = ImageIO.read(new File("head.png"));
            description += "Added head. ";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (headImage != null) {
            g.drawImage(headImage, 0, 0, this);
        }
    }
}

// grandchild class: adds torso
class EyeballWithTorso extends EyeballWithHead {
    private BufferedImage torsoImage;

    public EyeballWithTorso() {
        super();
    }

    @Override
    protected void loadImage() {
        super.loadImage();
        try {
            torsoImage = ImageIO.read(new File("torso.png"));
            description += "Added torso. ";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (torsoImage != null) {
            g.drawImage(torsoImage, 0, 0, this);
        }
    }
}

// great-grandchild class: will add right arm
class EyeballWithRightArm extends EyeballWithTorso {
    private BufferedImage rightArmImage;

    public EyeballWithRightArm() {
        super();
    }

    @Override
    protected void loadImage() {
        super.loadImage();
        try {
            rightArmImage = ImageIO.read(new File("right_arm.png"));
            description += "Added right arm. ";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rightArmImage != null) {
            g.drawImage(rightArmImage, 0, 0, this);
        }
    }
}

// GREAT-great-grandchild class: Adds left arm (randomized)
class EyeballWithLeftArm extends EyeballWithRightArm {
    private BufferedImage leftArmImage;
    private String leftArmType;

    public EyeballWithLeftArm() {
        super();
    }

    @Override
    protected void loadImage() {
        super.loadImage();
        try {
            double random = Math.random();
            String leftArmFileName;
            if (random < 0.5) {
                leftArmFileName = "left_arm_normal.png";
                leftArmType = "Link's regular old left arm.";
            } else {
                leftArmFileName = "left_arm_rauru.png";
                leftArmType = "Link, but from TOTK! Rauru's left arm is here!";
            }
            leftArmImage = ImageIO.read(new File(leftArmFileName));
            description += "Added... " + leftArmType;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        // override to show which random output was chosen
        return description;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (leftArmImage != null) {
            g.drawImage(leftArmImage, 0, 0, this);
        }
    }
}

// main class to show GUI
public class EyeballGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Eyeball GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(350, 350);

            // for polymorphism and random output
            EyeballWithLeftArm eyeball1 = new EyeballWithLeftArm();
            //EyeballWithLeftArm eyeball2 = new EyeballWithLeftArm();

            // Use a panel with BorderLayout to show both image and description
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(eyeball1, BorderLayout.CENTER);

            JLabel descriptionLabel = new JLabel("<html>" + eyeball1.getDescription() + "</html>");
            descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(descriptionLabel, BorderLayout.SOUTH);

            frame.add(mainPanel);
            frame.setVisible(true);

        });
    }
}
