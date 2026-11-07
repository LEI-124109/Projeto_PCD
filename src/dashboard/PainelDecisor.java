
package dashboard;

import decisor.MotorDecisao;
import events.sensor.SensorEvent;
import events.highLevelOrder.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class PainelDecisor extends JPanel {

    private final MotorDecisao motor;
    private final JTextArea historico;
    private final JLabel ultimaDecisao;
    private final Consumer<HighLevelOrderEvent> destinoOrdens;

    public PainelDecisor(Consumer<HighLevelOrderEvent> destinoOrdens) {

        this.motor = new MotorDecisao();
        this.destinoOrdens = destinoOrdens;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Decisores");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel descricao = new JLabel("Análise automática das leituras dos sensores");

        JPanel cabecalho = new JPanel();
        cabecalho.setLayout( new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        cabecalho.add(titulo);
        cabecalho.add(descricao);
        add(cabecalho, BorderLayout.NORTH);

        ultimaDecisao = new JLabel("A aguardar eventos dos sensores...");
        ultimaDecisao.setFont(new Font("SansSerif", Font.BOLD, 16));

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(ultimaDecisao, BorderLayout.NORTH);

        historico = new JTextArea();
        historico.setEditable(false);
        historico.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(historico);
        scroll.setBorder(
                BorderFactory.createTitledBorder(
                        "Histórico de decisões"
                )
        );

        centro.add(scroll, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

    }

    public void receberEvento(SensorEvent evento) {

        HighLevelOrderEvent ordem = motor.analisar(evento);

        String descricao = descreverOrdem(ordem);

        ultimaDecisao.setText(descricao);

        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        historico.append(
                "[" + hora + "] "
                        + evento.getArea()
                        + " | " + evento.getClass().getSimpleName()
                        + ": " + evento.getValue()
                        + " -> " + descricao + "\n"
        );

        historico.setCaretPosition(historico.getDocument().getLength());

        if (destinoOrdens != null) {
            destinoOrdens.accept(ordem);
        }

    }

    private String descreverOrdem(HighLevelOrderEvent ordem) {

        if (ordem instanceof AreaLightingOrderEvent) {

            AreaLightingOrderEvent a = (AreaLightingOrderEvent) ordem;

            return a.isLigado()
                    ? "Ligar iluminação"
                    : "Desligar iluminação";

        }

        if (ordem instanceof AreaVentilationOrderEvent) {

            AreaVentilationOrderEvent a = (AreaVentilationOrderEvent) ordem;

            return a.isLigado()
                    ? "Ligar ventilação"
                    : "Desligar ventilação";

        }

        if (ordem instanceof AreaIrrigationOrderEvent) {

            AreaIrrigationOrderEvent a = (AreaIrrigationOrderEvent) ordem;

            return a.isLigado()
                    ? "Ligar rega"
                    : "Desligar rega";

        }

        if (ordem instanceof AreaTemperatureOrderEvent) {

            AreaTemperatureOrderEvent a = (AreaTemperatureOrderEvent) ordem;

            if (a.getModo() == descricao.ModoClima.Desligado) {
                return "Desligar climatização";
            }

            return a.getModo()
                    + " até " + a.getTemperaturaAlvo() + " °C";

        }

        return "Ordem desconhecida";

    }
}