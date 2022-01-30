package frame.dialog;

import static constants.ProjectConstants.*;
import frame.*;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog implements MyContainable {
    public AboutDialog(Frame owner) {
        super(owner, "About " + PROJECT_NAME, false);
        build();
    }

    @Override
    public void createComponents() {}

    @Override
    public void addComponents() {
        JLabel projectImageLabel = new JLabel(PROJECT_ICON);
        Component spacing = Box.createHorizontalStrut(10);
        JLabel titleLabel = MyFrameTools.createJLabelWithFont(PROJECT_NAME, new Font("Consolas", Font.BOLD, 64));
        JLabel versionLabel = MyFrameTools.createJLabelWithFont("Version " + VERSION, new Font("Consolas", Font.PLAIN, 18));
        Component glue = Box.createVerticalGlue();
        JLabel programmerLabel = MyFrameTools.createJLabelWithFont(String.format("programmed by %s(%s)", PROGRAMMER, PROGRAMMER_NICKNAME), new Font("Consolas", Font.ITALIC, 12));
        JLabel dateLabel = MyFrameTools.createJLabelWithFont(String.format("(%s)", FINISH_DATE), new Font("Consolas", Font.ITALIC, 12));
        JLabel profileLabel = new JLabel(PROFILE_ICON);

        projectImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        profileLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Container c = getContentPane();
        GroupLayout layout = new GroupLayout(c);
        c.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(projectImageLabel)
                        .addComponent(spacing)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(titleLabel)
                                .addComponent(versionLabel)
                                .addComponent(glue)
                                .addComponent(programmerLabel)
                                .addComponent(dateLabel)
                                .addComponent(profileLabel))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(projectImageLabel)
                        .addComponent(spacing)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(titleLabel)
                                .addComponent(versionLabel)
                                .addComponent(glue)
                                .addComponent(programmerLabel)
                                .addComponent(dateLabel)
                                .addComponent(profileLabel))
        );

        setContentPane(MyFrameTools.addComponentWithEdgeSpacing(null, c, 20));
    }

    @Override
    public void configureSettings() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(getOwner());
    }
}
