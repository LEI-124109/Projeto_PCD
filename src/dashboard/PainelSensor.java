package dashboard;

import descricao.Area;
import events.sensor.*;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PainelSensor extends JPanel {

    private final JComboBox<String> sensorSelector;
    private final JSlider valueSlider;
    private final JLabel valueLabel;
    private final JTextArea eventLog;

    private final Consumer<SensorEvent> eventHandler;
    private Area currentArea;

    public PainelSensor(
            Area areaInicial,
            Consumer<SensorEvent> eventHandler) {

        this.currentArea = areaInicial;
        this.eventHandler = eventHandler;

        setLayout(new BorderLayout(15, 15));
        setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // Parte superior: escolha do sensor
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Tipo de sensor:"));

        sensorSelector = new JComboBox<>(new String[]{
                "Temperatura",
                "Luminosidade",
                "Humidade do ar",
                "Humidade do solo"
        });

        topPanel.add(sensorSelector);
        add(topPanel, BorderLayout.NORTH);

        // Parte central: controlo do valor
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(
                new BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        );

        valueLabel = new JLabel();
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        valueLabel.setFont(
                new Font("SansSerif", Font.BOLD, 22)
        );

        valueSlider = new JSlider();
        valueSlider.setMajorTickSpacing(10);
        valueSlider.setPaintTicks(true);

        JButton generateButton =
                new JButton("Gerar evento");

        generateButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        centerPanel.add(valueLabel);
        centerPanel.add(
                Box.createVerticalStrut(20)
        );
        centerPanel.add(valueSlider);
        centerPanel.add(
                Box.createVerticalStrut(20)
        );
        centerPanel.add(generateButton);

        add(centerPanel, BorderLayout.CENTER);

        eventLog = new JTextArea(7, 30);
        eventLog.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(eventLog);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Eventos gerados"
                )
        );

        add(scrollPane, BorderLayout.SOUTH);

        sensorSelector.addActionListener(e ->
                configureSensor()
        );

        valueSlider.addChangeListener(e ->
                updateValueLabel()
        );

        generateButton.addActionListener(e ->
                generateEvent()
        );

        configureSensor();
    }

    public void setArea(Area area) {
        currentArea = area;
    }

    private void configureSensor() {
        int selected = sensorSelector.getSelectedIndex();

        switch (selected) {
            case 0:
                valueSlider.setMinimum(-10);
                valueSlider.setMaximum(50);
                valueSlider.setValue(22);
                break;

            case 1:
                valueSlider.setMinimum(0);
                valueSlider.setMaximum(1000);
                valueSlider.setValue(400);
                break;

            case 2:
                valueSlider.setMinimum(0);
                valueSlider.setMaximum(100);
                valueSlider.setValue(50);
                break;

            case 3:
                valueSlider.setMinimum(0);
                valueSlider.setMaximum(100);
                valueSlider.setValue(35);
                break;
        }

        updateValueLabel();
    }

    private void updateValueLabel() {
        int value = valueSlider.getValue();
        int selected = sensorSelector.getSelectedIndex();

        String unit = selected == 0 ? "°C"
                : selected == 1 ? "lux"
                : "%";

        valueLabel.setText(value + " " + unit);
    }

    private void generateEvent() {
        float value = valueSlider.getValue();
        int selected = sensorSelector.getSelectedIndex();

        SensorEvent event;

        switch (selected) {
            case 0:
                event = new TemperatureSensorEvent(
                        currentArea, value
                );
                break;

            case 1:
                event = new LightSensorEvent(
                        currentArea, value
                );
                break;

            case 2:
                event = new HumiditySensorEvent(
                        currentArea, value
                );
                break;

            case 3:
                event = new GroundMoistureSensorEvent(
                        currentArea, value
                );
                break;

            default:
                return;
        }

        String description =
                event.getClass().getSimpleName()
                        + " | Área: " + currentArea
                        + " | Valor: " + valueLabel.getText();

        eventLog.append(description + "\n");

        System.out.println(description);

        if (eventHandler != null) {
            eventHandler.accept(event);
        }
    }
}
