package dashboard;

import descricao.Area;
import descricao.ModoClima;
import events.highLevelOrder.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PainelAtuador extends JPanel {

    private static class EstadoDivisao {

        boolean luzLigada = false;
        boolean ventilacaoLigada = false;
        boolean regaLigada = false;

        ModoClima modoAC = ModoClima.Desligado;

        float temperaturaAlvo = -1;

    }

    private final Map<Area, EstadoDivisao> estados = new HashMap<>();

    private Area areaAtual;

    private final JLabel estadoLuz;
    private final JLabel estadoAC;
    private final JLabel estadoVentilacao;
    private final JLabel estadoRega;
    private final JTextArea historico;

    public PainelAtuador(Area areaInicial) {

        this.areaAtual = areaInicial;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Atuadores");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel equipamentos = new JPanel(new GridLayout(2, 2, 15, 15));

        estadoLuz = new JLabel();
        estadoAC = new JLabel();
        estadoVentilacao = new JLabel();
        estadoRega = new JLabel();

        equipamentos.add(criarCartao("Iluminação", estadoLuz));
        equipamentos.add(criarCartao("Climatização", estadoAC));
        equipamentos.add(criarCartao("Ventilação", estadoVentilacao));
        equipamentos.add(criarCartao("Rega", estadoRega));
        add(equipamentos, BorderLayout.CENTER);

        historico = new JTextArea(7, 30);
        historico.setEditable(false);

        JScrollPane scroll = new JScrollPane(historico);
        scroll.setBorder(BorderFactory.createTitledBorder("Histórico de ações"));
        add(scroll, BorderLayout.SOUTH);
        atualizarInterface();

    }

    private JPanel criarCartao(String titulo, JLabel estado) {

        JPanel cartao = new JPanel();
        cartao.setLayout(new BoxLayout(cartao, BoxLayout.Y_AXIS));

        cartao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(20, 10, 20, 10)
                )
        );

        JLabel nome = new JLabel(titulo);
        nome.setFont(new Font("SansSerif", Font.BOLD, 18));
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);

        estado.setFont(new Font("SansSerif", Font.PLAIN, 16));

        estado.setAlignmentX(Component.CENTER_ALIGNMENT);

        cartao.add(Box.createVerticalGlue());
        cartao.add(nome);
        cartao.add(Box.createVerticalStrut(15));
        cartao.add(estado);
        cartao.add(Box.createVerticalGlue());

        return cartao;

    }

    private EstadoDivisao obterEstado(Area area) {
        return estados.computeIfAbsent(area,a -> new EstadoDivisao());
    }

    public void setArea(Area area) {

        if (area == null) {
            return;
        }

        areaAtual = area;
        atualizarInterface();

    }

    public void receberOrdem(HighLevelOrderEvent ordem) {

        if (ordem == null) {
            return;
        }

        EstadoDivisao estado = obterEstado(ordem.getArea());
        String descricao;

        if (ordem instanceof AreaLightingOrderEvent) {

            AreaLightingOrderEvent a = (AreaLightingOrderEvent) ordem;

            estado.luzLigada = a.isLigado();

            descricao = a.isLigado()
                    ? "Lâmpada ligada"
                    : "Lâmpada desligada";

        } else if (ordem instanceof AreaVentilationOrderEvent) {

            AreaVentilationOrderEvent a = (AreaVentilationOrderEvent) ordem;

            estado.ventilacaoLigada = a.isLigado();

            descricao = a.isLigado()
                    ? "Ventilação ligada"
                    : "Ventilação desligada";

        } else if (ordem instanceof AreaIrrigationOrderEvent) {

            AreaIrrigationOrderEvent a = (AreaIrrigationOrderEvent) ordem;

            estado.regaLigada = a.isLigado();

            descricao = a.isLigado()
                    ? "Rega ligada"
                    : "Rega desligada";

        } else if (ordem instanceof AreaTemperatureOrderEvent) {

            AreaTemperatureOrderEvent a = (AreaTemperatureOrderEvent) ordem;

            estado.modoAC = a.getModo();
            estado.temperaturaAlvo = a.getTemperaturaAlvo();

            if (a.getModo() == ModoClima.Desligado) {

                descricao = "Climatização desligada";

            } else {
                descricao = "Climatização: "
                        + a.getModo()
                        + " até "
                        + a.getTemperaturaAlvo()
                        + " °C";
            }

        } else {
            return;
        }

        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        historico.append(
                ordem.getArea()
                        + " | " + descricao
                        + " - " + hora + "\n"
        );

        historico.setCaretPosition(
                historico.getDocument().getLength()
        );

        if (ordem.getArea() == areaAtual) {
            atualizarInterface();
        }

    }

    private void atualizarInterface() {

        EstadoDivisao estado = obterEstado(areaAtual);

        atualizarLabel(
                estadoLuz,
                estado.luzLigada ? "Ligada" : "Desligada",
                estado.luzLigada
        );

        atualizarLabel(
                estadoVentilacao,
                estado.ventilacaoLigada
                        ? "Ligada" : "Desligada",
                estado.ventilacaoLigada
        );

        atualizarLabel(
                estadoRega,
                estado.regaLigada ? "Ligada" : "Desligada",
                estado.regaLigada
        );

        if (estado.modoAC == ModoClima.Desligado) {

            atualizarLabel(
                    estadoAC,
                    "Desligada",
                    false
            );

        } else {

            atualizarLabel(
                    estadoAC,
                    estado.modoAC
                            + " - " + estado.temperaturaAlvo
                            + " °C",
                    true
            );

        }
    }

    private void atualizarLabel(JLabel label, String texto, boolean ativo) {

        label.setText(texto);

        label.setForeground(
                ativo
                        ? new Color(0, 130, 70)
                        : Color.GRAY
        );

    }
}