package dashboard;

import descricao.Area;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private JComboBox<Area> areaSelector;
    private JTabbedPane tabs;
    private JLabel sectionLabel;
    private PainelSensor painelSensores;
    private PainelDecisor painelDecisores;
    private PainelAtuador painelAtuadores;


    public MainWindow() {

        setTitle("IscoT - Painel de Controlo");
        setSize(800, 550);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        createHeader();
        createTabs();
        updateSelectedArea();

    }

    private void createHeader() {

        JPanel header = new JPanel(new BorderLayout(15, 0));
        header.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        JLabel title = new JLabel("IscoT - Casa Inteligente");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        areaSelector = new JComboBox<>(Area.values());
        areaSelector.setSelectedItem(Area.Sala);
        areaSelector.addActionListener(e -> updateSelectedArea());

        JPanel areaPanel = new JPanel(new FlowLayout());
        areaPanel.add(new JLabel("Divisão:"));
        areaPanel.add(areaSelector);

        header.add(title, BorderLayout.WEST);
        header.add(areaPanel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

    }

    private void createTabs() {

        tabs = new JTabbedPane();

        painelAtuadores = new PainelAtuador(Area.Sala);

        painelDecisores = new PainelDecisor(
                ordem -> painelAtuadores.receberOrdem(ordem)
        );

        painelSensores = new PainelSensor(
                Area.Sala,evento -> painelDecisores.receberEvento(evento)
        );

        tabs.addTab("Sensores", painelSensores);
        tabs.addTab("Decisores", painelDecisores);
        tabs.addTab("Atuadores", painelAtuadores);

        add(tabs, BorderLayout.CENTER);

    }

    private JPanel createPlaceholder(String section) {

        JPanel panel = new JPanel(new GridBagLayout());

        JLabel label = new JLabel(
                "Painel de " + section + " - em desenvolvimento"
        );

        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(label);

        return panel;

    }

    private void updateSelectedArea() {

        Area areaSelecionada = (Area) areaSelector.getSelectedItem();

        if (areaSelecionada != null) {

            setTitle("IscoT - " + areaSelecionada);

            if (painelSensores != null) {
                painelSensores.setArea(areaSelecionada);
            }

            if (painelAtuadores != null) {
                painelAtuadores.setArea(areaSelecionada);
            }

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });

    }
}

